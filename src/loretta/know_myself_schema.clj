(ns loretta.know-myself-schema
    (:require [schema.core :as s])
    (:gen-class))

(s/defschema Book
  {:name s/Str
   :author s/Str})

(s/defschema Person
  {::id s/Int
   :name s/Str
   :hobbies {s/Str s/Int}
   (s/optional-key :languages) #{s/Keyword}
   :recommendBook [Book]})

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
    (s/check Person Loretta))

(defn definitely-not-me-if-without-hobbies []
    (s/check Person (dissoc Loretta :hobbies)))
    
