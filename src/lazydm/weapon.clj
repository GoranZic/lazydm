(ns lazydm.weapon
  (:require [lazydm.stats :refer :all]))


(defn melee-weapon-damage
  [damage-type damage-die weapon-attributes  stats]
  (if (and (some #{"finesse"} weapon-attributes) (> (attr-bonus stats :dexterity) (attr-bonus stats :strength)))
    (str damage-die (attr-bonus-str stats :dexterity) " " damage-type)
    (str damage-die (attr-bonus-str stats :strength) " " damage-type)))


(def weapons {:sword {:name "sword" :damage-die "1d8" :damage-type "slashing" :damage (partial melee-weapon-damage "slashing" "1d8" []) :attributes []} :greatsword {:name "greatsword" :damage-die "2d6" :damage (partial melee-weapon-damage "slashing" "2d6" ["heavy" "two-handed"]) :damage-type "slashing" :attributes ["heavy" "two-handed"]} :greataxe {:name "greataxe" :damage-die "1d12" :damage (partial melee-weapon-damage "slashing" "1d12" ["heavy" "two-handed"]) :attributes ["heavy" "two-handed"]}})

(defn write-down-weapon
  "writes down the equiped weapon"
  [stats weapon]
  (assoc stats :weapon (:name weapon)))
(defn calculate-weapon-damage
  [stats weapon]
  (assoc stats :damage ((:damage weapon) stats)))
(defn equip-weapon
  "equips character with a weapon"
  [stats weapon]
  (-> (write-down-weapon stats weapon)
      (calculate-weapon-damage weapon)))

