package sistemaexperto;

import java.util.ArrayList;

// Clase que gestiona la base de reglas
class BaseDeReglas {
    // Lista de las reglas
    protected ArrayList<Regla> reglas;
    public ArrayList<Regla> getReglas() {
       return reglas;
    }
    public void setReglas(ArrayList<Regla> _reglas) {
        // Se copia las reglas y se añaden
        for (Regla r : _reglas) {
            Regla copia = new Regla(r.nombre, r.premisas, r.conclusion);
            reglas.add(copia);
        }
    }
   
    // Constructor
    public BaseDeReglas() { 
        reglas = new ArrayList(); 
    } 

    // Eliminar las reglas
    public void ClearBase() { 
        reglas.clear(); 
    } 
  
    // Agregar una regla a la base
    public void AgregarRegla(Regla r) { 
        reglas.add(r); 
    } 
  
    // Eliminar una regla
    public void Eliminar(Regla r) { 
        reglas.remove(r); 
    }
}
