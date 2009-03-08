(ns index
    (:use compojure)
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
)

(defservlet me
    (GET "/"
        (page "Your Flock" (html [:h1 "Welcome"]))
    )
)

(run-server 
    {:port 8080}
    "/*" home
    "/me" me
)
