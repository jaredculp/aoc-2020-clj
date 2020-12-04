(ns advent.core
  (:require [clojure.string]
            [clojure.math.combinatorics :as combo]))

(defn input
  "Read a file, returning a vector containing each line"
  ([f]
   (input f #"\n"))
  ([f delim]
   (clojure.string/split (slurp f) delim)))

(defn as-strings
  "Coerce each item of the input to a string"
  [in]
  (map read-string in))

(defn is-tree?
  "Whether or not the current position 'x' on a line is a tree (marked by '#')"
  [x line]
  (= \# (nth (cycle line) x)))

(defn count-trees
  "Determine the number of trees encountered for a given path defined by dx and dy"
  [dx dy lines]
  (->> lines
       (drop dy)
       (take-nth dy)
       (keep-indexed #(when (is-tree? (* (inc %1) dx) %2) true))
       count))

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

(defn day3
  "--- Day 3: Toboggan Trajectory ---
  With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.

  Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

  ..##.......
  #...#...#..
  .#....#..#.
  ..#.#...#.#
  .#...##..#.
  ..#.##.....
  .#.#.#....#
  .#........#
  #.##...#...
  #...##....#
  .#..#...#.#
  These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:

  ..##.........##.........##.........##.........##.........##.......  --->
  #...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
  .#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
  ..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
  .#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
  ..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
  .#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
  .#........#.#........#.#........#.#........#.#........#.#........#
  #.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
  #...##....##...##....##...##....##...##....##...##....##...##....#
  .#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
  You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

  The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

  From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.

  The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:

  ..##.........##.........##.........##.........##.........##.......  --->
  #..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
  .#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
  ..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
  .#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
  ..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
  .#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
  .#........#.#........X.#........#.#........#.#........#.#........#
  #.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
  #...##....##...##....##...#X....##...##....##...##....##...##....#
  .#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
  In this example, traversing the map using this slope would cause you to encounter 7 trees.

  Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?
  "

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
  "--- Day 4: Passport Processing ---
  You arrive at the airport only to realize that you grabbed your North Pole Credentials instead of your passport. While these documents are extremely similar, North Pole Credentials aren't issued by a country and therefore aren't actually valid documentation for travel in most of the world.

  It seems like you're not the only one having problems, though; a very long line has formed for the automatic passport scanners, and the delay could upset your travel itinerary.

  Due to some questionable network security, you realize you might be able to solve both of these problems at the same time.

  The automatic passport scanners are slow because they're having trouble detecting which passports have all required fields. The expected fields are as follows:

  byr (Birth Year)
  iyr (Issue Year)
  eyr (Expiration Year)
  hgt (Height)
  hcl (Hair Color)
  ecl (Eye Color)
  pid (Passport ID)
  cid (Country ID)
  Passport data is validated in batch files (your puzzle input). Each passport is represented as a sequence of key:value pairs separated by spaces or newlines. Passports are separated by blank lines.

  Here is an example batch file containing four passports:

  ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
  byr:1937 iyr:2017 cid:147 hgt:183cm

  iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
  hcl:#cfa07d byr:1929

  hcl:#ae17e1 iyr:2013
  eyr:2024
  ecl:brn pid:760753108 byr:1931
  hgt:179cm

  hcl:#cfa07d eyr:2025 pid:166559648
  iyr:2011 ecl:brn hgt:59in
  The first passport is valid - all eight fields are present. The second passport is invalid - it is missing hgt (the Height field).

  The third passport is interesting; the only missing field is cid, so it looks like data from North Pole Credentials, not a passport at all! Surely, nobody would mind if you made the system temporarily ignore missing cid fields. Treat this 'passport' as valid.

  The fourth passport is missing two fields, cid and byr. Missing cid is fine, but missing any other field is not, so this passport is invalid.

  According to the above rules, your improved system would report 2 valid passports.

  Count the number of valid passports - those that have all required fields. Treat cid as optional. In your batch file, how many passports are valid?"
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
