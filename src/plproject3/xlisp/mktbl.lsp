

(defun make-table (text table)
    (cond ((null text) table)
          (t (make-table (cdr text) 
              (update-entry table (car text))))
    )
)

(defun update-entry (table word)
    (cond ((null table) (list (list word 1)))
          ((eq word (caar table))
               (cons(list word (add1 (cadar table)))
                  (cdr table)))
          (t(cons (car table)
               (update-entry (cdr table) word))) 
    )
)

(defun add1 (n)
  (+ n 1)
)
