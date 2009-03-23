(load-file "./controllers.clj")

(ns flockr
    (:use compojure)
    (:refer flockr.controllers))

(defservlet app
    (GET "/"
        (home))

    (GET "/logout"
        (logout session))

    (POST "/login"
        (login params session))

    (POST "/save-prefs"
        (save-prefs params session))

    (POST "/update"
        (update params session))

    (GET "/:twitter-name"
        (flockr (route :twitter-name) session))

    (GET "/*"
        (or (serve-file (route :*)) :next))

    (ANY "*"
        (page-not-found)))

(run-server 
    {:port 8080}
    "/*" app)
