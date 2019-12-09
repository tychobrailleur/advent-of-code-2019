(ns advent-of-code.day4
  (:require [clojure.string :as str]))

(defn split-digits [num]
  (map #(Character/digit % 10) (str num)))

(defn increasing?
  ([num] (if (<= (count num) 1)
           true
           (increasing? (first num) (rest num))))
  ([digit tail]
   (let [[x & xs] tail]
     (if (empty? xs)
       (<= digit x)
       (and (<= digit x) (increasing? tail))))))

(defn adjacent? [num]
  (let [[x & xs] num]
    (if (empty? xs)
      false
      (if (= x (first xs))
        true
        (adjacent? (rest num))))))

(defn adjacent-not-bigger? [num]
  (let [[x & xs] num]
    (if (empty? xs)
      false
      (if (= x (first xs))
        (if (= (count xs) 1)
          true
          (if (and (>= (count xs) 2) (not= (first (rest xs)) x))
            true
            (adjacent-not-bigger? (drop-while #(= % x) xs))))
        (adjacent-not-bigger? (rest num))))))

(defn match-rules? [num f]
  (let [digits (split-digits num)]
    (and (f digits) (increasing? digits))))

(defn count-valid-in-range [r f]
  (let [min-max (str/split r #"-")
        lower (Integer/parseInt (nth min-max 0))
        higher (Integer/parseInt (nth min-max 1))]
    (filter #(match-rules? % f) (range lower higher))))

(defn solve-part1 []
  (count (count-valid-in-range "265275-781584" adjacent?)))

(defn solve-part2 []
  (count (count-valid-in-range "265275-781584" adjacent-not-bigger?)))
