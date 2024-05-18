package Part2_AA.View;

import Part2_AA.Simulacion;
import Part2_AA.Particion;
import Part2_AA.Proceso;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MVTFrame extends JFrame {
    private int pasoCount = 0;
    private JTable tbProcesos;
    private JTable tbAreasLibres;
    private JTable tbParticiones;
    private Simulacion memory;
    private JButton pasoButton; // Variable para almacenar el botón Paso

    public MVTFrame(Simulacion memory) {
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
        procesos.setBorder(null);
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
        header.add(titulo, BorderLayout.CENTER);
        memoria.add(lbMemoria);
        this.createButton(footer);
        this.createTablaProcesos(procesos, lbProcesos);
        this.createTablaAreasLibres(areasLibres, lbAreaLibre);
        this.createTablaParticiones(particiones, lbParticiones);
        Memory memoryPanel = new Memory();
        memoria.add(memoryPanel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
        body.setLayout(new BorderLayout());
        body.add(procesos, BorderLayout.NORTH);
        body.add(tablas, BorderLayout.CENTER);
        body.add(memoria, BorderLayout.EAST);
        tablas.setLayout(new BorderLayout());
        tablas.add(areasLibres, BorderLayout.NORTH);
        tablas.add(particiones, BorderLayout.SOUTH);
    }

    public void titleConfiguration(JLabel label, String text) {
        label.setText(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    public void panelColors(JPanel panel, Color color) {
        panel.setBackground(color);
    }

    public void createButton(JPanel panel) {
        JButton paso = new JButton("Paso " + pasoCount);
        paso.setBackground(new Color(241, 240, 232));
        paso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasoCount++;
                paso.setText("Paso " + pasoCount);
                memory.pasoN(pasoCount);
                actualizarParticiones();
                actualizarAreasLibres();
            }
        });
        panel.add(paso);
        pasoButton = paso; // Almacenar el botón Paso en la variable de instancia
    }

    public void createTablaProcesos(JPanel procesos, JLabel lbProcesos) {
        String[] columnNames = {"Proceso", "Tamaño", "Tiempo de llegada", "Duración (tiempo en que finaliza)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 6);
        tbProcesos = new JTable(model);
        JScrollPane jScrollPaneTP = new JScrollPane(tbProcesos);
        this.configuracionTablas(tbProcesos, jScrollPaneTP);
        procesos.setLayout(new BorderLayout(10, 10));
        procesos.add(lbProcesos, BorderLayout.NORTH);
        procesos.add(jScrollPaneTP, BorderLayout.CENTER);
    }

    public void createTablaAreasLibres(JPanel areasLibres, JLabel lbAreasLibres) {
        String[] columnNames = {"No.", "Localidad", "Tamaño", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 4);
        tbAreasLibres = new JTable(model);
        JScrollPane jScrollPaneTAL = new JScrollPane(tbAreasLibres);
        this.configuracionTablas(tbAreasLibres, jScrollPaneTAL);
        areasLibres.setLayout(new BorderLayout(10, 10));
        areasLibres.add(lbAreasLibres, BorderLayout.NORTH);
        areasLibres.add(jScrollPaneTAL, BorderLayout.CENTER);
    }

    public void createTablaParticiones(JPanel particiones, JLabel lbParticiones) {
        String[] columnNames = {"No.", "Localidad", "Tamaño", "Estado", "Proceso"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 4);
        tbParticiones = new JTable(model);
        JScrollPane jScrollPaneTP = new JScrollPane(tbParticiones);
        this.configuracionTablas(tbParticiones, jScrollPaneTP);
        particiones.setLayout(new BorderLayout(10, 10));
        particiones.add(lbParticiones, BorderLayout.NORTH);
        particiones.add(jScrollPaneTP, BorderLayout.CENTER);
    }

    public void configuracionTablas(JTable table, JScrollPane jScrollPane) {
        Color blue = new Color(170, 215, 217);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder(null);
        table.getTableHeader().setBackground(blue);
        table.setBorder(null);
        table.setGridColor(new Color(229, 225, 218));
        jScrollPane.setViewportView(table);
        jScrollPane.getViewport().setBackground(null);
        jScrollPane.setViewportBorder(null);
    }

    public void actualizarParticiones() {
        DefaultTableModel model = (DefaultTableModel) tbParticiones.getModel();
        model.setRowCount(0);
        List<Particion> listaParticion = memory.getListaParticion();
        for (int i = 0; i < listaParticion.size(); i++) {
            Particion particion = listaParticion.get(i);
            model.addRow(new Object[]{i + 1, particion.getLocalidad(), particion.getTam(), particion.isEstado(), particion.getProceso()});
        }
    }
    public void actualizarAreasLibres() {
        DefaultTableModel model = (DefaultTableModel) tbAreasLibres.getModel();
        model.setRowCount(0);
        List<Particion> listaParticion = memory.getListaParticion();
        for (int i = 0; i < listaParticion.size(); i++) {
            Particion particion = listaParticion.get(i);
            if (particion.isEstado()) {
                model.addRow(new Object[]{i + 1, particion.getLocalidad(), particion.getTam(), particion.isEstado()});
            }
        }
    }

}
