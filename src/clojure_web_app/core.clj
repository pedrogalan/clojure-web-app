(ns clojure-web-app.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [clojure.pprint     :refer [pprint]]
            [compojure.core     :refer [routes GET]]
            [compojure.route    :refer [not-found]]))

(def my-routes-handler
  (routes
    (GET  "/info"  [] "<h1>Information</h1> This is information about the application")
    (not-found "<h1>Page not found</h1>")))

(defn -main []
  (run-jetty my-routes-handler {:port 3000}))
