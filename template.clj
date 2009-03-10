(ns flockr.template
    (:use compojure clojure.inspector))

(defn page
    ([title body] 
        (html 
            (doctype :html4)
            [:html
                [:head
                    [:title title]
                    [:link {:rel "stylesheet", :href "/stylesheets/flockr.css"}]
                ]
            [:body
                [:div#menu {:class "menu"}
                    [:a {:href "/"} "home"]
                    [:a {:href "/logout"} "logout"]
                ]
                body
                [:div#footer {:class "footer"}
                    "A Justin M. Tulloss Production"
                ]
            ]
        ])))

(defn twitter-status 
    ([tweet]
        (html 
            [:div {:class "tweet"} 
                [:div {:class "tweet-text"} (tweet "text")]
                [:div {:class "tweet-user"} ((tweet "user") "name") ]
            ])))

(defn twitter-feed
    ([title tweets]
        (html [:div {:class "feed"}
                [:div {:class "title"} title]
                (map twitter-status tweets)])))

