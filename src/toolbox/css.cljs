(ns ^{:author "Eduardo Julian <eduardoejp@gmail.com>"
      :doc "Utilities for handling the CSS of an element."}
     toolbox.css
  (:require [goog.style :as gstyle]
            [goog.dom.classes :as gcss]
            [clojure.walk :as walk]
            [toolbox.core :as $]))

(defn- hmap->jso "Makes a JavaScript object from a Clojure map."
  [cljmap]
  (if (map? cljmap)
    (let [out (js-obj)] (doseq [[k v] cljmap] (aset out (name k) (hmap->jso v)))
      out)
    cljmap))

; Properties
(defn set-style! "Provide the properties as a hash-map with keyword keys and values that can be strings, keywords or numbers."
  [node props]
  (doto node
    (gstyle/setStyle (hmap->jso (walk/keywordize-keys props)))))

; Classes
(defn add-class! "" [node class] (apply gcss/add node [(name class)]) node)
(defn add-classes! "" [node classes] (apply gcss/add node (map name classes)) node)
(defn get-classes "" [node] (vec (gcss/get node)))
(defn remove-classes! "" [node classes] (apply gcss/remove node (map name classes)) node)
(defn remove-class! "" [node class] (apply gcss/remove node [(name class)]) node)
(defn toggle-class! "" [node class] (gcss/toggle node (name class)) node)
(defn toggle-classes! "" [node classes] (doseq [c classes] (toggle-class! node c)) node)
(defn swap-classes! "" [node from-class to-class] (gcss/swap node (name from-class) (name to-class)) node)
(defn has-class? "" [node class] (gcss/has node (name class)))
(defn enable-class! "" [node class ?] (gcss/enable node (name class) ?) node)
(defn set-class! "" [node class] (gcss/set node (name class)) node)
(defn add-remove-classes! "" [node add remove] (gcss/addRemove node (to-array remove) (to-array add)) node)
