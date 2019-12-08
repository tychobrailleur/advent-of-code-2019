(ns advent-of-code.day1-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day1 :refer :all]))

(deftest first-fuel
  (testing "FIXME, I fail."
    (is (= (fuel 12) 2))))
(deftest second-fuel
  (testing "FIXME, I fail."
    (is (= (fuel 14) 2))))
(deftest third-fuel
  (testing "FIXME, I fail."
    (is (= (fuel 1969) 654))))
(deftest fourth-fuel
  (testing "FIXME, I fail."
    (is (= (fuel 100756) 33583))))

(deftest second-all-fuel
  (testing "FIXME, I fail."
    (is (= (sum-all-fuel 14) 2))))
(deftest third-all-fuel
  (testing "FIXME, I fail."
    (is (= (sum-all-fuel 1969) 966))))
(deftest fourth-all-fuel
  (testing "FIXME, I fail."
    (is (= (sum-all-fuel 100756) 50346))))
