(ns eight-puzzle.model)

(def end-state [0 1 2
                3 4 5
                6 7 8])

(defn end-state?
  "check that given state is the end state"
  [state]
  (= state [0 1 2 3 4 5 6 7 8]))

(defn index-to-position
	"index to position [i j]"
	[index]
	[(rem index 3) (int (/ index 3))])

(defn find-in-state
  "find i j position of an element in the state"
  [state elm]
  (-> (.indexOf state elm)
      (index-to-position)))

(defn position-to-index
	"position [i j] to a index"
	[[i j]]
   (+ i (* 3 j)))

(defn valid-position?
  "is the i j a valid position"
  [[i j]]
  (let [valid-index (set (range 0 3))]
    (and (contains? valid-index i)
         (contains? valid-index j))))

(defn lookup
  "get the element at position [i j]"
  [[i j] state]
  (if (valid-position? [i j])
    (nth state (position-to-index [i j]))))

(defn neighbourgs
  "return all neighbourg positions to position i j "
  [[i j]]
  (let [all-neighbourgs [[i (dec j)] [i (inc j)]
                         [(dec i) j] [(inc i) j]]]
    (filter valid-position? all-neighbourgs)))

(defn swap
  "swap two elements in a vector v, given index i1 i2"
  [v i1 i2]
   (assoc v i2 (v i1) i1 (v i2)))

(defn swap-elements
  "swap element at i1 j1 with element at i2 j2"
  [[i1 j1] [i2 j2] state]
  (if (and (valid-position? [i1 j1])
           (valid-position? [i2 j2]))
    (swap state (+ i1 (* 3 j1)) (+ i2 (* 3 j2)))))

(defn find-next-states
  "given a state return the set of states adjacent"
  [state]
  (let [empty-position (find-in-state state 0)
        next-positions (neighbourgs empty-position)]
    (map #(swap-elements empty-position % state) next-positions)))

(defn random-walk
  "takes a random numbers of random steps from a given state"
  [start-state]
  (loop [state start-state num (+ 10 (rand-int 10)) ]
    (if (zero? num)
      state
      (-> (find-next-states state)
         (rand-nth)
         (recur (dec num))))))

(defn get-state-id
  "return a state id string given a state"
  [state]
  (reduce str "st:" state))
