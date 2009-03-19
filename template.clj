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
                    [:script {:src "/javascripts/cobra.js", :type "text/javascript"}]
                    [:script {:src "/javascripts/flockr.js", :type "text/javascript"}]
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
            (re-matcher #"https?://[-\w]+\.\w[-\w/]*+" text)
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
            [:div {:class "tweet"} 
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
    ([title tweets]
        (html [:div {:class "feed"}
                [:div {:class "title"} title]
                (map twitter-status tweets)])))

