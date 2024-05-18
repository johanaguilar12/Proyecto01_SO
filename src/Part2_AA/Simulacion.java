package Part2_AA;

import java.util.ArrayList;
import java.util.List;

public class Simulacion {

    private static final int tam_total = 64;
    private static final int tam_SO = 10;
    private int tam_disponible;

    List<Particion> listaParticion;
    List<Proceso> listaProceso;
    List<Proceso> procesosRecienAgregados; // Lista para almacenar los procesos recién agregados
    private int idd = 1;

    public Simulacion() {
        this.listaProceso = new ArrayList<>();
        this.listaProceso.add(new Proceso("A", 8, 1, 7));
        this.listaProceso.add(new Proceso("B", 14, 2, 7));
        this.listaProceso.add(new Proceso("C", 18, 3, 4));
        this.listaProceso.add(new Proceso("D", 6, 4, 6));
        this.listaProceso.add(new Proceso("E", 14, 5, 5));
        this.listaParticion = new ArrayList<>();
        this.listaParticion.add(new Particion(1, 0, 10, false, "Sistema Operativo"));
        this.listaParticion.add(new Particion(2, 10, 54, true, ""));
        this.tam_disponible = 54;
        this.procesosRecienAgregados = new ArrayList<>();
    }

    public void agregarProceso(Proceso proceso) {
        int tam = proceso.getTam();
        boolean agregado = false;
        System.out.println("Tamaño disponible " + tam_disponible + "| Tamaño del Proceso " + tam);
        if (tam <= tam_disponible) {
            for (Particion particion : listaParticion) {
                if (particion.isEstado() && particion.getTam() >= tam) {
                    particion.setEstado(false);
                    particion.setProceso(proceso.getNombre());
                    tam_disponible -= tam;
                    // Calcular la nueva localidad y agregar la nueva partición
                    int nuevaLocalidad = particion.getLocalidad() + tam;
                    listaParticion.add(new Particion(idd++, nuevaLocalidad, tam_disponible, true, "")); // Crear nueva partición
                    System.out.println("Proceso " + proceso.getNombre() + " agregado en partición " + particion.getLocalidad());
                    agregado = true;
                    break;
                }
            }
            if (!agregado) {
                System.out.println("No hay una partición lo suficientemente grande para el proceso " + proceso.getNombre());
            }
        } else {
            System.out.println("No hay espacio suficiente para el proceso " + proceso.getNombre() + " de tamaño " + proceso.getTam() + " bytes.");
        }
    }

    public void eliminarProceso(Proceso proceso) {
        for (Particion particion : listaParticion) {
            if (particion.getProceso().equals(proceso.getNombre())) {
                particion.setEstado(true);
                particion.setProceso("");
                tam_disponible += particion.getTam();
                System.out.println("Proceso " + proceso.getNombre() + " eliminado de partición " + particion.getLocalidad());
                break; // Se debe salir del bucle después de encontrar el proceso
            }
        }
    }

    public void pasoN(int time) {
        System.out.println("Paso " + time);

        // Agregar procesos que lleguen en este tiempo
        List<Proceso> procesosParaAgregar = new ArrayList<>();
        for (Proceso proceso : listaProceso) {
            if (proceso.getTiempoLlegada() == time) {
                procesosParaAgregar.add(proceso);
            }
        }

        for (Proceso proceso : procesosParaAgregar) {
            agregarProceso(proceso);
            procesosRecienAgregados.add(proceso); // Agregar el proceso a la lista de recién agregados
        }

        // Actualizar duración de los procesos en ejecución
        for (Proceso proceso : listaProceso) {
            for (Particion particion : listaParticion) {
                if (!particion.isEstado() && proceso.getNombre().equals(particion.getProceso())) {
                    // No decrementar la duración si el proceso está en la lista de recién agregados
                    if (!procesosRecienAgregados.contains(proceso)) {
                        proceso.paso(1);
                        System.out.println("Proceso " + proceso.getNombre() + " actualizado, duración restante: " + proceso.getDuracion());
                    }
                }
            }
        }

        // Crear una lista de procesos para eliminar después de actualizar sus duraciones
        List<Proceso> procesosParaEliminar = new ArrayList<>();

        // Verificar si algún proceso ha terminado y agregarlo a la lista de eliminación
        for (Proceso proceso : listaProceso) {
            if (proceso.getDuracion() == 0) {
                procesosParaEliminar.add(proceso);
            }
        }

        // Eliminar procesos cuya duración ha llegado a cero
        for (Proceso proceso : procesosParaEliminar) {
            eliminarProceso(proceso);
        }

        // Verificar y combinar fragmentación después de eliminar procesos
        if (comprobarFragmentacion()) {
            combinarFragmentacion();
        }

        // Limpiar la lista de procesos recién agregados para el próximo ciclo
        procesosRecienAgregados.clear();
    }


    public boolean comprobarFragmentacion() {
        int cont = 0;
        for (Particion particion : listaParticion) {
            if (particion.isEstado()) {
                cont++;
            }
        }
        if (cont == 1) {
            System.out.println("No hay fragmentación externa.");
            return false;
        } else {
            System.out.println("Hay fragmentación externa.");
            return true;
        }
    }

    public void combinarFragmentacion() {
        for (int i = 0; i < listaParticion.size() - 1; i++) {
            if (listaParticion.get(i).isEstado() && listaParticion.get(i + 1).isEstado()) {
                listaParticion.get(i).setTam(listaParticion.get(i).getTam() + listaParticion.get(i + 1).getTam());
                listaParticion.remove(i + 1);
                i--; // Necesario para re-evaluar el índice actual después de la eliminación
            }
        }
    }

    public List<Particion> getListaParticion() {
        return listaParticion;
    }

    public List<Proceso> getListaProceso() {
        return listaProceso;
    }

    public void reiniciarSimulacion() {
        // Limpiar la lista de procesos
        listaProceso.clear();

        // Agregar los procesos iniciales
        this.listaProceso.add(new Proceso("A", 8, 1, 7));
        this.listaProceso.add(new Proceso("B", 14, 2, 7));
        this.listaProceso.add(new Proceso("C", 18, 3, 4));
        this.listaProceso.add(new Proceso("D", 6, 4, 6));
        this.listaProceso.add(new Proceso("E", 14, 5, 5));

        // Limpiar la lista de particiones
        listaParticion.clear();

        // Agregar las particiones iniciales
        this.listaParticion.add(new Particion(1, 0, 10, false, "Sistema Operativo"));
        this.listaParticion.add(new Particion(2, 10, 54, true, ""));

        // Restablecer el espacio disponible
        this.tam_disponible = 54;

        // Restablecer la lista de procesos recién agregados
        this.procesosRecienAgregados.clear();

        // Restablecer el identificador de particiones
        this.idd = 1;
    }

    public int getTam_disponible() {
        return tam_disponible;
    }


    public static void main(String[] args) {
        Simulacion simulacion = new Simulacion();

        for (int i = 1; i <= 11; i++) {
            simulacion.pasoN(i);
        }
    }

}