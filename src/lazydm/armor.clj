(ns lazydm.armor
  (:require [lazydm.stats :refer :all])
  (:require [lazydm.weapon :refer :all]))



(defn armor-ac
  "function for calculating armor AC"
  [armor-type max-bonus  ac dex-bonus]
  (if (not= armor-type "heavy") 
    (+ ac (if (> dex-bonus max-bonus) max-bonus dex-bonus) )
    ac)
  
)

(def armors {:hide {:armor-type "medium" :ac (partial armor-ac "medium" 2 12) :name "hide" } :ring-mail {:name "ring mail" :armor-type "heavy" :ac (partial armor-ac "heavy" 0 14)}})

(def shields {:shield {:ac 2 :name "shield"}})


(defn write-down-armor
  "Writes down armor on the character sheet"
  [stats armor]
  (assoc stats :armor (:name armor))
 )

(defn calculate-armor-ac 
  "calculate ac from armor"
  [stats armor]
  (update stats :ac (fnil + 0) ((:ac armor) (attr-bonus stats :dexterity))))

(defn equip-armor
  "Equips character with armor"
  [stats armor]
  (-> (write-down-armor stats armor)
      (calculate-armor-ac armor))
  )
(defn equip-shield? 
  "Checks if sheild can be equipped"
  [stats]
  (nil? (some #{"two-handed"} (:attributes ((keyword (:weapon stats)) lazydm.weapon/weapons))))
 )
(defn write-down-shield
  "writes down shield on charcter sheet"
  [stats shield]
  (assoc stats :shield (:name shield) ))
(defn calculate-shield-ac
  "calculate ac from shield"
  [stats shield]
  (update stats :ac (fnil + 0) (:ac shield))
)

(defn equip-shield
  "equips shield"
  [stats shield]
  (if (equip-shield? stats) 
    (->
     (write-down-shield stats shield)
     (calculate-shield-ac shield))
    stats
    )
  )
