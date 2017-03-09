(ns loretta.say-hi
  (:require [clojure.spec :as s])
  (:gen-class))


(s/def ::name string?)
(s/def ::style #(contains? #{"free" "conservative" "gentleman"} %))
(s/def ::handsome-person (s/keys :req [::name ::style]))
(s/def ::polite-greeting (s/and string? #(.contains  % "and thank you")))


(defn greeting
  [person]

  {:pre [(s/valid? ::handsome-person person)]
   :post [(s/valid? ::polite-greeting %)]}

  (let [data {"alice" "Nice to meet you~ " 
              "tom" "It's a good day and thank you"
              "leibusi" "Are you okay?..and thank you"}
         v  (get data (::name person))]
    (if (nil? v) "sorry,I don't know who you are ,and thank you." v)))






