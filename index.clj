(load-file "./controllers.clj")

(ns flockr
    (:use compojure)
    (:refer flockr.controllers))

(defservlet app
    (GET "/"
        (home))

    (GET "/logout"
        "Logged Out")

    (GET "/:twitter-name"
        (flockr (route :twitter-name)))

    (GET "/*"
        (or (serve-file (route :*)) :next))

    (ANY "*"
        (page-not-found)))

(run-server 
    {:port 8080}
    "/*" app)
