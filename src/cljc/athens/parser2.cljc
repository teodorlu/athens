(ns athens.parser2
  (:require [instaparse.core :as insta]
            [clojure.spec.alpha :as s]
            ))

;; A spike to see where we can get!

;; A sample data format:

'(should-equal (parse "a [[page]] we can link to")
               [:span "a " [:link "page"] " we can link to"])


(gen/generate (s/gen int?))
