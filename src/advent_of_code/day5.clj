(ns advent-of-code.day5
  (:require
   [clojure.string :as str]
   [advent-of-code.intcode :as intcode]))

(def code (str/trim (slurp "resources/intcode2.txt")))

(defn solve-part1 []
  (last ((intcode/process code [1]) :output))) ;; input 1

(defn solve-part2 []
  (last ((intcode/process code [5]) :output))) ;; input 5
