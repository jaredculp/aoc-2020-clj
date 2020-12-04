(ns advent.day3
  (:require [advent.core :as core]))

(defn is-tree?
  [x line]
  (= \# (nth (cycle line) x)))

(defn count-trees
  [dx dy lines]
  (->> lines
       (drop dy)
       (take-nth dy)
       (keep-indexed #(when (is-tree? (* (inc %1) dx) %2) true))
       count))

(defn sledding
  [slopes]
  (let [lines (core/input-file "day3.txt")]
    (->> slopes
         (map #(let [[dx, dy] %]
                 (count-trees dx dy lines)))
         (reduce *))))

(sledding [[3 1]])
;; => 181

(sledding [[1 1] [3 1] [5 1] [7 1] [1 2]])
;; => 1260601650
