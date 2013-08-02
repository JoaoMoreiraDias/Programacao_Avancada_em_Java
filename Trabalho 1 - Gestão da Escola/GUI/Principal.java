package GUI;

import BD.Aluno;
import BD.Cadeira;
import BD.Curso;
import BD.Ligacao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Principal extends JFrame
{
    private static Connection derby = Ligacao.getConnection();

    JButton B_Adiciona;
    JButton B_Remove;
    JButton B_Edita;
    JButton B_Notas;
    JButton B_Graficos;

    JTabbedPane jtp;

    JPanel jpAlunos;
    JPanel jpCadeiras;
    JPanel jpCursos;

    JList listAlunos;
    JList listCadeiras;
    JList listCursos;

    DefaultListModel listModelAlunos;
    DefaultListModel listModelCadeiras;
    DefaultListModel listModelCursos;

    JScrollPane listScrollPaneAlunos;
    JScrollPane listScrollPaneCadeiras;
    JScrollPane listScrollPaneCursos;

    public Principal()
    {
        setupGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void setupGUI()
    {
        Ligacao L = new Ligacao();

        jtp = new JTabbedPane();

        jpAlunos = new JPanel();
        jpAlunos.setLayout(new BorderLayout());
        jpCadeiras = new JPanel();
        jpCadeiras.setLayout(new BorderLayout());
        jpCursos = new JPanel();
        jpCursos.setLayout(new BorderLayout());

        jtp.addTab("Alunos", jpAlunos);
        jtp.addTab("Cadeiras", jpCadeiras);
        jtp.addTab("Cursos", jpCursos);

        //         listModelAlunos = new DefaultListModel();
        //         //listModel.addElement("Jane Doe"); <- adicionar todos os elementos, cadeiras
        //         listAlunos = new JList(listModelAlunos);
        //         listAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Compiler warning
        //         listAlunos.setSelectedIndex(0);
        //         listAlunos.setVisibleRowCount(5);
        //         listScrollPaneAlunos = new JScrollPane(listAlunos);
        //         jpAlunos.add(listScrollPaneAlunos);
        JScrollPane scrollPaneA = new JScrollPane();
        listAlunos = new JList(Aluno.getTodosAlunos());
        scrollPaneA.setViewportView(listAlunos);
        jpAlunos.add(scrollPaneA);
        //jpAlunos.add(listAlunos);

        //listModelCadeiras = new DefaultListModel();
        //         listCadeiras = new JList(listModelCadeiras);
        //         listCadeiras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Compiler warning
        //         listCadeiras.setSelectedIndex(0);
        //         listCadeiras.setVisibleRowCount(5);
        //         listScrollPaneCadeiras = new JScrollPane(listCadeiras);
        //         jpCadeiras.add(listScrollPaneCadeiras);
        // Aluno a = new Aluno();
        //listCadeiras.setListData(Aluno.getTodosAlunos());
        JScrollPane scrollPaneB =  new JScrollPane();
        listCadeiras = new JList(Cadeira.getTodasCadeiras());
        scrollPaneB.setViewportView(listCadeiras);
        jpCadeiras.add(scrollPaneB);

        //         listModelCursos = new DefaultListModel();
        //         //listModel.addElement("Jane Doe"); <- adicionar todos os elementos, cursos
        //         listCursos = new JList(listModelCursos);
        //         listCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Compiler warning
        //         listCursos.setSelectedIndex(0);
        //         listCursos.setVisibleRowCount(5);
        //         listScrollPaneCursos = new JScrollPane(listCursos);
        //         jpCursos.add(listScrollPaneCursos);
        JScrollPane scrollPaneC = new JScrollPane();
        listCursos = new JList(Curso.getTodosCurso());
        scrollPaneC.setViewportView(listCursos);
        jpCursos.add(scrollPaneC);

        B_Adiciona = new JButton();
        B_Adiciona.setSize(100,50);
        B_Adiciona.setText("Adicionar");
        final Object[] parametros=new Object[1];
        parametros[0]=this;
        B_Adiciona.addActionListener(new Eventos(parametros)
        {
            public void actionPerformed(ActionEvent ev){
                Principal este = (Principal)objecto[0];
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Alunos")){
                    AdicionarAluno aa = new AdicionarAluno(este);
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cadeiras")){
                    AdicionarCadeira ac = new AdicionarCadeira(este);
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cursos")){
                    AdicionarCurso ac = new AdicionarCurso(este);
                }
                repaint();
            }
        });

        B_Remove = new JButton();
        B_Remove.setSize(100,50);
        B_Remove.setText("Remover");
        B_Remove.addActionListener(new Eventos(parametros)
        {
            public void actionPerformed(ActionEvent ev){
                Aluno a = new Aluno();
                Cadeira c = new Cadeira();
                Curso cu = new Curso();
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Alunos")){
                    Object[] temp=listAlunos.getSelectedValuesList().toArray();
                    if (temp.length>0){
                        for(int i=0;i<temp.length;i++)
                        {
                            String s = (String)temp[i];
                            String arr[] = s.split(" ", 2);
                            String arr2[] = arr[1].split(" ", 2);
                            String intValue = arr2[0];
                            //listModelAlunos.removeElement(Integer.parseInt(intValue));
                            int index =  Integer.parseInt(intValue)-1;
                            listAlunos.removeSelectionInterval(index,index);
                            a.apagarAluno((String)temp[i]);
                            repaint();
                            Principal temp1 = (Principal)parametros[0];
                            temp1.setupGUI();
                            //repaint nao da, precisa de um refresh mais forte
                            //assim como todos os adicionar, editar e apagar
                            //a ser resolvido com eventos()  parametros[]
                        }
                    }
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cadeiras")){
                    Object[] temp=listCadeiras.getSelectedValuesList().toArray();
                    if(temp.length>0)
                    {
                        for(int i=0;i<temp.length;i++)
                        {
                            String s = (String)temp[i];
                            String arr[] = s.split(" ", 2);
                            String intValue = arr[0];
                            //listModelCadeiras.removeElement(Integer.parseInt(intValue));
                            int index = Integer.parseInt(intValue)-1;
                            listCadeiras.removeSelectionInterval(index,index);
                            c.apagarCadeira((String)temp[i]);
                            repaint();
                            Principal temp1 = (Principal)parametros[0];
                            temp1.setupGUI();
                        }
                    }
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cursos")){
                    Object[] temp=listCursos.getSelectedValuesList().toArray();
                    if(temp.length>0)
                    {
                        for(int i=0;i<temp.length;i++)
                        {
                            String s = (String)temp[i];
                            String arr[] = s.split(" ", 2);
                            String intValue = arr[0];
                            //listModelCursos.removeElement(Integer.parseInt(intValue));
                            int index = Integer.parseInt(intValue)-1;
                            listCursos.removeSelectionInterval(index,index);
                            cu.apagarCurso((String)temp[i]);
                            repaint();
                            Principal temp1 = (Principal)parametros[0];
                            temp1.setupGUI();
                        }
                    }
                }
                repaint();
            }
        });

        B_Edita = new JButton();
        B_Edita.setSize(100,50);
        B_Edita.setText("Edita");
        B_Edita.addActionListener(new Eventos(parametros)
        {
            public void actionPerformed(ActionEvent ev){
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Alunos")){
                    Object[] temp=listAlunos.getSelectedValuesList().toArray();
                    if(temp.length>0)
                    {
                        for(int i=0;i<temp.length;i++)
                        {
                            String s = (String)temp[i];
                            String arr[] = s.split(" ", 2);
                            String intValue = arr[0];
                            Principal este = (Principal)objecto[0];
                            este.setupGUI();
                            EditarAluno ea = new EditarAluno(Integer.parseInt(intValue),este);
                        }
                    }
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cadeiras")){
                    Object[] temp=listCadeiras.getSelectedValuesList().toArray();
                    if(temp.length>0)
                    {
                        for(int i=0;i<temp.length;i++)
                        {
                            String s = (String)temp[i];
                            String arr[] = s.split(" ", 2);
                            String intValue = arr[0];
                            Principal este = (Principal)objecto[0];
                            este.setupGUI();
                            EditarCadeira ec = new EditarCadeira(Integer.parseInt(intValue), este);
                        }
                    }
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cursos")){
                    Object[] temp=listCursos.getSelectedValuesList().toArray();
                    if(temp.length>0)
                    {
                        for(int i=0;i<temp.length;i++)
                        {
                            String s = (String)temp[i];
                            String arr[] = s.split(" ", 2);
                            String intValue = arr[0];
                            Principal este = (Principal)objecto[0];
                            este.setupGUI();
                            EditarCurso ec = new EditarCurso(Integer.parseInt(intValue),este);
                        }
                    }
                }
                repaint();
                setupGUI();
            }
        });

        B_Notas = new JButton();
        B_Notas.setSize(100,50);
        B_Notas.setText("Notas");
        B_Notas.addActionListener(new Eventos(parametros)
        {
            public void actionPerformed(ActionEvent ev){
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Alunos")){
                    B_Notas.setVisible(true);
                    Object[] temp=listAlunos.getSelectedValuesList().toArray();
                    for(int i=0;i<temp.length;i++)
                    {
                        String s = (String)temp[i];
                        String arr[] = s.split(" ", 2);
                        String intValue = arr[0];
//NotasA na = new NotasA((Integer)temp[i]);
                        Principal este = (Principal)objecto[0];
                        este.setupGUI();
                        NotasA na = new NotasA(Integer.parseInt(intValue),este);
                        repaint();
                    }
                    //Correr Query
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cadeiras")){
                    B_Notas.setVisible(true);
                    Object[] temp=listCadeiras.getSelectedValuesList().toArray();
                    for( int i=0;i<temp.length;i++)
                    {
                        String s = (String)temp[i];
                        String arr[] = s.split(" ", 2);
                        String intValue = arr[0];
                        NotasC nc = new NotasC(Integer.parseInt(intValue));
                    }
                    repaint();
//Correr Query
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cursos")){
                    B_Notas.setVisible(false);
                    repaint();
                }
                repaint();
                setupGUI();
            }
        });

        B_Graficos = new JButton();
        B_Graficos.setSize(100,50);
        B_Graficos.setText("Relatório/Gráfico");
        B_Graficos.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev){
//                     Graficos g = new Graficos();
                Object[] temp=listAlunos.getSelectedValuesList().toArray();
                for(int i=0;i<temp.length;i++)
                {
                    String s = (String)temp[i];
                    String arr[] = s.split(" ", 2);
                    String intValue = arr[0];

                    JFrame f = new JFrame();
                    f.add(new Graficos(Integer.parseInt(intValue)));
                    f.pack();
                    f.setSize(500,500);
                    f.setTitle("Relatório/Gráfico");
                    f.setVisible(true);
                    repaint();
                }
                repaint();
                setupGUI();
            }
        });

        jtp.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent ev) {
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Alunos")){
                    B_Notas.setVisible(true);
                    B_Graficos.setVisible(true);
                    repaint();
                    /*Principal temp1 = (Principal)parametros[0];
                    temp1.setupGUI();*/
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cadeiras")){
                    B_Notas.setVisible(true);
                    B_Graficos.setVisible(false);
                    repaint();
                    /*Principal temp1 = (Principal)parametros[0];
                    temp1.setupGUI();*/
                }else
                if (jtp.getTitleAt(jtp.getSelectedIndex()).equals("Cursos")){
                    B_Notas.setVisible(false);
                    B_Graficos.setVisible(false);
                    repaint();
                    /*Principal temp1 = (Principal)parametros[0];
                    temp1.setupGUI();*/
                }
                repaint();
             }
        });

        JPanel principal = new JPanel();
        principal.setLayout(new BorderLayout());
        principal.add(jtp, BorderLayout.CENTER);

        JPanel buts = new JPanel();
        buts.setLayout(new FlowLayout());
        buts.setAlignmentX(FlowLayout.CENTER);
        buts.add(B_Adiciona);
        buts.add(B_Remove);
        buts.add(B_Edita);
        buts.add(B_Graficos);
        buts.add(B_Notas);

        principal.add(buts, BorderLayout.SOUTH);

        add(principal);
        setTitle("Trabalho 1 - Gestão da Escola");
        setSize(481,608);
        setVisible(true);
        setResizable(true);
    }

    public static void main( String args[] )
    {
        new Principal();
    }
}