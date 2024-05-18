package Part2_AA;

public class Particion {
    private int id;
    private int localidad;
    private int tam;
    private boolean estado;
    private String proceso;

    public Particion(int id, int localidad, int tam, boolean estado, String proceso) {
        this.id = id;
        this.localidad = localidad;
        this.tam = tam;
        this.estado = estado;
        this.proceso = proceso;
    }

    public int getId() {
        return id;
    }

    public int getLocalidad() {
        return localidad;
    }

    public int getTam() {
        return tam;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getProceso() {
        return proceso;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }
}