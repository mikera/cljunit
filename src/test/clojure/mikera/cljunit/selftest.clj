(ns mikera.cljunit.selftest
  (:use clojure.test))

(deftest test1
  (is (= 1 1)))

(deftest test2
  (is (= 2 2)))