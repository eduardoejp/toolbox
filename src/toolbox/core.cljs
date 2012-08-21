(ns ^{:author "Eduardo Julian <eduardoejp@gmail.com>"
      :doc "Core data-types and type extensions."}
     toolbox.core
  (:require [goog.dom :as gdom]
            [cljs.core :as cljs]))

(defn- ->seq [x] (doall (for [i (range (.-length x))] (.item x i))))

(deftype ToolboxMapEntry [k v]
  IMapEntry
  (-key [_] k)
  (-val [_] v))

; It seems cljs.core already extends JavaScript's array.
#_(extend-type js/Array
  ICounted
  (-count [self] (.-length self))
  
  IEmptyableCollection
  (-empty [_] (js/Array))
  
  ICollection
  (-conj [self o] (cons o (-seq self)))
  
  IEquiv
  (-equiv [self o] (= self o))
  
  IIndexed
  (-nth
    ([self n] (aget self n))
    ([self n not-found] (if (< n (.-length self))
                          (aget self n)
                          not-found)))
  
  ISeqable
  (-seq [self] (->seq self))
  
  ISeq
  (-first [self] (aget self 0))
  (-rest [self] (rest (-seq self)))
  
  INext
  (-next [self] (next (-seq self)))
  
  ILookup
  (-lookup
    ([self i] (-lookup self i nil))
    ([self i not-found] (if (< i (.-length self))
                          (aget self i)
                          not-found)))
  
  IVector
  (-assoc-n [self n val] (aset self n val))
  
  IFn
  (-invoke
    ([self i] (-lookup self i nil))
    ([self i nf] (-lookup self i nf)))
  )

(extend-type js/NodeList
  ICounted
  (-count [self] (.-length self))
  
  ICollection
  (-conj [self o] (cons o (-seq self)))
  
  IEquiv
  (-equiv [self o] (= self o))
  
  ISeqable
  (-seq [self] (->seq self))
  )

(extend-type js/Element
  ;Object
  ;(entry-at [self k] (if-let [v (.getAttribute self (name k))] (ToolboxMapEntry. k v)))
  
  IAssociative
  (-contains-key? [self k] (.hasAttribute self (name k)))
  (-entry-at [self k] (if-let [v (.getAttribute self (name k))] (ToolboxMapEntry. k v)))
  (-assoc [self k v]
          (case k
            :$text (gdom/setTextContent self v)
            :$value (set! (.-value self) v)
            (.setAttribute self (name k) v))
          self)
  
  IMap
  (-dissoc [self k] (.removeAttribute self (name k)) self)
  
  ICounted
  (-count [self] (.-length (.-attributes self)))
  
  IEmptyableCollection
  (-empty [self] (js/document.createElement (.-tagName self)))
  
  ICollection
  (-conj [self o] (doseq [[k v] o] (.setAttribute self (name k) v)) self)
  
  IEquiv
  (-equiv [self o] (= self o))
  
  ISeqable
  (-seq [self] (doall (for [attr (->seq (.-attributes self))
                            :let [k (.-name attr)
                                  v (.-value attr)]]
                        (ToolboxMapEntry. (keyword k) v))))
  
  ILookup
  (-lookup
    ([self k] (.valAt self k nil))
    ([self k not-found] (case k
                          :$text (gdom/getTextContent self)
                          :$value (.-value self)
                          :$parent (.-parentNode self)
                          :$children (.-childNodes self)
                          (if (.hasAttribute self (name k))
                            (.getAttribute self (name k))
                            not-found))))
  
  IFn
  (-invoke
    ([self i] (.valAt self i nil))
    ([self i nf] (.valAt self i nf)))
  )
