(load-file "./template.clj")
(load-file "./twitter-model.clj")

(ns flockr.controllers
    (:use compojure) 
    (:use clojure.contrib.json.read)
    (:use clj-http-client.core)
    (:refer flockr.template))

(defn flockr
    ([twitter-name session]
        (if (and (@session :twitter-user) (@session :twitter-password))
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
                                    (twitter/rest-get "public_timeline"))
                            ]
                            [:div.feed-panel
                                (twitter-feed "Public" 
                                    (twitter/rest-get "public_timeline"))
                            ]
                            [:div.feed-panel
                                (twitter-feed "Public" 
                                    (twitter/rest-get "public_timeline"))
                            ]
                        ]
                        [:div {:class "feed-column right"}
                            [:div.feed-panel
                                (twitter-feed "Friends" 
                                    (twitter/rest-get "friends_timeline" 
                                        (@session :twitter-user) 
                                        (@session :twitter-password)))
                            ]
                            [:div.feed-panel
                                (twitter-feed "#pre OR Palm"
                                    (twitter/search "#pre OR Palm"))
                            ]
                            [:div.feed-panel
                            ]
                        ]
                    ]
                    [:div.r]
                    ))
            (page "Your Flock"
                (html 
                    [:h1 "Welcome " twitter-name]
                    [:h3 "Please enter your twitter password"]
                    [:form {:method "POST", :action "/login"}
                        [:div
                            [:input {:type "hidden", :name "twitter-user" :value twitter-name}]
                            [:input {:type "password", :name "twitter-password"}]
                            [:input {:type "submit", :value "login"}]
                        ]
                    ])))))


(defn home
    ([]
        (page "Flockr"
            (html 
                [:h1 {:class "title"} "Flockr"]
                [:h2 "Twitter Portal"]
                [:div#login-form
                    [:form {:method "POST", :action "/login"}

                        [:div
                            [:label {:for "twitter-user"} "twitter user name or email:"]
                            [:input {:type "text", :name "twitter-user"}]
                        ]

                        [:div
                            [:label {:for "twitter-password"} "twitter password:"]
                            [:input {:type "password", :name "twitter-password"}]
                        ]

                        [:div
                            [:input {:type "submit", :value "login"}]
                        ]
                    ]
                ]))))

(defn login
    ([params session]
        ; Since the flockr page has to do error checking anyway, we
        ; don't do any here. Just store what we got and send them on
        ; their way.
        (dosync
            (alter session assoc :twitter-user (params :twitter-user))
            (alter session assoc :twitter-password (params :twitter-password)))
        (compojure.http.helpers/redirect-to (str "/" (@session :twitter-user)))))

(defn logout
    ([session]
        (dosync
            (alter session assoc :twitter-user nil)
            (alter session assoc :twitter-password nil)
            (redirect-to "/"))))
