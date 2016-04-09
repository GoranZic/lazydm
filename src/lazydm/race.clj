(ns lazydm.race
  (:require [lazydm.stats :refer :all]))

(def races {:orc {:name "orc" :size "medium" :mods {:strength 2 :intelligence -2} :abilities ["darkvision" "aggressive"]} :goblin {:name "goblin" :size "small" :mods {:strength -2 :dexterity 2} :abilities ["nimble escape" "darkvision"]} :human {:name "human" :size "medium" :mods {:strength 1 :dexterity 1 :constitution 1 :wisdom 1 :intelligence 1 :charisma 1} :abilities [] }})

(def abilities {:ambusher {:name "ambusher" :cr {:attack-bonus 1}} :rampage {:name "rampage" :cr {:damage 2}} :pack-tactics {:name "pack tactbics" :cr {:damage 1}} })

(def hd-size {:medium 4.5 :small 3.5 :large 5.5})

(defn calculate-class
  "calculates proficiency and hit dice based on desired level of the character"
  [level]
  (let [random-modifier  (range-gen (- (int (/ level 4))) (+ (int (/ level 4)) 1))]
    {:proficiency (+ (int (/  (+ level random-modifier) 4)) 2) :hd-multiplier (+ (int (/ (- level random-modifier) 1/2)))})
)

(defn apply-race-modifiers
  "applies race modifiers"
  [character-stats race]
  (let [old-stats (assoc character-stats :race (:name race) )]  
    (reduce 
     (fn [stats mod] 
       (update stats (key mod) + (val mod)))
     old-stats (:mods race))))
(defn apply-class
  "applies class (with level) to stats"
  [stats class]
  (merge stats class)
)
(defn apply-race-abilities
  "applies all the racial abilities to the character"
  [character-stats {abilities :abilities}]
  (reduce 
   (fn [stats ability] (assoc stats :abilities (conj (:abilities stats) ability) ))
   character-stats
   abilities
))


(defn apply-race
  "applies abilites, mods of the race"
  [stats race]
  (-> (apply-race-modifiers stats race)
      (apply-race-abilities race))
  )
(defn calculate-hp
  "calculate hp"
  [stats]
  (assoc stats :hit-points (+ (int (*  ((keyword (:size ((keyword (:race stats)) races))) hd-size) (:hd-multiplier stats))) (* (:hd-multiplier stats) (attr-bonus stats :constitution) )))
  )
