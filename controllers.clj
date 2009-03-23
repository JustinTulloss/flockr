; Justin Tulloss
;
; Controllers for flockr


(load-file "./template.clj")
(load-file "./channels.clj")
(load-file "./prefs.clj")

(ns flockr.controllers
    (:use compojure) 
    (:use clj-http-client.core)
    (:refer flockr.template))

(defn flockr [twitter-name session]
    (if (and (@session :twitter-user) (@session :twitter-password))
        (let [prefs (flockr.prefs/get (@session :twitter-user))]
            (page "Your Flock" 
                (html 
                    (center-dialog (html [:div {:class "question"} "What are you doing?"]
                        (html [:form#twitter {:method "POST"}
                            [:textarea#status {:name "status"}]
                            [:input#update {:type "submit", :name "update", :value "update my twitter"}]
                            [:div.r]
                        ])))
                    [:div.feed-grid
                        ; Get channels out of user preferences or use the
                        ; defaults
                        (let [channels 
                            (:channels prefs flockr.channels/*default-channels*)]
                            (html [:div {:class "feed-column left"}
                                (map (fn [channel]
                                    (println channel)
                                    [:div.feed-panel
                                        (flockr.channels/render-channel 
                                            channel session)
                                    ]) (:1 channels))
                            ]
                            [:div {:class "feed-column right"}
                                (map (fn [channel]
                                    [:div.feed-panel
                                        (flockr.channels/render-channel 
                                            channel session)
                                    ]) (:2 channels))
                            ]))
                    ]
                    [:div.r]
                    )))
            (page "Your Flock"
                (html 
                    [:h1 "Welcome " twitter-name]
                    (center-dialog (html [:h3 "Please enter your twitter password"]
                        [:form {:method "POST", :action "/login"}
                            [:div
                                [:input {:type "hidden", :name "twitter-user" :value twitter-name}]
                                [:input {:type "password", :name "twitter-password"}]
                                [:input {:type "submit", :value "login"}]
                            ]
                        ]))))))


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
