(ns eight-puzzle.seesaw-view
  (:use [seesaw core]))

(defn do-stuff [id txt]
  "do-stuff")

(defn add-alert [frame i]
  (let [id (keyword (str "#b" i))
        elm (select frame [id])]
    (listen elm :action
            (fn [e]
              (let [res (do-stuff i (text elm))]
                (alert e (str id " " (text elm) ": " res)))))))

(defn add-action [frame id action]
  (let [elm (select frame [id])]
    (listen elm :action
            action)))

(defn select-id [frame key]
  (let [id (keyword (str "#" (name key)))]
    (select frame [id])))

(defn set-element-text [frame id text]

  (text! (select-id frame id) text))

(defn listen-to-tiles [frame]
  (for [i (range 1 9)]
    (add-alert frame i)))


(defn make-frame []
  ; Put all the widgets together
  (frame :title "8-puzzle" :width 1000 :height 1000
    :content
    (border-panel :border 5 :hgap 5 :vgap 5
      :north  (label :id :header :text "Location")

      :center (grid-panel
        :hgap 5 :vgap 5 :columns 3 :rows 3 :border 10
        :items (into []
                     (for [i (range 0 9)]
                       (if (zero? i)
                         (let [b0 (button :id "b0" :text "_")
                               _ (.setVisible b0 false )]
                           b0)
                         (button :id (keyword (str "b" i)) :text (str i))))))

      :south  (grid-panel
               :columns 2
               :items [(button :id :shuffle :text "shuffle")
                       (button :id :slove :text "solve")]))))

(defn explorer-hook-up []
  (let [f (make-frame)]
    ;; Hook up a selection listener to the tree to update stuff
    (listen (select f [:#tree]) :selection
            (fn [e]
              (if-let [elm (last (selection e))]
                (let [elemets elm]
                  (config! (select f [:#header]) :text "This is a header")
                  (config! (select f [:#status]) :text "South")
                  (config! (select f [:#list]) :model elemets)))))
    f))

(defn start [frame]
	(invoke-later
	      (-> frame
		    ;;(explorer-hook-up)
	          pack!
	          show!)))