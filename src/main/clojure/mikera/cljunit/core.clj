(ns mikera.cljunit.core
  (:import [org.junit.runner.notification RunNotifier Failure])
  (:import org.junit.runner.Description)
  ;; (:require [bultitude.core :as b])
  (:require [clojure.tools.namespace :as ctns]) 
  (:require [clojure.tools.namespace.find :as ctnf])
  (:require [clojure.java.classpath :as jcp])
  (:use clojure.test))

(set! *warn-on-reflection* true)

;; intended for binding to capture failures
(def ^:dynamic *reports* nil)

(defn assertion-message [m]
  (str "Assertion failed: {:expected " (:expected m) 
                           " :actual " (:actual m) 
                           (when (:message m) (str " :message " (:message m))) "}"
       " <" (:file m) ":" (:line m) ">"))

(defn truncate-stacktrace [off-top]
  (let [st (.getStackTrace (Thread/currentThread))
        stlen (alength st)
        st (java.util.Arrays/copyOfRange st (int off-top) stlen)]
    st))

(def report-fn
  (fn [m]
     ;;(println m)              
     (let [m (if (= :fail (:type m))
               (assoc m :stacktrace (truncate-stacktrace 4))
               m)] 
       (swap! *reports* conj m))))

(defn invoke-test [v]
  (when-let [t v]   ;; (:test (meta v))
    (binding [clojure.test/report report-fn
              *reports* (atom [])]
      (eval `(~t))
      ;; (println @*reports*)              
      (doseq [m @*reports*]
        (let [type (:type m)]
          (cond 
            (= :pass type) m
            (= :fail type) 
              (let [ex (junit.framework.AssertionFailedError. (assertion-message m))]
                ;; (println m) 
                (.setStackTrace ex (:stacktrace m)) 
                (throw ex))
            (= :error type) (throw (:actual m))
            :else "OK"))))))
                      
;; (deftest failing-test (is (= 2 3)))
(deftest test-in-core
  (testing "In Core"
    (is (= 1 1))))

(defn ns-for-name [name]
  (namespace (symbol name)))

(defn get-test-vars 
  "Gets the vars in a namespace which represent tests, as defined by :test metadata"
  ([ns]
    (filter
      (fn [v] (:test (meta v)))
      (vals (ns-interns ns)))))

(defn get-test-var-names 
  "Gets the names of all vars for a given namespace name"
  ([ns-name]
  (require (symbol ns-name))
  (vec (map
         #(str (first %))
         (filter
           (fn [[k v]] (:test (meta v)))
           (ns-interns (symbol ns-name)))))))

;; we need to exclude clojure.parallel, as loading it causes an error if ForkJoin framework not present
(def DEFAULT-EXCLUDES
  ["clojure.parallel"])

(defn get-namespace-symbols 
  "Gets the symbols defining namespaces on the classpath, subject to options map.

   Options map may include:
     :prefix   - only include namespaces which start with the given prefix
     :excludes - exclude the list of specified namespaces (exact string names or symbols)"
  ([options]
    (let [prefix ^String (or (:prefix options) "")
          exclude-list (or (:excludes options) DEFAULT-EXCLUDES)
          exclude-set (into #{} (map name exclude-list))
          nms (ctnf/find-namespaces (jcp/classpath))
          nms (filter (fn [nsym] 
                              (let [nsname ^String (name nsym)]
                                (and
                                  (not (exclude-set nsname))
                                  (.startsWith nsname prefix)))) 
                            nms)]
      nms)))

(defn get-test-namespace-names 
  "Return namespace names as strings"
  ([]
    (get-test-namespace-names nil))
  ([options]
	  (vec
	    (filter (complement nil?)
		    (for [nms (get-namespace-symbols options)] 
		      (try 
		        (require nms) ;; might fail during namespace loading
		        (str nms)
		        (catch Throwable x
		          (throw (RuntimeException. (str "Failed to load namespace:" nms) x)))))))))


(defn test-results [test-vars]
  (vec (map
    (fn [var]
      (let [t (:test (meta test-var))]
        (try 
          (t)
          (catch Throwable e
            e)))
    test-vars))))
