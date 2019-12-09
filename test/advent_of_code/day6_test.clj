(ns advent-of-code.day6-test
  (:require [advent-of-code.day6 :as sut]
            [clojure.test :refer :all]))

(deftest add-orbiter-non-existent
  (testing "Non existing orbiter"
    (is (= (sut/add-orbiter "A)B" {}) {"A" {:orbiters ["B"]}}))))

(deftest add-orbiter-existent
  (testing "Existing orbiter"
    (is (= (sut/add-orbiter "A)C" {"A" {:orbiters ["B"]}}) {"A" {:orbiters ["B" "C"]}}))))
