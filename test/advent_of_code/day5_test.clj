(ns advent-of-code.day5-test
  (:require [advent-of-code.day5 :as sut]
            [clojure.test :refer :all]))

(deftest opcode-parser-four-digits
  (testing "Four digits"
    (is (= (sut/opcode-parser 1102) [2 1 1 0]))))
