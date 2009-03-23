; Justin Tulloss
;
; Deals with the preferences.
(ns flockr.prefs
    (:refer-clojure :exclude [get])
    (:use clojure.contrib.json.read)
    (:use clojure.contrib.json.write)
    (:require tokyo-cabinet))

(def *db-file* "user-prefs.hdb")

(defn get [username]
    (tokyo-cabinet/use *db-file*
        (let [prefs (tokyo-cabinet/get username)]
            (if prefs (read-json-string(prefs)) nil))))

(defn save [username prefs]
    (tokyo-cabinet/use *db-file*
        (tokyo-cabinet/put (json-str prefs))))
