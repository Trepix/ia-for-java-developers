eat(cat, mouse).
eat(mouse,cheese).

fur(cat).
fur(mouse).

friends(X, Y) :-
     eat(X, Z),
     eat(Z, Y).
