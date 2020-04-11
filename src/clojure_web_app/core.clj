(ns clojure-web-app.core
  (:require [ring.adapter.jetty   :refer [run-jetty]]
            [clojure.pprint       :refer [pprint]]
            [compojure.core       :refer [routes GET POST]]
            [compojure.route      :refer [not-found]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response   :refer [response]]
            [clojure.java.jdbc    :as    sql]))

(def db-connection {:classname "org.postgresql.Driver"
                    :subprotocol "postgresql"
                    :subname "//localhost:5432/books"
                    :user "postgres"
                    :password "postgres"})

(defn get-all-customers []
  (sql/query db-connection ["select * from books"]))

(def my-routes-handler
  (routes
    (GET  "/info"  []      (response {:version "0.1" :created "April 11th 2020"}))
    (GET  "/books" []      (response (get-all-customers)))
    (POST "/debug" request (response (with-out-str (pprint request))))
    (not-found "<h1>Page not found</h1>")))

(def app
  (wrap-json-body (wrap-json-response my-routes-handler)))

(defn -main []
  (run-jetty app {:port 3000}))
