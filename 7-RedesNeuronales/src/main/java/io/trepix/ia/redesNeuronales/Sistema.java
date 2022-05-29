package redNeuronal;

// Clase que gestiona el red y el aprendizaje
public class Sistema {
    protected ColleccionPuntos datos;
    protected RedNeuronal red;
    protected IHM ihm;
    
    protected double tasaAprendizaje = 0.3;
    protected double errorMax = 0.005;
    protected int numIteracionesMax = 10001;
    
    public Sistema(int _numEntradas, int _numOcultas, int _numSalidas, String[] _datos, double _ratioAprendizaje, IHM _ihm) {
        datos = new ColleccionPuntos(_datos, _numSalidas, _ratioAprendizaje);
        red = new RedNeuronal(_numEntradas, _numOcultas, _numSalidas);
        ihm = _ihm;
    }
    
    public void setTasaAprendizaje(double valor) {
        tasaAprendizaje = valor;
    }
    
    public void setErrorMax(double valor) {
        errorMax = valor;
    }
    
    public void setNumIteracionesMax(int valor) {
        numIteracionesMax = valor;
    }
    
    public void Lanzar() {
        // Iinicialización
        int numIteraciones = 0;
        double errorTotal = Double.POSITIVE_INFINITY;
        double antiguoError = Double.POSITIVE_INFINITY;
        double errorGeneralizacionTotal = Double.POSITIVE_INFINITY;
        double antiguoErrorGeneralizacion = Double.POSITIVE_INFINITY;
        int numDeaprendizaje = 0;
        
        while (numIteraciones < numIteracionesMax && errorTotal > errorMax && numDeaprendizaje < 5) {
            // Pasa a la siguiente iteración
            antiguoError = errorTotal;
            errorTotal = 0;
            antiguoErrorGeneralizacion = errorGeneralizacionTotal;
            errorGeneralizacionTotal = 0;
            
            // Evaluación y aprendizaje
            errorTotal = Evaluar();
            errorGeneralizacionTotal = Generalizacion();

            if (errorGeneralizacionTotal > antiguoErrorGeneralizacion) {
                numDeaprendizaje++;
            }
            else {
                numDeaprendizaje = 0;
            }
            
            // Visualización e incremento
            ihm.MostrarMensaje("Iteración n°" + numIteraciones + " - Error total : " + errorTotal + " - Generalizacion : " + errorGeneralizacionTotal + " - Tasa : " + tasaAprendizaje + " - Media : " + Math.sqrt(errorTotal / datos.ptsAprendizaje.length));
            numIteraciones++;
        }
    }
    
    private double Evaluar() {
        double errorTotal = 0;
        for (PuntoND punto : datos.getPtsAprendizaje()) {
            double[] salidas = red.Evaluar(punto);
            for (int nb = 0; nb < salidas.length; nb++) {
                double error = punto.salidas[nb] - salidas[nb];
                errorTotal += (error * error);
            }
            red.AjustarPeso(punto, tasaAprendizaje);
        }
        return errorTotal;
    }
    
    private double Generalizacion() {
        double errorGeneralizacionTotal = 0;
        for (PuntoND punto : datos.getPtsGeneralizacion()) {
            double[] salidas = red.Evaluar(punto);
            for (int nb = 0; nb < salidas.length; nb++) {
                double error = punto.salidas[nb] - salidas[nb];
                errorGeneralizacionTotal += (error * error);
            }
        }
        return errorGeneralizacionTotal;
    }
}
