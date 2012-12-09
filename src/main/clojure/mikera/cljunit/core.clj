(ns mikera.cljunit.core
  (:import [org.junit.runner.notification RunNotifier Failure])
  (:import org.junit.runner.Description)
  (:require [bultitude.core :as b])
  (:use clojure.test))

(set! *warn-on-reflection* true)


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
  (require (symbol ns-name))
  (map
      #(str (first %))
      (filter
        (fn [[k v]] (:test (meta v)))
        (ns-interns (symbol ns-name)))))

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