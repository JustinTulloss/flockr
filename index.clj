(ns index
    (:use compojure)
)

(defservlet home
    (GET "/"
        (html [:h1 {:class "title"} "Flockr"]
            (html [:h2 "Twitter Portal"])
        )
    )
)

(run-server {:port 8080}
    "/*" home
)
