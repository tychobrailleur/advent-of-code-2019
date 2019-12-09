(ns advent-of-code.day6
  (:require [clojure.string :as str]))

(def example "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L")

(defn add-orbiter [line acc]
  (let [[body orbiter] (str/split line #"\)")]
    (if (contains? acc body)
      (update-in acc [body] #(update-in % [:orbiters] conj orbiter))
      (assoc-in acc [body] {:orbiters [orbiter]}))))

(defn build-orbits
  ([list] (build-orbits list {}))
  ([list acc]
   (if (empty? list)
     acc
     (build-orbits (rest list) (add-orbiter (first list) acc)))))

(defn count-acc [entry acc]
  (let [e (acc entry)]
    (if (nil? e)
      0
      (+ (count (e :orbiters)) (reduce + (map #(count-acc % acc) (e :orbiters)))))))

(defn iterate-bodies [bodies]
  (reduce + (map #(count-acc % bodies) (keys bodies))))

(defn solve-part1 []
  (iterate-bodies (build-orbits (str/split (slurp "resources/orbits.txt") #"\n"))))
