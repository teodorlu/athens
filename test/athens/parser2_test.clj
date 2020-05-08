(ns athens.parser2-test
  (:require [clojure.test :refer [deftest is testing]]
            [athens.parser2 :as parser2]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            ))

;; sample generative tests

(defn passes-monoid-properties
  [a b c]
  (and (= (+ 0 a) a)
       (= (+ a 0) a)
       (= (+ a (+ b c)) (+ (+ a b) c))))

(deftest plus-and-0-are-a-monoid
  (testing "+ and 0 form a monoid"
    (is (let [p (prop/for-all* [gen/small-integer gen/small-integer gen/small-integer] passes-monoid-properties)]
          (:result
           (tc/quick-check 1000 p))))))
