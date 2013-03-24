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

(defn Triangle [p1 p2 p3]
  {:point1 p1,
   :point2 p2,
   :point3 p3,
   :__class_symbol__ 'Triangle})

(defn equal-triangles? [& triangles]
  (apply = triangles))

(defn valid-triangle? [& points]
  (= (distinct points) points))

(def right-triangle (Triangle (Point 0 0)
                              (Point 0 1)
                              (Point 1 0)))

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def different-triangle (Triangle (Point 0 0)
                                  (Point 0 10)
                                  (Point 10 0)))


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

(deftest TriangleTest
  (def t (make Triangle
               (make Point 0 0)
               (make Point 1 0)
               (make Point 0 1)))
  (testing "creation"
    (is (= 'Triangle (class-of t))))
  (testing "equality"
    (is (equal-triangles? right-triangle right-triangle))
    (is (not (equal-triangles? right-triangle different-triangle)))
    (is (equal-triangles? right-triangle equal-right-triangle))
    (is (not (equal-triangles? right-triangle
                               equal-right-triangle
                               different-triangle))))
  (testing "validity"
    (is (valid-triangle? (make Point 0 0)
                         (make Point 1 0)
                         (make Point 0 1)))
    (is (not (valid-triangle? (make Point 0 0)
                              (make Point 0 0)
                              (make Point 0 1))))))

(run-tests)
