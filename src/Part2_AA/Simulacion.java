package Part2_AA;

import java.util.ArrayList;
import java.util.List;

public class Simulacion {

    private static final int tam_total = 64;
    private static final int tam_SO = 10;
    private int tam_disponible;
    private int tam_ocupado;

    List<Particion> listaParticion;
    List<AreaLibre> listaAreaLibre;

    List<Proceso> listaProceso;

    public Simulacion(){
        this.listaProceso = new ArrayList<>();
        this.listaParticion = new ArrayList<>();
        this.listaParticion.add(new Particion(1, 0,  10, false, "Sistema Operativo"));
        this.listaParticion.add(new Particion(2, 10, tam_total - tam_SO, true, "" ));
        this.tam_disponible = tam_total - tam_SO;
    }

    public void agregarProceso(Proceso proceso){
        int tam = proceso.getTam();
        if(tam <= tam_disponible){
            for(Particion particion : listaParticion){
                if(particion.isEstado()){
                    if(particion.getTam() >= tam){
                        particion.setEstado(false);
                        particion.setProceso(proceso.getNombre());
                        tam_disponible -= tam;
                        break;
                    }
                }
            }
        }
        else{
            System.out.println("No hay espacio suficiente para el proceso " + proceso.getNombre() + " de tamaño " + proceso.getTam() + " bytes.");
        }
    }

    public void eliminarProceso(Proceso proceso){
        for(Particion particion : listaParticion){
            if(particion.getProceso().equals(proceso.getNombre())){
                particion.setEstado(true);
                particion.setProceso("");
                tam_disponible += particion.getTam();
                System.out.println("Proceso " + proceso.getNombre() + " eliminado.");
            }
        }
    }

    public void pasoN(int time){
        for(Proceso proceso : listaProceso){
            if(proceso.getTiempoLlegada() == time){
                agregarProceso(proceso);
            }
        }
        for(Particion particion : listaParticion){
            if(!particion.isEstado()){
                for(Proceso proceso : listaProceso){
                    if(proceso.getNombre().equals(particion.getProceso())){
                        proceso.paso(1);
                        if(proceso.getDuracion() == 0){
                            eliminarProceso(proceso);
                            if(comprobarFragmentacion()){
                                combinarFragmentacion();
                            }
                        }
                    }
                }
            }
        }
    }


    public boolean comprobarFragmentacion(){
        int cont = 0;
        for(Particion particion : listaParticion){
            if(particion.isEstado()){
                cont++;
            }
        }
        if(cont == 1){
            System.out.println("No hay fragmentación externa.");
            return false;
        }
        else{
            System.out.println("Hay fragmentación externa.");
            return true;
        }
    }

    public void combinarFragmentacion(){
        for(int i = 0; i < listaParticion.size(); i++){
            if(listaParticion.get(i).isEstado()){
                if(i < listaParticion.size() - 1){
                    if(listaParticion.get(i + 1).isEstado()){
                        listaParticion.get(i).setTam(listaParticion.get(i).getTam() + listaParticion.get(i + 1).getTam());
                        listaParticion.remove(i + 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Crear el objeto Mem para simular la asignación de memoria
        Simulacion simulacion = new Simulacion();

        // Crear la lista de procesos
        List<Proceso> procesos = new ArrayList<>();
        procesos.add(new Proceso("A", 8, 1, 7));
        procesos.add(new Proceso("B", 14, 2, 7));
        procesos.add(new Proceso("C", 18, 3, 4));
        procesos.add(new Proceso("D", 6, 4, 6));
        procesos.add(new Proceso("E", 14, 5, 5));

        for(Proceso proceso : procesos){
            simulacion.agregarProceso(proceso);
        }


    }
}
