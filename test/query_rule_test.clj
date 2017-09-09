(ns query-rule-test
  (:require [clojure.test :refer :all]
            [query :refer :all]))


(deftest query-rule-rule-doesnt-exists-test
  (testing "should return false"
    (is (= (query-rule {:hijo [["varon" [1]] ["padre" [1 0]]]} {:varon [["juan"] ["pepe"]] :padre [["pepe" "juan"]]} ["una-regla" ["var1" "var2"]])
          false)))  
)

(deftest query-rule-rule-doesnt-exists-empty-rule-hash-map-test
  (testing "should return false"
    (is (= (query-rule {} {:varon [["juan"] ["pepe"]] :padre [["pepe" "juan"]]} ["una-regla" ["var1" "var2"]])
          false)))  
)

(deftest query-rule-rule-doesnt-exists-nil-rule-hash-map-test
  (testing "should return false"
    (is (= (query-rule nil {:varon [["juan"] ["pepe"]] :padre [["pepe" "juan"]]} ["una-regla" ["var1" "var2"]])
          false)))  
)

(deftest query-rule-rule-exists-rule-is-false-test
  (testing "should return false as facts varon(maria) and padre(maria pedro) are false"
    (is (= (query-rule {:hijo [["varon" [1]] ["padre" [1 0]]]} {:varon [["juan"] ["pepe"]] :padre [["pepe" "juan"]]} ["hijo" ["pedro" "maria"]])
          false)))
  (testing "should return false as fact varon(juan) is true but padre(juan maria) is false"
    (is (= (query-rule {:hijo [["varon" [1]] ["padre" [1 0]]]} {:varon [["juan"] ["pepe"]] :padre [["pepe" "juan"]]} ["hijo" ["maria" "juan"]])
          false)))
  (testing "should return false as fact padre(maria juan) is true but varon(maria) is false"
    (is (= (query-rule {:hijo [["varon" [1]] ["padre" [1 0]]]} {:varon [["juan"] ["pepe"]] :padre [["maria" "juan"]]} ["hijo" ["juan" "maria"]])
          false))) 
)

(deftest query-rule-rule-exists-rule-is-true-test
  (testing "should return true as facts varon(pepe) and padre(pepe juan) are true"
    (is (= (query-rule {:hijo [["varon" [1]] ["padre" [1 0]]]} {:varon [["juan"] ["pepe"]] :padre [["pepe" "juan"]]} ["hijo" ["juan" "pepe"]])
          true))) 
)