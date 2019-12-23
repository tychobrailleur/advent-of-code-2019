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
      (let [new-value (first (intcode/process-code
                              {:code (vec code)
                               :input [(first s) value]
                               :index 0
                               :output []}))]
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
  (println (find-max-signal (into [] (map #(Integer/parseInt %) (str/split code #","))) 0 (combo/permutations (range 5)))))
