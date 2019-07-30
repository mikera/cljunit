(ns mikera.cljunit.test-main
  (:use clojure.test)
  (:use mikera.cljunit.core)
  (:require [clojure.tools.namespace :as ctns])
  (:require [clojure.tools.namespace.find :as ctnf])
  (:require [clojure.java.classpath :as jcp]))

(deftest test-core
  (testing "Trivial test"
    (is (= 1 1))))

(deftest test-find-namespaces
  (testing "Namespace lookup using tools.namespace"
    (let [nms (ctnf/find-namespaces (jcp/classpath))
          nmset (into #{} (map str nms))]
      (is (contains? nmset "mikera.cljunit.core"))
    )))

(deftest test-namespaces
  (testing "Getting all test namespace names"
    (let [nms (get-test-namespace-names)
          nmset (into #{} nms)]
      ;; (println "NMS=" nms) 
      (is (contains? nmset "mikera.cljunit.core"))
      (is (contains? nmset "clojure.core"))
      )))

(deftest failing-assertion
;;  (is (= 1 2))
  ) 

