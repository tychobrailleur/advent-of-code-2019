(ns advent-of-code.day7
  (:require
   [clojure.string :as str]
   [advent-of-code.intcode :as intcode]
   [clojure.math.combinatorics :as combo]))

(def code (str/trim (slurp "resources/intcode3.txt")))

(defn run-sequence [code sequence]
  (loop [s sequence
         value 0]
    (if (empty? s)
      value
      (let [new-value (first (intcode/process code [(first s) value]))]
        (recur (rest s)
               new-value)))))

(defn find-max-signal [code max permutations]
  (if (empty? permutations)
    max
    (let [signal (run-sequence code (first permutations))]
      (if (> signal max)
        (find-max-signal code signal (rest permutations))
        (find-max-signal code max (rest permutations))))))

(defn solve-part1 []
  (find-max-signal code 0 (combo/permutations (range 5))))
