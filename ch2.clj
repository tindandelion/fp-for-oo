;; This is a chapter 2 of a book
(ns org.dandelion.fp-for-oo
  (:use clojure.test))

(defn Point [x y]
  {:x x,
   :y y
   :__class_symbol__ 'Point})

(def class-of :__class_symbol__)
(def x :x)
(def y :y)

(defn shift [this incx incy]
  (Point (+ (x this) incx)
         (+ (y this) incy)))

(deftest PointTest
  (def p (Point 3 4))
  (testing "creation"
    (is (= 3 (x p)))
    (is (= 4 (y p)))
    (is (= 'Point (class-of p))))
  
  (testing "shifting"
    (def shifted-p (shift p 2 3))
    (is (= 5 (x shifted-p)))
    (is (= 7 (y shifted-p)))))

(run-tests)
