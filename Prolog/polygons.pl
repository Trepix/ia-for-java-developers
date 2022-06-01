%********** Facts **********

equalSides(X) :- ask(equalSides, 'How many equal sides does the figure have? ', X).

rightAngle(X) :- ask(rightAngle, 'Does the figure have at least one right angle? (yes, no)? ', X).

parallelSides(X) :- ask(parallelSides, 'How many sides are parallel to each other? (0, 2 o 4)? ', X).

sides(X) :- ask(sides, 'How many sides does it have? ', X).

%********** Engine **********

% dyanimic because it's mutable
% /2 because it receives 2 arguments
:- dynamic memory/2.

solve :-
    retractall(memory(_,_)),
    findall(X, name(X), R),
    write(R).

ask(Predicate, _, X) :-
    memory(Predicate, X).
ask(Predicate, _, _) :-
    memory(Predicate, _),
    !,
    fail.
ask(Predicate, Question, X) :-
    write(Question),
    read(Y),
    asserta(memory(Predicate, Y)),
    X == Y.


%********** Rules **********
% Triangles
name(triangle) :-
    sides(3).

name(isoscelesTriangle) :-
    name(triangle),
    equalSides(2).

name(rightTriangle) :-
    name(triangle),
    rightAngle(yes).

name(isoscelesRightTriangle) :-
    name(isoscelesTriangle),
    name(rightTriangle).

name(equilateralTriangle) :-
    name(triangle),
    equalSides(3).

% Quadrilaterals
name(quadrilateral) :-
    sides(4).

name(trapezium) :-
    name(quadrilateral),
    parallelSides(2).

name(parallelogram) :-
    name(quadrilateral),
    parallelSides(4).

name(rectangle) :-
    name(parallelogram),
    rightAngle(yes).

name(diamond) :-
    name(parallelogram),
    equalSides(4).

name(square) :-
    name(diamond),
    name(rectangle).
