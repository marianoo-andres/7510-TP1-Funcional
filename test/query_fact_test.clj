(ns query-fact-test
  (:require [clojure.test :refer :all]
            [query :refer :all]))


(deftest query-fact-fact-key-doesnt-exists-test
  (testing "should return false as key padre doesnt exists"
    (is (= (query-fact {:varon [["juan"]]} ["padre" ["juan" "maria"]])
           false)))
		   
  (testing "should return false as key varon doesnt exists"
    (is (= (query-fact {:padre [["juan" "pedro"]]} ["varon" ["pedro"]])
           false)))
)
			
(deftest query-fact-fact-value-doesnt-exists-test
  (testing "should return false as padre(juan,maria) doesnt exists"
    (is (= (query-fact {:varon [["juan"]] :padre [["juan" "pedro"]]} ["padre" ["juan" "maria"]])
           false)))
		   
  (testing "should return false as varon(pedro) doesnt exists"
    (is (= (query-fact {:varon [["juan"]] :padre [["juan" "pedro"]]} ["varon" ["pedro"]])
           false)))
)

(deftest query-fact-fact-value-does-exists-test
  (testing "should return true as padre(juan,pedro) exists"
    (is (= (query-fact {:varon [["juan"]] :padre [["juan" "pedro"]]} ["padre" ["juan" "pedro"]])
           true)))
		   
  (testing "should return true as varon(juan) exists"
    (is (= (query-fact {:varon [["juan"]] :padre [["juan" "pedro"]]} ["varon" ["juan"]])
           true)))
)