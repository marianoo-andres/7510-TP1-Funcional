(ns query)
(require '[clojure.string :as str])

(defn query-fact [facts-hash-map fact]
  "fact = vector as [fact-name [fact-val1 fact-val2..]] for example ['padre' ['juan' 'pepe']]
  Return true if facts-hash-map contains the fact"
  (let [fact-name-keyword (keyword (get fact 0)) fact-value (get facts-hash-map fact-name-keyword) fact-parameters (get fact 1)]
    (if (contains? facts-hash-map fact-name-keyword)
      (if (some #{fact-parameters} fact-value)
        true
        false
      )
    false
    )
  )
)

(defn all-rule-facts-true? [facts-hash-map facts-binded]
" Return true if all facts are present in facts-hash-map"
  (defn query-fact-map [facts-binded-element]
    (query-fact facts-hash-map facts-binded-element)
  )
  (every? #{true} (map query-fact-map facts-binded))
)

(defn bind-facts-of-rule [rule-value rule-parameters]
"Binds the rule parameters to the facts of the rule and returns it
Example: rule-value = [['varon' [1]] ['padre' [1 0]]]
         rule-parameters = ['juan' 'pepe']
         returns = [['varon' ['pepe']] ['padre' ['pepe' 'juan']]]
"
  (defn map-aux [rule-value-element]
    "Example of rule-value-element = ['varon' [1]]"
    (defn map-aux-aux [rule-value-element-value]
      "Example of rule-value-element-value = 1"
      (get rule-parameters rule-value-element-value)
    )
    [(get rule-value-element 0) (into [] (map map-aux-aux (get rule-value-element 1)))]
  )
  (into [] (map map-aux rule-value))
)
(defn query-rule [rule-hash-map facts-hash-map rule]
"rule = vector as [rule-name [rule-val1 rule-val2..]] for example ['hijo' ['juan' 'pepe']]
   Return true if rule is present in rule-hash-map and all the facts of the rule are present
   in facts-hash-map
"
  (let [rule-name-keyword (keyword (get rule 0)) rule-parameters(get rule 1)]
    (if (contains? rule-hash-map rule-name-keyword)
      (let [rule-value (get rule-hash-map rule-name-keyword) facts-binded (bind-facts-of-rule rule-value rule-parameters)]
        (all-rule-facts-true? facts-hash-map facts-binded)
      )
      false
    )
  )
)

(defn parse-query [query-string]
  "Returns a vector with first element name of fact/rule and second element vector of parameters"
  (try (let [query-vector (str/split (str/replace (str/replace query-string #"\)" "") #" " "") #"\(")
        name (get query-vector 0)
        parameters (str/split (get query-vector 1) #",")]
        [name parameters]
      )
  (catch Exception e nil))
)

(defn get-query-result [database query-string]
  "Evaluates the query. Returns true if fact/rule is in database or false if not"
  (let [parsed-query (parse-query query-string)]
       (if (= parsed-query nil) 
        nil
        (let [facts-hash-map (get database 0)
              rules-hash-map (get database 1)
              fact-query-result (query-fact facts-hash-map parsed-query)
              rule-query-result (query-rule rules-hash-map facts-hash-map parsed-query)
              ]
            (or fact-query-result rule-query-result)
        )
      )
  )
)