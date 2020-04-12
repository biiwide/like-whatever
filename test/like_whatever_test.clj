(ns like-whatever-test
  (:require [clojure.test :refer :all]
            [like-whatever :refer [≊]]))

(deftest test-primitives
  (are [p? a b]
    (p? (≊ a b))

    true?  nil nil

    false? nil false
    false? nil 1
    false? nil "abc"
    false? nil :kw

    false? false nil
    false? 1     nil
    false? "abc" nil
    false? :kw   nil

    true?  true true
    true?  false false
    true?  "xyz" "xyz"
    true?  456   456

    false? true  false
    false? false true
    false? "abc" "xyz"
    false? 123   456
    false? :aaa  :bbb
    ))


(deftest test-predicates
  (are [p? a b]
    (p? (≊ a b))

    true? odd? 1
    true? pos? 2
    true? neg? -3
    true? string? "abc"

    false? odd? 2
    false? pos? -2
    false? neg? 5
    false? string? :kw
    ))


(deftest test-patterns
  (are [p? re s]
    (p? (≊ re s))

    true?  #".*" "abc"
    true?  #".*a.*" "abc"

    false? #".*a.*" "bee"
    false? #"a"     "cat"
    ))


(deftest test-maps
  (are [p? a b]
    (p? (≊ a b))

    true?  {} {}
    true?  {} {:abc "def"}
    true?  {:foo 1} {:foo 1}
    true?  {"foo" :abc} {"foo" :abc :bar 1}
    true?  {1 "one" 4 "four"} {1 "one" 2 :two 3 'three 4 "four"}

    false? {:bar 5} {}
    false? {:bar 5} {:bar 6}

    true?  {:word #".*a.*"} {:word "cat" :other "stuff"}
    false? {:word #".*b.*"} {:word "cat" :other "stuff"}

    true?  {:a1 {:b1 {:c #"dog"}}}
           {:a0 11 :a1 {:b0 :x :b1 {:c "dog"}} :a2 "x"}

    false? {:a1 {:b1 {:c #"cat"}}}
           {:a0 11 :a1 {:b0 :x :b1 {:c "dog"}} :a2 "x"}
    ))

(deftest test-sequentials
  (are [p? a b]
    (p? (≊ a b))

    false? nil (list)
    false? nil []

    true?  [] (list)
    true?  (list) []

    true?  (list 1) [1]
    true?  [1] (list 1)

    false? [1 2] [1]
    false? [1]   [1 2]

    true?  [odd? even?] [1 2]
    false? [odd? even?] [2 1]
    ))


(deftest test-set-contains
  (are [p? s v]
    (p? (≊ s v))

    false? #{}    nil
    false? #{}    123

    true?  #{nil} nil
    true?  #{123} 123

    false? #{123} odd?
    true?  #{\a \b} \a
    ))


(deftest test-subset
  (are [p? a b]
    (p? (≊ a b))

    true?  #{}  #{}
    true?  #{}  #{1 2}
    true?  #{1} #{1 2}
   )) 

