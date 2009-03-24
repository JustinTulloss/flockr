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

(load-file "./controllers.clj")

(ns flockr
    (:use compojure)
    (:refer flockr.controllers))

(defservlet app
    (GET "/"
        (home))

    (GET "/logout"
        (logout session))

    (POST "/login"
        (login params session))

    (POST "/save-prefs"
        (save-prefs params session))

    (POST "/update"
        (update params session))

    (GET "/:twitter-name"
        (flockr (route :twitter-name) session))

    (GET "/*"
        (or (serve-file (route :*)) :next))

    (ANY "*"
        (page-not-found)))

(run-server 
    {:port 8080}
    "/*" app)
