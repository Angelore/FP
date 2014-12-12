(ns lab2.core
  (:require [clojure.string :as string]
            [clj-http.client :as http]
            [net.cgrand.enlive-html :as html])
  (:use [slingshot.slingshot :only [try+]]))

;; CRAAAAAAAAAAWLING IIIIIIIIIIIN MY SKIIIIIIIIIIIIN


;; Service functions
(defn un-nil
  "Removes all nils in the sequence."
  [arg]
  (filter #(not (nil? %)) arg))

(un-nil [nil 2 3 nil 4])

(defn create-node [id parent uris depth uri status children location]
  "Create a node in a tree" ; data structure taken from Savich Valery
  {:id id :parent parent :uris uris :depth depth :uri uri :status status :children children :location location})

(defn fetch-url
  "Downloads html, if an exception occurs, sets status to 404 and headers to nil."
  [url]
  (try+
    (http/get url {:throw-exceptions false})
    ;(catch [:status 404] {:status 404 :headers nil}
    ;  (println "No longer exists!"))
    (catch Object _ {:status 404 :headers nil})
   ))

(:headers (fetch-url "http://google.com/nopagehere"))
(fetch-url "http://fkasdflkajsdf.com")
;; End Service functions


;; Parsing and whatnot
(defn parse-blob
  "Returns a vector with numbers to process"
  [inputStr]
  (into [] (string/split-lines inputStr))) ; Check performance in comparsion to butlast? Whatever

(defn parse-file
  "Parses a source file and returns a list of vectors and yada yada yada"
  [fileName]
  (parse-blob (slurp fileName)))

(parse-file "addresses.txt")


;(slurp "testhtml.html")
;(html/select (html/html-resource (slurp "file:///E:/Projects/Clojure/FP-Clojure/lab2/swannodette_enlive-tutorial.htm")) #{[:a]})
;; End Parsing and whatnot


;; Test launch!

;; End Test launch!


;; Entry point

;; End Entry point
