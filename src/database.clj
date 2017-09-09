(ns database)
(require '[clojure.string :as str])

(defn add-fact [facts-hash-map fact]
  "facts-hash-map: key-value hash-map with key = keyword of name of fact and
  value = vector of vectors as [ [fact-val-1..fact-val-2]..[fact-val-1..fact-val-2] ]
  fact: vector as [fact-name [fact-val-1 fact-val-2..]]
  Return a new facts-hash-map with the new fact added
  "
  (let [fact-name-keyword (keyword (get fact 0)) fact-value (get facts-hash-map fact-name-keyword) fact-parameters (get fact 1)]
    (if (contains? facts-hash-map fact-name-keyword)
        (assoc facts-hash-map fact-name-keyword (conj fact-value fact-parameters))
        
        (assoc facts-hash-map fact-name-keyword [fact-parameters])
    )
  )
)

(defn add-rule [rules-hash-map rule]
  "rules-hash-map: key-value hash-map with key = keyword of name of rule and
  value = vector as [[fact-name1 [fact-number-1..]] [fact-name2 [fact-number-1..]]..]
  rule: vector as [rule-name [fact-name1 [fact-number-1..] [fact-name2 [fact-number-1..].. ]]]
  Return a new rules-hash-map with the new rule added, nil if rule already exists.
  "
  (let [rule-name-keyword (keyword (get rule 0)) rule-facts (get rule 1)]
    (if (contains? rules-hash-map rule-name-keyword)
        nil
        
        (assoc rules-hash-map rule-name-keyword rule-facts)
    )
  )
)

(defn to-rule-parameters-with-numbers [rule-parameters]
"rule-parameters: vector as [ [rule-variable1 rule-variable2..] [ [fact-name1 [rule-variable..]] [fact-name2 [rule-variable..]] ] ]

Returns the same rule but with the rule-variables in [ [fact-name1 rule-variable..] [fact-name2 rule-variable..] ] replaced by index numbers of [rule-variable1..]

Example: rule-parameters = [ ['X' 'Y'] [ ['varon' ['Y'] ] ['padre' ['Y' 'X'] ] ] ]
         returns = [ ['varon' [1]] ['padre' [1 0]] ]
"
  (let [rule-variables (get rule-parameters 0) rule-facts (get rule-parameters 1)]
    (defn map-aux [rule-facts-element]
      "Example of rule-facts-element = ['varon' ['Y']]"
      (defn map-aux-aux [rule-facts-element-parameters]
        "Example of rule-facts-element-parameters = 'Y' "
        (.indexOf rule-variables rule-facts-element-parameters) 
      )
      [(get rule-facts-element 0) (into [] (map map-aux-aux (get rule-facts-element 1)))]
    )
    (into [] (map map-aux rule-facts))
  )
)

(defn get-facts-and-rules-string-vector [database-string]
"Returns a vector with first element as the vector of facts strings and the second element as 
the vector of rule strings. Returns nil on parsing error"
  (let [database-splitted (str/split (str/replace (str/replace (str/replace database-string #"\n" "") #"\t" "") #" " "") #"\.")
      rules-string-vector (into [] (filter (fn [element] (str/includes? element ":-")) database-splitted))
      facts-string-vector (into [] (filter (fn [element] (not (str/includes? element ":-"))) database-splitted))
      ]
     
  [facts-string-vector rules-string-vector]
  )
)

(defn to-fact-vector [fact-string]
  "Parses fact-string. Returns a vector with first element name of the fact
  and second element vector of values of fact. Returns nil on parsing error"
  (try   (let [fact-string-splitted (str/split (str/replace fact-string #"\)" "") #"\(")
        fact-name (get fact-string-splitted 0)
        fact-values (str/split (get fact-string-splitted 1) #",")]
        [fact-name fact-values]
        )
  (catch Exception e nil) )
)

(defn to-rule-vector [rule-string]
  "Parses rule-string. Return nil on error"
  (try (let [rule-string-splitted (str/split rule-string #":-")
        rule-string-splitted-first (get rule-string-splitted 0)
        rule-string-splitted-first-splitted (str/split (str/replace rule-string-splitted-first #"\)" "") #"\(")
        rule-name (get rule-string-splitted-first-splitted 0)
        rule-variables (str/split (get rule-string-splitted-first-splitted 1) #",")
        rule-string-splitted-second (get rule-string-splitted 1)
        facts-string-vector (str/split rule-string-splitted-second #"\),")
        rule-facts (into [] (map to-fact-vector facts-string-vector))
        ]
      (if (some nil? rule-facts) 
      nil
      [rule-name rule-variables rule-facts]
      )
  )
  (catch Exception e nil))
)

(defn build-facts-hash-map [facts]
  "Returns the facts-hash-map"
  (defn reduce-aux [hash-map, fact]
    (add-fact hash-map fact)
  )
  (reduce reduce-aux {} facts)
)

(defn build-rules-hash-map [rules]
  "Returns the rules-hash-map"
  (defn bind-rule [rules-element]
    [(get rules-element 0) (to-rule-parameters-with-numbers (subvec rules-element 1))]
  )
  
  (let [rules-numbered (into [] (map bind-rule rules))]
    (defn reduce-aux [hash-map, rule-numbered]
      (add-rule hash-map rule-numbered)
    )
    (reduce reduce-aux {} rules-numbered)
  )
)

(defn build-database [database-string]
  "Parse the database-string. Returns the database [facts-hash-map rules-hash-map] or
  nil on error"
  (let [facts-and-rules-string-vector (get-facts-and-rules-string-vector database-string)
        facts (into [] (map to-fact-vector (get facts-and-rules-string-vector 0)))
        rules (into [] (map to-rule-vector (get facts-and-rules-string-vector 1)))]
      (if (or (some nil? facts) (some nil? rules))
      nil
      [(build-facts-hash-map facts) (build-rules-hash-map rules)]
      )
  )
)