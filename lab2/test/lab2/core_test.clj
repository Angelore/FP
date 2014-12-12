(ns lab2.core-test
  (:require [clojure.test :refer :all]
            [lab2.core :refer :all]))

(deftest test-404
  (testing "The 404 address was handled incorrectly."
    (is (= 404 (:status (fetch-url "http://googfsdfasdfle.com"))))))

(deftest link-count
  (testing "The url count is not correct."
    (is (= 11 (count (get-urls (normalize-content (fetch-url "http://google.com"))))))))

(test-404)
(link-count)
