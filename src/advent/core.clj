(ns advent.core
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn input-file
  ([f]
   (input-file f #"\n"))
  ([f delim]
   (-> (io/resource f)
       slurp
       (s/split delim))))
