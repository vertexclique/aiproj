(ns hanoi.core
  (:gen-class))

(def hanoi
  "Takes the number size of the Hanoi tower and the names of the poles and
   generates the sequence of moves [from to] to translate the tower."
  (memoize
    (fn [n src aux tgt]
      (when (pos? n)
        (concat
          (hanoi (dec n) src tgt aux)
          [[src tgt]]
          (hanoi (dec n) aux src tgt))))))

(defn -main [^String n]
  (doseq [[from to] (hanoi (Integer/parseInt n) "a" "b" "c")]
    (println (format "Move from %s to %s" from to))))
