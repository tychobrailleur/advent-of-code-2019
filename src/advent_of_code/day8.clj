(ns advent-of-code.day8
  (:require [clojure.string :as str]))

(def width 25)
(def height 6)
(def display-char {0 " " 1 "░"})

(defn image-layers [pixels width height]
  (partition height (partition width pixels)))

(defn count-digit [list digit]
  (count (filter #(= % digit) list)))

(defn read-pixels [file]
  (map #(Integer/parseInt %) (str/split (str/trim (slurp file)) #"")))

(defn render-pixels [pixels width height]
  (loop [index 0
         i 0
         rendered []]
    (if (< index (* width height))
      (let [current-pixel (nth pixels i)]
        (if (= current-pixel 2)
          (recur index (+ i (* width height)) rendered)
          (recur (inc index) (inc index) (conj rendered current-pixel))))
      rendered)))

(defn solve-part1 []
  (let [layer
        (first (sort-by #(count-digit % 0)
                        (map flatten (image-layers (read-pixels "resources/image.txt") width height))))]
    (* (count-digit layer 1) (count-digit layer 2))))

(defn solve-part2 []
  (let [pixels (flatten (image-layers (read-pixels "resources/image.txt") width height))
        rendered (render-pixels pixels width height)
        lines (map #(display-char %) rendered)]
    (doseq [line (map str/join (partition width lines))]
      (println line))))
