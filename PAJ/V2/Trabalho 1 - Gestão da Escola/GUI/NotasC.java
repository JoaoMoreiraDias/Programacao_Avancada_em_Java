package GUI;

import BD.Cadeira;
import java.awt.*;
import javax.swing.*;

public class NotasC extends JFrame
{
    JList listaNotas;

    JLabel L_Notas;
    JLabel L_Media;
    JLabel L_Valor;

    JPanel jpNotas;
    JPanel jpMedia;

    DefaultListModel listModelNotas;

    JScrollPane listScrollPaneNotas;

    public NotasC(int id)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(id);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(int id)
    { 
        Cadeira c = new Cadeira();
        
        jpNotas = new JPanel();
        jpMedia = new JPanel();
        jpNotas.setLayout(new BorderLayout());

        //listModelNotas = new DefaultListModel();
        //         //listModel.addElement("Jane Doe"); <- adicionar todos os elementos, cadeiras
        //         listaNotas = new JList(listModelNotas);
        //         listaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Compiler warning
        //         listaNotas.setSelectedIndex(0);
        //         listaNotas.setVisibleRowCount(5);
        //         listScrollPaneNotas = new JScrollPane(listaNotas);
        //         jpNotas.add(listScrollPaneNotas);
        JScrollPane scrollPaneA = new JScrollPane();
        listaNotas = new JList(Cadeira.getNotasDaCadeira(id));
        scrollPaneA.setViewportView(listaNotas);
        jpNotas.add(listaNotas);

        L_Media = new JLabel("Média: "+Double.toString(Cadeira.getMedia(id)));
        //jpMedia.add(new JLabel(Double.toString(Cadeira.getMedia(id))));

        L_Notas = new JLabel("Notas para esta cadeira: ");

        getContentPane().add(L_Notas);
        getContentPane().add(jpNotas);
        getContentPane().add(L_Media);

        setTitle("Notas e Média (Cadeira)");
        setSize(381,608);
        setVisible(true);
        setResizable(true);
    }

    //public static void main( String args[] )
    public static void main ( int id )
    {
        new NotasC(id);
    }
}