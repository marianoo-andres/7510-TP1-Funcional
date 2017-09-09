(ns add-rule-test
  (:require [clojure.test :refer :all]
            [database :refer :all]))


(deftest add-rule-rule-doesnt-exists-test
  (testing "should return a hash-map with the rule added"
    (is (= (add-rule {} ["hijo" [["varon" [1]] ["padre" [1 0]]]])
           {:hijo [["varon" [1]] ["padre" [1 0]]]})))  
)

(deftest add-rule-rule-exists-test
  (testing "should return nil)"
    (is (= (add-rule {:hijo [["varon" [1]] ["padre" [1 0]]]} ["hijo" [["varon" [1]] ["padre" [1 0]]]])
           nil)))  
)

(deftest add-rule-rule-hash-map-empty-test
  (testing "should return a hash-map with the rule added"
    (is (= (add-rule {} ["hijo" [["varon" [1]] ["padre" [1 0]]]])
           {:hijo [["varon" [1]] ["padre" [1 0]]]})))  
)

(deftest add-rule-rule-hash-map-niltest
  (testing "should return a hash-map with the rule added"
    (is (= (add-rule nil ["hijo" [["varon" [1]] ["padre" [1 0]]]])
           {:hijo [["varon" [1]] ["padre" [1 0]]]})))  
)