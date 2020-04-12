# like-whatever

A function for testing if two values are _almost_ equal.

## Usage

```
(require '[like-whatever :refer [≊]])

```

Primitive Values
```
(true? (≊ nil nil))
(false? (≊ 0 nil))
(false? (≊ nil 0))

(true? (≊ 5 5))
(true? (≊ false false))
(true? (≊ "abc" "abc))
(true? (≊ :foo :foo))
```

Regular Expressions and Strings
```
(true? (≊ #".*a.*" "cat"))
(false? "abc" #".*a.*")
```

Predication Functions
```
(true? (≊ odd? 1))
(false? (≊ string? 111))
```

Sequential collections
```
(true? (≊ [1 :two] '(1 :two)))
(true? (≊ [pos? #"a.*"] [3 "apple"]))
(false? (≊ [1 2 3] [1 2]))
(false? (≊ [1 2] [1 2 3]))
```

Maps
```
(true? (≊ {} {}))
(true? (≊ {} {:a 1 :b 2}))
(true? (≊ {:a 1} {:a 1}))
(false? (≊ {:c 3} {:a 1 :b 2}))

(true? (≊ {:a pos? :b #"t.*"}
          {:a 1 :b "two" :other "stuff"}))
```

## License

Copyright © 2020 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
