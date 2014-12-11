(ns interact.core-test
  (:require [clojure.test :refer :all]
            [interact.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(a-test)

(deftest phi-test
  (testing "Phi calculation."
    (is (= (phi 0 100) 1.0))))

(phi-test)

;(facts "about pendulums"
;  (fact (phi 0 100) => 1.0)
;  (fact (phi 50 100) => -1.0)
;  (fact (phi 100 100) => 1.0)
;  (fact (< (length2period 1) (length2period 2)) => true)
;  (fact (< (freq2length 2) (freq2length 1)) => true))
