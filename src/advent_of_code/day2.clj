(ns advent-of-code.day2
  (:require [clojure.string :as str]
            [advent-of-code.util :as util]))

(def code (str/trim (slurp "resources/intcode.txt")))

(declare do-op)

(defn process-code
  ([codes index]
   (cond
     (= (nth codes index) 1)
     (do-op + codes index)
     (= (nth codes index) 2)
     (do-op * codes index)
     (= (nth codes index) 99)
     codes))
  ([codes] (process-code codes 0)))

(defn do-op [op codes index]
  (let [first-op (nth codes (nth codes (+ 1 index)))
        second-op (nth codes (nth codes (+ 2 index)))
        result (op first-op second-op)]
    (process-code (util/replace-nth codes (nth codes (+ 3 index)) result) (+ 4 index))))

(defn process [sv noun verb]
  (let [s (into [] (map #(Integer/parseInt %) (str/split sv  #",")))
        c1 (util/replace-nth s 1 noun)
        c2 (util/replace-nth c1 2 verb)]
    (process-code c2)))

(defn print-result [noun verb]
  (println noun verb (+ (* 100 noun) verb)))

(defn solve-part1 []
  (process code 12 2))

(defn solve-part2 []
  (for [verb (range 99)
        noun (range 99)
        :let [output (first (process code noun verb))]
        :when (= output 19690720)]
    (print-result noun verb)))
