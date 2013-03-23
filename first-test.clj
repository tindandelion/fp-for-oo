(ns my.test.lib
  (:use clojure.test))

(deftest first-test
  (is (= 1 1)))

(with-test
  (defn addition [x y]
    (+ x y))
  (is (= 7 (addition 3 4))))

(run-tests)



  