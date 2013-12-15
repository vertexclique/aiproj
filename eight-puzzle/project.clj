(defproject eight-puzzle "0.1.0-SNAPSHOT"
  :description "Cloj 8 - puzzle solver"
  :url "http://github.com/vertexclique/clj-eight-puzzle"
  :main eight-puzzle.core
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.priority-map "0.0.2"]
                 [org.clojure/math.combinatorics "0.0.3"]
                 [seesaw "1.4.2"]
                 [criterium "0.3.1"]
                 [org.clojure/tools.nrepl "0.2.2"]]
  :injections [(require 'clojure.pprint)]
  :profiles {:dev {:dependencies [
                 [bultitude "0.1.7"]
                 ]
                  :plugins []}})
