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
  "Create a node in a tree" ; data structure acquired from Savich Valery
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

(defn is-redirect-status?
  "Determines, if the status is equal to one of the 3xx statuses/"
  [status]
  (reduce #(or %1 %2) (map #(= status %) '(300 301 302 303 307))))

(defn get-redirected-url
  "Takes the contents of http request, and returns an url if the request was redirected. Otherwise, returns nil."
  [content]
  (let [status (:status content)]
    (if (is-redirect-status? status)
      (:location (:headers content))
      nil)))


(:headers (fetch-url "http://google.com/nopagehere"))
(fetch-url "http://fkasdflkajsdf.com")
(fetch-url "file:///E:/Projects/Clojure/FP-Clojure/lab2/swannodette_enlive-tutorial.htm")
(fetch-url "http://google.com")

(defn is-html?
  "Returns true, if input string contains 'text/html'"
  [inputStr]
  (contains? (set (map string/trim (string/split inputStr #";"))) "text/html"))

(defn normalize-content
  "Takes the contents of http request and returns enlive node list, if the content contains html page. Otherwise, returns nil."
  [content]
  (let [content-type (:content-type (:headers content))]
    (cond
     (= 404 (:status content))
       nil
     (not (is-html? content-type))
       nil
     :else
       (html/html-snippet (:body content))
    )
  )
)

(defn get-urls
  "Generates a list of links in envive node list." ; Example here: https://github.com/swannodette/enlive-tutorial/
                                                   ; This function will also return links like "/otherpage.html".
  [normalized-content]
  (un-nil (map #(:href (:attrs %)) (html/select normalized-content #{[:a]}))))


(normalize-content (fetch-url "http://google.com"))
(get-urls (normalize-content (fetch-url "http://google.com")))
(html/select (normalize-content (fetch-url "http://google.com")) #{[:a]})
;; End Service functions


;; Parsing files and whatnot
(defn parse-blob
  "Returns a vector with strings to process."
  [inputStr]
  (into [] (string/split-lines inputStr)))

(defn parse-file
  "Parses a source file and returns a list of vectors and yada yada yada."
  [fileName]
  (parse-blob (slurp fileName)))

(parse-file "addresses.txt")
;; End Parsing files and whatnot


;; Test launch!

;; End Test launch!


;; Entry point

;; End Entry point
