package Part2_AA;

public class Proceso {
    String nombre;
    int tam;
    int tiempoLlegada;
    int duracion;
    public Proceso(String nombre, int tam, int tiempoLlegada, int duracion){
        this.nombre = nombre;
        this.tam = tam;
        this.tiempoLlegada = tiempoLlegada;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void paso(int duracion){
        if(duracion > 0){
            duracion--;
        }
        else{
            System.out.println("Proceso Terminado");
        }
    }
}
