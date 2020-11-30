(ns advent.core)

(defn input [] (line-seq (java.io.BufferedReader. *in*)))

(defn -main
  [& args]
  (doseq [line (input)]
    (println line)))
