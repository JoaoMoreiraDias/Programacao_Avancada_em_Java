package BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import sun.util.resources.CalendarData_in_ID;

public class Aluno
{
    private Integer id = null;
    private String nome;
    private Integer numero;
    private Curso curso;
    private Integer ano;

    private ArrayList<Cadeira> cadeiras = new ArrayList<Cadeira>();
    private ArrayList<Nota> notas = new ArrayList<Nota>();

    private static Connection derby = Ligacao.getConnection();

    public Aluno()
    {
        id = 0;
        this.numero=null;
        this.nome="";
        this.curso=null;
        ano = null;
    }

    public Aluno(int numero, String nome, Curso curso, Integer anoInscrição)
    {
        id = 0;
        this.numero=numero;
        this.nome=nome;
        this.curso=curso;
        ano = Calendar.getInstance().get(Calendar.YEAR)-anoInscrição;
    }

    //public static Aluno getAlunoId(int id) {
        public static String getAlunoId(int id) {
        //Aluno aluno = null;
        String nome = "";
        String q;
        Statement st = null;
        ResultSet res = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return null;
        }
        q = "SELECT A_nome FROM Aluno WHERE Alu_id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            if (res.next()) { // teve resposta ?
                //aluno = new Aluno();
                //aluno.setId(res.getInt("Alu_id"));
                //aluno.setNumero(res.getInt("Alu_numero"));
                //aluno.setNome(res.getString("Alu_nome"));
                nome= res.getString("A_nome");
                derby.commit();
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getAlunoId " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return null;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return nome;
    }

    // retorna aluno com numero=numero
    public static Aluno getAlunoNum(int numero) {
        Aluno aluno = null;
        String q;
        Statement st = null;
        ResultSet res = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return null;
        }
        q = "SELECT * FROM Aluno WHERE id=" + numero;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            if (res.next()) { // teve resposta ?
                aluno = new Aluno();
                aluno.setId(res.getInt("Alu_id"));
                aluno.setNumero(res.getInt("Alu_numero"));
                aluno.setNome(res.getString("Alu_nome"));
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getAlunoNum " + st);
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return null;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return aluno;
    }

    public boolean grava() {
        String q;
        Statement st = null;
        ResultSet res = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out
            .println(" Erro ao criar o 'statement' da base de dados ");
            return false; // falhou
        }
        if (id == 0) { // aluno novo, ainda não tem id
            try {
                derby.setAutoCommit(false);
                q = "INSERT INTO Aluno (numero,nome) VALUES ("+numero+",'"+nome+"')";
                st.executeUpdate(q);
                q = "SELECT * FROM Aluno WHERE numero=" + numero;
                res = st.executeQuery(q);
                if (res.next()) { // teve resposta ?
                    setId(res.getInt("Alu_id")); // define o id do objecto, definido pela BD
                }
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer grava " + st);
                }catch (Exception ro) {
                    ro.printStackTrace();
                }
                return false; // falhou
            } finally {
                if (res!=null)
                    try {
                        res.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                }
                if (st!=null)
                    try {
                        st.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                }
            }
        } else { // o objecto já tem id, é uma alteração de dados; a referência é o id; tudo o resto pode mudar
            q = "UPDATE Aluno SET " + "numero=" + numero + ", "
            + "nome='" + nome + "' "
            + "WHERE Alu_id="+id;
            try {
                st.executeUpdate(q);
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer grava depois do else " + st);
                }catch (Exception ro) {
                    ro.printStackTrace();
                }
                return false;
            } finally {
                if (st!=null)
                    try {
                        st.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                }
            }
        }
        return true; // alteração feita
    }

    public void apaga() {
        String q;
        Statement st = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return;
        }
        q = "DELETE FROM Aluno WHERE Alu_id=" + id;
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer apaga " + st);
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return;
        } finally {
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
    }

    public String toString() {
        String st = "Aluno id="+id+"  num="+numero+"  nome="+nome+"\n";
        return st;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public Integer getNumero()
    {
        return numero;
    }

    public Curso getCurso()
    {
        return curso;
    }

    public Integer getAno()
    {
        return ano;
    }

    public ArrayList<Cadeira> getCadeiras()
    {
        return cadeiras;
    }

    public ArrayList<Nota> getNotas()
    {
        return notas;
    }

    public void setNome(String nome)
    {
        this.nome=nome;
    }

    public void setNumero(Integer numero)
    {
        this.numero=numero;
    }

    public void setCurso(Curso curso)
    {
        this.curso=curso;
    }

    public void setAno(Integer anoInscrição)
    {
        ano = Calendar.getInstance().get(Calendar.YEAR)-anoInscrição;
    }

    public void addCadeira(Cadeira cadeira)
    {
        cadeiras.add(cadeira);
    }

    public void removeCadeira(Integer codigoCadeira)
    {
        for(Cadeira i : cadeiras) {
            if (i.getCodigo()==codigoCadeira)
            {
                cadeiras.remove(i);
                break;
            }
        }
    }

    public void addNota(Nota nota)
    {
        notas.add(nota);
    }

    public void removeNota(Integer codigoCadeira)
    {
        for(Nota i : notas) {
            if (i.getCadeira().getCodigo()==codigoCadeira)
            {
                cadeiras.remove(i);
                break;
            }
        }
    }

    //     public double getMedia(String nomeCadeira)
    //     {
    //         double n=0;
    //         double total=0;
    //         double media=0;
    //         for(Nota i : notas) {
    //             if(i.getCadeira().getNome()==nomeCadeira)
    //             {
    //                 total=total+i.getNota();
    //                 n=n+1;
    //             }
    //         }
    //         media=(total/n);
    //         return media;
    //     }

    public String toStringNotas(String nomeCadeira)
    {
        String str="";
        int pos=0;
        for(Cadeira i : cadeiras) {
            if(i.getNome()==nomeCadeira)
                for(Nota a : notas) {
                    if(pos==notas.size()){
                        str+=a.getNota()+", ";
                    }
                    else{
                        str+=a.getNota();
                    }
            }
            pos=pos+1;
        }
        return str;
    }

    public ArrayList<Double> obterListas(String nomeCadeira)
    {
        ArrayList<Double> lista = new  ArrayList<Double>();
        for(Cadeira i : cadeiras) {
            if(i.getNome()==nomeCadeira)
                for(Nota a : notas) {
                    lista.add(a.getNota());
            }
        }
        return lista;
    }

    public void saberCadeiras()
    {
        //         ArrayList<String> lista = new ArrayList<String>();
        //         String str="";
        //         for(Cadeira i : cadeiras) {
        //             if (getMedia(i.getNome())>10)
        //             {
        //                 str="Aprovado a "+i.getNome()+" com "+getMedia(i.getNome())+"("+toStringNotas(i.getNome())+")";
        //                 lista.add(str);
        //             }
        //             else{
        //                 str="reprovado a "+i.getNome()+" com "+getMedia(i.getNome())+"("+toStringNotas(i.getNome())+")";
        //                 lista.add(str);
        //             }
        //         }
        //         return lista;

        //         ResultSet res;
        //         Statement st = derby.createStatement(); // cria statement
        //         res = st.executeQuery("--Saber as cadeiras a que um aluno já foi aprovado, com notas e média, e quais falta fazer SELECT  C_id AS 'Cadeira', N_id AS 'Nota' FROM AlunoCadeira , WHERE N_id > 10");
    }

    public static Vector getTodosAlunos() {
        Vector<String> valores = new Vector<String>();
        String q;
        Statement st = null;
        ResultSet res = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return null;
        }
        q = "SELECT * FROM Aluno";
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            while (res.next()) { // teve resposta ?
                valores.add(""+res.getInt("Alu_id")+" "+res.getInt("A_numero")+" "+res.getString("A_nome"));
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getTodosAlunos " + st);
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return null;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return valores;
    }

    public static Vector getNotasDoAluno(int id) {
        Vector<String> valores = new Vector<String>();
        String q;
        Statement st = null;
        ResultSet res = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return null;
        }
        q = "SELECT Cad_id, N_id FROM AlunoCadeira WHERE Alu_id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            while (res.next()) { // teve resposta ?
                //valores.add(""+res.getInt("Alu_id")+" "+res.getInt("Alu_numero")+" "+res.getString("Alu_nome")));
                valores.add(""+res.getInt("Cad_id")+" "+res.getDouble("N_id"));
                derby.commit();
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getNotasDoAluno " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return null;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return valores;
    }

    public static double getMedia(int id) {
        double media = 0.0;
        String q;
        Statement st = null;
        ResultSet res = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return -1.0;
        }
        //Alu_id ou id
        q = "SELECT AVG(N_id) AS Media FROM AlunoCadeira WHERE Alu_id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            if (res.next()) { // teve resposta ?
                media=(res.getDouble("Media"));
                derby.commit();
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getMedia " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return -1.0;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return media;
    }

    public static String aprovadasToString(int id) {
        String aprovadas = "";
        String q = "";
        String q2 = "";
        String nome = "";
        Statement st = null;
        Statement st2 = null;
        ResultSet res = null;
        ResultSet res2 = null;
        try {
            st = derby.createStatement(); // cria statement
            st2 = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return null;
        }
        //q = "SELECT Cad_id FROM AlunoCadeira WHERE AC_id=" + id + " AND " + "N_id>=9.5";
         q = "SELECT Cadeira.Cad_Nome, AlunoCadeira.AC_Id FROM Cadeira INNER JOIN AlunoCadeira ON AlunoCadeira.Cad_Id = Cadeira.Cad_Id WHERE AlunoCadeira.N_id>=9.5 AND AlunoCadeira.Alu_Id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            //NOTA: OU É WHILE OU É IF, SECALHAR É ORECISO REVER TODOS OS OUTORS (ESPECIALMENTE QUERYS PARA PRENCHER LISTAS)
            while (res.next()) { // teve resposta ?
                nome = res.getString("Cad_Nome");
                aprovadas = aprovadas+" "+nome;
               /* q2 = "SELECT Cad_nome FROM Cadeira WHERE Cad_id=" + res.getInt("Cad_id");
                res2 = st2.executeQuery(q2);
                while (res2.next()) { // teve resposta ?
                    nome = res2.getString("Cad_nome");
                    aprovadas=aprovadas+" "+nome+"\n";
                }*/
                //Jlist Jvalores = new Jlist(valores);
                //derby.commit();
            }
            derby.commit();
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer aprovadasToString " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return null;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (res2!=null)
                try {
                    res2.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            if (st2!=null)
                try {
                    st2.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return aprovadas;
    }

    public static String reprovadasToString(int id) {
        String reprovadas = "";
        String q;
        String nome = "";
        Statement st = null;
        ResultSet res = null;
        ResultSet res2 = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
            return null;
        }
        //q = "SELECT Cad_id FROM AlunoCadeira WHERE AC_id=" + id + " AND " + "N_id<9.5";
        q = "SELECT Cadeira.Cad_Nome, AlunoCadeira.AC_Id FROM Cadeira INNER JOIN AlunoCadeira ON AlunoCadeira.Cad_Id = Cadeira.Cad_Id WHERE AlunoCadeira.N_id<9.5 AND AlunoCadeira.Alu_Id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            //NOTA: OU É WHILE OU É IF, SECALHAR É ORECISO REVER TODOS OS OUTORS (ESPECIALMENTE QUERYS PARA PRENCHER LISTAS)
            while (res.next()) { // teve resposta ?
                nome = res.getString("Cad_Nome");
                reprovadas = reprovadas+" "+nome;
              //  res2 = st.executeQuery("SELECT Cad_nome FROM Cadeira WHERE Alu_id=" + res.getInt("Alu_id"));
              //  if (res2.next()) { // teve resposta ?
              //      nome = res2.getString("Cad_nome");
              //      reprovadas=reprovadas+" "+nome+"\n";
              //  }
              //  derby.commit();
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer reprovadasToString " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
            return null;
        } finally {
            if (res!=null)
                try {
                    res.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            if (st!=null)
                try {
                    st.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
            }
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
        return reprovadas;
    }

    public String[] getCursos()
    {
        ArrayList<String> cursos = new ArrayList<String>();
        int index = 0;
        String SQL = "SELECT * FROM Curso";
        Statement stmt = null;
        //Statement st = null;
        Ligacao lig=new Ligacao();
        try {
            stmt = lig.createStatement();
            //st = derby.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            //ResultSet rs = st.executeQuery(SQL);
            while(rs.next()) {
                cursos.add(rs.getString("Cur_nome"));
                index++;
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro popular ComboBox \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String[] temp = new String[cursos.size()];
        temp = cursos.toArray(temp);
        return temp;
    }

    //     public String[] getCad(int id)
    //     {
    //         String cursos[] = {};
    //         int index = 0;
    //         String SQL = "SELCET * FROM Curso"
    //         Statement stmt = null;
    //         try {
    //             // stmt = lig.createStatement();
    //             ResultSet rs = stmt.executeQuery(SQL);
    //             while(rs.next())
    //                 cursos[index] = rs.getString("Cur_nome");
    //         } catch (SQLException e) {
    //             System.out.println();
    //             System.out.println("Erro popular ComboBox \n" + SQL);
    //             System.out.println(e.getMessage());
    //             e.printStackTrace();
    //         }
    //         return cursos;
    //     }

    public void apagarAluno(String temp)
    {
        if(temp.length()>0)
        {
            String q;
            Statement st = null;
            try {
                st = derby.createStatement(); // cria statement
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao criar o 'statement' da base de dados ");
            }
            //String intValue = temp.replaceAll("[a-zA-Z]", "");
            //intValue = temp.replaceAll(" ", "");
            String arr[] = temp.split(" ", 2);
            String intValue = arr[0];
            q = "DELETE FROM Aluno WHERE Alu_id="+intValue;
            try {
                derby.setAutoCommit(false);
                st.executeUpdate(q);
                derby.commit();
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer apagarAluno " + st);
                    derby.rollback();
                }catch (Exception ro) {
                    ro.printStackTrace();
                }
            } finally {
                try{
                    derby.setAutoCommit(true);
                }catch (Exception co) {
                    co.printStackTrace();
                }
            }
        }
    }

    public int getRecente()
    {
        Statement st = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
        }
        String SQL = "SELECT A_numero FROM Aluno ORDER BY A_numero DESC FETCH FIRST 1 ROWS ONLY";
        try {
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next())
                numero = rs.getInt("A_numero");
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro popular getRecente @ Aluno \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return numero;
    }

    public void criarAluno(String nome, int curso, int ano)
    {
        String q;
        Statement st = null;
        numero = getRecente()+1;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
        }
        q ="INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) " + "VALUES ('"+nome+"',"+numero+","+curso+","+ano+")";
        //q = "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('"+nome+"',"+numero+","+curso+","+ano+")";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
            derby.commit();
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer criarAluno " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
        } finally {
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
    }

    public void alteraAluno(int id, String nome, int curso, int ano)
    {
        String q;
        Statement st = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
        }
        q = "UPDATE Aluno SET A_nome='" + nome + "', A_ano=" + ano + ", Cur_id=" + curso + " WHERE Alu_id=" + id + "";
        //q = "UPDATE Aluno SET A_nome='"+nome+"', A_numero="+numero+", A_ano="+ano+", Cur_id="+curso+" WHERE A_id='"+id+"'";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
            derby.commit();
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer alteraAluno " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
        } finally {
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
    }

    public void addAC(int id, int cadeira, int nota)
    {
        String q;
        Statement st = null;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
        }
        q = "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES ('"+id+"',"+cadeira+","+nota+")";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
            derby.commit();
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer criarAluno " + st);
                derby.rollback();
            }catch (Exception ro) {
                ro.printStackTrace();
            }
        } finally {
            try{
                derby.setAutoCommit(true);
            }catch (Exception co) {
                co.printStackTrace();
            }
        }
    }

    public String[] getCadEspecial(int id)
    {
        ArrayList<String> cursos = new ArrayList<String>();
        int index = -1;
        int curso = -1;
        String SQL1 = "SELECT Cur_id FROM Aluno WHERE Alu_id = "+id;
        try {
            Statement stmt = null;
            stmt = derby.createStatement();
            ResultSet rs = stmt.executeQuery(SQL1);
            if (rs.next()) { // teve resposta ?
                curso = rs.getInt("Cur_id");
            }
        }
        catch (SQLException e1) {
            System.out.println();
            System.out.println("Erro na SQL1 \n" + SQL1);
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        }
        //SELECT Cadeira.Cad_nome FROM Cadeira INNER JOIN AlunoCadeira ON Cadeira.Cad_id=AlunoCadeira.Cad_id INNER JOIN Aluno ON AlunoCadeira.Alu_id=Aluno.Alu_id WHERE Aluno.ALU_ID=1
        //String SQL2 = "SELECT * FROM ALUNO INNER JOIN ALUNOCADEIRA ON A_ID=AC_ID INNER JOIN CADEIRA ON ID_CADEIRA=ID_CADEIRA INNER JOIN CADEIRACURSO ON ID_CADEIRA=CACEIRA_ID INNER JOIN CURSO ON CURSO_ID=ID_CURSO WHERE ALUNO_ID="+id+" AND CURSO_ID="+curso+";";
        String SQL2 = "SELECT Cadeira.Cad_nome FROM Cadeira INNER JOIN AlunoCadeira ON Cadeira.Cad_id=AlunoCadeira.Cad_id INNER JOIN Aluno ON AlunoCadeira.Alu_id=Aluno.Alu_id WHERE Aluno.ALU_ID="+curso;
        Statement stmt = null;
        try {
            stmt = derby.createStatement();
            ResultSet rs = stmt.executeQuery(SQL2);
            while (rs.next()) { // teve resposta ?
                cursos.add(rs.getString("Cad_nome"));
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (SQLException e2) {
            System.out.println();
            System.out.println("Erro na getCadEspecial @ Aluno: \n" + SQL2);
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        }
        String[] temp = new String[cursos.size()];
        temp = cursos.toArray(temp);
        return temp;
    }
}