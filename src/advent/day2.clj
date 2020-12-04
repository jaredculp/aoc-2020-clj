(ns advent.day2
  (:require [advent.core :as core]))

(->> (core/input-file "day2.txt")
     (filter #(let [[_ min-n max-n letter pass] (re-find #"(\d+)-(\d+) ([a-z]): (.*)" %)
                    n (count (re-seq (re-pattern letter) pass))]
                (<= (read-string min-n) n (read-string max-n))))
     count)
;; => 439

(->> (core/input-file "day2.txt")
     (filter #(let [[_ first-pos second-pos letter pass] (re-find #"(\d+)-(\d+) ([a-z]): (.*)" %)
                    i (dec (read-string first-pos))
                    j (dec (read-string second-pos))
                    c (first letter)]
                (not=
                 (= (nth pass i) c)
                 (= (nth pass j) c))))
     count)
;; => 584
