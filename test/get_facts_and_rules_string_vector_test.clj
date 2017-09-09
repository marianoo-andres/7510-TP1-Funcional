(ns get-facts-and-rules-string-vector-test
  (:require [clojure.test :refer :all]
            [database :refer :all]))

(def database-string "
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
")

(deftest get-facts-and-rules-string-vector-test
  (testing "should return vector of facts string vector and rule string vector"
    (is (= (get-facts-and-rules-string-vector database-string)
           [["varon(juan)" "varon(pepe)" "varon(hector)" "varon(roberto)" "varon(alejandro)" "mujer(maria)" "mujer(cecilia)" "padre(juan,pepe)" "padre(juan,pepa)" "padre(hector,maria)" "padre(roberto,alejandro)" "padre(roberto,cecilia)"] ["hijo(X,Y):-varon(X),padre(Y,X)" "hija(X,Y):-mujer(X),padre(Y,X)"]])))
)
