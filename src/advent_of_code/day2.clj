(ns advent-of-code.day2
  (:require [clojure.string :as str]
            [advent-of-code.intcode :as intcode]))

(def code (str/trim (slurp "resources/intcode.txt")))

(defn solve-part1 []
  (intcode/process code 12 2))

(defn solve-part2 []
  (for [verb (range 99)
        noun (range 99)
        :let [output (first (intcode/process code noun verb))]
        :when (= output 19690720)]
    (intcode/print-result noun verb)))
