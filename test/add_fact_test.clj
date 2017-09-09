(ns add-fact-test
  (:require [clojure.test :refer :all]
            [database :refer :all]))


(deftest add-fact-key-already-exists-test
  (testing "should return a hash-map with the fact padre(juan, maria) appended to value of fact-keyword :padre"
    (is (= (add-fact {:varon [["juan"]] :padre [["juan" "pedro"]]} ["padre" ["juan" "maria"]])
           {:varon [["juan"]] :padre [["juan" "pedro"] ["juan" "maria"]]})))
		   
  (testing "should return a hash-map with the fact varon(pedro) appended to value of fact-keyword :varon"
    (is (= (add-fact {:varon [["juan"]] :padre [["juan" "pedro"]]} ["varon" ["pedro"]])
           {:varon [["juan"] ["pedro"]] :padre [["juan" "pedro"]]})))
)

(deftest add-fact-key-doesnt-exists-test
  (testing "should return a hash-map with the fact padre(juan, maria) appended to value of fact-keyword :padre"
    (is (= (add-fact {:varon [["juan"]]} ["padre" ["juan" "maria"]])
           {:varon [["juan"]] :padre [["juan" "maria"]]})))
		   
  (testing "should return a hash-map with the fact varon(pedro) appended to value of fact-keyword :varon"
    (is (= (add-fact {:padre [["juan" "pedro"]]} ["varon" ["pedro"]])
           {:varon [["pedro"]] :padre [["juan" "pedro"]]})))
)

(deftest add-fact-hash-map-nil-test
  (testing "should return a hash-map with the fact varon(pedro) appended to value of fact-keyword :varon"
    (is (= (add-fact nil ["varon" ["pedro"]])
           {:varon [["pedro"]]})))
)

(deftest add-fact-hash-map-empty-test
  (testing "should return a hash-map with the fact varon(pedro) appended to value of fact-keyword :varon"
    (is (= (add-fact {} ["varon" ["pedro"]])
           {:varon [["pedro"]]})))
)
 
