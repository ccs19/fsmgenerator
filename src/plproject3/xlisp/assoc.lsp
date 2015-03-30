
(defun assoc (p l)
    (COND ((null l)	 nil)
        ((atom l)	 nil)
        ((equal p (caar l)) (princ "match") (cadar l))
        (t  (princ "recurse ") (assoc p (cdr l)))
    )
)

(defun setdata()
    (set 'l '((name (john smith)) (age (23)) (phone (1234 5678)) (salary (40000))))
)