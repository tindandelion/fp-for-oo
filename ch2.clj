;; This is a chapter 2 of a book
(ns org.dandelion.fp-for-oo
  (:use clojure.test))

(defn make [klass & args]
  (apply klass args))

(def class-of :__class_symbol__)

(defn send-to [object message & args]
  (apply (message (:__methods__ object)) object args))

(defn Point [x y]
  {:x x,
   :y y
   :__class_symbol__ 'Point,
   :__methods__
   {
    :x (fn [this] (:x this))
    :y (fn [this] (:y this))
    :shift (fn [this incx incy]
             (Point (+ (send-to this :x) incx)
                    (+ (send-to this :y) incy)))
    :add (fn [this that]
           (send-to this :shift
                    (send-to that :x)
                    (send-to that :y)))
    
    }})
           
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
    (is (= 3 (send-to p :x)))
    (is (= 4 (send-to p :y)))
    (is (= 'Point (class-of p))))
  
  (testing "shifting"
    (def shifted-p (send-to p :shift 2 3))
    (is (= 5 (send-to shifted-p :x)))
    (is (= 7 (send-to shifted-p :y))))
  (testing "addition"
    (def added-p (send-to p :add (make Point 5 6)))
    (is (= 8 (send-to added-p :x)))
    (is (= 10 (send-to added-p :y)))))

;; (deftest TriangleTest
;;   (def t (make Triangle
;;                (make Point 0 0)
;;                (make Point 1 0)
;;                (make Point 0 1)))
;;   (testing "creation"
;;     (is (= 'Triangle (class-of t))))
;;   (testing "equality"
;;     (is (equal-triangles? right-triangle right-triangle))
;;     (is (not (equal-triangles? right-triangle different-triangle)))
;;     (is (equal-triangles? right-triangle equal-right-triangle))
;;     (is (not (equal-triangles? right-triangle
;;                                equal-right-triangle
;;                                different-triangle))))
;;   (testing "validity"
;;     (is (valid-triangle? (make Point 0 0)
;;                          (make Point 1 0)
;;                          (make Point 0 1)))
;;     (is (not (valid-triangle? (make Point 0 0)
;;                               (make Point 0 0)
;;                               (make Point 0 1))))))

(run-tests)
