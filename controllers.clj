(load-file "./template.clj")

(ns flockr.controllers
    (:use compojure clojure.contrib.json.read clj-http-client.core)
    (:refer flockr.template))

(def *twitter-url* "http://twitter.com/statuses/public_timeline.json")

(defn flockr
    ([twitter-name]
        (page "Your Flock" 
            (html 
                [:h1 "Welcome " twitter-name]
                [:form#twitter {:method "POST"}
                    [:input#status {:type "text", :name "status"}]
                    [:input#update {:type "submit", :name "update", :value "update my twitter"}]
                ]
                [:div.feed-grid
                    [:div {:class "feed-column left"}
                        [:div.feed-panel
                            (twitter-feed "Public"
                                (read-json-string (let [[status headers body]
                                    (http-get *twitter-url*)] body)))
                        ]
                        [:div.feed-panel
                            (twitter-feed "Friend Feed"
                                (read-json-string (let [[status headers body]
                                    (http-get *twitter-url*)] body)))
                        ]
                        [:div.feed-panel
                            (twitter-feed "Friend Feed"
                                (read-json-string (let [[status headers body]
                                    (http-get *twitter-url*)] body)))
                        ]
                    ]
                    [:div {:class "feed-column right"}
                        [:div.feed-panel
                            (twitter-feed "Friend Feed"
                                (read-json-string (let [[status headers body]
                                    (http-get *twitter-url*)] body)))
                        ]
                        [:div.feed-panel
                            (twitter-feed "Friend Feed"
                                (read-json-string (let [[status headers body]
                                    (http-get *twitter-url*)] body)))
                        ]
                        [:div.feed-panel
                            (twitter-feed "#palmpre"
                                (get (read-json-string (let [[status headers body]
                                    (http-get (str "http://search.twitter.com/search.json?q=%23palmpre") )] body)) "results"))
                        ]
                    ]
                ]
                [:div.r]
                ))))


(defn home
    ([]
        (page "Flockr"
            (html 
                [:h1 {:class "title"} "Flockr"]
                [:h2 "Twitter Portal"]
                [:a {:href "me"} "View My Twitter"]))))
