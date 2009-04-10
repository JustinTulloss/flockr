; Copyright (C) 2009 Justin Tulloss
; 
; This program is free software: you can redistribute it and/or modify
; it under the terms of the GNU General Public License as published by
; the Free Software Foundation, either version 3 of the License, or
; (at your option) any later version.
; 
; This program is distributed in the hope that it will be useful,
; but WITHOUT ANY WARRANTY; without even the implied warranty of
; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
; GNU General Public License for more details.
; 
; You should have received a copy of the GNU General Public License
; along with this program.  If not, see <http://www.gnu.org/licenses/>.


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
        (page "Your Flock"
            (html
                (center-dialog
                    (html [:div {:class "question"} "What are you doing?"]
                    (html [:form#twitter {:method "POST" :action "/update"}
                        [:textarea#status {:name "status"}]
                        [:input#update {
                            :type "submit",
                            :name "update",
                            :value "update my twitter"}]
                        [:div.r]
                    ])))
                [:div.feed-grid
                    ; Get channels out of user preferences or use the
                    ; defaults
                    (time (let [channels
                        (flockr.prefs/get (@session :twitter-user) :channels
                            flockr.channels/*default-channels*)]
                            (let [channel-agents [
                                (map
                                    (fn [channel]
                                        (send-off (agent "")
                                            (fn [_]
                                                (flockr.channels/render-channel
                                                    channel 
                                                    session)
                                            )))
                                    (first channels))
                                (map
                                    (fn [channel]
                                        (send-off (agent "") 
                                            (fn [_]
                                                (flockr.channels/render-channel
                                                    channel session)
                                            )))
                                    (second channels))]]
                                (apply
                                    await
                                    (concat
                                        (first channel-agents)
                                        (second channel-agents)))

                                (html [:div#col1 {:class "feed-column left"}
                                    (map 
                                        (fn [ch-agent]
                                            (html [:div.feed-panel @ch-agent]))
                                            (first channel-agents))
                                ]
                                [:div#col2 {:class "feed-column right"}
                                    (map
                                        (fn [ch-agent]
                                            (html [:div.feed-panel @ch-agent]))
                                            (second channel-agents))
                                ]))))
                ]
                [:div.r]
                ))
        (page "Your Flock"
            (html 
                [:h1 "Welcome " twitter-name]
                (center-dialog (html [:h3 "Please enter your twitter password"]
                    [:form {:method "POST", :action "/login"}
                        [:div
                            [:input {
                                :type "hidden", 
                                :name "twitter-user" 
                                :value twitter-name}]
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

(defn save-prefs [params session]
    (println params)
    (map (fn [entry] (flockr.prefs/save
            (@session :twitter-user) 
            (key entry) 
            (val entry)))
        params))

(defn update [params session]
    (twitter/update (params :status) 
        (@session :twitter-user)
        (@session :twitter-password))
    (redirect-to (str "/" (@session :twitter-user))))

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
