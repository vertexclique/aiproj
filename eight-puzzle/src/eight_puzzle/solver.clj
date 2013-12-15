(ns eight-puzzle.solver
  (:require [eight-puzzle.utils.a-star :as a])
  (:require [eight-puzzle.model :as puzzle])
  (:require [eight-puzzle.heuristics :as h]))

;; (a-star :arad (romania-edge-cost romania) (romania-heuristic
;; romania) city-is-bucharest? (romania-city-neighbours romania))
;;   [init-state edge-cost-fn heuristic-fn goal-fn action-fn]

(defn solve-puzzle [start-state]
    (a/a-star start-state
          (fn [x y] 1)
          h/manhattan-heuristic-fn
          puzzle/end-state?
          puzzle/find-next-states))

(defn solve-puzzle-simple [start-state]
    (a/a-star start-state
          (fn [x y] 1)
          h/simple-heuristic-fn
          puzzle/end-state?
          puzzle/find-next-states))