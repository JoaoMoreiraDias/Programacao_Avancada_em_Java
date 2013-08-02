package GUI;

import BD.Aluno;
import BD.Cadeira;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;

public class NotasA extends JFrame
{
    JLabel L_Media;
    JLabel L_Valor;

    JLabel L_mediaAluno;
    JLabel L_Notas;
    JPanel jpNotas;
    JLabel L_Curs;
    JList listaNotas;

    JButton B_Adiciona;


    public NotasA(int id)
    {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setupGUI(id);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void setupGUI(final int id)
    { 
        final Aluno a = new Aluno();

        final JComboBox CB_Cad;

        JTextArea aprovadas = new JTextArea(a.aprovadasToString(id));
        JOptionPane.showMessageDialog(null,aprovadas,"Cadeiras Aprovadas",1);

        JTextArea reprovadas = new JTextArea(a.reprovadasToString(id));
        JOptionPane.showMessageDialog(null,reprovadas,"Cadeiras Reprovadas",2);

        L_Notas = new JLabel("Notas deste aluno: ");

        //listModelNotas = new DefaultListModel();
        //listModel.addElement("Jane Doe"); <- adicionar todos os elementos, cadeiras
        //         listaNotas = new JList(listModelNotas);
        //         listaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Compiler warning
        //         listaNotas.setSelectedIndex(0);
        //         listaNotas.setVisibleRowCount(5);
        //         listScrollPaneNotas = new JScrollPane(listaNotas);
        //         jpNotas.add(listScrollPaneNotas);
        jpNotas = new JPanel();
        JScrollPane scrollPaneA = new JScrollPane();
        listaNotas = new JList(Aluno.getNotasDoAluno(id));
        scrollPaneA.setViewportView(listaNotas);
        jpNotas.add(scrollPaneA);

        L_Media = new JLabel("Média: "+Double.toString(Aluno.getMedia(id)));

        JLabel L_NNota = new JLabel();
        //L_Nota.setLocation(18,3);
        //L_Nota.setSize(100,50);
        L_NNota.setText("---Nova Nota---");

        JTextField TF_Nota = new JTextField();
        //TF_Nota.setLocation(18,53);
        //TF_Nota.setSize(100,50);
        TF_Nota.setText("0.0");
        TF_Nota.setColumns(10);


        //L_Curs.setLocation(18,103);
        //L_Curs.setSize(100,50);

        CB_Cad = new JComboBox(a.getCadEspecial(id));
        //CB_Cad.setLocation(18,153);
        //CB_Cad.setSize(100,50);
        CB_Cad.setEditable(false);

        B_Adiciona = new JButton();
        //B_Adiciona.setLocation(17,312);
        //B_Adiciona.setSize(100,50);
        B_Adiciona.setText("Adicionar");
        B_Adiciona.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev){
                    Cadeira c = new Cadeira();
                    int nota = -1;
                    int cadeira = -1;
                    try {
                        cadeira = c.getCadeiradoSQL((String)CB_Cad.getSelectedItem());
                    }
                    catch (SQLException e) {  
                        e.printStackTrace();  
                        JOptionPane.showMessageDialog(null,"Erro no acesso ao getCadeiradoSQL()!!");  
                    }
                    //codigo de validação

                    //SQL
                    if (cadeira!=-1 && nota>=0 && nota<=20){
                        a.addAC(id,cadeira,nota);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Erro, valores não todos validos");
                    }
                }
            });


        getContentPane().add(L_Notas);
        getContentPane().add(jpNotas);
        getContentPane().add(L_Media);

        getContentPane().add(L_NNota);
        getContentPane().add(TF_Nota);
        getContentPane().add(CB_Cad);
        getContentPane().add(B_Adiciona);

        setTitle("Notas e Média (Aluno)");
        setSize(200,200);
        setVisible(true);
        setResizable(true);
    }

    //public static void main( String args[] )
    public static void main( int id )
    {
        new NotasA(id);
    }
}