(ns flockr.template
    (:use compojure clojure.inspector)
)

(defn page
    ([title body] 
        (html [:html
            (html [:head
                (html [:title title])
            ]
            (html [:body
                (html [:div#menu "Menu"]
                    body
                )
            ])
            )
        ])
    )
)

(defn twitter-status 
    ([tweet]
        (html [:p {:class "tweet"} 
            (html [:div {:class "tweet-text"} (get tweet "text")])
            (html [:div {:class "tweet-user"} (get (get tweet "user") "name") ])
        ])
    )
)

