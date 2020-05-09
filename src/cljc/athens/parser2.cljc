(ns athens.parser2
  (:require [instaparse.core :as insta]
            [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            ))

;; A spike to see where we can get!

;; A sample data format:

(def examples
  [{:plaintext "hello"
    :ast "hello"
    :doc "just a string ..."}
   {:plaintext [:span "hello, " "there"]
    :doc "string + span"}
   {:plaintext "a [[page]] we can link to"
    :ast [:span "a " [:link "page"] " we can link to"]
    :doc "Using both a span and a link"}])

(s/def ::string string?)
(s/def ::span
  (s/cat
   :identifier #{:span}
   :children (s/* ::athens-markup)
   )
  )

(s/def ::athens-markup
  (s/or
   :string ::string
   :span ::span
   )
  )

(comment
  (s/explain ::athens-markup (first examples))
  (s/conform ::athens-markup "hello")
  ;; [:plain-string "hello"]

  (s/conform ::athens-markup [:span "hello" "there" [:span "!"]])
  ;; => [:span
  ;;     {:identifier :span,
  ;;      :children
  ;;      [[:string "hello"]
  ;;       [:string "there"]
  ;;       [:span {:identifier :span, :children [[:string "!"]]}]]}]

  )

(comment

  (take 6 (gen/sample (s/gen ::athens-markup)))
  ;; => ("" "" (:span (:span "" (:span "2")) (:span)) "e" (:span) "qJ")
  )

(comment
  ;; Spec 101

  ;; s/explain shows errors if present, otherwise returns nil
  (s/explain string? "123")
  ;; nil
  (s/explain string? 123)

  (s/conform string? "99lkksdlk")
  (s/conform string? 82)


  )

