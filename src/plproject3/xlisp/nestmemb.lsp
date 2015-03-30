
(DEFUN NESTMEMB (X L)
    (COND ((NULL L)	 NIL)
        ((ATOM L)	 NIL)
        ((EQUAL X (CAR L)) T)
        (T  (or (NESTMEMB X (CAR L))
		(NESTMEMB X (CDR L))))
    )
)