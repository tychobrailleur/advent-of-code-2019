(ns advent-of-code.intcode
  (:require
   [clojure.string :as str]
   [advent-of-code.util :as util]))


(declare do-algebraic-op)
(declare do-store-input)
(declare do-print-address)
(declare do-jump-true)
(declare do-jump-false)
(declare do-less-than)
(declare do-equal)

(defn- digit-at-pos
  [str pos]
  (Character/digit (.charAt str pos) 10))

(defn- digit-from-end [s index]
  (if (< (count s) index)
    0
    (digit-at-pos s (- (count s) index))))

(defn- arg-value [codes index pos instr]
  (let [mode (nth instr pos)]
    (cond
      (= mode 0) ;; position mode
      (nth codes (nth codes (+ pos index)))
      (= mode 1) ;; immediate mode
      (nth codes (+ pos index)))))

(defn- read-op [s]
  (if (= 1 (count s))
    (digit-at-pos s 0)
    (Integer/parseInt (subs s (- (count s) 2)))))

(defn opcode-parser [opcode-int]
  (let [s (str opcode-int)
        op (read-op s)
        arg1-mode (digit-from-end s 3)
        arg2-mode (digit-from-end s 4)
        arg3-mode (digit-from-end s 5)]
    [op arg1-mode arg2-mode arg3-mode]))

(defn process-code
  ([codes index]
   (let [instr (opcode-parser (nth codes index))
         opcode (first instr)]
     (cond
       (= opcode 1)
       (do-algebraic-op + codes index instr)
       (= opcode 2)
       (do-algebraic-op * codes index instr)
       (= opcode 3)
       (do-store-input codes index instr)
       (= opcode 4)
       (do-print-address codes index instr)
       (= opcode 5)
       (do-jump-true codes index instr)
       (= opcode 6)
       (do-jump-false codes index instr)
       (= opcode 7)
       (do-less-than codes index instr)
       (= opcode 8)
       (do-equal codes index instr)
       (= opcode 99)
       codes)))
  ([codes] (process-code codes 0)))

(defn do-algebraic-op [op codes index instr]
  (let [first-op (arg-value codes index 1 instr)
        second-op (arg-value codes index 2 instr)
        result (op first-op second-op)]
    (process-code (util/replace-nth codes (nth codes (+ 3 index)) result) (+ 4 index))))

(defn do-store-input [codes index _instr]
  (let [input (Integer/parseInt (str/trim (read-line)))
        store (nth codes (inc index))
        stored (util/replace-nth codes store input)]
    (process-code stored (+ 2 index))))

(defn do-print-address [codes index instr]
  (println (arg-value codes index 1 instr))
  (process-code codes (+ 2 index)))

(defn do-jump-true [codes index instr]
  (if (> (arg-value codes index 1 instr) 0)
    (process-code codes (arg-value codes index 2 instr))
    (process-code codes (+ 3 index))))

(defn do-jump-false [codes index instr]
  (if (= (arg-value codes index 1 instr) 0)
    (process-code codes (arg-value codes index 2 instr))
    (process-code codes (+ 3 index))))

(defn do-less-than [codes index instr]
  (if (< (arg-value codes index 1 instr) (arg-value codes index 2 instr))
    (process-code (util/replace-nth codes (nth codes (+ 3 index)) 1) (+ 4 index))
    (process-code (util/replace-nth codes (nth codes (+ 3 index)) 0) (+ 4 index))))

(defn do-equal [codes index instr]
  (if (= (arg-value codes index 1 instr) (arg-value codes index 2 instr))
    (process-code (util/replace-nth codes (nth codes (+ 3 index)) 1) (+ 4 index))
    (process-code (util/replace-nth codes (nth codes (+ 3 index)) 0) (+ 4 index))))

(defn process
  ([sv]
   (process-code (into [] (map #(Integer/parseInt %) (str/split sv  #",")))))
  ([sv noun verb]
   (let [s (into [] (map #(Integer/parseInt %) (str/split sv  #",")))
         c1 (util/replace-nth s 1 noun)
         c2 (util/replace-nth c1 2 verb)]
     (process-code c2))))

(defn print-result [noun verb]
  (println noun verb (+ (* 100 noun) verb)))
