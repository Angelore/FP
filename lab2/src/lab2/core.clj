(ns lab2.core
  (:use [slingshot.slingshot :only [try+]]))

;; CRAAAAAAAAAAWLING IIIIIIIIIIIN MY SKIIIIIIIIIIIIN

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


;; Service functions
(defn un-nil
  "Removes all nils in the sequence."
  [arg]
  (filter #(not (nil? %)) arg))

(un-nil [nil 2 3 nil 4])


;; End Service functions


;; Parsing and whatnot

;; End Parsing and whatnot


;; Test launch!

;; End Test launch!


;; Entry point

;; End Entry point
