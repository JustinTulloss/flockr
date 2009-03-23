; Justin Tulloss
;
; Deals with the preferences.
(ns flockr.prefs
    (:refer-clojure :exclude [get])
    (:require tokyo-cabinet))

(def *db-file* "user-prefs.bdb")

(defn get 
    ([username pref default]
        (tokyo-cabinet/use *db-file* :bdb
            (or (tokyo-cabinet/get (str username "_" pref)) default)))
    ([username pref]
        (get username pref nil)))

(defn save [username pref value]
    (tokyo-cabinet/use *db-file* :bdb
        (tokyo-cabinet/put (str username "_" pref) (str value))))
