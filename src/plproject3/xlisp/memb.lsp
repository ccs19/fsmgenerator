
(DEFUN MEMB (X L)
    (COND ((NULL L)	 NIL)
        ((ATOM L)	 NIL)
        ((EQUAL X (CAR L)) T)
        (T  (MEMB X (CDR L)))
    )
)