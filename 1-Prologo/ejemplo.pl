comer(gato, raton).
comer(raton,queso).

pelaje(gato).
pelaje(raton).

amigos(X, Y) :-
     comer(X, Z),
     comer(Z, Y).
