package GUI;

import BD.Aluno;
import BD.Curso;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;

public class EditarAluno extends JFrame
{

    JLabel L_Nom;
    JComboBox CB_Curs;
    JTextField TF_Nom;
    JLabel L_Curs;
    JButton B_Grava;
    JLabel L_An;
    JComboBox CB_An;

    public EditarAluno(int id, Principal este)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(id,este);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(final int id, Principal este)
    {
        final Aluno a = new Aluno();
        L_Nom = new JLabel();
        //L_Nom.setLocation(18,3);
       // L_Nom.setSize(100,50);
        L_Nom.setText("Nome");

        CB_Curs = new JComboBox(a.getCursos());
        //CB_Curs.setLocation(18,153);
        //CB_Curs.setSize(100,50);
        CB_Curs.setEditable(false );

        TF_Nom = new JTextField();
        //TF_Nom.setLocation(18,53);
        //TF_Nom.setSize(100,50);
        TF_Nom.setText(""+Aluno.getAlunoId(id));
        TF_Nom.setColumns(10);

        L_Curs = new JLabel();
        //L_Curs.setLocation(18,103);
        //L_Curs.setSize(100,50);
        L_Curs.setText("Curso");

        B_Grava = new JButton();
        //B_Grava.setLocation(17,312);
        //B_Grava.setSize(100,50);
        B_Grava.setText("Gravar");
        final Object[] parametros=new Object[1];
        parametros[0]=este;
        B_Grava.addActionListener(new Eventos(parametros)
            {
                public void actionPerformed(ActionEvent ev){
                    Curso c = new Curso();
                    int curso = -1;
                    String nome = TF_Nom.getText();
                    try{
                        curso = c.getCursodoSQL((String)CB_Curs.getSelectedItem());
                    }
                    catch (SQLException e) {  
                        e.printStackTrace();  
                        JOptionPane.showMessageDialog(null,"Erro no acesso ao getCursodoSQL()!!");  
                    }
                    int ano = (Integer)CB_An.getSelectedItem();
                    //codigo de validação

                    //SQL
                    a.alteraAluno(id,nome,curso,ano);
                    Principal temp = (Principal)parametros[0];
                    temp.setupGUI();
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

        getContentPane().add(L_Nom);
        getContentPane().add(TF_Nom);
        getContentPane().add(L_Curs);
        getContentPane().add(CB_Curs);
        getContentPane().add(L_An);
        getContentPane().add(CB_An);
        getContentPane().add(B_Grava);

        setTitle("Editar Aluno");
        setSize(247,180);
        setVisible(true);
        setResizable(false);
    }
    
    //public static void main( String args[] )
    public static void main( int id ,Principal este)
    {
        new EditarAluno(id,este);
    }
}