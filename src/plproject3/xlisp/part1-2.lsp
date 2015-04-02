(DEFUN S0 (L)
  (COND 
		((ATOM L) NIL)
		((NULL L) NIL)
		((EQUAL 'x (CAR L))(S0 (CDR L)))
		((EQUAL 'y (CAR L))(S1 (CDR L)))
		(T NIL)
	)
)

(DEFUN S1 (L)
  (COND 
		((NULL L) T)
		((ATOM L) NIL)
		((EQUAL 'x (CAR L))(S2 (CDR L)))
		(T NIL)
	)
)

(DEFUN S2 (L)
  (COND 
		((ATOM L) NIL)
		((NULL L) NIL)
		((EQUAL 'x (CAR L))(S2 (CDR L)))
		((EQUAL 'y (CAR L)) (S3 (CDR L)))
		(T NIL)
	)
)

(DEFUN FSM (L)
	(COND
		((EQUAL T (S0 L)) (PRINC "This is a valid string!"))
		(T (PRINC "This is an invalid string"))
	)
)

(DEFUN S3 (L)
  (COND 
		((NULL L) T)
		((ATOM L) NIL)
		((EQUAL 'x (CAR L))(S3 (CDR L)))
		((EQUAL 'z (CAR L))(S4 (CDR L)))
		(T NIL)
	)
)

(DEFUN S4 (L)
  (COND 
		((ATOM L) NIL)
		((NULL L) NIL)
		((EQUAL 'x (CAR L))(S4 (CDR L)))
		((EQUAL 'a (CAR L))(S1 (CDR L)))
		(T NIL)
	)
)