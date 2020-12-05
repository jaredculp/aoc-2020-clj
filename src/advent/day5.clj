(ns advent.day5
  (:require [advent.core :as core]
            [clojure.string :as s]))

(defn seat-id [boarding-pass]
  (-> boarding-pass
      (s/replace #"[FL]" "0")
      (s/replace #"[BR]" "1")
      (Integer/parseInt 2)))

(->> (core/input-file "day5.txt")
     (map seat-id)
     (reduce max))
;; => 998

(defn my-seat? [[a b]]
  (= 2 (- b a)))

(->> (core/input-file "day5.txt")
     (map seat-id)
     sort
     (partition 2 1)
     (filter my-seat?)
     first
     first
     inc)
;; => 676
