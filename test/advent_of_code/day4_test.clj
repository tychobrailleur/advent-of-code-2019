(ns advent-of-code.day4-test
  (:require [advent-of-code.day4 :as sut]
            [clojure.test :refer :all]))

(deftest adjacent-not-bigger-test1
  (testing "Simple case"
      (is (= (sut/adjacent-not-bigger? '(1 1 2 2 3 3)) true))))

(deftest adjacent-not-bigger-test2
  (testing "Simple case"
      (is (= (sut/adjacent-not-bigger? '(1 1 2 2 3 4)) true))))

(deftest adjacent-not-bigger-test3
  (testing "Simple case"
    (is (= (sut/adjacent-not-bigger? '(1 2 2 2 3 3)) true))))

(deftest adjacent-not-bigger-test4
  (testing "Simple case"
    (is (= (sut/adjacent-not-bigger? '(2 2 2 3 3 3)) false))))

(deftest adjacent-not-bigger-test5
  (testing "Simple case"
    (is (= (sut/adjacent-not-bigger? '(2 2 2 2 2 2)) false))))
