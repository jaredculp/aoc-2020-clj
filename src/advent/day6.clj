(ns advent.day6
  (:require [advent.core :as core]
            [clojure.set]
            [clojure.string :as s]))

(->> (core/input-file "day6.txt" #"\n\n")
     (map s/split-lines)
     (map s/join)
     (map set)
     (map count)
     (reduce +))
;; => 6763

(->> (core/input-file "day6.txt" #"\n\n")
     (map s/split-lines)
     (map #(map set %))
     (map #(apply clojure.set/intersection %))
     (map count)
     (reduce +))
;; => 3512
