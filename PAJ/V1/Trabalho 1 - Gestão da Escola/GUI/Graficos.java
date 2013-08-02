package GUI;

import BD.Aluno;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Vector;
import javax.swing.*;

public class Graficos extends JPanel
{
    private int id;

    Vector<String> valores;
    //ArrayList<Double> x = new ArrayList<Double>();
    final double PAD = 20;

    private double[] data;

    public Graficos(int id){
        super();
        this.setId(id);
        final Aluno a = new Aluno();
        valores = new Vector<String>(a.getNotasDoAluno(this.id));
        data = new double[valores.size()];
        for (int i = 0; i<valores.size();i++)
        {
            try
            {
                String s = (String)valores.get(i);
                String arr[] = s.split(" ", 2);
                String dado = arr[1];
                data[i] = Double.parseDouble(dado);
            }
            catch (Exception ex)
            {
              System.out.println("Erro no Graficos @ Fazer data[]");
            }
        }
        add(new JLabel("Valor Maximo: "+max(data)));
        add(new JLabel("Valor Minimo: "+min(data)));
        add(new JLabel("Média: "+mean(data)));
        add(new JLabel("Mediana: "+median(data)));
        repaint();

    }

    protected void paintComponent(Graphics g) {     
        /*for (String s : valores) {
            String arr[] = s.split(" ", 2);
            String intValue = arr[0];
            //x.add(Double.parseDouble(intValue));
            for (int i=0;i<valores.size();i++){
                data[i]=Double.parseDouble(intValue);
            }
        }*/

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double w = getWidth();
        double h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));

        for (int n=0; n<=19; n++)
        {
            g2.drawString(""+(20-(n)), 0, (int)(PAD+(((h-PAD)/20)*n)));
        }

        for (int n=0; n<20; n++)
        {
        //g2.draw(new Line2D.Double(PAD, PAD-(((h-PAD)-2*PAD)/(20))*n, w-PAD, PAD-(((h-PAD)-2*PAD)/(20))*n));
            if (n==10)
           {
               g2.setPaint(Color.red);
               g2.draw(new Line2D.Double(PAD,(PAD+(((h-PAD)/20)*n)), w-PAD,PAD+(((h-PAD)/20)*n)));
           }
            else
           {
               g2.setPaint(Color.black);
               g2.draw(new Line2D.Double(PAD,(PAD+(((h-PAD)/20)*n)), w-PAD,PAD+(((h-PAD)/20)*n)));
           }
        }

        double xInc = (double)(w - 2*PAD)/(data.length-1);
        //double scale = (double)(h - 2*PAD)/getMax();
        //double scale = (double)(h - 2*PAD)/max(data);
        double scale = (double)(h - 2*PAD)/20;
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
        repaint();
    }

    /*private double getMax() {
        double max = -Integer.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }*/

    public static double max(double[] array) {
        // Validates input
        if (array== null) {
            throw new IllegalArgumentException("A Array não pode estar a null!");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("A Array não pode estar vazia!");
        }

        // Finds and returns max
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Double.isNaN(array[j])) {
                return Double.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static double min(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("A Array não pode estar a null!");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("A Array não pode estar vazia!");
        }

        // Finds and returns min
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static double mean(double[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }

    public static double median(double[] m) {
        int middle = m.length/2;
        if (m.length%2 == 1) {
            return m[middle];
        } else {
            return (m[middle-1] + m[middle]) / 2.0;
        }
    }

    public void setId(int id)
    {
        this.id=id;
    }

    //public static void main(String[] args) {
    public static void main(int id) {    
        Graficos graficos = new Graficos(id);
        graficos.setId(id);
        //this.id=id;
        JFrame f = new JFrame();
        //f.add(new Graficos());
        f.add(graficos);
        f.pack(); // teste para iniciar automaticamente
        f.repaint();
        //f.setSize(400,400);
        //f.setLocation(200,200);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}