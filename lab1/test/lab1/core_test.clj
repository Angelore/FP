(ns lab1.core-test
  (:require [clojure.test :refer :all]
            [lab1.core :refer :all]))


(deftest hamming-test
  (testing "Hamming distance has been computed incorrectly."
    (is (= 2 (hamming-distance [1 2 3] [1 3 5])))))

(deftest euclidean-test
  (testing "Euclidean distance has been computed incorrectly."
    (is (= 5 (euclidean-distance-square [1 2 3] [1 3 5])))))

(deftest parsing-test
  (testing "The string was parsed incorrectly."
    (is (= [5.1 3.5 1.4 0.2] (parse-string "5.1,3.5,1.4,0.2,Iris-setosa")))))

(deftest parsing-blob
  (testing "The blob was parsed incorrectly."
    (is (= '([5.1 3.5 1.4 0.2]
             [4.9 3.0 1.4 0.2]
             [4.7 3.2 1.3 0.2])
           (parse-blob "5.1,3.5,1.4,0.2,Iris-setosa
                        4.9,3.0,1.4,0.2,Iris-setosa
                        4.7,3.2,1.3,0.2,Iris-setosa
                        ")))))

(hamming-test)
(euclidean-test)
(parsing-test)
(parsing-blob)

;5.1,3.5,1.4,0.2,Iris-setosa
;4.9,3.0,1.4,0.2,Iris-setosa
;4.7,3.2,1.3,0.2,Iris-setosa
;
