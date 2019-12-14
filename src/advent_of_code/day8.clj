(ns advent-of-code.day8
  (:require [clojure.string :as str]))

(defn image-layers [pixels width height]
  (partition height (partition width pixels)))

(defn count-digit [list digit]
  (count (filter #(= % digit) list)))

(defn read-pixels [file]
  (map #(Integer/parseInt %) (str/split (str/trim (slurp file)) #"")))

(defn solve-part1 []
  (let [layer
        (first (sort-by #(count-digit % 0)
                        (map flatten (image-layers (read-pixels "resources/image.txt") 25 6))))]
    (* (count-digit layer 1) (count-digit layer 2))))
