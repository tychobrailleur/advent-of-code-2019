(ns advent-of-code.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def actions {"U" "go-up"
              "D" "go-down"
              "L" "go-left"
              "R" "go-right"})

(def origin '(0 0))

(defn extend-trail
  ([trail moves]
   (let [move (first moves)
         dir (get move 0)
         f (actions (str dir))
         new-steps (apply (resolve (symbol f))
                          (list (last trail) (Integer/parseInt (subs move 1))))]
     (if (empty? (rest moves))
       (concat trail new-steps)
       (extend-trail (concat trail new-steps) (rest moves)))))
  ([moves] (extend-trail ['(0 0)] moves)))

(defn compute-path [path]
  (let [steps (str/split path #",")]
    (extend-trail steps)))

(defn go-steps-in-direction [pos step f]
  (loop [p (f pos)
         i step
         l []]
    (if (= 0 i)
      l
      (recur (f p)
             (- i 1)
             (conj l p)))))

(defn go-up [pos step]
  (go-steps-in-direction pos step #(list (first %) (+ 1 (last %)))))

(defn go-down [pos step]
  (go-steps-in-direction pos step #(list (first %) (- (last %) 1))))

(defn go-left [pos step]
  (go-steps-in-direction pos step #(list (- (first %) 1) (last %))))

(defn go-right [pos step]
  (go-steps-in-direction pos step #(list (+ (first %) 1) (last %))))

(defn manhattan-distance [p1 p2]
  (let [[x1 y1] p1
        [x2 y2] p2]
    (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2)))))

(defn compute-details [p wire1 wire2]
  {:manhattan (manhattan-distance p origin)
   :index1 (.indexOf wire1 p)
   :index2 (.indexOf wire2 p)})

(defn closest-intersection [wire1 wire2]
  (let [path1 (compute-path wire1)
        path2 (compute-path wire2)
        intersections (filter #(not= origin %) (set/intersection (set path1) (set path2)))]
    (first (sort #(compare (%1 :manhattan) (%2 :manhattan))
          (map (fn [e] (compute-details e path1 path2)) intersections)))))


(defn shortest-intersection [wire1 wire2]
  (let [path1 (compute-path wire1)
        path2 (compute-path wire2)
        intersections (filter #(not= origin %) (set/intersection (set path1) (set path2)))]
    (first (sort #(compare (+ (%1 :index1) (%1 :index2)) (+ (%2 :index1) (%2 :index2)))
          (map (fn [e] (compute-details e path1 path2)) intersections)))))

(defn solve-part1 []
  (let [content (str/trim (slurp "resources/manhattan.txt"))
        lines (str/split content #"\n")
        wire1 (nth lines 0)
        wire2 (nth lines 1)
        result (closest-intersection wire1 wire2)]
    (result :manhattan)))

(defn solve-part2 []
  (let [content (str/trim (slurp "resources/manhattan.txt"))
        lines (str/split content #"\n")
        wire1 (nth lines 0)
        wire2 (nth lines 1)
        result (shortest-intersection wire1 wire2)]
    (+ (result :index1) (result :index2))))
