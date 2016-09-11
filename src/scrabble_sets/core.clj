(ns scrabble-sets.core
  (:require
    [clojure.string :as string]))

(def ^:private tile-distribution
  {"E" 12 "A" 9 "I" 9 "O" 8 "N" 6 "R" 6 "T" 6
   "L" 4 "S" 4 "U" 4 "D" 4 "G" 3 "_" 2 "B" 2
   "C" 2 "M" 2 "P" 2 "F" 2 "H" 2 "V" 2 "W" 2
   "Y" 2 "K" 1 "J" 1 "X" 1 "Q" 1 "Z" 1})

(defn- display-tiles-frequencies [[freq tiles]]
  (str freq ": " (string/join ", " (map str tiles))))

(defn- format-tiles [sorted-tiles]
  (->> sorted-tiles
       (map display-tiles-frequencies)
       (string/join "\n")))

(defn- sort-tiles [tiles-left-distribution]
  (map (fn [[freq & [tiles]]]
         (vector freq (sort (map first tiles))))
       (sort-by
         key > (group-by #(second %) tiles-left-distribution))))

(defn- consume-tile [distribution tile-in-play]
  (update distribution (str tile-in-play) dec))

(defn- consume [tiles-in-play tile-distribution]
  (reduce consume-tile tile-distribution tiles-in-play))

(defn- display-error [consumed-tiles]
  (str "Invalid input. More "
       (string/join ", " (map first consumed-tiles))
       "'s have been taken from the bag than possible."))

(defn- display-distribution [tiles-left-distribution]
  (let [overconsumed-tiles (filter #(neg? (second %))
                                   tiles-left-distribution)]
    (if (empty? overconsumed-tiles)
      (format-tiles (sort-tiles tiles-left-distribution))
      (display-error overconsumed-tiles))))

(defn tiles-left [tiles-in-play]
  (->> tile-distribution
       (consume tiles-in-play)
       display-distribution))
