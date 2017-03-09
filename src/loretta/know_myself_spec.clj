(ns loretta.know-myself-spec
  (:require [clojure.spec :as s])
  (:gen-class))

(s/def ::id integer?)
(s/def ::name string?)
(s/def ::hobbies (s/map-of string? integer?))
(s/def :user.Book/name string?)
(s/def :user.Book/author string?)

(s/def ::languages
  (s/* keyword?))

(s/def ::Book
  (s/keys :req-un [:Book/name
                   :user.Book/author]))

(s/def ::Person
  (s/keys :req [::id]
          :req-un [::name
                   ::hobbies
                   ::recommendBook]
          :opt-un [::languages]))


(def Loretta
  {::id 9527
   :name "Loretta"
   :hobbies {"play clojure" 666
             "play game" 233
             "watch movie" 100}
   :languages #{:english :chinese :clj :java :scala}
   :recommendBook [{:name "Clojure for the Brave and True"
                    :author "Daniel Higginbotham"}
                   {:name "Clojure Programming"
                    :author "Chas Emerick"}]})

(defn completeMe []
 (s/valid? ::Person Loretta))

(defn definitely-not-me-if-without-recommendBook []
    (s/valid? ::Person (dissoc Loretta :recommendBook)))

(s/explain-data
  ::Person (dissoc Loretta :recommendBook))
