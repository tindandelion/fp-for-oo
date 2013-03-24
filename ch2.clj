;; This is a chapter 2 of a book
(ns org.dandelion.fp-for-oo
  (:use clojure.test))

(defn send-to [object message & args]
  (apply (message (:__instance_methods__ object)) object args))

(defn make [klass & args]
  (let [allocated {}
        initialized (merge allocated klass)]
    (apply send-to initialized :add-instance-values args)))

(def class-of :__class_symbol__)

(def Point
  {
   :__class_symbol__ 'Point
   :__instance_methods__
   {
    :x (fn [this] (:x this))
    :y (fn [this] (:y this))
    :add-instance-values (fn [this x y]
                           (assoc this :x x :y y))
    :shift (fn [this incx incy]
             (make Point
                   (+ (send-to this :x) incx)
                   (+ (send-to this :y) incy)))
    :add (fn [this that]
           (send-to this :shift
                    (send-to that :x)
                    (send-to that :y)))
    }
   })

(def Triangle
  {
   :__class_symbol__ 'Triangle
   :__instance_methods__
   {
    :add-instance-values (fn [this pt1 pt2 pt3]
                           (assoc this :point1 pt1 :point2 pt2 :point3 pt3))
    }
   })

(defn equal-triangles? [& triangles]
  (apply = triangles))

(defn valid-triangle? [& points]
  (= (distinct points) points))

(def right-triangle (make Triangle
                          (make Point 0 0)
                          (make Point 0 1)
                          (make Point 1 0)))

(def equal-right-triangle (make Triangle
                                (make Point 0 0)
                                (make Point 0 1)
                                (make Point 1 0)))

(def different-triangle (make Triangle
                              (make Point 0 0)
                              (make Point 0 10)
                              (make Point 10 0)))


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
