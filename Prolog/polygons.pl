%********** Facts **********

equalSides(X) :- ask(equalSides, 'How many sides does it have? ', X).

rightAngle(X) :- ask(rightAngle, 'Does the figure have at least one right angle? (yes, no)? ', X).

parallelSides(X) :- ask(parallelSides, 'How many sides are parallel to each other? (0, 2 o 4)? ', X).

sides(X) :- ask(sides, 'How many sides does it have? ', X).

%********** Engine **********

:- dynamic memory/2.

solve :-
    retractall(memory(_,_)),
    findall(X, name(X), R),
    write(R).

ask(Pred, _, X) :-
    memory(Pred, X).
ask(Pred, _, _) :-
    memory(Pred, _),
    !,
    fail.
ask(Pred, Pregunta, X) :-
    write(Pregunta),
    read(Y),
    asserta(memory(Pred, Y)),
    X == Y.


%********** Rules **********
% Triangles
name(triangle) :-
    order(3).

name(isoscelesTriangle) :-
    name(triangle),
    equalSides(2).

name(rightTriangle) :-
    name(triangle),
    rightAngle(oui).

name(isoscelesRightTriangle) :-
    name(isoscelesTriangle),
    name(rightTriangle).

name(equilateralTriangle) :-
    name(triangle),
    equalSides(3).

% Quadrilaterals
name(quadrilateral) :-
    order(4).

name(trapezium) :-
    name(quadrilateral),
    parallelSides(2).

name(parallelogram) :-
    name(quadrilateral),
    parallelSides(4).

name(rectangle) :-
    name(parallelogram),
    rightAngle(si).

name(diamond) :-
    name(parallelogram),
    equalSides(4).

name(Square) :-
    name(diamond),
    name(rectangle).
