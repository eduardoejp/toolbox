(ns ^{:author "Eduardo Julian <eduardoejp@gmail.com>"
      :doc "Utilities for event handling."}
     toolbox.event
  (:require [goog.events :as gevents]
            [toolbox.core :as $])
  (:use-macros [clojure.template :only [do-template]]))

(defn on "Assigns an event handler to an object to work for the given event (passed as either a keyword or an string)."
  [x evt f] (doto x (gevents/listen (name evt) f)))

(do-template [<sym> <evt>]
  (defn <sym> "" [x f] (on x <evt> f))
  ; DOM Interaction
  on-change      :change
  on-click       :click
  on-load        :load
  ; Audio/Video
  on-progress    :progress
  on-time-update :timeupdate
  on-play        :play
  on-pause-ended "pause ended"
  )

(do-template [<sym> <evt>]
  (defn <sym> [widget f] (doto widget (.addEventListener (name <evt>) f)))
  ; Drag and Drop
  on-drop        :drop
  on-drag-start  :dragstart
  on-drag-enter  :dragenter
  on-drag-leave  :dragleave
  on-drag-over   :dragover
  )
