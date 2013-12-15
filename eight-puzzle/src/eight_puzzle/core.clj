(ns eight-puzzle.core
	(:require [eight-puzzle.solver :as solv])
	(:gen-class :main true))

(defn -main []
	(println "@author: Mahmut Bulut based on the code of Peter Juhl Christiansen.")
	(println "Enter a sequence of 8-puzzle matrix in range of 0 to 8: ")
	(let [input (read-line)]
    	(println "SOLUTION: " (solv/solve-puzzle-simple (read-string input)))))