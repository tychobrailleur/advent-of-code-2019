(ns advent-of-code.day5
  (:require
   [clojure.string :as str]
   [advent-of-code.intcode :as intcode]))

(def code (str/trim (slurp "resources/intcode2.txt")))

(defn solve-part1 []
  (intcode/process code)) ;; input 1

(defn solve-part2 []
  (intcode/process code)) ;; input 5
