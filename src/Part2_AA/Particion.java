package Part2_AA;

public class Particion {
    int numero;
    int localidad;
    int tam;
    boolean estado;
    String proceso;

    public Particion(int numero, int localidad, int tam, boolean estado, String proceso){
        this.numero = numero;
        this.localidad = localidad;
        this.tam = tam;
        this.estado = estado;
        this.proceso = proceso;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getLocalidad() {
        return localidad;
    }

    public void setLocalidad(int localidad) {
        this.localidad = localidad;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }
}
