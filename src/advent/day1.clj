(ns advent.day1
  (:require [advent.core :as core]
            [clojure.math.combinatorics :as combo]))

(as-> (core/input-file "day1.txt") x
  (map #(read-string %) x)
  (combo/combinations x 2)
  (filter #(= 2020 (reduce + %)) x)
  (first x)
  (reduce * x))
;; => 646779

(as-> (core/input-file "day1.txt") x
  (map #(read-string %) x)
  (combo/combinations x 3)
  (filter #(= 2020 (reduce + %)) x)
  (first x)
  (reduce * x))
;; => 246191688
