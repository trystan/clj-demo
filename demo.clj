;; Clojure!



;; www.tryclj.com

;; www.lighttable.com

;; brew install leiningen




































;; Syntax

;; procedural:       write(file, text, something)

;; object oriented:  file.write(text, something)

;; lisp:             (write file text something)


(count "Clojure")


;; Math operators are just functions.

(+ 1 2)


(+ 1 2 3)


; (+)


; (*)


; (/)


;; Keywords evaluate to themselves (like symbols in Ruby or Scala).

(= :a :b)


(< 1 2 3)

(< 1 1 3)

(<= 1 3 3)


(def two 2)


(defn twice [x]
  (* x two))


(let [a 3
      b (twice a)
      c (twice b)]
  (twice c))


(if (= (twice 3) 6)
  (twice 1)
  (twice 10))


(case (twice 2)
  1  "one"
  2  "two"
  3  "three"
  4  "four"
     "too much")




































;; collections

[1 2 3]


{ :x 4, :y 5, [1 2 3] "why?" }


(list 1 2 3)




































;; Higher order functions

(filter even? (range 5 25))


(map twice [1 2 3])


;; map is also zipWith

(map + [1 2 3] [4 5 6])




;; partial application

(def add-five (partial + 5))

(add-five 10)


;; function composition

(def twice-plus-one (comp inc twice))

(twice-plus-one 3)




































;; Java interop

Math/PI


(Math/min 5 2)


(new java.util.Date)


(let [d (new java.util.Date)]
  (.setYear d 123)
  (.setMonth d 4)
  (.setDate d 5)
  (.getTime d))


(import java.util.Date)


(new Date)



































;; An example function

(defn recursive-swap-first-two [xs]
  (if (and (list? xs) (> (count xs) 1))
    (let [a (recursive-swap-first-two (first xs))
          b (recursive-swap-first-two (second xs))
          cs (map recursive-swap-first-two (drop 2 xs))]
      (conj cs a b)) ;; conj *prepends* on lists and *appends* on vectors
    xs))


(recursive-swap-first-two
  1)

(recursive-swap-first-two
  (list :one :two :three :four))

(recursive-swap-first-two
  (list "a" "b" "c" (list "d" "e" "f") "g" "h"))




































;; Macros!

;; A function takes values as input and returns values at runtime.
;; A macro takes code as input and returns code at read time.
;; Since code and data are the same, any function that has lists as input and output can be a macro.

(defmacro oo-syntax-1 [expression]
  (recursive-swap-first-two expression))

(oo-syntax-1 ((java.util.Date new) .getMinutes))





(defmacro oo-syntax [& expressions]
  `(do ~@(map recursive-swap-first-two expressions)))

(let [d (new java.util.Date)]
  (oo-syntax
   (d .setYear 1)
   (d .setMonth 1)
   (d .setMinutes 1)
   (d .toString)))





(doto (new Date)
  (.setYear 1)
  (.setMonth 1)
  (.setHours 1)
  (.setMinutes 1)
  (.setSeconds 1))






;; Some macro possibilties

(defmacro math: [& symbols] :not-implemented)

(math: 5 x + sin(4 y) * 3 pi)
;; (+ (* 5 x) (* (Math/sin 4 y) 3 Math/PI))





(defmacro select [& symbols] :not-implemented)

(select * from people where first_name = "Trystan")




;; core.typed, strong static typing as a library




;; core.async, go-style channels and go routines on the JVM, CLR, and javascript.




































;; Multimethods

;; Polymorphism on steroids. Dispatch on basically anything at runtime.

(defmulti encounter (fn [x y] [(:Species x) (:Species y)]))

(defmethod encounter [:Bunny :Lion] [b l] :flee)
(defmethod encounter [:Lion :Bunny] [l b] :eat)
(defmethod encounter [:Lion :Lion] [l1 l2] :fight)
(defmethod encounter [:Bunny :Bunny] [b1 b2] :mate)
(defmethod encounter :default [x y] :wtf?)

(let [b1 { :Species :Bunny, :other :stuff }
      b2 { :Species :Bunny, :other :stuff }
      l1 { :Species :Lion, :other :stuff }
      l2 { :Species :Lion, :other :stuff }]
  (encounter l1 b2))



;; Traditional OO single polymorphism dispatches on the runtime type of the "this" parameter only.

(defmulti single-dispatch-method (fn [obj & parameters] (type obj)))




































;; REPL driven development with asteroids

(def point { :x 8 :y 6 :vx 0.1 :vy -0.02 })

(get point :x)

(assoc point :mass 2 :radius 2)

(dissoc point :vx :vy)

(defn update [p]
  (assoc p :x (+ (:x p) (:vx p))
           :y (+ (:y p) (:vy p))))

(update point)


;; On to clj-asteroids!
;;   https://github.com/trystan/clj-asteroids
