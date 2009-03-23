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
                    [:script {:src "/javascripts/jquery-1.3.2.min.js", :type "text/javascript"}]
                    [:script {:src "/javascripts/jquery-ui.js", :type "text/javascript"}]
                    [:script {:src "/javascripts/cobra.js", :type "text/javascript"}]
                    [:script {:src "/javascripts/flockr.js", :type "text/javascript"}]
                ]
            [:body
                [:div#menu {:class "menu"}
                    [:span {:class "links"}
                        [:a {:href "#"} "add channel"]
                        [:a {:href "/logout"} "logout"]
                    ]
                    [:a {:href "/"}
                        [:img {:src "/images/logo.png", :alt "floc.kr"}]
                    ]
                ]
                body
                [:div#footer {:class "footer"}
                    "A Justin M. Tulloss Production"
                ]
            ]
        ])))

(defn center-dialog
    ([markup]
        (html [:center [:div {:class "dialog"} markup]])))

(defn link-twitter-page
    "Takes a twitter user name and returns a link with an @ in front of it"
    ([twitter-name]
        (str "@" (html [:a {:href (str "http://twitter.com/" twitter-name)} 
            twitter-name]))))

(defn urlize
    "A function that takes some text and creates links out of URLs
    that it finds"
    ([text]
        (.replaceAll 
            (re-matcher #"https?://[-\w/\.]*+" text)
        (html [:a {:href "$0"} "$0"])))) 

(defn link-replies
    "Takes a tweet and links the @<username> components to the appropriate
    profiles"
    ([text]
        (.replaceAll 
            (re-matcher #"@(\w+)" text)
        (str "@" (html [:a {:href "http://twitter.com/$1"} "$1"])))))

(defn twitter-status 
    ([tweet]
        (html 
            [:div {:class "tweet" } 
                [:div {:class "tweet-photo"} 
                    [:img {:src ((tweet "user" {}) "profile_image_url" '())}]
                ]
                [:div {:class "tweet-text"} 
                    (link-replies (urlize (tweet "text")))]
                (when (tweet "user")
                    [:div {:class "tweet-user"} 
                        (html [:a {:href 
                            (str "http://twitter.com/" 
                                ((tweet "user" {}) "screen_name" '()))} 
                            ((tweet "user" {}) "name" '())]) ])
            ])))

(defn twitter-feed
    ([title tweets show]
        (html [:div {:class "feed"}
                [:div {:class "title"}
                    [:span {:class "options"}
                        [:a {:href "#"} [:img.remove {:src "images/x.png"}]]
                    ]
                    title
                ]
                [:div {:class "tweets" :style (when (not show) "display:none;")}
                    (map twitter-status tweets)
                ]
            ])))

