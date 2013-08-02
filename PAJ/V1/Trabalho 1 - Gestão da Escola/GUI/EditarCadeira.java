package GUI;

import BD.Cadeira;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class EditarCadeira extends JFrame
{
    JLabel L_Semestr;
    JComboBox CB_Curs;
    JTextField TF_Nom;
    JLabel L_Curs;
    JButton B_Grava;
    JLabel L_An;
    JComboBox CB_An;
    JComboBox CB_Semestr;
    JLabel L_Nom;

    public EditarCadeira(int id)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(id);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(final int id)
    {
        final Cadeira c = new Cadeira();
        L_Semestr = new JLabel();
        //L_Semestr.setLocation(18,300);
        //L_Semestr.setSize(100,50);
        L_Semestr.setText("Semestre");

        CB_Curs = new JComboBox(c.getTodasCadeiras());
       //esta mal a logica, quero serSelectedIndex(Curso que tem esta cadeira), mas e um extra
       // try {
         //   CB_Curs.setSelectedIndex(id);
            //CB_Curs.setSelectedIndex(c.getCadeiradoSQL(c.getCadeiradoPorInt(id)));
        //} catch (SQLException e) {
        //    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
       // }

        //CB_Curs.setLocation(18,153);
        //CB_Curs.setSize(100,50);
        CB_Curs.setEditable(false );

        TF_Nom = new JTextField();
        //TF_Nom.setLocation(18,53);
        //TF_Nom.setSize(100,50);
        try {
            TF_Nom.setText(""+c.getCadeiradoPorInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TF_Nom.setColumns(10);

        L_Curs = new JLabel();
        //L_Curs.setLocation(18,103);
        //L_Curs.setSize(100,50);
        L_Curs.setText("Curso");

        B_Grava = new JButton();
        //B_Grava.setLocation(18,414);
        //B_Grava.setSize(100,50);
        B_Grava.setText("Gravar");
        B_Grava.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev){
                    String nome = TF_Nom.getText();
                    int ano = (Integer)CB_An.getSelectedItem();
                    int semestre = (Integer)CB_Semestr.getSelectedItem();
                    //codigo de validação

                    //SQL
                    c.alterCadeira(id,nome,ano,semestre);
                }
            });

        L_An = new JLabel();
        //L_An.setLocation(18,203);
        //L_An.setSize(100,50);
        L_An.setText("Ano");

        Integer[] anos = {1,2,3};
        CB_An = new JComboBox(anos);
        //CB_An.setLocation(18,252);
        //CB_An.setSize(100,50);
        CB_An.setEditable(false);

        Integer[] semes = {1,2};
        CB_Semestr = new JComboBox(semes);
        //CB_Semestr.setLocation(18,349);
        //CB_Semestr.setSize(100,50);
        CB_Semestr.setEditable(false);

        L_Nom = new JLabel();
        //L_Nom.setLocation(18,3);
        //L_Nom.setSize(100,50);
        L_Nom.setText("Nome");

        getContentPane().add(L_Nom);
        getContentPane().add(TF_Nom);
        getContentPane().add(L_Curs);
        getContentPane().add(CB_Curs);
        getContentPane().add(L_An);
        getContentPane().add(CB_An);
        getContentPane().add(L_Semestr);
        getContentPane().add(CB_Semestr);
        getContentPane().add(B_Grava);


        setTitle("Editar Cadeira");
        setSize(152,222);
        setVisible(true);
        setResizable(false);
    }

    //public static void main( String args[] )
    public static void main( int id )
    {
        new EditarCadeira(id);
    }
}