package Part2_AA.View;

import Simulation.Mem;
import Simulation.Partition;
import Simulation.Process;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MVTFrame extends JFrame {
    private int pasoCount = 0;
    private JTable tbProcesos;
    private JTable tbAreasLibres;
    private JTable tbParticiones;
    private Mem memory;

    public MVTFrame(Mem memory) {
        this.memory = memory;
        setTitle("SIMULACIÓN DE ASIGNACIÓN DE MEMORIA CON MVT");
        setSize(800, 650);
        setLayout(new BorderLayout(5, 5));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelConfiguration();

        setVisible(true);
    }

    public void panelConfiguration() {
        JPanel header = new JPanel();
        JPanel body = new JPanel();
        JPanel footer = new JPanel();
        JPanel procesos = new JPanel();
        JPanel tablas = new JPanel();
        JPanel areasLibres = new JPanel();
        JPanel particiones = new JPanel();
        JPanel memoria = new JPanel();
        Color lightGray = new Color(245, 247, 248);
        Color blue = new Color(150, 182, 197);
        new Color(241, 240, 232);
        new Color(69, 71, 75);
        this.panelColors(header, blue);
        this.panelColors(footer, blue);
        this.panelColors(body, lightGray);
        this.panelColors(tablas, lightGray);
        this.panelColors(procesos, lightGray);
        this.panelColors(areasLibres, lightGray);
        this.panelColors(particiones, lightGray);
        this.panelColors(memoria, lightGray);
        header.setPreferredSize(new Dimension(100, 50));
        body.setPreferredSize(new Dimension(100, 100));
        footer.setPreferredSize(new Dimension(100, 50));
        procesos.setPreferredSize(new Dimension(650, 150));
        tablas.setPreferredSize(new Dimension(500, 400));
        memoria.setPreferredSize(new Dimension(200, 400));
        areasLibres.setPreferredSize(new Dimension(450, 150));
        particiones.setPreferredSize(new Dimension(450, 150));
        procesos.setBorder((Border)null);
        JLabel titulo = new JLabel("Label");
        JLabel lbProcesos = new JLabel();
        JLabel lbAreaLibre = new JLabel();
        JLabel lbParticiones = new JLabel();
        JLabel lbMemoria = new JLabel();
        this.titleConfiguration(titulo, "SIMULACIÓN DE ASIGNACIÓN DE MEMORIA CON MVT");
        this.titleConfiguration(lbProcesos, "Tabla de Procesos");
        this.titleConfiguration(lbAreaLibre, "Tabla de Áreas Libres [TAL]");
        this.titleConfiguration(lbParticiones, "Tabla de Particiones [TP]");
        this.titleConfiguration(lbMemoria, "Memoria");
        header.setLayout(new BorderLayout());
        header.add(titulo, "Center");
        memoria.add(lbMemoria);
        this.createButton(footer);
        this.createTablaProcesos(procesos, lbProcesos);
        this.createTablaAreasLibres(areasLibres, lbAreaLibre);
        this.createTablaParticiones(particiones, lbParticiones);
        Memory memory = new Memory();
        memory.addProcess(10);
        memory.addProcess(54);
        memoria.add(memory, "Center");
        add(header, "North");
        add(body, "Center");
        add(footer, "South");
        body.add(procesos, "North");
        body.add(tablas, "Center");
        body.add(memoria, "East");
        tablas.add(areasLibres, "North");
        tablas.add(particiones, "South");
    }

    public void titleConfiguration(JLabel label, String text) {
        label.setText(text);
        label.setFont(new Font("Segoe UI", 1, 12));
        label.setHorizontalAlignment(0);
        label.setVerticalAlignment(0);
    }

    public void panelColors(JPanel panel, Color color) {
        panel.setBackground(color);
    }

    public void createButton(JPanel panel) {
        JButton paso = new JButton();

        paso.setText("Paso " + pasoCount); // Inicialmente, muestra "Paso 0"
        paso.setBackground(new Color(241, 240, 232));
        paso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasoCount++; // Incrementar el contador de pasos
                paso.setText("Paso " + pasoCount); // Actualizar el texto del botón
                memory.runStep(); // Avanzar un paso en la simulación
                updateProcessTable(memory.getProcessList()); // Actualizar tabla de procesos
                updateFreeAreasTable(memory.getPartitions()); // Actualizar tabla de áreas libres
                updatePartitionsTable(memory.getPartitions()); // Actualizar tabla de particiones
            }
        });
        panel.add(paso);
    }

    public void createTablaProcesos(JPanel procesos, JLabel lbProcesos) {
        String[] columnNames = new String[]{"Proceso", "Tamaño", "Tiempo de llegada", "Duración (tiempo en que finaliza)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 6);
        tbProcesos = new JTable(model);
        JScrollPane jScrollPaneTP = new JScrollPane();
        this.configuracionTablas(tbProcesos, jScrollPaneTP);
        procesos.setLayout(new BorderLayout(10, 10));
        procesos.add(lbProcesos, "North");
        procesos.add(jScrollPaneTP, "Center");
    }

    public void createTablaAreasLibres(JPanel areasLibres, JLabel lbAreasLibres) {
        String[] columnNames = new String[]{"No.", "Localidad", "Tamaño", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 4);
        tbAreasLibres = new JTable(model);
        JScrollPane jScrollPaneTAL = new JScrollPane();
        this.configuracionTablas(tbAreasLibres, jScrollPaneTAL);
        areasLibres.setLayout(new BorderLayout(10, 10));
        areasLibres.add(lbAreasLibres, "North");
        areasLibres.add(jScrollPaneTAL, "Center");
    }

    public void createTablaParticiones(JPanel particiones, JLabel lbParticiones) {
        String[] columnNames = new String[]{"No.", "Localidad", "Tamaño", "Estado", "Proceso"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 4);
        tbParticiones = new JTable(model);
        JScrollPane jScrollPaneTP = new JScrollPane();
        this.configuracionTablas(tbParticiones, jScrollPaneTP);
        particiones.setLayout(new BorderLayout(10, 10));
        particiones.add(lbParticiones, "North");
        particiones.add(jScrollPaneTP, "Center");
    }

    public void configuracionTablas(JTable table, JScrollPane jScrollPane) {
        Color blue = new Color(170, 215, 217);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder((Border)null);
        table.getTableHeader().setBackground(blue);
        table.setBorder((Border)null);
        table.setGridColor(new Color(229, 225, 218));
        jScrollPane.setViewportView(table);
        jScrollPane.getViewport().setBackground((Color)null);
        jScrollPane.setViewportBorder((Border)null);
    }

    // Método para actualizar la tabla de áreas libres en MVTFrame
    public void updateFreeAreasTable(List<Partition> partitions) {
        DefaultTableModel model = (DefaultTableModel) tbAreasLibres.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de actualizarla

        for (Partition partition : partitions) {
            if (partition.isAvailable()) {
                Object[] row = {partition.getLocation(), partition.getSize(), "Disponible"};
                model.addRow(row);
            }
        }
    }

    // Método para actualizar la tabla de particiones en MVTFrame
    public void updatePartitionsTable(List<Partition> partitions) {
        DefaultTableModel model = (DefaultTableModel) tbParticiones.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de actualizarla

        for (Partition partition : partitions) {
            String processName = partition.isAvailable() ? "Libre" : partition.getProcess();
            Object[] row = {partition.getLocation(), partition.getSize(), partition.isAvailable() ? "Libre" : "Ocupado", processName};
            model.addRow(row);
        }
    }

    // Método para actualizar la tabla de procesos en MVTFrame
    public void updateProcessTable(List<Process> processList) {
        DefaultTableModel model = (DefaultTableModel) tbProcesos.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de actualizarla

        for (Process process : processList) {
            Object[] row = {process.getName(), process.getSize(), process.getArrivalTime(), process.getDuration()};
            model.addRow(row);
        }
    }

    public void runNextStep() {
        // Verificar si hay procesos en la lista de espera
        if (!memory.getProcessList().isEmpty()) {
            // Ejecutar un paso en la simulación para el siguiente proceso en la lista
            memory.runStep();
            // Actualizar las tablas en la interfaz gráfica
            updateProcessTable(memory.getProcessList());
            updateFreeAreasTable(memory.getPartitions());
            updatePartitionsTable(memory.getPartitions());
        } else {
            // Si no hay procesos en espera, mostrar mensaje
            JOptionPane.showMessageDialog(this, "No hay más procesos para ejecutar.");
        }
    }
}
