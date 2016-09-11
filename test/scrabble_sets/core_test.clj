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
    those letters appear in alphabetical order, with blank tiles at the end"

    (fact
      "when no tiles are in play"
      (scrabble/tiles-left
        "") => (str "12: E\n"
                    "9: A, I\n"
                    "8: O\n"
                    "6: N, R, T\n"
                    "4: D, L, S, U\n"
                    "3: G\n"
                    "2: B, C, F, H, M, P, V, W, Y, _\n"
                    "1: J, K, Q, X, Z")))

  (fact
    "when some tiles are in play"
    (scrabble/tiles-left
      "PQAREIOURSTHGWIOAE_") => (str "10: E\n"
                                     "7: A, I\n"
                                     "6: N, O\n"
                                     "5: T\n"
                                     "4: D, L, R\n"
                                     "3: S, U\n"
                                     "2: B, C, F, G, M, V, Y\n"
                                     "1: H, J, K, P, W, X, Z, _\n"
                                     "0: Q")

    (scrabble/tiles-left
      "LQTOONOEFFJZT") => (str "11: E\n"
                               "9: A, I\n"
                               "6: R\n"
                               "5: N, O\n"
                               "4: D, S, T, U\n"
                               "3: G, L\n"
                               "2: B, C, H, M, P, V, W, Y, _\n"
                               "1: K, X\n"
                               "0: F, J, Q, Z"))

  (fact
    "when trying to put in play too many tiles of some kind"
    (scrabble/tiles-left
      "AXHDRUIOR_XHJZUQEE")
    => "Invalid input. More X's have been taken from the bag than possible."))
