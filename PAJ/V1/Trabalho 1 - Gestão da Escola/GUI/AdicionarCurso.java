package GUI;

import BD.Curso;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdicionarCurso extends JFrame
{
    JLabel L_Nom;
    JTextField TF_Nom;
    JButton B_Adiciona;

    public AdicionarCurso()
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI()
    {
        final Curso c =  new Curso();

        L_Nom = new JLabel();
        //L_Nom.setLocation(18,3);
        //L_Nom.setSize(100,50);
        L_Nom.setText("Nome");

        TF_Nom = new JTextField();
        //TF_Nom.setLocation(18,53);
        //TF_Nom.setSize(100,50);
        TF_Nom.setText("NOME AQUI");
        TF_Nom.setColumns(10);

        B_Adiciona = new JButton();
        //B_Adiciona.setLocation(18,114);
        //B_Adiciona.setSize(100,50);
        B_Adiciona.setText("Adicionar");
        B_Adiciona.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev){
                    String nome = "";
                    nome = TF_Nom.getText();
                    //codigo de validação

                    //SQL
                    if(nome!=""){
                        c.criarCurso(nome);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Erro, valores não todos validos");
                    }
             }
            });


        getContentPane().add(L_Nom);
        getContentPane().add(TF_Nom);
        getContentPane().add(B_Adiciona);

        setTitle("Adicionar Curso");
        setSize(152,320);
        setVisible(true);
        setResizable(false);
    }

    public static void main( String args[] )
    {
        new AdicionarCurso();
    }
}