%*** Reglas ***

queens(N, Result) :-
    numlist(1, N, List),
    permutation(Result, List),
    diagonalsDontCollide(Result).

diagonalsDontCollide( [ _ | [] ] ) :-
    true.

diagonalsDontCollide([Head | List]) :-
     queenDontCollideOnDiagonal(Head, List, 1),
     diagonalsDontCollide(List).

queenDontCollideOnDiagonal(_, [], _) :-
     true.

queenDontCollideOnDiagonal(Queen, [Head | List], Col) :-
     (Queen + Col) =\= Head,
     (Queen - Col) =\= Head,
     queenDontCollideOnDiagonal(Queen, List, Col+1).
