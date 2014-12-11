(ns lab1.core
  (:require [clojure.string :as string]
            )
  )


;; Distance calculation
(defn euclidean-distance-square
  "Calculates the square of euclidean distance between two vectors"
  [c1 c2]
  (reduce + (map #(* % %) (map - c1 c2))))

(defn hamming-distance
  "Calculates the hamming distance between two vectors"
  [c1 c2]
  (count (filter true? (map not= c1 c2))))
;; End Distance calculation


;; Constants
(def Ra 3.0)          ; Ra, for defining a point potential
(def Rb (* 1.5 Ra))   ; Rb, for reducing a point potential
(def EpsilonTop 0.5)  ; EpsilonTop, the highest margin for estimation
(def EpsilonBot 0.15) ; EpsilonBot, the lowest margin for estimation
(def alpha (/ 4 (* Ra Ra)))  ; Calculate alpha param for calculating point potential.
(def beta (/ 4 (* Rb Rb)))   ; Calculate beta param for calculating point potential.
;; End Constants


;; Parsing and whatnot
(defn parse-string
  "Returns a vector with numbers to process"
  [inputStr]
  (into [] (map read-string (drop-last (map string/trim (string/split inputStr #",")))))) ; Check performance in comparsion to butlast? Whatever

(defn parse-blob
  "Returns a list of vectors with coordinates"
  [blob]
  (filter #(not= 0 (count %)) (map parse-string (string/split-lines blob))))

(defn parse-file
  "Parses a source file and returns a list of vectors and yada yada yada."
  [fileName]
  (parse-blob (slurp fileName))) ; Worked on the first try. Holy crackers!
;; End Parsing and whatnot


;; Core calculations

;; End Core calculations


;; Entry point
(defn -main
  [& args]
  (if (< (count args) 2)
    (println "Not enough arguments. Please go and stay go.")
    (do)
  )
)
;; End Entry point
