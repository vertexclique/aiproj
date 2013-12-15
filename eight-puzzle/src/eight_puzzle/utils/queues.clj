(ns eight-puzzle.utils.queues
  (:require [clojure.data.priority-map :as pm]))

(defmethod print-method clojure.lang.PersistentQueue
  [q, w]
  (print-method '<- w) (print-method (seq q) w) (print-method '-< w))

(defprotocol GenericQueue
  (queue-empty? [q])
  (queue-peek [q])
  (queue-pop [q])
  (queue-push [q e]))

(extend-protocol GenericQueue
  clojure.lang.PersistentQueue
  (queue-empty? [q] (clojure.core/empty?  q))
  (queue-peek [q] (clojure.core/peek  q))
  (queue-pop [q] (clojure.core/pop  q))
  (queue-push [q e] (conj q e))

  ;clojure.lang.PersistentList
  ;(empty? [q] (clojure.core/empty? q))
  ;(peek [q] (clojure.core/peek q))
  ;(pop [q] (clojure.core/pop q))
  ;(push [q e] (conj q e))

  clojure.lang.PersistentTreeSet
  (queue-empty? [q] (clojure.core/empty? q))
  (queue-peek [q] (first q))
  (queue-pop [q] (disj q (first q)))
  (queue-push [q e] (conj q e))

  clojure.lang.PersistentTreeMap
  (queue-empty? [q] (clojure.core/empty? q))
  (queue-peek [q] (first q))
  (queue-pop [q] (dissoc q (first (first q))))
  (queue-push [q [k v]] (assoc q k v))

  clojure.data.priority_map.PersistentPriorityMap
  (queue-empty? [q] (clojure.core/empty? q))
  (queue-peek [q] (first q))
  (queue-pop [q] (dissoc q (first (first q))))
  (queue-push [q [k v]] (assoc q k v))

  java.util.PriorityQueue
  (queue-empty? [q] (.isEmpty q))
  (queue-peek [q] (.peek q))
  (queue-pop [q] (.poll q))
  (queue-push [q e] (let [new-q (java.util.PriorityQueue. q)
                    _ (.offer new-q e)] new-q))
  )


(defn empty-queue
  "create a empty Queue"
  [] clojure.lang.PersistentQueue/EMPTY)

;(defn empty-stack
;  "creates a empty Stack"
;  (clojure.lang.PersistentList/EMPTY))

(defn seq-2-queue
  "takes a sequence and return a queue"
  [sq]
  (reduce #(conj %1 %2) (empty-queue) sq))

;(defn seq-2-stack
;  "takes a sequence and return a queue"
;  [sq]
;  (reduce #(conj %1 %2) (empty-stack) sq))

(defn empty-priority-queue
  "Creates a empty priority queue (sorted set) given a compare function"
  [compare-fn]
  (sorted-set-by compare-fn))

(defn empty-priority-key-map
  "Creates a empty priority queue (sorted map) given a compare function"
  [compare-fn]
  (sorted-map-by compare-fn))

(defn empty-priority-val-map
  "Creates a empty priority queue (sorted map) given a compare function"
  [compare-fn]
  (pm/priority-map-by compare-fn))

(defn empty-java-priority-queue
  "Creates a empty java priority queue given a initialCapacity and comparator"
  [^long initialCapacity ^java.util.Comparator comparator]
  (java.util.PriorityQueue. initialCapacity comparator))


