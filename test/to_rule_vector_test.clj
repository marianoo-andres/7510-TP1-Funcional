(ns to-rule-vector-test
  (:require [clojure.test :refer :all]
			[database :refer :all]))

(deftest to-rule-vector-good-string-test
  (testing "should return vector of rule"
    (is (= (to-rule-vector "hijo(X,Y):-varon(X),padre(Y,X)")
           ["hijo" ["X" "Y"] [["varon" ["X"]] ["padre" ["Y" "X"]]]])))
)

(deftest to-fact-vector-bad-string-test
  (testing "should return nil"
    (is (= (to-rule-vector "hijo(X,Y)varon(Y),padre(Y,X)")
           nil)))
  (testing "should return nil"
    (is (= (to-rule-vector "hijo(X,Y):-varon(Y),padre")
           nil)))
)
