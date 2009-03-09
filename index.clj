(load-file "./template.clj")

(ns flockr
    (:use compojure clojure.contrib.zip-filter.xml clojure.contrib.json.read clj-http-client.core)
    (:refer flockr.template)
)

(def *twitter-url* "http://twitter.com/statuses/public_timeline.json")

(defservlet home
    (GET "/"
        (page "Flockr"
            (html [:h1 {:class "title"} "Flockr"]
                (html [:h2 "Twitter Portal"]
                    (html [:a {:href "me"} "View My Twitter"])
                )
            )
        )
    )
    (GET "/:twitter-name"
        (page "Your Flock" 
            (html [:h1 "Welcome " (route :twitter-name)]
                (map twitter-status
                    (read-json-string (let [[status headers body]
                        (http-get *twitter-url*)] body)
                    )
                )
            )
        )
    )
)

(run-server 
    {:port 8080}
    "/*" home
)
