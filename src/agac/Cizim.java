package agac;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Cizim extends JPanel {

    static int x = 0, y = 0, maxy = 512, maxx = 512, miny = 0, minx = 0;
    static int x1 = 0, y1 = 0, x2 = 0, y2 = 0, sayi = 0, sayi2 = 0, sayi3 = 0, Slider = 50, N;

    static int sayac = 0;
    Agac st = new Agac();
    static Cizim panel = new Cizim();
    static Random rand = new Random();
    static int[] xdizisi = new int[1000];
    static int[] ydizisi = new int[1000];
    static int i = 0;

    static String string = "";

    Cizim() {
        addMouseListener(new MouseKontrolu());

    }

    public void SWsetxy(int maxx, int miny, int minx, int maxy) {

        this.maxx = maxx;
        this.miny = miny;
        this.minx = minx;
        this.maxy = maxy;

    }

    public void NWsetxy(int maxx, int maxy, int minx, int miny) {

        this.maxx = maxx;
        this.maxy = maxy;
        this.minx = minx;
        this.miny = miny;
    }

    public void SEsetxy(int minx, int miny, int maxx, int maxy) {

        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    public void NEsetxy(int minx, int maxy, int maxx, int miny) {

        this.minx = minx;
        this.maxy = maxy;
        this.maxx = maxx;
        this.miny = miny;
    }

    class MouseKontrolu extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Graphics g = panel.getGraphics();

            x = e.getX();
            y = e.getY();

            if (sayi % 2 == 1) {
                x1 = e.getX();
                y1 = e.getY();
                g.setColor(Color.BLUE);
                g.drawOval(x1 - (Slider / 2), y1 - (Slider / 2), Slider, Slider);
                st.search(x1, y1);
                sayi2 = 1;

            }
            if (sayi % 2 == 0) {
                //System.out.println("(" + x + "," + y + ")");
                st.insert(x, y, maxx, minx, maxy, miny);

                float r = rand.nextFloat();
                float gg = rand.nextFloat();
                float b = rand.nextFloat();
                Color randColor = new Color(r, gg, b);
                g.setColor(randColor);
                g.drawLine(minx, y, maxx, y);
                g.drawLine(x, miny, x, maxy);
                g.setColor(randColor);
                g.fillOval(x - 3, y - 3, 6, 6);

            }

        }

    }

    public void Boyama(int x, int y, int x1, int y1) {
        if (Math.pow(x - x1, 2) + Math.pow(y - y1, 2) < Math.pow(Slider / 2, 2)) {
            ydizisi[i] = y1;
            xdizisi[i] = x1;

            i++;
            Graphics g = panel.getGraphics();
            g.setColor(Color.red);
            g.fillOval(x1 - 4, y1 - 4, 8, 8);
            System.out.println("(" + x1 + "," + y1 + ")");

        }
    }

    public static void BubbleSort(int[] dizi, int[] dizi2) {
        int temp, temp2;
        for (int i = 1; i < sayi3; i++) {
            for (int j = 0; j < sayi3 - 1; j++) {
                if (dizi[j] > dizi[j + 1]) {
                    temp = dizi[j];
                    dizi[j] = dizi[j + 1];
                    dizi[j + 1] = temp;
                    temp2 = dizi2[j];
                    dizi2[j] = dizi2[j + 1];
                    dizi2[j + 1] = temp2;
                } else if (dizi[j] == dizi[j + 1]) {
                    if (dizi2[j] > dizi2[j + 1]) {
                        temp = dizi[j];
                        dizi[j] = dizi[j + 1];
                        dizi[j + 1] = temp;
                        temp2 = dizi2[j];
                        dizi2[j] = dizi2[j + 1];
                        dizi2[j + 1] = temp2;

                    }
                }
            }
        }
    }

    public void Siralama() {

        for (int j = 0; xdizisi[j] != 0; j++) {
            sayi3++;

        }

        BubbleSort(xdizisi, ydizisi);

        for (int j = 0; xdizisi[j] != 0; j++) {

            sayac++;

            string += "(" + xdizisi[sayac - 1] + "," + ydizisi[sayac - 1] + ")\n";

            System.out.println("(" + xdizisi[j] + " " + ydizisi[j] + ")" + sayac + " . nokta");

        }
        string += sayac + " adet nokta";
    }

    public void Sirala(JButton sirala, JTextArea area) {
        sirala.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Siralama();
                area.setText(string);
                string = "";
                for (int j = 0; j < xdizisi.length; j++) {
                    xdizisi[j] = 0;
                    ydizisi[j] = 0;
                }
                i = 0;
                sayac = 0;
                sayi3 = 0;

            }
        });

    }

    public void Random(JButton rnd) {
        rnd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < N; i++) {
                    x = rand.nextInt(512);
                    y = rand.nextInt(512);

                    st.insert(x, y, maxx, minx, maxy, miny);
                    float r = rand.nextFloat();
                    float gg = rand.nextFloat();
                    float b = rand.nextFloat();
                    Color randColor = new Color(r, gg, b);
                    Graphics g = panel.getGraphics();

                    g.setColor(randColor);
                    g.drawLine(minx, y, maxx, y);
                    g.drawLine(x, miny, x, maxy);
                    g.setColor(randColor);
                    g.fillOval(x - 3, y - 3, 6, 6);

                }

            }
        });

    }

    public void Temizle(JButton clr, Agac nesne, JTextArea area) {
        clr.addActionListener(new ActionListener() {

            @Override
            @SuppressWarnings("empty-statement")
            public void actionPerformed(ActionEvent e) {
                nesne.freeTree();

                Graphics g = panel.getGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, 512, 512);

                area.setText("");

            }
        });

    }

    public void Cember(JButton cmbr) {
        cmbr.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sayi++;
            }
        });

    }

    public void Text(JTextField text) {
        text.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String string = e.getActionCommand();
                N = Integer.parseInt(string);
                if (N > 500) {
                    N = 500;
                }
            }
        });

    }

    public void Slider(JSlider slider) {

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Slider = slider.getValue();
            }
        });
    }

}
