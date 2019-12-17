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

(defn- arg-value [context pos instr]
  (let [mode (nth instr pos)
        codes (context :code)
        index (context :index)]
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
  ([context]
   (let [instr (opcode-parser (nth (context :code) (context :index)))
         opcode (first instr)]
     (cond
       (= opcode 1)
       (do-algebraic-op + context instr)
       (= opcode 2)
       (do-algebraic-op * context instr)
       (= opcode 3)
       (do-store-input context instr)
       (= opcode 4)
       (do-print-address context instr)
       (= opcode 5)
       (do-jump-true context instr)
       (= opcode 6)
       (do-jump-false context instr)
       (= opcode 7)
       (do-less-than context instr)
       (= opcode 8)
       (do-equal context instr)
       (= opcode 99)
       (context :output))))
  ([codes input] (process-code {:code codes :index 0 :input input :output []})))

(defn- store-and-process [context value]
  (let [codes (context :code)
        index (context :index)]
    (process-code (assoc context
                         :code (util/replace-nth codes (nth codes (+ 3 index)) value)
                         :index (+ 4 index)))))

(defn do-algebraic-op [op context instr]
  (let [first-op (arg-value context 1 instr)
        second-op (arg-value context 2 instr)
        result (op first-op second-op)]
    (store-and-process context result)))

(defn do-store-input [context _instr]
  (let [codes (context :code)
        index (context :index)
        store (nth codes (inc index))
        stored (util/replace-nth codes store (context :input))]
    (process-code (assoc context :code stored :index (+ 2 index)))))

(defn do-print-address [context instr]
  (let [output (conj (context :output) (arg-value context 1 instr))]
    (process-code (assoc context :index (+ 2 (context :index)) :output output))))

(defn do-jump-true [context instr]
  (if (> (arg-value context 1 instr) 0)
    (process-code (assoc context :index (arg-value context 2 instr)))
    (process-code (assoc context :index (+ 3 (context :index))))))

(defn do-jump-false [context instr]
  (if (= (arg-value context 1 instr) 0)
    (process-code (assoc context :index (arg-value context 2 instr)))
    (process-code (assoc context :index (+ 3 (context :index))))))

(defn do-less-than [context instr]
  (if (< (arg-value context 1 instr) (arg-value context 2 instr))
    (store-and-process context 1)
    (store-and-process context 0)))

(defn do-equal [context instr]
  (if (= (arg-value context 1 instr) (arg-value context 2 instr))
    (store-and-process context 1)
    (store-and-process context 0)))

(defn print-result [noun verb]
  (println noun verb (+ (* 100 noun) verb)))

(defn process
  ([sv input]
   (process-code {:code (into [] (map #(Integer/parseInt %) (str/split sv #",")))
                  :index 0
                  :input input
                  :output []}))
  ([sv noun verb input]
   (let [s (into [] (map #(Integer/parseInt %) (str/split sv #",")))
         c1 (util/replace-nth s 1 noun)
         c2 (util/replace-nth c1 2 verb)]
     (process-code {:code c2 :index 0 :input input :output []}))))
