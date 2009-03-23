; Justin Tulloss
;
; Helper for channels

(load-file "./template.clj")
(load-file "./twitter-model.clj")

(ns flockr.channels
    (:refer flockr.template))

(def *render-types* {

    :public_timeline (fn [session channel]
        (twitter-feed "Public" 
            (twitter/get "public_timeline")
            (:open channel)))

    :following (fn [session channel]
            (twitter-feed "Following" 
                (twitter/get "friends_timeline" 
                    (@session :twitter-user)
                    (@session :twitter-password))
                (:open channel)))

    :search (fn [session channel]
        (twitter-feed (:terms channel)
            (twitter/search (:terms channel))
            (:open channel)))

    :replies (fn [session channel]
        (twitter-feed 
            (str "Replies " (link-twitter-page (@session :twitter-user)))
            (twitter/get "replies"
                (@session :twitter-user) 
                (@session :twitter-password)) 
            (:open channel)))
    :friends (fn [session channel] "Not implemented")
})

(def *default-channels* {
    :1 [
        {:type :following :open true}
        {:type :replies :open false}
    ],
    :2 [
        {:type :search, :terms "#flockr" :open true}
        {:type :public_timeline :open false}
    ]
})

(defn render-channel [channel session]
    ((*render-types* (:type channel)) session channel))
