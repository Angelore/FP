(ns interact.core
  "Pendulum Mayhem"
  (:require [quil.core :refer :all]))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(+ 1 2)

(foo "Stanislau")

(defn bar [x] (foo x))

(bar "Test!")

(def Tau (* 2 Math/PI))

Tau

(defn phi [t period]
  (Math/cos (* Tau (/ t period))))

(phi 0 100)

(phi 150 100)

(defn draw-pendulum [position length]
  (let [p (phi (frame-count) (length2period length))]
    (with-translation [position]
      (with-rotation [(/ p 3)]
        (stroke-weight 5)
        (stroke 128 0 0)
        (fill 100 0 0)
        (line 0 0 0 length)
        (stroke 255)
        (fill 100)
        (ellipse 0 length 30 30)))))

(defn length2period [length]
  (* Tau (Math/sqrt (/ length gravity))))

(def gravity 9.8)

(def pendulums (atom []))

(defn add-pendulum [position length]
  (swap! pendulums conj [position length]))

(defn draw []
  (background-float 0)
  (doseq [p @pendulums]
    (draw-pendulum p)))

(defn run [] (sketch
 :title "Testerino"
 :draw draw
 :size [400 400]))

(add-pendulum [200 50] 300)
(add-pendulum [300 60] 300)
