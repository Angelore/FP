(ns lab1.core
  (:require [clojure.string :as string])
  )

(defn euclidean-distance-square
  "Calculates the square of euclidean distance between two vectors"
  [c1 c2]
  (reduce + (map #(* % %) (map - c1 c2))))

(defn hamming-distance
  "Calculates the hamming distance between two vectors"
  [c1 c2]
  (count (filter true? (map not= c1 c2))))

;; Constants
(def Ra 3)            ; Ra, for defining a point potential
(def Rb (* 1.5 Ra))   ; Rb, for reducing a point potential
(def EpsilonTop 0.5)  ; EpsilonTop, the highest margin for estimation
(def EpsilonBot 0.15) ; EpsilonBot, the lowest margin for estimation

(defn parse-string
  "Returns a vector with numbers to process"
  [inputStr]
  (into [] (map read-string (drop-last (map string/trim (string/split inputStr #",")))))) ; Check performance in comparsion to butlast? Whatever

(parse-string "5.1,3.5,1.4,0.2,Iris-setosa")
