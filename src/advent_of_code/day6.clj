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

(def example2 "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN")

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

(defn count-paths [entry acc]
  (let [e (acc entry)]
    (if (nil? e)
      0
      (+ (count (e :orbiters)) (reduce + (map #(count-paths % acc) (e :orbiters)))))))

(defn iterate-bodies [bodies]
  (reduce + (map #(count-paths % bodies) (keys bodies))))

(defn find-parent [tree body]
  (first (first (filter (fn [e] (some #(= body %) ((second e) :orbiters))) tree))))

(defn find-ancestry
  ([tree body] (find-ancestry tree body []))
  ([tree body acc] (let [parent (find-parent tree body)]
                     (if (nil? parent)
                       acc
                       (find-ancestry tree parent (conj acc parent))))))

(defn find-first-common-ancestor [tree you san]
  (let [you-ancestry (find-ancestry tree you)
        san-ancestry (find-ancestry tree san)]
    (first (drop-while (fn [e] (not-any? #(= % e) san-ancestry)) you-ancestry))))

(defn path-length [tree from to]
  (loop [parent (find-parent tree to)
         length 0]
    (if (= parent from)
      length
      (recur (find-parent tree parent) (+ 1 length)))))

(defn solve-part1 []
  (iterate-bodies (build-orbits (str/split (slurp "resources/orbits.txt") #"\n"))))

(defn solve-part2 []
  (let [orbits (build-orbits (str/split (slurp "resources/orbits.txt") #"\n"))
        common-ancestor (find-first-common-ancestor orbits "YOU" "SAN")]
    (+ (path-length orbits common-ancestor "YOU")
       (path-length orbits common-ancestor "SAN"))))
