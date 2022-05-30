package io.trepix.ia.sistemaexperto;

// Clase que permtite crear los hechos, independientemente de su tipo
class HechoFactory {
    // Crea un nuevo hecho rellenando el valor dado por el usuario
    static IHecho Hecho(IHecho f, MotorInterferencias m) {
        IHecho nuevoHecho;
        Class<?> clase = f.getClass();
        if (clase.equals(HechoEntero.class)) {
            nuevoHecho = CrearHechoEntero(f, m);
        } else if (clase.equals(HechoBooleano.class)) {
            nuevoHecho = CrearHechoBooleen(f, m);
        } else nuevoHecho = null;

        return nuevoHecho;
    }

    // Crea un hecho entero
    static IHecho CrearHechoEntero(IHecho f, MotorInterferencias m) {
        int valor = m.PedirValorEntero(f.Pregunta());
        return new HechoEntero(f.Nombre(), valor, null, 0);
    }

    // Crea un hecho booleano
    static IHecho CrearHechoBooleen(IHecho f, MotorInterferencias m) {
        boolean valorB = m.PedirValorBooleano(f.Pregunta());
        return new HechoBooleano(f.Nombre(), valorB, null, 0);
    }

    // Crea un nuevo hecho a partir de su cadena
    static IHecho Hecho(String hechoStr) {
        hechoStr = hechoStr.trim();
        if (hechoStr.contains("=")) {
            // Existe el símbolo "=", por lo que es un hecho entero
            hechoStr = hechoStr.replaceFirst("^" + "\\(", "");
            String[] nombreValorPregunta = hechoStr.split("[=()]");
            if (nombreValorPregunta.length >= 2) {
                // Tenemos un hecho correcto Nombre=Valor[(pregunta)]
                String pregunta = null;
                if (nombreValorPregunta.length == 3) {
                    pregunta = nombreValorPregunta[2].trim();
                }
                return new HechoEntero(nombreValorPregunta[0].trim(), Integer.parseInt(nombreValorPregunta[1].trim()), pregunta, 0);
            }
        } else {
            // Es un hecho booleano nombre[(pregunta)] o !nombre[(pregunta)]
            boolean valor = true;
            if (hechoStr.startsWith("!")) {
                valor = false;
                hechoStr = hechoStr.substring(1).trim();
            }
            // Split, después de aber eliminado el primer delimitador si es necesario : '('
            hechoStr = hechoStr.replaceFirst("^" + "\\(", "");
            String[] nombrePregunta = hechoStr.split("[()]");
            String pregunta = null;
            if (nombrePregunta.length == 2) {
                pregunta = nombrePregunta[1].trim();
            }
            return new HechoBooleano(nombrePregunta[0].trim(), valor, pregunta, 0);
        }
        // Si llegamos aquí, la sintaxis es incorrecta
        return null;
    }
}
