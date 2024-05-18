package Part2_AA.View;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

public class Memory extends JPanel {
    private static final int memorySizeKb = 64;
    private static final int width = 160;
    private static final int height = 280;
    private List<Process> processList = new ArrayList();

    public Memory() {
        this.setPreferredSize(new Dimension(160, 280));
    }

    public void addProcess(int sizeKb) {
        Process process = new Process(sizeKb);
        this.processList.add(process);
    }

    protected void paintComponent(Graphics rectangle) {
        new Color(38, 80, 115);
        Color lightBlue = new Color(146, 199, 207);
        super.paintComponent(rectangle);
        rectangle.setColor(Color.WHITE);
        rectangle.fillRect(0, 0, 160, 280);
        int yOffset = 10;

        int processHeight;
        for(Iterator var5 = this.processList.iterator(); var5.hasNext(); yOffset += processHeight + 10) {
            Process process = (Process)var5.next();
            processHeight = process.getSizeKB() * 280 / 64;
            rectangle.setColor(lightBlue);
            rectangle.fillRect(50, yOffset, 60, processHeight);
            rectangle.setColor(Color.BLACK);
            rectangle.drawString(process.getSizeKB() + " KB", 10, yOffset + processHeight / 2);
        }

    }

    private static class Process {
        private int sizeKB;

        public Process(int sizeKB) {
            this.sizeKB = sizeKB;
        }

        public int getSizeKB() {
            return this.sizeKB;
        }
    }
}
