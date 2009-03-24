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
