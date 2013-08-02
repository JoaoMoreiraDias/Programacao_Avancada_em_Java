package GUI;

import BD.Cadeira;
import BD.Curso;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class EditarCurso extends JFrame
{
    JLabel L_Nom;
    JTextField TF_Nom;
    JButton B_Grava;

    public EditarCurso(int id)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(id);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(final int id)
    {
        final Curso c  = new Curso();
        Cadeira cad = new Cadeira();
        L_Nom = new JLabel();
        //L_Nom.setLocation(18,3);
        //L_Nom.setSize(100,50);
            L_Nom.setText("Nome");

        TF_Nom = new JTextField();
        //TF_Nom.setLocation(18,53);
        //TF_Nom.setSize(100,50);
        try {
            TF_Nom.setText(""+c.getCursoPorInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TF_Nom.setColumns(10);

        B_Grava = new JButton();
        //B_Grava.setLocation(18,114);
        //B_Grava.setSize(100,50);
        B_Grava.setText("Gravar");
        B_Grava.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev){
                    String nome = TF_Nom.getText();
                    //codigo de validação

                    //SQL
                    c.alterCurso(id,nome);
                }
            });

        getContentPane().add(L_Nom);
        getContentPane().add(TF_Nom);
        getContentPane().add(B_Grava);

        setTitle("Editar Curso");
        setSize(147,202);
        setVisible(true);
        setResizable(false);

    }
    
    //public static void main( String args[] )
    public static void main( int id )
    {
        new EditarCurso(id);
    }
}