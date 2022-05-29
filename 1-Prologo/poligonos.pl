%********** Facts **********

ladosIguales(X) :- ask(ladosIguales, '¿Cuántos lados iguales tiene la figura? ', X).

anguloRecto(X) :- ask(anguloRecto, '¿La figura tiene ángulos rectos (só, no)? ', X).

ladosParalelos(X) :- ask(ladosParalelos, '¿Cuántos lados paralelos tiene la figura (0, 2 o 4)? ', X).

orden(X) :- ask(orden, '¿Cuántos lados? ', X).

%********** Motor **********

:- dynamic memory/2.

solve :-
    retractall(memory(_,_)),
    findall(X, nombre(X), R),
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
% Triangulos
nombre(triangulo) :-
    ordre(3).

nombre(trianguloIsosceles) :-
    nombre(triangulo),
    ladosIguales(2).

nombre(trianguloRectangulo) :-
    nombre(triangulo),
    anguloRecto(oui).

nombre(trianguloRectanguloIsosceles) :-
    nombre(trianguloIsosceles),
    nombre(trianguloRectangulo).

nombre(trianguloEquilatero) :-
    nombre(triangulo),
    ladosIguales(3).

% Cuadrilateros
nombre(cuadrilatero) :-
    ordre(4).

nombre(trapecio) :-
    nombre(cuadrilatero),
    ladosParalelos(2).

nombre(paralelogramo) :-
    nombre(cuadrilatero),
    ladosParalelos(4).

nombre(rectangulo) :-
    nombre(paralelogramo),
    anguloRecto(si).

nombre(diamante) :-
    nombre(paralelogramo),
    ladosIguales(4).

nombre(cuadrado) :-
    nombre(diamante),
    nombre(rectangulo).
