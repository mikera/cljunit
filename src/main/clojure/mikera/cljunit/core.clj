(ns mikera.cljunit.core
  (:import [org.junit.runner.notification RunNotifier Failure])
  (:import org.junit.runner.Description)
  (:import mikera.cljunit.TestRunner)
  (:require [bultitude.core :as b])
  (:use clojure.test))

(set! *warn-on-reflection* true)

(deftest temp-test
  (testing "Core"
    (is (= 1 1))))

(deftest temp-test2
  (testing "Core"
    (is (= 1 1))))

(defn get-test-vars [ns]
  (filter
    (fn [v] (:test (meta v)))
    (vals (ns-interns ns))))

(defn get-all-test-vars []
  (doseq [nms (b/namespaces-on-classpath)] 
    (try 
      (require nms)
      (catch Exception x
        nil)))
  (mapcat get-test-vars (all-ns)))

(defn run-test [^RunNotifier notifier test-var]
  (binding [clojure.test/*testing-vars* (conj clojure.test/*testing-vars* test-var)]
	  (let [t (:test (meta test-var))
          suite-desc mikera.cljunit.TestRunner/DESCRIPTION
	        desc (Description/createSuiteDescription (str test-var) nil)
         ]
	    (if (nil? t) (throw (Error. (str "No test available for: " test-var))))
      (.addChild suite-desc desc)
      (.fireTestStarted notifier desc)    
      (try 
	      (t)
	      (catch Throwable e
          (.fireTestFailure notifier
            (org.junit.runner.notification.Failure. desc e))))
      (.fireTestFinished notifier desc)
      )))

(defn run-junit-tests [^RunNotifier notifier] 
  (let [tests (get-all-test-vars)]
    (doseq [test-var tests]
      (run-test notifier test-var))))