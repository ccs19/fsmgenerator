
(defun getprop (p l)
    (COND ((null l)	 nil)
        ((atom l)	 nil)
        ((equal p (car l)) (cadr l))
        (t  (getprop p (cddr l)))
    )
)