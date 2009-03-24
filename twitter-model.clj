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


; This file provides a convenient way of getting at twitter datat.
; Always returns clojure data structures

(ns twitter
    (:refer-clojure :exclude [get])
    (:use clojure.contrib.json.read)
    (:use clj-http-client.core)
    (:import (java.net URLEncoder)))

(def *twitter-url* "https://twitter.com/statuses/")

(defn get
    ([method]
        (read-json-string (let [[status headers body]
            (http-get (str *twitter-url* method ".json"))] body)))
    ([method username password]
        (read-json-string (let [[status headers body]
            (http-get-auth (str *twitter-url* method ".json") username password)] body))))

(defn search
    ([term]
        ((read-json-string (let [[status headers body]
            (http-get (str "http://search.twitter.com/search.json?q=" 
                (URLEncoder/encode term "UTF-8")) )] body)) "results")))

(defn update
    ([status username password]
        (read-json-string (let [[return-status headers body]
            (http-post-auth 
                (str *twitter-url* "update.json") 
                username password (str "status=" (URLEncoder/encode status)))] body))))
