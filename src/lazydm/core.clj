(ns lazydm.core
  (:require [lazydm.stats :refer :all]
            [lazydm.armor :refer :all]
            [lazydm.trait :refer :all]

            [lazydm.weapon :refer :all]
            [lazydm.race :refer :all])
  (:gen-class))






(defn generate-character
  "outline the character geneartion process, if novalues are passed generates a random cr 1-2 character"
  ([]
   (generate-character (rand-nth (keys races)) 2))
   ([race class]
   (-> (generate-stats standard-stats (partial range-gen 8 18))
       (apply-race ((if (keyword? race) race (keyword race)) races))
       (apply-class (calculate-class class))
       (calculate-hp)
       (equip-armor (rand-map armors))
       (equip-weapon (rand-map weapons))
       (equip-shield (:shield shields))
       (attack-bonus)
       (generate-description)))
   )

(defn generate-characters
  "generate mulitple characters"
  [race class number]
  (for [x (range (min number 5))]
    (generate-character race class)
    )
  )

