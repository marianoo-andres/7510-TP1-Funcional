(ns logical-interpreter 
(:use [database :only [build-database]])
(:use [query :only [get-query-result]]))

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database-string query-string]
  (let [database (build-database database-string)]
	(if (= database nil) nil
		(get-query-result database query-string)
	)
  )
)



