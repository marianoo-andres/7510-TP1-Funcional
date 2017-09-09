(ns to-fact-vector-test
  (:require [clojure.test :refer :all]
            [database :refer :all]))

(deftest to-fact-vector-good-string-test
  (testing "should return vector of fact"
    (is (= (to-fact-vector "padre(maria,juan)")
           ["padre" ["maria" "juan"]])))
)

(deftest to-fact-vector-bad-string-test
  (testing "should return nil"
    (is (= (to-fact-vector "padre")
           nil)))
)
