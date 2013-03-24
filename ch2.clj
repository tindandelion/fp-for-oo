;; This is a chapter 2 of a book
(ns org.dandelion.fp-for-oo
  (:use clojure.test))

(defn make [klass & args]
  (apply klass args))

(def class-of :__class_symbol__)

(defn Point [x y]
  {:x x,
   :y y
   :__class_symbol__ 'Point})

(def x :x)
(def y :y)

(defn shift [this incx incy]
  (Point (+ (x this) incx)
         (+ (y this) incy)))

(defn add [p1 p2]
  (shift p1 (x p2) (y p2)))


;; ---- Tests

(deftest PointTest
  (def p (make Point 3 4))
  (testing "creation"
    (is (= 3 (x p)))
    (is (= 4 (y p)))
    (is (= 'Point (class-of p))))
  
  (testing "shifting"
    (def shifted-p (shift p 2 3))
    (is (= 5 (x shifted-p)))
    (is (= 7 (y shifted-p))))
  (testing "addition"
    (def added-p (add p (Point 5 6)))
    (is (= 8 (x added-p)))
    (is (= 10 (y added-p)))))

(run-tests)
