package GUI;

import BD.Aluno;
import BD.Cadeira;
import BD.Curso;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.*;

public class AdicionarCadeira extends JFrame
{
    JLabel L_Semestr;
    JComboBox CB_Curs;
    JTextField TF_Nom;
    JLabel L_Curs;
    JButton B_Adiciona;
    JLabel L_An;
    JComboBox CB_An;
    JComboBox CB_Semestr;
    JLabel L_Nom;

    public AdicionarCadeira(Principal este)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(este);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(Principal este)
    {
        final Cadeira c = new Cadeira();
        final Aluno a = new Aluno();
        final Curso cur = new Curso();

        L_Semestr = new JLabel();
        //L_Semestr.setLocation(18,300);
        //L_Semestr.setSize(100,50);
        L_Semestr.setText("Semestre");

        CB_Curs = new JComboBox(a.getCursos());
        //CB_Curs.setLocation(18,153);
        //CB_Curs.setSize(100,50);
        CB_Curs.setEditable(false);

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
        //B_Adiciona.setLocation(18,414);
        //B_Adiciona.setSize(100,50);
        B_Adiciona.setText("Adicionar");
        final Object[] parametros=new Object[1];
        parametros[0]=este;
        B_Adiciona.addActionListener(new Eventos(parametros)
            {
                public void actionPerformed(ActionEvent ev){
                    String nome = "";
                    int ano = -1;
                    int semestre = -1;
                    nome = TF_Nom.getText();
                    ano = (Integer)CB_An.getSelectedItem();
                    semestre = (Integer)CB_Semestr.getSelectedItem();
                    int curso = 0;
                    try {
                        curso = cur.getCursodoSQL(String.valueOf(CB_Curs.getSelectedItem()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    int este = -1;
                    este = c.getRecente();
                    //codigo de validação

                    //SQL
                    if(nome!=null && ano!=-1 && semestre!=-1){
                        c.criarCadeira(nome,ano,semestre);
                        c.addCC(este,curso);
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
        getContentPane().add(B_Adiciona);

        setTitle("Adicionar Cadeira");
        setSize(152,220);
        setVisible(true);
        setResizable(false);
    }

    public static void main(Principal este)
    {
        new AdicionarCadeira(este);
    }
}