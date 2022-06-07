package io.trepix.ia.fuzzylogic;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toCollection;

// Clase principal que gestiona los conjuntos difusos
public class FuzzySet {

    // Atributos
    protected final LinkedList<Punto2D> puntos;

    public FuzzySet(LinkedList<Punto2D> points) {
        puntos = points;
        Collections.sort(puntos);
    }

    public FuzzySet applyMembershipDegree(double value) {
        return new FuzzySet(
                puntos.stream()
                        .map(point -> new Punto2D(point.x, point.y * value))
                        .collect(toCollection(LinkedList::new))
        );
    }

    // Cálculo del grado de pertenencia de un punto
    public double membershipDegree(double valor) {
        // Caso 1 : al exteriour del intervalo del conjunto difuso
        if (puntos.size() < 2) return 0;
        if (valor < minimum() || valor > maximum()) return 0;

        Punto2D ptAntes = puntos.get(0);
        Punto2D ptDespues = puntos.get(1);
        int index = 0;
        while (valor >= ptDespues.x) {
            index++;
            ptAntes = ptDespues;
            ptDespues = puntos.get(index);
        }

        if (ptAntes.x == valor) {
            // Caso 2 : un punto con eete valor
            return ptAntes.y;
        } else {
            // Caso 3 : se appica la interpolación
            return ((ptAntes.y - ptDespues.y) * (ptDespues.x - valor) / (ptDespues.x - ptAntes.x) + ptDespues.y);
        }
    }

    // Operador Y
    public FuzzySet Y(FuzzySet e2) {
        return Fusionar(this, e2, "Min");
    }

    // Operador O
    public FuzzySet O(FuzzySet e2) {
        return Fusionar(this, e2, "Max");
    }

    // Métodos min o max
    private static double Optimo(double valor1, double valor2, String metodo) {
        if (metodo.equals("Min")) {
            return Math.min(valor1, valor2);
        } else {
            return Math.max(valor1, valor2);
        }
    }

    // Métodos genérico
    private static FuzzySet Fusionar(FuzzySet e1, FuzzySet e2, String metodo) {
        // Creación del resultado
        LinkedList<Punto2D> mergedPoints = new LinkedList<>();

        // On va recorrer las listas con los iteradores
        Iterator<Punto2D> iterador1 = e1.puntos.iterator();
        Punto2D ptConjunto1 = iterador1.next();
        Punto2D antiguoPtConjunto1 = ptConjunto1;
        Iterator<Punto2D> iterador2 = e2.puntos.iterator();
        Punto2D ptConjunto2 = iterador2.next();

        // Se calcula la posición relativa de las dos curvas
        int antiguaPosicionRelativa;
        int nuevaPosicionRelativa = (int) Math.signum(ptConjunto1.y - ptConjunto2.y);

        boolean lista1terminada = false;
        boolean lista2terminada = false;
        // Bucle sobre todos los puntos de las dos colecciones
        while (!lista1terminada && !lista2terminada) {
            // Se recuperan las abcisas de las puntos actuales
            double x1 = ptConjunto1.x;
            double x2 = ptConjunto2.x;

            // Cálculo de las posiciones relativas
            antiguaPosicionRelativa = nuevaPosicionRelativa;
            nuevaPosicionRelativa = (int) Math.signum(ptConjunto1.y - ptConjunto2.y);

            // ¿Están invertidas las curvas?
            // Si no, ¿se debe tener en cuenta dos o un único punto?
            if (antiguaPosicionRelativa != nuevaPosicionRelativa && antiguaPosicionRelativa != 0 && nuevaPosicionRelativa != 0) {
                // Se debe calcular el punto de intersección
                double x = (x1 == x2 ? antiguoPtConjunto1.x : Math.min(x1, x2));
                double xPrimo = Math.max(x1, x2);

                // Cálculo de las pendientes
                double p1 = e1.membershipDegree(xPrimo) - e1.membershipDegree(x) / (xPrimo - x);
                double p2 = e2.membershipDegree(xPrimo) - e2.membershipDegree(x) / (xPrimo - x);
                // Cálculo del delta
                double delta = 0;
                if ((p2 - p1) != 0) {
                    delta = (e2.membershipDegree(x) - e1.membershipDegree(x)) / (p1 - p2);
                }

                // Agregar el punto de intersección al resultado
                mergedPoints.add(new Punto2D(x + delta, e1.membershipDegree(x + delta)));

                // Se pasa al punto siguiente
                if (x1 < x2) {
                    antiguoPtConjunto1 = ptConjunto1;
                    if (iterador1.hasNext()) {
                        ptConjunto1 = iterador1.next();
                    } else {
                        lista1terminada = true;
                        ptConjunto1 = null;
                    }
                } else if (x1 > x2) {
                    if (iterador2.hasNext()) {
                        ptConjunto2 = iterador2.next();
                    } else {
                        ptConjunto2 = null;
                        lista2terminada = true;
                    }
                }
            } else if (x1 == x2) {
                // Dos puntos con la misma abcisa, es suficiente con guardar el correcto
                mergedPoints.add(new Punto2D(x1, Optimo(ptConjunto1.y, ptConjunto2.y, metodo)));

                // Se pasa al siguiente punto 
                if (iterador1.hasNext()) {
                    antiguoPtConjunto1 = ptConjunto1;
                    ptConjunto1 = iterador1.next();
                } else {
                    ptConjunto1 = null;
                    lista1terminada = true;
                }
                if (iterador2.hasNext()) {
                    ptConjunto2 = iterador2.next();
                } else {
                    ptConjunto2 = null;
                    lista2terminada = true;
                }
            } else if (x1 < x2) {
                // La curva 1 tiene un punto antes
                // Se calcula el gradio para el segundo y se guarda el correcto
                mergedPoints.add(new Punto2D(x1, Optimo(ptConjunto1.y, e2.membershipDegree(x1), metodo)));
                if (iterador1.hasNext()) {
                    antiguoPtConjunto1 = ptConjunto1;
                    ptConjunto1 = iterador1.next();
                } else {
                    ptConjunto1 = null;
                    lista1terminada = true;
                }
            } else {
                // ültimo caso, es la curva 2 que tiene un punto antes
                // Se calcula el grado para la primera y se guarda el correcto
                mergedPoints.add(new Punto2D(x2, Optimo(e1.membershipDegree(x2), ptConjunto2.y, metodo)));
                if (iterador2.hasNext()) {
                    ptConjunto2 = iterador2.next();
                } else {
                    ptConjunto2 = null;
                    lista2terminada = true;
                }
            }
        }

        // Aquí, al menos una de las listas está termianda
        // Se añaden los puntos restants
        if (!lista1terminada) {
            while (iterador1.hasNext()) {
                ptConjunto1 = iterador1.next();
                mergedPoints.add(new Punto2D(ptConjunto1.x, Optimo(ptConjunto1.y, 0, metodo)));
            }
        } else if (!lista2terminada) {
            while (iterador2.hasNext()) {
                ptConjunto2 = iterador2.next();
                mergedPoints.add(new Punto2D(ptConjunto2.x, Optimo(ptConjunto2.y, 0, metodo)));
            }
        }

        return new FuzzySet(mergedPoints);
    }

    public double Baricentro() {
        // Si hay menos de dos puntos, no hay baricentro
        if (puntos.size() <= 2) {
            return 0;
        } else {
            // Iinicialización de las áreas
            double areaPonderada = 0;
            double areaTotal = 0;
            double areaLocal;
            // Recorrido de la lista conservando 2 puntos
            Punto2D antiguoPt = null;
            for (Punto2D pt : puntos) {
                if (antiguoPt != null) {
                    // Cálculo del baricentro local
                    if (antiguoPt.y == pt.y) {
                        // Es un rectángulo, baricentro al centro
                        areaLocal = pt.y * (pt.x - antiguoPt.x);
                        areaTotal += areaLocal;
                        areaPonderada += areaLocal * ((pt.x - antiguoPt.x) / 2.0 + antiguoPt.x);
                    } else {
                        // Es un trapecio, se puede descomponer
                        // como un rectángulo con un triangulo rectángulo debajo
                        // Se separan los dos formas
                        // Momento 1 : rectángulo
                        areaLocal = Math.min(pt.y, antiguoPt.y) * (pt.x - antiguoPt.x);
                        areaTotal += areaLocal;
                        areaPonderada += areaLocal * ((pt.x - antiguoPt.x) / 2.0 + antiguoPt.x);
                        // Momento 2 : triangulo rectángulo
                        areaLocal = (pt.x - antiguoPt.x) * Math.abs(pt.y - antiguoPt.y) / 2.0;
                        areaTotal += areaLocal;
                        if (pt.y > antiguoPt.y) {
                            // Baricentro de 1/3 lado pt
                            areaPonderada += areaLocal * (2.0 / 3.0 * (pt.x - antiguoPt.x) + antiguoPt.x);
                        } else {
                            // Baricentro de 1/3 lado antiguoPt
                            areaPonderada += areaLocal * (1.0 / 3.0 * (pt.x - antiguoPt.x) + antiguoPt.x);
                        }
                    }
                }
                antiguoPt = pt;
            }
            // Se devuelve la coordada del baricentro
            return areaPonderada / areaTotal;
        }
    }

    public double minimum() {
        return puntos.getFirst().x;
    }

    public double maximum() {
        return puntos.getLast().x;
    }

    // Visualización
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("[" + minimum() + "-" + maximum() + "]:");
        for (Punto2D pt : puntos) {
            sj.add(pt.toString());
        }
        return sj.toString();
    }

    // Operador de comparación (se comparan las cadenas resultantes)
    @Override
    public boolean equals(Object pt2) {
        return toString().equals(pt2.toString());
    }
}
