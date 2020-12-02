(ns advent.core
  (:require [clojure.string]
            [clojure.math.combinatorics :as combo]))

(defn input
  "Read a file, returning a vector containing each line"
  [f]
  (clojure.string/split (slurp f) #"\n"))

(defn as-strings
  "Coerce each item of the input to a string"
  [in]
  (map read-string in))

(defn day1
  "--- Day 1: Report Repair ---
  After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a tropical island. Surely, Christmas will go on without you.

  The tropical island has its own currency and is entirely cash-only. The gold coins used there have a little picture of a starfish; the locals just call them stars. None of the currency exchanges seem to have heard of them, but somehow, you'll need to find fifty of these coins by the time you arrive so you can pay the deposit on your room.

  To save your vacation, you need to get all fifty stars by December 25th.

  Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

  Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input); apparently, something isn't quite adding up.

  Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.

  For example, suppose your expense report contained the following:

  1721
  979
  366
  299
  675
  1456
  In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.

  Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you multiply them together?

  --- Part Two ---
  The Elves in accounting are thankful for your help; one of them even offers you a starfish coin they had left over from a past vacation. They offer you a second one if you can find three numbers in your expense report that meet the same criteria.

  Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying them together produces the answer, 241861950.

  In your expense report, what is the product of the three entries that sum to 2020?"
  [n]
  (as-> (input "resources/day1.txt") x
    (as-strings x)
    (combo/combinations x n)
    (filter #(= 2020 (reduce + %)) x)
    (first x)
    (reduce * x)))

(defn day2-1
  "--- Day 2: Password Philosophy ---
  Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via toboggan.

  The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. 'Something's wrong with our computers; we can't log in!' You ask if you can take a look.

  Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the Official Toboggan Corporate Policy that was in effect when they were chosen.

  To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted database) and the corporate policy when that password was set.

  For example, suppose you have the following list:

  1-3 a: abcde
  1-3 b: cdefg
  2-9 c: ccccccccc
  Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

  In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

  How many passwords are valid according to their policies?"
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
  "--- Part Two ---
  While it appears you validated the passwords correctly, they don't seem to be what the Official Toboggan Corporate Authentication System is expecting.

  The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.

  Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of 'index zero'!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

  Given the same example list from above:

  1-3 a: abcde is valid: position 1 contains a and position 3 does not.
  1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
  2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
  How many passwords are valid according to the new interpretation of the policies?"
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

(defn -main
  []
  ; answer: 646779
  (println (str "day1-part1: " (day1 2)))

  ; answer: 246191688
  (println (str "day1-part2: " (day1 3)))

  ; answer: 439
  (println (str "day2-part1: " (day2-1)))

  ; answer: 584
  (println (str "day2-part2: " (day2-2))))
