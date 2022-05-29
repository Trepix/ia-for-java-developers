package sistemaexperto;

// Interface para todos los hechos que se debe implementar
public interface IHecho {
    String Nombre();
    Object Valor();
    int Nivel();
    String Pregunta();
    
    void setNivel(int l); // Permite modificar el nivel de un hecho
}
