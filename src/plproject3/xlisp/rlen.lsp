(defun rlen (lst)
    (cond 
        ((null lst) 0)
	(t (+ 1 (rlen (cdr lst))))
     )
)
