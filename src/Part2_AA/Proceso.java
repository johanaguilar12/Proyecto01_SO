package Part2_AA;

public class Proceso {
    private String nombre;
    private int tam;
    private int tiempoLlegada;
    private int duracion;

    public Proceso(String nombre, int tam, int tiempoLlegada, int duracion) {
        this.nombre = nombre;
        this.tam = tam;
        this.tiempoLlegada = tiempoLlegada;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTam() {
        return tam;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getDuracion() {
        return duracion;
    }

    public void paso(int tiempo) {
        this.duracion -= tiempo;
    }
}