(ns lab1.core-test
  (:require [clojure.test :refer :all]
            [lab1.core :refer :all]))


(deftest hamming-test
  (testing "Hamming distance has been computed incorrectly."
    (is (= 2 (hamming-distance-square [1 2 3] [1 3 5])))))

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

(deftest parsing-file
  (testing "The file was parsed incorrectly. Maybe. Maybe not. It's classified."
    (is (= 15 (count (parse-file "butterfly.txt"))))))

(deftest pointify-test
  (testing "The list was parsed incorrectly."
    (is (= 15 (count (pointify (parse-file "butterfly.txt")))))))

(deftest potential-calculation-test
  (testing "The potential was computed incorrectly."
    (is (= 30.703912665847533 (do
                                (let [points  (pointify (parse-file "irises.txt"))]
                                  (:potential (point-potential (first points) points hamming-distance-square))))))))

(hamming-test)
(euclidean-test)
(parsing-test)
(parsing-blob)
(parsing-file)
(pointify-test)
(potential-calculation-test)

;5.1,3.5,1.4,0.2,Iris-setosa
;4.9,3.0,1.4,0.2,Iris-setosa
;4.7,3.2,1.3,0.2,Iris-setosa
;
