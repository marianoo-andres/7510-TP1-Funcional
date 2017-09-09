(ns build-database-test
  (:require [clojure.test :refer :all]
            [database :refer :all]))

(def good-database "
	varon(juan).
	varon(pepe).
	varon(hector).
	varon(roberto).
	varon(alejandro).
	mujer(maria).
	mujer(cecilia).
	padre(juan, pepe).
	padre(juan, pepa).
	padre(hector, maria).
	padre(roberto, alejandro).
	padre(roberto, cecilia).
	hijo(X, Y) :- varon(X), padre(Y, X).
	hija(X, Y) :- mujer(X), padre(Y, X).
"
)

(def bad-database "
	varon(juan).
	varon
"
)

(def good-result [{:varon [["juan"] ["pepe"] ["hector"] ["roberto"] ["alejandro"]], :mujer [["maria"] ["cecilia"]], :padre [["juan" "pepe"] ["juan" "pepa"] ["hector" "maria"] ["roberto" "alejandro"] ["roberto" "cecilia"]]} {:hijo [["varon" [0]] ["padre" [1 0]]], :hija [["mujer" [0]] ["padre" [1 0]]]}])

(deftest build-database-good-database-test
  (testing "should return the database"
    (is (= (build-database good-database)
           good-result)))
)

(deftest build-database-bad-database-test
  (testing "should return nil"
    (is (= (build-database bad-database)
           nil)))
)
