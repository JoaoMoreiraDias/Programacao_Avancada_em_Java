package GUI;

import BD.Aluno;
import BD.Curso;
import BD.Ligacao;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;

public class AdicionarAluno extends JFrame
{
    private Ligacao lig;

    JLabel L_Nom;
    JComboBox CB_Curs;
    JTextField TF_Nom;
    JLabel L_Curs;
    JButton B_Adiciona;
    JLabel L_An;
    JComboBox CB_An;

    public AdicionarAluno(Principal este)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(este);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI (Principal este)
    {
        final Aluno a = new Aluno();
        lig = new Ligacao();
        lig.getConnection();
        L_Nom = new JLabel();
        //L_Nom.setLocation(18,3);
        //L_Nom.setSize(100,50);
        L_Nom.setText("Nome");


        CB_Curs = new JComboBox(a.getCursos());
        //CB_Curs.setLocation(18,153);
        //CB_Curs.setSize(100,50);
        CB_Curs.setEditable(false );

        TF_Nom = new JTextField();
        //TF_Nom.setLocation(18,53);
        //TF_Nom.setSize(100,50);
        TF_Nom.setText("NOME AQUI");
        TF_Nom.setColumns(10);

        L_Curs = new JLabel();
        //L_Curs.setLocation(18,103);
        //L_Curs.setSize(100,50);
        L_Curs.setText("Curso");

        B_Adiciona = new JButton();
        //B_Adiciona.setLocation(17,312);
        //B_Adiciona.setSize(100,50);
        B_Adiciona.setText("Adicionar");
        final Object[] parametros=new Object[1];
        parametros[0]=este;
        B_Adiciona.addActionListener(new Eventos(parametros)
            {
                public void actionPerformed(ActionEvent ev){
                    Curso c = new Curso();
                    int curso = -1;
                    int ano = -1;
                    String nome = TF_Nom.getText();
                    try{
                        curso = c.getCursodoSQL((String)CB_Curs.getSelectedItem());
                    }
                    catch (SQLException e) {  
                        e.printStackTrace();  
                        JOptionPane.showMessageDialog(null,"Erro no acesso ao getCursodoSQL()!!");  
                    }
                    ano = (Integer)CB_An.getSelectedItem();
                    //codigo de validação

                    //SQL
                    if ((curso != -1) && (nome != "") && (ano != -1)){
                        a.criarAluno(nome,curso,ano);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Erro, valores não todos validos");
                    }
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
        getContentPane().add(B_Adiciona);

        setTitle("Adicionar Aluno");
        setSize(247,180);
        setVisible(true);
        setResizable(false);
    }

    public static void main(Principal este )
    {
        new AdicionarAluno(este);
    }
}  