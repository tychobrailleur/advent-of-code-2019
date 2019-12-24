(ns advent-of-code.day7
  (:require
   [clojure.string :as str]
   [advent-of-code.intcode :as intcode]
   [clojure.math.combinatorics :as combo]))

(def code (str/trim (slurp "resources/intcode3.txt")))

(defn run-sequence [code sequence input]
  (loop [s sequence
         value input]
    (if (empty? s)
      value
      (let [new-value (first ((intcode/process code [(first s) value]) :output))]
        (recur (rest s)
               new-value)))))

(defn run-feedback-sequence [code sequence]
  (loop [s sequence
         value 0]
    (let [new-value (run-sequence code s value)]
      (println new-value)
      (recur sequence new-value))))

(defn find-max-signal [code max permutations]
  (if (empty? permutations)
    (do (println max)
        max)
    (let [signal (run-sequence code (first permutations) 0)]
      (if (> signal max)
        (find-max-signal code signal (rest permutations))
        (find-max-signal code max (rest permutations))))))

(defn find-max-signal-amplified [code max permutations]
  (if (empty? permutations)
    max
    (let [signal (run-feedback-sequence code (first permutations))]
      (if (> signal max)
        (find-max-signal-amplified code signal (rest permutations))
        (find-max-signal-amplified code max (rest permutations))))))

(defn solve-part1 []
  (find-max-signal code 0 (combo/permutations (range 5))))

(defn solve-part2 []
  (find-max-signal-amplified code 0 (combo/permutations (range 5 10))))
