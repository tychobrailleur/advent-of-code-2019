(ns advent-of-code.util)

(defn replace-nth
  "Returns a new list based on `l` where value at index `i` is replaced with `new-val`"
  [l i new-val]
  (let [v (into [] l)]
    (flatten (list (conj (subvec v 0 i) new-val) (subvec v (+ i 1))))))
