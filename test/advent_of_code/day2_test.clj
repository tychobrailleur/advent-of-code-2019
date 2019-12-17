(ns advent-of-code.day2-test
  (:require [clojure.test :refer :all]
            [advent-of-code.intcode :refer :all]))

(deftest first-test
  (testing "FIXME, I fail."
    (is (= (process-code {:code [1 0 0 0 99] :index 0}) '(2 0 0 0 99)))))

(deftest second-test
  (testing "FIXME, I fail."
    (is (= (process-code {:code [2 3 0 3 99] :index 0}) '(2 3 0 6 99)))))

(deftest third-test
  (testing "FIXME, I fail."
    (is (= (process-code {:code [2 4 4 5 99 0] :index 0}) '(2 4 4 5 99 9801)))))

(deftest fourth-test
  (testing "FIXME"
    (is (= (process-code {:code [1 1 1 4 99 5 6 0 99] :index 0}) '(30 1 1 4 2 5 6 0 99)))))
