package Part2_AA;

public class AreaLibre {
    int numero;
    int localidad;
    int tam;
    boolean estado;
    public AreaLibre(int numero, int localidad, int tam, boolean estado){
        this.numero = numero;
        this.localidad = localidad;
        this.tam = tam;
        this.estado = estado;
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
}
