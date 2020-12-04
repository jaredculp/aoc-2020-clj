(ns advent.day2
  (:require [advent.core :as core]))

(->> (core/input-file "day2.txt")
     (filter #(let [groups (re-find #"(\d+)-(\d+) ([a-z]): (.*)" %)
                    min-n  (read-string (nth groups 1))
                    max-n  (read-string (nth groups 2))
                    letter (nth groups 3)
                    pass   (nth groups 4)
                    n      (count (re-seq (re-pattern letter) pass))]
                (and (>= n min-n) (<= n max-n))))
     count)
;; => 439

(->> (core/input-file "day2.txt")
     (filter #(let [groups     (re-find #"(\d+)-(\d+) ([a-z]): (.*)" %)
                    first-pos  (dec (read-string (nth groups 1)))
                    second-pos (dec (read-string (nth groups 2)))
                    letter     (nth (nth groups 3) 0)
                    pass       (nth groups 4)]
                (not=
                 (= (nth pass first-pos) letter)
                 (= (nth pass second-pos) letter))))
     count)
;; => 584
