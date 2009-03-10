(ns flockr.template
    (:use compojure clojure.inspector))

(defn page
    ([title body] 
        (html 
            [:html
                [:head
                    [:title title]
                ]
            [:body
                [:div#menu "Menu"]
                    body
            ]
        ]))) 

(defn twitter-status 
    ([tweet]
        (html 
            [:p {:class "tweet"} 
                [:div {:class "tweet-text"} (get tweet "text")]
                [:div {:class "tweet-user"} (get (get tweet "user") "name") ]
            ])))

