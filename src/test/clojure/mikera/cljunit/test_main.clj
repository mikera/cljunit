(ns mikera.cljunit.test-main
  (:use clojure.test)
  (:use mikera.cljunit.core))

(deftest test-core
  (testing "Core"
    (is (= 1 1))))

