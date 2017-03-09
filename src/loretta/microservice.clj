(ns loretta.microservice
  (:require [clojure.spec :as s])
  (:gen-class))

(defmulti api-with-version #(get % :api-version))

(s/def ::request-param string?)
(s/def ::request-url string?)
(s/def ::request-token (s/and string? #(< 20 (.length %))))
(s/def ::api-check (s/multi-spec api-with-version #(get % :api-version))) 

(defmethod api-with-version :api-demo [_]
  (s/keys :req [::request-url]))

(defmethod api-with-version :api-dev [_]
  (s/keys :req [::request-url ::request-param]))

(defmethod api-with-version :api-awesome [_]
  (s/keys :req [ ;; ::request-url  比如这个版本帅到可以不需要URL
                ::request-param ::request-token]))



(defn data [] 
  {:demo {:api-version :api-demo 
                       ::request-url "https://clojure.org/"}
   :dev {:api-version :api-dev 
                      ::request-url "https://clojure.org/" 
                      ::request-param "德玛西亚 ~_~!"}
   :awesome {:api-version :api-awesome 
                   ;; ::request-url "https://clojure.org/"
                   ::request-param "面对疾风吧 ~_~!"
                   ::request-token  (-> (java.util.UUID/randomUUID) .toString)}
   :dirtydata { ;; :api-version  -- lost api version
               ::request-url "https://clojure.org/"}})
(defn check-req [x]
  (s/valid? ::api-check x))

(defn check-rep "Always true , and I will do it later" [x]
  true)

(defmulti api-service #(get % :api-version))

(defmethod api-service :api-demo [x]
  {:pre [(check-req x)]
   :post [#(check-rep %)]}
  (str "It's demo service with args:" x))

(defmethod api-service :api-dev [x]
   {:pre [(check-req x)]
    :post [#(check-rep %)]}
  (str "It's dev service with args:" x))

(defmethod api-service :api-awesome [x]
   {:pre [(check-req x)]}
   :post [#(check-rep %)]
  (str "It's awesome service with args:" x))

(defmethod api-service nil [x]
  (str "No support service for args:" x))

;;;; run code here 
(-> (data) :demo api-service)
(-> (data) :dev api-service)
(-> (data) :awesome api-service)
(-> (data) :dirtydata api-service)
