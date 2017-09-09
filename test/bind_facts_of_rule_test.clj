(ns bind-facts-of-rule-test
  (:require [clojure.test :refer :all]
            [query :refer :all]))


(deftest bind-facts-of-rule-test
  (testing "should return 'juan' mapped to 0 'pepe' mapped to 1"
    (is (= (bind-facts-of-rule [["varon" [1]] ["padre" [1 0]]] ["juan" "pepe"])
           [["varon" ["pepe"]] ["padre" ["pepe" "juan"]]])))  
)