package GUI;

import BD.Cadeira;
import BD.Curso;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;

public class EditarCurso extends JFrame
{
    JLabel L_Nom;
    JTextField TF_Nom;
    JButton B_Grava;

    public EditarCurso(int id,Principal este)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(id,este);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(final int id, Principal este)
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
        final Object[] parametros=new Object[1];
        parametros[0]=este;
        B_Grava.addActionListener(new Eventos(parametros)
            {
                public void actionPerformed(ActionEvent ev){
                    String nome = TF_Nom.getText();
                    //codigo de validação

                    //SQL
                    c.alterCurso(id,nome);
                    Principal temp = (Principal)parametros[0];
                    temp.setupGUI();
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
    public static void main( int id , Principal este)
    {
        new EditarCurso(id,este);
    }
}