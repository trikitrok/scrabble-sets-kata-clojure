(ns scrabble-sets.core
  (:require
    [clojure.string :as string]))

(def ^:private tiles-in-bag
  {"E" 12 "A" 9 "I" 9 "O" 8 "N" 6 "R" 6 "T" 6
   "L" 4 "S" 4 "U" 4 "D" 4 "G" 3 "_" 2 "B" 2
   "C" 2 "M" 2 "P" 2 "F" 2 "H" 2 "V" 2 "W" 2
   "Y" 2 "K" 1 "J" 1 "X" 1 "Q" 1 "Z" 1})

(def ^:private group-by-frequency
  (partial group-by second))

(defn- format-tile [[freq tiles]]
  (str freq ": " (string/join ", " (map str tiles))))

(defn- format-tiles [sorted-tiles]
  (->> sorted-tiles
       (map format-tile)
       (string/join "\n")))

(defn- sort-by-frequency [tiles-in-bag]
  (map (fn [[freq & [tiles]]]
         [freq (sort (map first tiles))])
       (sort-by key > (group-by-frequency tiles-in-bag))))

(defn- consume-tile [tiles-in-bag tile-in-play]
  (update tiles-in-bag tile-in-play dec))

(defn- consume [tiles-in-play tiles-in-bag]
  (reduce consume-tile tiles-in-bag tiles-in-play))

(defn- format-error-message [consumed-tiles]
  (str "Invalid input. More "
       (string/join ", " (map first consumed-tiles))
       "'s have been taken from the bag than possible."))

(defn- display-distribution [tiles-in-bag]
  (let [overconsumed-tiles (filter #(neg? (second %)) tiles-in-bag)]
    (if (empty? overconsumed-tiles)
      (format-tiles (sort-by-frequency tiles-in-bag))
      (format-error-message overconsumed-tiles))))

(defn tiles-left [tiles-in-play]
  (->> tiles-in-bag
       (consume (map str tiles-in-play))
       display-distribution))
