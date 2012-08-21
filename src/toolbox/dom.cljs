(ns ^{:author "Eduardo Julian <eduardoejp@gmail.com>"
      :doc "Utilities for handling the DOM."}
     toolbox.dom
  (:require [goog.dom :as gdom]
            [toolbox.core :as $]))

(defn by-id "" [id] (gdom/getElement id))
(defn by-class "" [class & [root]] (gdom/getElementsByClass class root))
(defn by-class1 "" [class & [root]] (gdom/getElementByClass class root))
(defn by-tag-and-class "" [tag class & [root]] (gdom/getElementsByTagNameAndClass tag class root))
(defn by-tag-and-class1 "" [tag class & [root]] (first (by-tag-and-class tag class root)))
(defn by-tag "" [tag & [root]] (gdom/getElementsByTagNameAndClass tag nil root))
(defn by-tag1 "" [tag & [root]] (first (by-tag tag root)))

(defn append! "" [node & xs] (apply gdom/append node xs) node)
(defn prepend! "" [node & xs] (doseq [x (reverse xs)] (gdom/insertChildAt node x 0)) node)
(defn insert-before! "" [node ref] (gdom/insertSiblingBefore node ref) node)
(defn insert-after! "" [node ref] (gdom/insertSiblingAfter node ref) node)

(defn text-node "" [txt] (gdom/createTextNode txt))

(defn replace! "" [old new] (gdom/replaceNode new old))

(defn remove! "" [node] (gdom/removeNode node))
(defn remove-children! "" [node] (gdom/removeChildren node))

(defn last-elem-child "" [x] (gdom/getLastElementChild x))
