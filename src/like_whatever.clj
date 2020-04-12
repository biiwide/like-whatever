(ns like-whatever)

(defmulti ≊
  "Test if one value is almost equal to another.
Arguments:
  a: An expected value
  b: The actual value
  
  This function is a multimethod and is open to extension."
  (fn [a b] [(type a) (type b)]))


(def almost=
  "An alias for ≊.
Test if one value is almost equal to another.
Arguments:
  a: An expected value
  b: The actual value
  
  This function is a multimethod and is open to extension."
  ≊)


;; nil is equal to nil
(defmethod ≊ [nil nil] [_ _] true)


;; nil is not equal to any other object
(defmethod ≊ [nil Object] [_ _] false)
(defmethod ≊ [Object nil] [_ _] false)


;; two objects may be equal
(defmethod ≊ [Object Object] [a b] (= a b))


;; Two maps are almost equal when every
;; entry in the first map is almost equal
;; to every entry in the second map
(defmethod ≊ [java.util.Map java.util.Map]
  [a b]
  (every? (fn [[ak av]]
            (when (contains? b ak)
              (≊ av (get b ak))))
          (seq a)))


;; A regex may be almost equal to a
;; string that matches the regex
(defmethod ≊ [java.util.regex.Pattern String]
  [re s]
  (string? (re-matches re s)))


;; A predicate function may be almost equal
;; to a value that satisfies the predicate
(defmethod ≊ [clojure.lang.Fn Object] [p? o] (boolean (p? o)))
(defmethod ≊ [clojure.lang.Fn nil] [p? n] (boolean (p? n)))


;; Two sequential collections are almost
;; equal when every value in the first
;; collection is almost equal to every
;; value in the second collection
(defmethod ≊ [clojure.lang.Sequential clojure.lang.Sequential]
  [a b]
  (cond (and (empty? a) (empty? b)) true
        (empty? a) false
        (empty? b) false
        :else
        (if (≊ (first a) (first b))
          (recur (next a) (next b))
          false)))


;; A set is almost equal to a non-set when the
;; set contains the non-set value
(defmethod ≊ [java.util.Set nil] [s v] (contains? s v))
(defmethod ≊ [java.util.Set Object] [s v] (contains? s v))


;; Two sets are almost equal when the first is
;; a subset of the second
(defmethod ≊ [java.util.Set java.util.Set]
  [a b]
  (every? (partial contains? b) (seq a)))

