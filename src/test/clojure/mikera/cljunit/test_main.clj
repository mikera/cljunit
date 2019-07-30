(ns mikera.cljunit.test-main
  (:use clojure.test)
  (:use mikera.cljunit.core)
  (:require [clojure.tools.namespace :as ctns])
  (:require [clojure.tools.namespace.find :as ctnf])
  (:require [clojure.java.classpath :as jcp]))
;
(deftest test-core
  (testing "Core"
    (is (= 1 1))))

(deftest test-find-namespaces
  (testing "Core"
    (let [nms (ctnf/find-namespaces (jcp/classpath))
          nmset (into #{} (map str nms))]
      ; (println "NMS=" nms)
      (is (nmset "mikera.cljunit.core"))
    )))

(deftest test-namespaces
  (testing "Core"
    (let [nms (get-test-namespace-names)
          nmset (into #{} nms)]
      ;; (println nms)
      ;;(is (nmset "mikera.cljunit.core"))
      )))

(deftest failing-assertion
;;  (is (= 1 2))
  ) 

