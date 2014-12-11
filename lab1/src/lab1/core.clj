(ns lab1.core
  (:require [clojure.string :as string])
  )

(defn euclidean-distance
  [c1 c2]
  (reduce + (map #(* % %) (map - c1 c2))))

(defn hamming-distance
  [c1 c2]
  (count (filter true? (map not= c1 c2))))

(hamming-distance [1 2 3] [1 3 5])
(euclidean-distance [1 2 3] [1 3 5])
