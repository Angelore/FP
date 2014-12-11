(ns lab1.core
  (:require [clojure.string :as string]
            )
  )


;; Distance calculation
(defn euclidean-distance-square
  "Calculates the square of euclidean distance between two vectors"
  [c1 c2]
  (reduce + (map #(* % %) (map - c1 c2))))

(defn hamming-distance-square
  "Calculates the square of hamming distance between two vectors"
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
  "Parses a source file and returns a list of vectors and yada yada yada"
  [fileName]
  (parse-blob (slurp fileName))) ; Worked on the first try. Holy crackers!

(defn pointify
  "Turns a list of vectors into a vector of maps"
  [list]
  (loop [result [] list list]
    (if (= 0 (count list))
    result
    (recur (conj result {:coordinates (last list) ; <- seriously fuck this one
                          :position (count (drop-last list))})
           (drop-last list)))))

;(pointify (parse-file "butterfly.txt"))
;; End Parsing and whatnot


;; Core calculations
(defn potential
  "Calculates a single addendum for the potential calculation"
  [distance]
  (Math/exp (- (* alpha distance))))

(defn revised-potential
  "Calculates a single addendum for the revised potential calculation"
  [distance]
  (Math/exp (- (* beta distance))))

(defn point-potential
  "Calculates the potential of a single point.
    Point: a point which potential needs to be calculated;
    Points: a list of all points;
    calculation-method: hamming or euclidean calc. method."
  [point points calculation-method]
  (assoc point :potential                                               ; Add a property to the map
    (reduce
      #(+ %1                                                            ; Accumulate all potentials, starting with 0
        (potential
          (calculation-method (:coordinates point) (:coordinates %2))   ; Calculate the distance between a point and a point from the list
        )
      ) 0 points                                                        ; Start with 0
    )
  )
)

(defn revise-point-potential
  "Calculates the revisedpotential of a single point."
  [center point calculation-method]
  (

  )
)
;; End Core calculations
;max-key

;(let [points  (pointify (parse-file "irises.txt"))]
;(point-potential (first points) points hamming-distance-square)
;)

;; Entry point
(defn -main
  [& args]
  (if (< (count args) 2)
    (println "Not enough arguments. Please go and stay go.")
    (do)
  )
)
;; End Entry point
