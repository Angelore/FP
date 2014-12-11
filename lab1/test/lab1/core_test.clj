(ns lab1.core-test
  (:require [clojure.test :refer :all]
            [lab1.core :refer :all]))


(deftest hamming-test
  (testing "Hamming distance has been computed incorrectly."
    (is (= 2 (hamming-distance [1 2 3] [1 3 5])))))

(deftest euclidean-test
  (testing "Euclidean distance has been computed incorrectly."
    (is (= 5 (euclidean-distance [1 2 3] [1 3 5])))))

(hamming-test)
(euclidean-test)

;5.1,3.5,1.4,0.2,Iris-setosa
