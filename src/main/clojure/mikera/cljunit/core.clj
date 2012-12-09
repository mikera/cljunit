(ns mikera.cljunit.core
  (:import [org.junit.runner.notification RunNotifier Failure])
  (:import org.junit.runner.Description)
  (:require [bultitude.core :as b])
  (:use clojure.test))

(set! *warn-on-reflection* true)

;; intended for binding to capture failures
(def ^:dynamic *reports* nil)

(defn assertion-message [m]
  (str "Assertion failed: {:expected " (:expected m) " :actual " (:actual m) "}"))

(defn setup []
  (alter-var-root #'clojure.test/report 
                  (fn [old]
                    (fn [m]
                      ;;(println m)              
                      (swap! *reports* conj m)))))

(setup)

(defn invoke-test [v]
  (when-let [t v]   ;; (:test (meta v))
    (binding [*reports* (atom [])]
      (t)
      ;; (println @*reports*)              
      (doseq [m @*reports*]
        (let [type (:type m)]
          ;;(println m) 
          (cond 
            (= :pass type) m
            (= :fail type) (throw (junit.framework.AssertionFailedError. (assertion-message m)))
            (= :error type) (throw (:actual m))
            :else "OK"))))))
                      
;; (deftest failing-test (is (= 2 3)))
(deftest test-in-core
  (testing "In Core"
    (is (= 1 1))))

(defn ns-for-name [name]
  (namespace (symbol name)))

(defn get-test-vars [ns]
  (filter
    (fn [v] (:test (meta v)))
    (vals (ns-interns ns))))

(defn get-test-var-names [ns-name]
  (try
    (require (symbol ns-name))
	  (vec (map
          #(str (first %))
          (filter
            (fn [[k v]] (:test (meta v)))
            (ns-interns (symbol ns-name)))))
   (catch Throwable t
     (binding [*out* *err*]
       (println "Error attempting to get var names!")
       (.printStackTrace t))
     [])))

(defn get-test-namespace-names []
  (vec
    (filter (complement nil?)
    (for [nms (b/namespaces-on-classpath)] 
      (try 
        (require nms)
        (str nms)
        (catch Throwable x
          nil))))))

(defn get-all-test-vars []
  (doseq [nms (b/namespaces-on-classpath)] 
    (try 
      (require nms)
      (catch Throwable x
        nil)))
  (mapcat get-test-vars (all-ns)))

(defn test-results [test-vars]
  (vec (map
    (fn [var]
      (let [t (:test (meta test-var))]
        (try 
          (t)
          (catch Throwable e
            e)))
    test-vars))))