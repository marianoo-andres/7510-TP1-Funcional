(ns to-rule-parameters-with-numbers-test
  (:require [clojure.test :refer :all]
            [database :refer :all]))


(deftest to-rule-parameters-with-numbers-test
  (testing "should return X mapped to 0, Y mapped to 1"
    (is (= (to-rule-parameters-with-numbers [["X" "Y"][["varon" ["Y"]] ["padre" ["Y" "X"]]]])
           [["varon" [1]] ["padre" [1 0]]])))  
)