(ns lazydm.stats)

(defn rand-map
  [map]
  (get map (rand-nth (keys map))))

(defn range-gen 
  "generates numbers within a certain range"
  [start end]
  (+ start (rand-int (- end start))))
(def standard-stats [:strength :dexterity :constitution :wisdom :intelligence :charisma])
 (defn attr-bonus
  [stats attr]
  (int (/ (- (attr stats) 10) 2)))


(defn generate-stats
  "Generates character stats"
  [stats genfn]
  (into {} 
        (map (fn [stat] [stat (genfn)]) stats))
)

(defn attr-bonus-str
 "return attr-bonus as string with +/- prefix"
 [stats attr]
 (let [bonus (attr-bonus stats attr)]
   (if (< bonus 0) (str bonus) (str "+" bonus)))
)

(defn attack-bonus
  "calculates attack bonus for the character"
  [stats]
  (assoc stats :attack-bonus (+ (attr-bonus stats :strength) (:proficiency stats)))
)
