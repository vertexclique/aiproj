(ns eight-puzzle.heuristics
  (:use [eight-puzzle.model :only [index-to-position]]))

(defn manhattan-distance
  "give two positions calculates the block distance"
  [[x1 y1] [x2 y2]]
  (let [diff (fn [a b] (Math/abs (- a b)))]
    (+ (diff x1 x2) (diff y1 y2))))

(defn indexs-to-manhattan-distance
  "Given two index, calculate the Manhattan distance"
  [i1 i2]
  (let [p1 (index-to-position i1)
        p2 (index-to-position i2)]
    (if (= i2 0) 0 (manhattan-distance p1 p2))))

(defn manhattan-heuristic-fn
  "calculate heuristic, based on manhattan distance"
  [state]
  (reduce + (map indexs-to-manhattan-distance [0 1 2 3 4 5 6 7 8] state)))

(defn simple-heuristic-fn
  "calculate simple heuristic, based on is in place yes/no"
  [state]
  (reduce + (map (fn [x y] (if (or (zero? x) (= x y)) 0 1)) [0 1 2 3 4 5 6 7 8] state)))
