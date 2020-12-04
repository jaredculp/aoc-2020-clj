(ns advent.core
  (:require [clojure.string]
            [clojure.math.combinatorics :as combo]))

(defn input
  ([f]
   (input f #"\n"))
  ([f delim]
   (clojure.string/split (slurp f) delim)))

(defn as-strings
  [in]
  (map read-string in))

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

(defn day1
  [n]
  (as-> (input "resources/day1.txt") x
    (as-strings x)
    (combo/combinations x n)
    (filter #(= 2020 (reduce + %)) x)
    (first x)
    (reduce * x)))

(defn day2-1
  []
  (->> (input "resources/day2.txt")
       (filter #(let [groups (re-find #"(\d+)-(\d+) ([a-z]): (.*)" %)
                      min-n  (read-string (nth groups 1))
                      max-n  (read-string (nth groups 2))
                      letter (nth groups 3)
                      pass   (nth groups 4)
                      n      (count (re-seq (re-pattern letter) pass))]
                  (and (>= n min-n) (<= n max-n))))
       count))

(defn day2-2
  []
  (->> (input "resources/day2.txt")
       (filter #(let [groups     (re-find #"(\d+)-(\d+) ([a-z]): (.*)" %)
                      first-pos  (dec (read-string (nth groups 1)))
                      second-pos (dec (read-string (nth groups 2)))
                      letter     (nth (nth groups 3) 0)
                      pass       (nth groups 4)]
                  (not=
                   (= (nth pass first-pos) letter)
                   (= (nth pass second-pos) letter))))
       count))

(defn day3
  [slopes]
  (let [lines (input "resources/day3.txt")]
    (->> slopes
         (map #(let [[dx, dy] %]
                 (count-trees dx dy lines)))
         (reduce *))))

(defn is-valid-passport?
  "Determine whether a passport has all required fields"
  [input]
  (every? #(clojure.string/includes? input %) ["byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid"]))

(defn day4
  []
  (->> (input "resources/day4.txt" #"\n\n")
       (filter is-valid-passport?)
       count))

(defn -main
  []
  ; answer: 646779
  (println (str "day1-part1: " (day1 2)))

  ; answer: 246191688
  (println (str "day1-part2: " (day1 3)))

  ; answer: 439
  (println (str "day2-part1: " (day2-1)))

  ; answer: 584
  (println (str "day2-part2: " (day2-2)))

  ; answer: 181
  (println (str "day3-part1: " (day3 [[3 1]])))

  ; answer: 1260601650
  (println (str "day3-part2: " (day3 [[1 1]
                                      [3 1]
                                      [5 1]
                                      [7 1]
                                      [1 2]])))

  ; answer: 228
  (println (str "day4-part1: " (day4))))
