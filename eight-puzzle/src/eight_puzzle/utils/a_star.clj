(ns eight-puzzle.utils.a-star
  (:require [eight-puzzle.utils.queues :as q]))

(defn update-frontier
  "Given a node, cost to reach the node,
frontier map and list of successor nodes the  "
  [node cost frontier successor-nodes edge-cost-fn heuristic-fn]
  (let [f-new-cost (fn [s] (+ cost (edge-cost-fn node s)))
        f-new-estimate (fn [s] (try
                                (+ (f-new-cost s)
                                   (heuristic-fn s))
                                (catch Exception e (do (println (str "exp" s))
                                                     Integer/MAX_VALUE))))]
    (reduce #(if (or (not (%1 %2))
                     (> (:estimate (%1 %2))
                        (f-new-estimate %2)))
               (do
                 ;(println (str "update " (%1 %2) "=>" %2 " p " node))
                   (assoc %1 %2 {:cost (f-new-cost %2)
                                 :estimate (f-new-estimate %2)
                                 :parent node}))
               %1)
            frontier
            successor-nodes)))

(defn get-successors
  "given the present node expanded node map and
action function return successors "
  [node expanded action-fn]
  (filter #(not (contains? expanded %)) (action-fn node)))

(defn backtrace
  "creates the path given the end node"
  [end-node expanded cost]
  (loop [state end-node path '()]
    (if (nil? state)
      {:states (count expanded)
       :cost cost
       :path path}
      (let [parent-state (:parent (expanded state))]
        (recur parent-state (conj path state))))))

(defn a-star
  "Implementing a*"
  [init-state edge-cost-fn heuristic-fn goal-fn action-fn]
  (loop [frontier (q/queue-push (q/empty-priority-val-map #(< (:estimate %1) (:estimate %2)))
                          [init-state {:cost 0
                                       :estimate (heuristic-fn init-state)
                                       :parent nil} ])
         expanded {}
         depth 0]
    (cond (empty? frontier) {:status :failed
                             :states expanded}
          (>= depth 350000) :stop
          :else
          (let [state (first (q/queue-peek frontier))
                {f-estimate :estimate parent :parent} (frontier state)
                g-cost (- f-estimate (heuristic-fn state))
                expanded (assoc expanded state
                                {:parent parent
                                 :cost g-cost
                                 :heuristic (heuristic-fn state)})
                successors (get-successors state expanded action-fn)
                frontier (update-frontier
                          state g-cost (q/queue-pop frontier) successors
                          edge-cost-fn heuristic-fn)]
            (if (goal-fn state)
              (backtrace state expanded g-cost)
              (recur  frontier expanded (inc depth)))))))
