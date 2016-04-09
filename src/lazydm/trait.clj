(ns lazydm.trait
  (:require [lazydm.stats :refer :all]))

(def appearances ["adorned in gold jewlery" "wearing a tooth necklace" "piercings" "wearing oddly color clothes" ["clothes torn", "clothes new"]  "scar across the eye" "missing teeth" "missing fingers" "missing ear" "crooked nose" ["bald" "long hair" "unusual hair color"] ["fat" "well built" "skinny"] ["beautiful" "ugly"] ])

(def behaviors [["brave" "cowardly"] ["kind" "cruel"] "drunkard" ["easy going" "uptight"] ["deep voice" "high pitched voice"] "scratches all the time"  ["lazy" "jumpy" "focused"] ["optimist" "pesimist"] ["honest" "liar"] ["loud" "quiet"] "with a lisp" "stuterring" ["eloquent" "inarticulate"]  ])


(defn get-random-trait
  "goes down the trait group and picks one trait"
  [trait]

  (if (vector? trait) (get-random-trait (rand-nth trait)) trait )
)

(defn generate-random-traits
 "get a number of random traits from trait list watches it takes only one trait froma trait group"
  [description traits number]
  (loop [remaining-traits traits  picked-traits []]
    (if (>= (count picked-traits) number) 
      (reduce #(str %1 (if (= %1 "") "" ", ") %2) description picked-traits)
      (let [picked-trait (rand-nth remaining-traits)]
        (recur (remove #(= picked-trait %) remaining-traits) (conj picked-traits (get-random-trait picked-trait)) ))
      )
    )
  )

(defn generate-description
  [stats]
  (->   (generate-random-traits "" appearances 2)
      (generate-random-traits behaviors 2)
      ((partial assoc stats :description)))
  )
