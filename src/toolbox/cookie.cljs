(ns ^{:author "Eduardo Julian <eduardoejp@gmail.com>"
      :doc "Utilities for dealing with browser cookies."}
     toolbox.cookie
  (:refer-clojure :exclude [get set!])
  (:require [goog.net.cookies :as gcookies]
            [cljs.reader :as reader]
            [toolbox.core :as $]))

(defn get "Returns the cookie after parsing it with cljs.reader/read-string."
  [k] (reader/read-string (or (.get goog.net.cookies (name k)) "nil")))

(defn set! "Stores the cookie value using pr-str."
  [k v] (.set goog.net.cookies (name k) (pr-str v)))

(defn remove! "" [k] (.remove goog.net.cookies (name k)))
