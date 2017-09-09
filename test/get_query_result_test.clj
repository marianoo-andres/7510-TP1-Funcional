(ns get-query-result-test
  (:require [clojure.test :refer :all]
            [query :refer :all]))


(def database [{:varon [["juan"] ["pepe"]] :padre [["juan" "pepe"]]} {:hijo [["varon" [0]] ["padre" [1 0]]]}])

(deftest get-query-result-valid-query-true-test
  (testing "should return true"
    (is (= (get-query-result database "varon(juan)")
          true)))
	(testing "should return true"
    (is (= (get-query-result database "varon(pepe)")
          true)))
	(testing "should return true"
    (is (= (get-query-result database "padre(juan,pepe)")
          true)))
	(testing "should return true"
    (is (= (get-query-result database "hijo(pepe,juan)")
          true)))
)

(deftest get-query-result-valid-query-false-test
  (testing "should return false"
    (is (= (get-query-result database "varon(maria)")
          false)))
	(testing "should return false"
    (is (= (get-query-result database "varon(jose)")
          false)))
	(testing "should return false"
    (is (= (get-query-result database "padre(pepe,juan)")
          false)))
	(testing "should return false"
    (is (= (get-query-result database "hijo(juan,pepe)")
          false)))
)

(deftest get-query-result-invalid-query-test
  (testing "should return nil"
    (is (= (get-query-result database "varon")
          nil)))
	(testing "should return false"
    (is (= (get-query-result database "")
          nil)))
	(testing "should return false"
    (is (= (get-query-result database "padre")
          nil)))
	(testing "should return false"
    (is (= (get-query-result database "hijo"))
		nil))
)
