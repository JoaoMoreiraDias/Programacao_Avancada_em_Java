package BD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Curso
{
    private Integer id = null;
    private String nome;
    private Integer codigo;
    private ArrayList<Cadeira> cadeiras = new ArrayList<Cadeira>();

    private static Connection derby = Ligacao.getConnection();

    public Curso()
    {
        id = 0;
        this.nome="";
        this.codigo=null;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome=nome;
    }

    public void setId(int id)
    {
        this.id=id;
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

    public boolean criarCurso(String nome, int codigo)
    {
        String q;
        Statement st = null;
        ResultSet res = null;
        codigo = getRecente()+1;
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
                q = "INSERT INTO Curso (nome,codigo) VALUES ("+nome+",'"+codigo+"')";
                st.execute(q);
                q = "SELECT * FROM Curso WHERE Cur_id=" + id;
                res = st.executeQuery(q);
                if (res.next()) { // teve resposta ?
                    setId(res.getInt("Cur_id")); // define o id do objecto, definido pela BD
                }
                //Nota: estes Cur_id eram Alu_id, acho que foi um bug a copypaste por isso mudei
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer criarCurso " + st);
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
            q = "UPDATE Curso SET " + "Cur_id=" + id + ", "
            + "nome='" + nome + "' "
            + "WHERE Cur_id="+id;
            try {
                st.execute(q);
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer criarCurso depois do else " + st);
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

    public static Vector getTodosCurso() {
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
        q = "SELECT * FROM Curso";
        try {
            derby.setAutoCommit(false);
            res = st.executeQuery(q);
            while (res.next()) { // teve resposta ?
                int iID = res.getInt("Cur_id");
                int iCOD = res.getInt("Cur_codigo");
                String sNOME =res.getString("Cur_nome");
                valores.add(""+iID+" "+iCOD+" "+sNOME);
                //valores.add(""+res.getInt("Cur_id")+" "+res.getInt("Cur_codigo")+" "+res.getString("Cur_nome"));
                //Jlist Jvalores = new Jlist(valores);
            }
        } catch (Exception ex) {
            try{
                ex.printStackTrace();
                System.out.println();
                System.out.println(" Erro ao fazer getTodosCurso " + st);
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

    public int getCursodoSQL(String nome) throws SQLException
    {
        int curso = -1;
        String SQL = "SELECT Cur_id FROM Curso WHERE Cur_nome="+"'"+nome+"'";
        Statement stmt = null;
        try {
            stmt = derby.createStatement();;
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                curso = rs.getInt("Cur_id");
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro getCursodoSQL @ Curso \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return curso;
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
        }        String SQL = "SELECT Cur_codigo FROM Curso ORDER BY Cur_codigo DESC FETCH FIRST 1 ROWS ONLY";
        try {
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next())
                codigo = rs.getInt("Cur_codigo");
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro popular getRecente @ Curso \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return codigo;
    }

    public void criarCurso(String nome)
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
        q = "INSERT INTO Curso (Cur_nome,Cur_codigo) VALUES ('"+nome+"',"+codigo+")";
        try {
            derby.setAutoCommit(false);
            st.executeUpdate(q);
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

    public void alterCurso(int id,String nome)
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
        q = "UPDATE Curso SET Cur_nome='"+nome+"', Cur_codigo="+codigo+" WHERE Cur_id="+id+"";
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

    public void apagarCurso(String temp)
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
            q = "DELETE FROM Curso WHERE Cur_id="+intValue;
            try {
                derby.setAutoCommit(false);
                st.executeUpdate(q);
            } catch (Exception ex) {
                try{
                    ex.printStackTrace();
                    System.out.println();
                    System.out.println(" Erro ao fazer apagarCurso " + st);
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

    public String getCursoPorInt(int id) throws SQLException
    {
        String nome = "";
        String SQL = "SELECT Cur_nome FROM Curso WHERE Cur_id="+id;
        Statement stmt = null;
        try {
            stmt = derby.createStatement();;
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                nome = rs.getString("Cur_nome");
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro @ getCursoPorInt \n" + SQL);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return nome;
    }
}