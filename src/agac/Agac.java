package agac;

import java.awt.*;
import javax.swing.*;

public class Agac {

    static Node kök;
    static int sayac = 0;
    static Cizim jp = new Cizim();
    static JTextArea area = new JTextArea();

    class Node {

        public int x, y, maxx, minx, maxy, miny;
        Node NW, NE, SE, SW;
        Color color;

        /**
         * @
         *
         * @param x x kordinatı
         * @param y y kordinatı
         * @param maxx maxx kordinatı
         * @param minx minx kordinatı
         * @param maxy maxy kordinatı
         * @param miny miny kordinatı
         */

        Node(int x, int y, int maxx, int minx, int maxy, int miny) {
            this.x = x;
            this.y = y;
            this.maxx = maxx;
            this.maxy = maxy;
            this.minx = minx;
            this.miny = miny;

        }
    }

    public void insert(int x, int y, int maxx, int minx, int maxy, int miny) {

        kök = insert(kök, x, y, maxx, minx, maxy, miny);

    }

    Node insert(Node h, int x, int y, int maxx, int minx, int maxy, int miny) {
        Cizim nesne = new Cizim();
        if (h == null && x < 512 && y < 512) {

            return new Node(x, y, maxx, minx, maxy, miny);
        } else if (less(x, h.x) && less(y, h.y)) {

            maxx = h.x;
            maxy = h.y;
            minx = h.minx;
            miny = h.miny;
            nesne.NWsetxy(h.x, h.y, minx, miny);

            h.NW = insert(h.NW, x, y, maxx, minx, maxy, miny);

        } else if (less(x, h.x) && !less(y, h.y)) {

            miny = h.y;
            maxx = h.x;
            maxy = h.maxy;
            minx = h.minx;
            nesne.SWsetxy(h.x, h.y, minx, maxy);

            h.SW = insert(h.SW, x, y, maxx, minx, maxy, miny);

        } else if (!less(x, h.x) && less(y, h.y)) {

            minx = h.x;
            maxy = h.y;
            maxx = h.maxx;
            miny = h.miny;
            nesne.NEsetxy(h.x, h.y, maxx, miny);

            h.NE = insert(h.NE, x, y, maxx, minx, maxy, miny);

        } else if (!less(x, h.x) && !less(y, h.y)) {

            minx = h.x;
            miny = h.y;
            maxx = h.maxx;
            maxy = h.maxy;
            nesne.SEsetxy(h.x, h.y, maxx, maxy);
            h.SE = insert(h.SE, x, y, maxx, minx, maxy, miny);

        }
        return h;
    }

    public void search(int x1, int y1) {

        search(kök, x1, y1);
    }

    void search(Node h, int x1, int y1) {

        if (h == null) {
            return;
        }

        search(h.NW, x1, y1);
        search(h.SW, x1, y1);
        search(h.NE, x1, y1);
        search(h.SE, x1, y1);

        Cizim.panel.Boyama(x1, y1, h.x, h.y);

    }

    public void printTree() {

        printTree(kök);
        System.out.println();
    }

    private void printTree(Node node) {
        if (node == null) {
            return;
        }

        printTree(node.NE);
        printTree(node.SE);
        printTree(node.NW);
        printTree(node.SW);
        System.out.print("listele =" + node.x + "  ");
    }

    public void freeTree() {

        freeTree(kök);

    }

    public void freeTree(Node node) {
        if (node == null) {
            return;
        }

        freeTree(node.NE);
        freeTree(node.SE);
        freeTree(node.NW);
        freeTree(node.SW);

        node.NE = null;
        node.SE = null;
        node.NW = null;
        node.SW = null;
        kök.x = 0;
        kök.y = 0;
        sayac = 0;

    }

    private boolean less(int k1, int k2) {
        return k1 < k2;
    }

    public static void main(String[] args) {
        Agac b = new Agac();

        JFrame jf = new JFrame("Quad Tree");

        JButton clr = new JButton("Temizle");
        JButton rnd = new JButton("Rastgele");
        JButton cmbr = new JButton("Cember");
        JButton sirala = new JButton("Sırala");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        JTextField text = new JTextField();
        JScrollPane scroll = new JScrollPane(area);

        Cizim.panel.Random(rnd);
        Cizim.panel.Cember(cmbr);
        Cizim.panel.Temizle(clr, b, area);
        Cizim.panel.Slider(slider);
        Cizim.panel.Text(text);
        Cizim.panel.Sirala(sirala, area);
        Cizim.panel.setBackground(Color.white);
        Cizim.panel.setSize(512, 512);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.add(rnd);
        jf.add(clr);
        jf.add(cmbr);
        jf.add(slider);
        jf.add(text);
        jf.add(scroll);
        jf.add(sirala);

        scroll.setBounds(525, 250, 100, 150);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jf.setSize(650, 555);

        rnd.setBounds(527, 460, 100, 50);
        clr.setBounds(527, 5, 100, 50);
        cmbr.setBounds(527, 60, 100, 50);
        text.setBounds(527, 430, 100, 25);
        sirala.setBounds(527, 180, 100, 50);

        area.setBackground(Color.WHITE);
        area.setBounds(525, 250, 100, 150);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        slider.setBounds(520, 120, 110, 50);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        jf.add(Cizim.panel);
        jf.add(jp);
        jf.setVisible(true);

    }

}
