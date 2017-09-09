(ns parse-query-test
  (:require [clojure.test :refer :all]
            [query :refer :all]))


(deftest parse-query-valid-query-test
  (testing "should return the parsed query"
    (is (= (parse-query "padre(juan,pepe)")
           ["padre" ["juan" "pepe"]])))
  (testing "should return the parsed query"
    (is (= (parse-query "varon(juan")
           ["varon" ["juan"]])))  		   
)

(deftest parse-query-invalid-query-test
  (testing "should return nil"
    (is (= (parse-query "")
           nil)))
  (testing "should return the parsed query"
    (is (= (parse-query "varon")
           nil)))  		   
)
