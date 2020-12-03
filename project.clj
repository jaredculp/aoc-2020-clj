(defproject advent "0.1.0-SNAPSHOT"
  :description "Advent of Code 2020"
  :url "https://adventofcode.com"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[cljfmt "0.7.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/math.combinatorics "0.1.6"]]
  :plugins [[lein-cljfmt "0.7.0"]
            [cider/cider-nrepl "0.25.3"]]
  :main advent.core)
