(ns lab2.core (:gen-class)
  (:require [clojure.string :as string]
            [clj-http.client :as http]
            [net.cgrand.enlive-html :as html]))

;; CRAAAAAAAAAAWLING IIIIIIIIIIIN MY SKIIIIIIIIIIIIN


;; Service functions
(defn un-nil
  "Removes all nils in the sequence."
  [arg]
  (filter #(not (nil? %)) arg))

(un-nil [nil 2 3 nil 4])

(defn uuid []
  "Get a random uuid."
  (str (java.util.UUID/randomUUID)))

(defn create-node [id parent uris depth uri status children location]
  "Create a node in a tree" ; data structure acquired from Savich Valery
  {:id id :parent parent :uris uris :depth depth :uri uri :status status :children children :location location})

(defn create-root-node
  "Creates a root node to start algorithm from."
  [urls depth]
  (create-node (uuid) {:id nil} urls depth "root" nil (atom []) nil))

(defn fetch-url
  "Downloads html, if an exception occurs, sets status to 404 and headers to nil."
  [url]
  (try                ; Example here: http://stackoverflow.com/questions/20708580/try-and-slingshot-try-differences
    (http/get url)
    (catch Exception e {:status 404 :headers nil})
   ))

(defn is-redirect-status?
  "Determines, if the status is equal to one of the 3xx statuses."
  [status]
  (reduce #(or %1 %2) (map #(= status %) '(300 301 302 303 307))))

(defn get-redirected-url
  "Takes the contents of http request, and returns an url if the request was redirected. Otherwise, returns nil."
  [content]
  (let [status (:status content)]
    (if (is-redirect-status? status)
      (:location (:headers content))
      nil)))

;(:headers (fetch-url "http://google.com/nopagehere"))
;(fetch-url "http://fkasdflkajsdf.com")
;(fetch-url "file:///E:/Projects/Clojure/FP-Clojure/lab2/swannodette_enlive-tutorial.htm")
;(fetch-url "http://google.com")

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
                                                   ; This function will also return links like "/otherpage.html"
  [normalized-content]
  (un-nil (map #(:href (:attrs %)) (html/select normalized-content #{[:a]}))))

;(normalize-content (fetch-url "http://google.com"))
;(get-urls (normalize-content (fetch-url "http://google.com")))
;(count (get-urls (normalize-content (fetch-url "http://google.com"))))
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


;; Core functions
(defn generate-tree
  "Genereates a tree of nodes, returns the root node."
  [filename depth]
  (let [urls (parse-file filename)               ; Get urls from file
        parent (create-root-node urls depth)]    ; Create a parent node
    (iterate-node parent urls depth)             ; Start iterating through nodes
    parent                                       ; Return parent for printing
  )
)

(defn iterate-node
  "Iterates through nodes recursively."
  [node urls depth]
  (let [new-depth (dec depth)]                   ; Reduce depth by one
    (if (= depth 0)                              ; If stumble upon a leaf,
      node                                       ; return leaf
      (doseq                                     ; http://clojuredocs.org/clojure.core/doseq
        [child (pmap #(parse-page node % depth) urls)]     ; In parallel, create a child node from every url
          (iterate-node child (:uris child) new-depth))    ; And iterate through it
    )
  )
)

;(defn iterate-node
;  [node urls depth]
;  (loop [node node urls urls new-depth (dec depth)]
;    (if (= depth 0)
;      node
;      (doseq                                     ; http://clojuredocs.org/clojure.core/doseq
;        [child (pmap #(parse-page node % depth) urls)]     ; In parallel, create a child node from every url
;          (recur child (:uris child) (dec new-depth)))
;    )
;  )
;)

(defn parse-page
  "Parses the page, returns child node."
  [parent url depth]
  (let [content (fetch-url url)                         ; Download page
        normalized-content (normalize-content content)  ; Create a list of enlive nodes
        status (:status content)
        id (uuid)]
    (swap! (:children parent) conj                      ; Add a child to the list of children
      (if (not (nil? normalized-content))               ; Decide if the child is empty
        (create-node
           id
           (:id parent)
           (get-urls normalized-content)
           depth
           url
           status
           (atom [])
           (get-redirected-url content)
        )
        (create-node
           id
           (:id parent)
           '()
           depth
           url
           status
           (atom [])
           (get-redirected-url content)
        )
      )
    )
    (:children parent)
  )
)

(defn print-results
  "Prints all results to the console."
  [node nesting]
  (let [indent (* 2 nesting)   ; Generate the resulting string
        uri (:uri node)
        status-message         ; Generate a status message
          (if (= (:status node) 404)
            " bad url."
            (let [message (str " " (count (:uris node)) " link(s)")]
              (when (not (nil? (:location node)))
                (str message " redirects to " (:location node)))
              message
            )
          )]
    (if (> nesting 0)         ; Do not print the root node
      (println (str (apply str (repeat indent " ")) uri status-message)))
  )
  (doseq [child @(:children node)] (print-results child (inc nesting)))   ; Repeat for all children
)
;; End Core functions


;; Test launch!
(print-results (generate-tree "addresses.txt" 1) 0)
;; End Test launch!


;; Entry point
(defn -main
  [& args]
  (if (< (count args) 2)
    (println "Not enough arguments. Please go and stay go.")
    (let [filepath (first args)
          nesting (last args)]
      (print-results (generate-tree filepath nesting) 0)
    )
  )
)
;; End Entry point
