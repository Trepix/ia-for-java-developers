package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import redNeuronal.IHM;
import redNeuronal.Sistema;

// Clase del programa principal
public class Aplicacion implements IHM {
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.Lanzar();
    }
    
    protected void Lanzar() {
        // Problema del XOR
        /*String[] contenido = leerArchivo("xor.txt", true);
        Sistema sistema = new Sistema(2, 2, 1, contenido, 1.0, this);
        sistema.Lanzar();*/
        
        // Problema Abalone
        String[] contenido = leerArchivo("abalone_norm.txt", false);
        Sistema sistema = new Sistema(10, 4, 1, contenido, 0.8, this);
        sistema.setTasaAprendizaje(0.1);
        sistema.setNumIteracionesMax(50000);
        sistema.Lanzar();
    }

    protected String[] leerArchivo(String nombreArchivo, boolean eliminarEncabezado) {
        try {
            ArrayList<String> lineas = new ArrayList();
            BufferedReader buffer = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            while ((linea = buffer.readLine()) != null) {
                lineas.add(linea);
            }
            buffer.close();
            if (eliminarEncabezado) {
                lineas.remove(0);
            }
            String[] contenido = lineas.toArray(new String[lineas.size()]);
            return contenido;
        }
        catch (IOException e) {
            System.err.println(e.toString());
            return null;
        }
    }
    
    @Override
    public void MostrarMensaje(String msg) {
        System.out.println(msg);
    }
}
