(ns advent.day6
  (:require [advent.core :as core]
            [clojure.string :as s]))

(defn count-any-answered-question [group]
  (->> group
       (map #(s/replace % #"\n" ""))
       (filter seq)
       (into #{})
       count))

(defn alphabet []
  (->> (range 97 123)
       (map char)
       (map str)
       seq))

(defn all-answered? [answers question]
  (every? #(s/includes? % question) answers))

(defn count-all-answered-questions [group]
  (let [answers (s/split-lines group)]
    (->> (alphabet)
         (filter #(all-answered? answers %)))))

(->> (core/input-file "day6.txt" #"\n\n")
     (map count-any-answered-question)
     (reduce +))
;; => 6763

(->> (core/input-file "day6.txt" #"\n\n")
     (map count-all-answered-questions)
     (map count)
     (reduce +))
;; => 3512
