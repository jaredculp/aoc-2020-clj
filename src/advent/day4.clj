(ns advent.day4
  (:require [advent.core :as core]))

(defn parse-line [line]
  (->> (re-seq #"(\w+):(\S+)" line)
       (map #(let [[_ k v] %] [(keyword k) v]))
       (into {})))

(defn valid? [passport]
  (every? passport #{:byr :iyr :eyr :hgt :hcl :ecl :pid}))

(defn valid2? [{:keys [:byr :iyr :eyr :hgt :hcl :ecl :pid]}]
  (and
   byr (<= 1920 (read-string byr) 2002)
   iyr (<= 2010 (read-string iyr) 2020)
   eyr (<= 2020 (read-string eyr) 2030)
   hgt (let [[_ amt unit] (re-find #"(\d+)(cm|in)" hgt)]
         (case unit
           "cm" (<= 150 (read-string amt) 193)
           "in" (<= 59 (read-string amt) 76)
           false))
   hcl (re-matches #"^#[0-9a-f]{6}$" hcl)
   ecl (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl)
   pid (re-matches #"^\d{9}$" pid)))

(->> (core/input-file "day4.txt" #"\n\n")
     (map parse-line)
     (filter #(valid? %))
     count)
;; => 228

(->> (core/input-file "day4.txt" #"\n\n")
     (map parse-line)
     (filter #(valid2? %))
     count)
;; => 175
