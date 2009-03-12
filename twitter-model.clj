; This file provides a convenient way of getting at twitter datat.
; Always returns clojure data structures

(load-file "base64.clj")

(ns twitter
    (:use clojure.contrib.json.read)
    (:use clj-http-client.core)
    (:refer base64))

(def *twitter-url* "https://twitter.com/statuses/")

(defn rest-get
    ([method]
        (read-json-string (let [[status headers body]
            (http-get (str *twitter-url* method ".json"))] body)))
    ([method username password]
        (read-json-string (let [[status headers body]
            (http-get-auth (str *twitter-url* method ".json") username password)] body))))
