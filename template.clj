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
            [:p {:class "tweet"} 
                [:div {:class "tweet-text"} (tweet "text")]
                [:div {:class "tweet-user"} ((tweet "user") "name") ]
            ])))

