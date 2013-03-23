;; The exercises from Chapter 1
(ns org.dandelion.fp-for-oo
  (:use clojure.test))

;; Exerise 3 - Implement add-squares

(defn add-squares [& values]
  (apply + (map * values values)))

(deftest add-squares-test
  (is (= 1 (add-squares 1)))
  (is (= 4 (add-squares 2)))
  (is (= 5 (add-squares 1 2)))
  (is (= 38 (add-squares 2 3 5))))

;; Exerise 4 - Implement factorial with 'apply'

(defn fac [n]
  (apply * (range 1 (inc n))))

(deftest fac-test
  (is (= 1 (fac 1)))
  (is (= 2 (fac 2)))
  (is (= 6 (fac 3)))
  (is (= 24 (fac 4))))

;; Exercise 6
(defn prefix-of? [candidate sequence]
  (= candidate (take (count candidate) sequence)))

(deftest prefix-of-test
  (is (prefix-of? '(1 2) '(1 2 3)))
  (is (prefix-of? '(1 2 3) [1 2 3 4 5 6]))
  (is (prefix-of? [1 2 3] [1 2 3 4 5 6]))
  (is (not (prefix-of? '(1 3) '(1 2 3)))))

;; Exercise 7

(defn tails [sequence]
  (map drop (range) (repeat (inc (count sequence)) sequence)))

(deftest tails-test
  (is (= '(()) (tails [])))
  (is (= '((1) ()) (tails [1])))
  (is (= '((1 2) (2) ()) (tails [1 2])))
  (is (= '((1 2 3 4 5) (2 3 4 5) (3 4 5) (4 5) (5) ()) (tails [1 2 3 4 5]))))


(run-tests)

