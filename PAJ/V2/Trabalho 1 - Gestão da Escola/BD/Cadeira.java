package BD;

import java.util.Vector;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Cadeira
{
    private Integer id = null;
    private String nome;
    private Integer codigo;
    private Integer ano;
    private Integer semestre;

    private static Connection derby = Ligacao.getConnection();

    public Cadeira()
    {
        this.id = 0;
        this.codigo=null;
        this.nome=null;
        this.ano=null;
        this.semestre=null;
    }

    public Cadeira(String nome, Integer ano, Integer semestre)
    {
        this.codigo = 0;
        this.nome=nome;
        this.ano=ano;
        this.semestre=semestre;
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
        if (codigo == 0) { // aluno novo, ainda não tem id
            try {
                derby.setAutoCommit(false);
                q = "INSERT INTO Aluno (nome,codigo,ano,semestre) VALUES ('"+nome+"',"+codigo+","+ano+","+semestre+")";
                st.executeUpdate(q);
                q = "SELECT * FROM Cadeira WHERE codigo=" + codigo;
                res = st.executeQuery(q);
                if (res.next()) { // teve resposta ?
                    setId(res.getInt("Cad_id")); // define o id do objecto, definido pela BD
                }
                derby.commit();
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer grava(), codigo == 0 " + st);
                    derby.rollback();
                    return false; // falhou
                }catch (Exception ro) {
                    ro.printStackTrace();
                }
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
        } else { // o objecto já tem id, é uma alteração de dados; a referência é o id; tudo o resto pode mudar
            q = "UPDATE Cadeira SET codigo="+codigo+", nome='"+nome+"', ano='"+ano+"', semestre='"+semestre+"' WHERE Cad_id="+id;
            try {
                derby.setAutoCommit(false);
                st.executeUpdate(q);
                derby.commit();
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer grava() no else " + st);
                    derby.rollback();
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
                try{
                    derby.setAutoCommit(true);
                }catch (Exception co) {
                    co.printStackTrace();
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
        q = "DELETE FROM Cadeira WHERE Cad_id=" + id;
        try {
            derby.setAutoCommit(false);
            st.execute(q);
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer apaga em cadeira " + st);
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

    public String getNome()
    {
        return nome;
    }

    public Integer getCodigo()
    {
        return codigo;
    }

    public Integer getAno()
    {
        return ano;
    }

    public void setNome(String nome)
    {
        this.nome=nome;
    }

    private void setAno(Integer ano)
    {
        this.ano=ano;
    }

    private void setSemestre(Integer semestre)
    {
        this.semestre=semestre;
    }

    private void setId(Integer id)
    {
        this.id=id;
    }

    public static Vector getTodasCadeiras() {
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
        q = "SELECT * FROM Cadeira";
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            while (res.next()) { // teve resposta ?
                valores.add(""+res.getInt("Cad_id")+" "+res.getString("Cad_nome")+" "+res.getInt("Cad_ano")+" "+res.getInt("Cad_semestre"));
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getTodasCadeiras " + st);
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

    public static Vector getNotasDaCadeira(int id) {
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
                    //N_id = double nota
        q = "SELECT N_id FROM AlunoCadeira WHERE Cad_id="+id;
        //q = "SELECT N_id FROM AlunoCadeira WHERE AC_id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            while (res.next()) { // teve resposta ?
                valores.add(""+res.getDouble("N_id"));
                //valores.add(""+res.getInt("Cad_id")+" "+res.getInt("Cad_numero")+" "+res.getString("Cad_nome"));
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getNotasDaCadeira " + st);
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
        q = "SELECT AVG(N_id) AS Media FROM AlunoCadeira WHERE Cad_id=" + id;
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
        String SQL = "SELECT Cad_codigo FROM Cadeira ORDER BY Cad_codigo DESC FETCH FIRST 1 ROWS ONLY";
        try {
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next())
                codigo = rs.getInt("Cad_codigo");
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro popular getRecente @ Cadeira \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return codigo;
    }

    public void criarCadeira(String nome, int ano, int semestre)
    {
        String q;
        Statement st = null;
        codigo = getRecente()+1;
        try {
            st = derby.createStatement(); // cria statement
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println();
            System.out.println(" Erro ao criar o 'statement' da base de dados ");
        }
        q = "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('"+nome+"',"+codigo+","+ano+","+semestre+")";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer criarCadeia " + st);
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

    public void alterCadeira(int id,String nome, int ano, int semestre)
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
        q = "UPDATE Cadeira SET Cad_nome='"+nome+"', Cad_ano="+ano+", Cad_semestre="+semestre+" WHERE Cad_id="+id+"";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
            derby.commit();
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer criarCadeia " + st);
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

    public void apagarCadeira(String temp)
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
            q = "DELETE FROM Cadeira WHERE Cad_id="+intValue;
            try {
                derby.setAutoCommit(false);
                st.executeUpdate(q);
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
    
    public int getCadeiradoSQL(String nome) throws SQLException
    {
        int cadeira = -1;
        String SQL = "SELECT Cad_id FROM Cadeira WHERE Cad_nome="+nome;
        Statement stmt = null;
        try {
            stmt = derby.createStatement();;
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                cadeira = rs.getInt("Cur_id");
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro popular ler ComboBox \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return cadeira;
    }

    public String getCadeiradoPorInt(int id) throws SQLException
    {
        String nome = "";
        String SQL = "SELECT Cad_nome FROM Cadeira WHERE Cad_id="+id;
        Statement stmt = null;
        try {
            stmt = derby.createStatement();;
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                nome = rs.getString("Cad_nome");
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro popular ler ComboBox \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return nome;
    }
    
    public void addCC(int cadeira, int curso)
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
        q = "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES ("+cadeira+","+curso+")";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
            derby.commit();
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer addCC " + st);
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

    public static String getCadeiraId(int id) {
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
        q = "SELECT Cad_nome FROM Cadeira WHERE Cad_id=" + id;
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            if (res.next()) { // teve resposta ?
                //aluno = new Aluno();
                //aluno.setId(res.getInt("Alu_id"));
                //aluno.setNumero(res.getInt("Alu_numero"));
                //aluno.setNome(res.getString("Alu_nome"));
                nome= res.getString("Cad_nome");
                derby.commit();
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getCadeiraId " + st);
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
}