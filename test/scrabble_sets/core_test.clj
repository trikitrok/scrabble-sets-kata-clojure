(ns scrabble-sets.core-test
  (:require
    [midje.sweet :refer :all]
    [scrabble-sets.core :as scrabble]))

(facts
  "about scrabble"

  (fact
    "it shows the tiles that are left in the bag
    in descending order of the quantity of each tile left.
    In cases where more than one letter has the same quantity remaining,
    output those letters in alphabetical order, with blank tiles at the end"

    (fact
      "when no tile are in play"

      (scrabble/tiles-left
        "") => (str "12: E\n"
                    "9: A I\n"
                    "8: O\n"
                    "6: N R T\n"
                    "4: D L S U\n"
                    "3: G\n"
                    "2: B C F H M P V W Y _\n"
                    "1: J K Q X Z"))))
