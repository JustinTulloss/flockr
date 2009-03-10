(load-file "./template.clj")

(ns flockr.controllers
    (:use compojure clojure.contrib.json.read clj-http-client.core)
    (:refer flockr.template))

(def *twitter-url* "http://twitter.com/statuses/public_timeline.json")

(defn flockr
    ([twitter-name]
        (page "Your Flock" 
            (html [:h1 "Welcome " twitter-name]
                (twitter-feed "Public"
                    (read-json-string (let [[status headers body]
                        (http-get *twitter-url*)] body)))))))

(defn home
    ([]
        (page "Flockr"
            (html 
                [:h1 {:class "title"} "Flockr"]
                [:h2 "Twitter Portal"]
                [:a {:href "me"} "View My Twitter"]))))
