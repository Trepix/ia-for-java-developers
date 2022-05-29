%*** Reglas ***

reinas(N, Res) :-
    numlist(1,N,Base),
    permutacion(Res,Base),
    diagsOK(Res).

diagsOK( [ _ | [] ] ) :-
    true.

diagsOK([Encabezado | Cola]) :-
     diagReina(Encabezado, Cola, 1),
     diagsOK(Cola).

diagReina(_, [], _) :-
     true.

diagReina(Reina, [T|Q], Col) :-
     (Reina + Col) =\= T,
     (Reina - Col) =\= T,
     diagReina(Reina, Q, Col+1).
