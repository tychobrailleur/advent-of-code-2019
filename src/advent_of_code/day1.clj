(ns advent-of-code.day1)

(defn fuel [mass]
  (int (- (Math/floor (/ (float mass) 3)) 2)))

(defn sum-fuel [file fn]
  (with-open [rdr (clojure.java.io/reader file)]
    (reduce + 0 (map #(fn (Integer/parseInt %)) (line-seq rdr)))))

(defn sum-all-fuel [mass]
  (loop [m mass
         f (fuel m)
         s 0]
    (if (<= f 0)
      s
      (recur f (fuel f) (+ s f)))))



(sum-fuel "resources/masses.txt" fuel)
(sum-fuel "resources/masses.txt" sum-all-fuel)
