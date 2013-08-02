package BD;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Cria
{
    private Ligacao lig;
    public Cria()
    {
        lig = new Ligacao();
        lig.getConnection();
        try{
            dropTabelas();
            createTableCurso();
            createTableCadeira();
            createTableCadeiraCurso();
            createTableAluno();
            createTableAlunoCadeira();
            createConstraints();
            exemplos();
            System.out.println("\nCriação Completa!");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void createTableAluno() throws SQLException {
        String createString =
            "CREATE TABLE " + "Aluno" +
            "(Alu_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "A_nome VARCHAR(40) NOT NULL, " +
            "A_numero INT NOT NULL, " +
            "Cur_id INT NOT NULL," +
            "A_ano INT NOT NULL" +
                //   "AC_id INT NOT NULL" +
                //   "UNIQUE(A_numero')" +
            ")";
        //   "CREATE UNIQUE INDEX A_id ON Aluno(id)')";
        Statement stmt = null;
        try {
            stmt = lig.createStatement();
            stmt.execute(createString);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro ao criar tabela Aluno \n" + createString);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void createTableCadeira() throws SQLException {
        String createString =
            "CREATE TABLE " + "Cadeira" +
            "(Cad_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "Cad_nome VARCHAR(40) NOT NULL, " +
            "Cad_codigo INT NOT NULL, " +
            "Cad_ano INT NOT NULL, " +
            "Cad_semestre INT NOT NULL)";
        //   "CC_id INT NOT NULL," +
        //   "AC_id INT NOT NULL)";
        //   "UNIQUE(Cad_codigo)," +
        //     "CREATE UNIQUE INDEX Cad_id ON Cadeira(id)')";
        Statement stmt = null;
        try {
            stmt = lig.createStatement();
            stmt.execute(createString);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro ao criar tabela Cadeiras \n" + createString);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void createTableCurso() throws SQLException {
        String createString =
            "CREATE TABLE " + "Curso" +
            "(Cur_id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "Cur_nome VARCHAR(40) NOT NULL, " +
            "Cur_codigo INT NOT NULL)";
        // "CC_id INT NOT NULL)";
        //    "UNIQUE(Cur_codigo)," +
        //   "CREATE UNIQUE INDEX Cur_id ON Curso(id)')";
        Statement stmt = null;
        try {   
            stmt = lig.createStatement();
            stmt.execute(createString);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro ao criar tabela Curso \n" + createString);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void createTableCadeiraCurso() throws SQLException {
        String createString =
            "CREATE TABLE " + "CadeiraCurso" +
            "(CC_id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "Cad_id INT NOT NULL  REFERENCES Cadeira(Cad_id) ON DELETE CASCADE," +
            "Cur_id INT NOT NULL  REFERENCES Curso(Cur_id) ON DELETE CASCADE)";
        //  "PRIMARY KEY (id)')" +
        //  "CREATE UNIQUE INDEX CC_id ON CadeiraCurso(id)')";
        Statement stmt = null;
        try {
            stmt = lig.createStatement();
            stmt.execute(createString);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro ao criar tabela CadeiraCurso \n" + createString);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void createTableAlunoCadeira() throws SQLException {
        String createString =
            "CREATE TABLE " + "AlunoCadeira" +
            "(AC_id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "Alu_id INT NOT NULL REFERENCES Aluno(Alu_id) ON DELETE CASCADE," +
            "Cad_id INT NOT NULL REFERENCES Cadeira(Cad_id) ON DELETE CASCADE," +
            "N_id DOUBLE NOT NULL)";
        //  "PRIMARY KEY (id)')" +
        //  "CREATE UNIQUE INDEX AC_id ON AlunoCadeira(id)')";
        Statement stmt = null;
        try {
            stmt = lig.createStatement();
            stmt.execute(createString);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro ao criar tabela AlunoCadeira \n" + createString);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void createConstraints() throws SQLException {
        // String String1 = "ALTER TABLE Aluno ADD FOREIGN KEY (AC_id) REFERENCES AlunoCadeira(AC_id')";
        String String2 = "ALTER TABLE Aluno ADD FOREIGN KEY (Cur_id) REFERENCES Curso(Cur_id) ON DELETE CASCADE";
        // String String3 = "ALTER TABLE Cadeira ADD FOREIGN KEY (CC_id) REFERENCES CadeiraCurso(CC_id')";
        // String String4 = "ALTER TABLE Cadeira ADD FOREIGN KEY (AC_id) REFERENCES AlunoCadeira(AC_id')";
        // String String5 = "ALTER TABLE Curso ADD FOREIGN KEY (CC_id) REFERENCES CadeiraCurso(CC_id')";
        String String6 = "ALTER TABLE CadeiraCurso ADD FOREIGN KEY (Cad_id) REFERENCES Cadeira(Cad_id) ON DELETE CASCADE";
        String String7 = "ALTER TABLE CadeiraCurso ADD FOREIGN KEY (Cur_id) REFERENCES Curso(Cur_id) ON DELETE CASCADE";
        String String8 = "ALTER TABLE AlunoCadeira ADD FOREIGN KEY (Alu_id) REFERENCES Aluno(Alu_id) ON DELETE CASCADE";
        String String9 = "ALTER TABLE AlunoCadeira ADD FOREIGN KEY (Cad_id) REFERENCES Cadeira(Cad_id) ON DELETE CASCADE";
        Statement stmt = null;
        try {
            stmt = lig.createStatement();
            //stmt.execute(String1);
            stmt.execute(String2);
            //stmt.execute(String3);
            //stmt.execute(String4);
            //stmt.execute(String5);
            //stmt.execute(String6);
            stmt.execute(String7);
            stmt.execute(String8);
            stmt.execute(String9);
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Erro no createConstraints()!\n" + stmt);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    public void exemplos()  throws SQLException {
        String[] exemplo={
                "INSERT INTO Curso (Cur_nome,Cur_codigo) VALUES ('Informatica',1)",
                "INSERT INTO Curso (Cur_nome,Cur_codigo) VALUES ('Economia',2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I1',1,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I2',2,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I3',3,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I4',4,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I5',5,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I6',6,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I7',7,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I8',8,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I9',9,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I10',10,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I11',11,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I12',12,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I13',13,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I14',14,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I15',15,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I16',16,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I17',17,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I18',18,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I19',19,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I20',20,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I21',21,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I22',22,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I23',23,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I24',24,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I25',25,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I26',26,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I27',27,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I28',28,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('I29',29,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E1',30,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E2',31,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E3',32,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E4',33,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E5',34,1,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E6',34,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E7',35,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E8',36,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E9',37,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E10',38,1,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E11',39,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E12',40,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E13',41,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E14',42,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E15',43,2,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E16',44,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E17',45,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E18',46,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E19',47,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E20',48,2,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E21',49,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E22',50,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E23',51,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E24',52,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E25',53,3,1)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E26',54,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E27',55,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E28',56,3,2)",
                "INSERT INTO Cadeira (Cad_nome,Cad_codigo,Cad_ano,Cad_semestre) VALUES ('E29',57,3,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (1,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (2,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (3,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (4,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (5,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (6,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (7,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (8,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (9,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (10,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (11,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (12,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (13,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (14,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (15,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (16,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (17,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (18,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (19,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (20,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (21,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (22,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (23,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (24,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (25,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (26,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (27,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (28,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (29,1)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (1,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (2,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (3,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (4,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (5,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (6,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (7,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (8,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (9,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (10,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (11,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (12,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (13,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (14,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (15,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (16,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (17,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (18,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (19,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (20,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (21,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (22,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (23,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (24,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (25,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (26,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (27,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (28,2)",
                "INSERT INTO CadeiraCurso (Cad_id,Cur_id) VALUES (29,2)",
                "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('João',1,1,3)",
                "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('José',2,1,3)",
                "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('Henrique',3,1,5)",
                "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('Manuel',4,2,3)",
                "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('Sebastião',5,2,1)",
                "INSERT INTO Aluno (A_nome,A_numero,Cur_id,A_ano) VALUES ('Bernardino',6,2,1)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (1,1,20.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (1,2,20.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (2,1,20.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (2,2,20.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (3,1,5.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (3,2,8.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (4,30,1.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (4,31,1.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (6,30,17.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (6,31,18.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (5,30,12.0)",
                "INSERT INTO AlunoCadeira (Alu_id,Cad_id,N_id) VALUES (5,31,7.0)"};

        Statement stmt = null;
        stmt = lig.createStatement();
        for(int i=0; i<exemplo.length; i++)
        {
            try
            {
                stmt.execute(exemplo[i]);
            }
            catch (SQLException e) {
                System.out.println();
                System.out.println("Erro no exemplos()!\n" + exemplo[i]);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        if (stmt != null) { stmt.close();
        }
    }

    public void dropTabelas() throws SQLException 
    {
        Statement stmt = null;
        stmt = lig.createStatement();
        String[] tabelas={
                "DROP TABLE AlunoCadeira",
                "DROP TABLE CadeiraCurso",
                "DROP TABLE Aluno",
                "DROP TABLE Cadeira",
                "DROP TABLE Curso"};

        for(int i=0; i<tabelas.length; i++)
        {
            try
            {
                stmt.execute(tabelas[i]);
            }
            catch (SQLException e) {
                if( !e.getSQLState().equals("42Y55") ) {
                    //                     System.out.println();
                    //                     System.out.println("Erro a dropTabelas()!\n" + tabelas[i]);
                    //                     System.out.println(e.getMessage());
                    //                     e.printStackTrace();
                    throw e;
                }
            }
        }
        if (stmt != null) { stmt.close();
        }
    }

    //     public void createTableNotasAluno() throws SQLException {
    //         String createString =
    //             "CREATE TABLE " + "NotasAluno" +
    //             ".SUPPLIERS " +
    //             "(id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
    //             "nome VARCHAR(40) NOT NULL, " +
    //             "numero VARCHAR(40) NOT NULL, " +
    //             "curso VARCHAR(40) NOT NULL, " +
    //             "ano VARCHAR(20) NOT NULL, " +
    //             "PRIMARY KEY (id))";
    //         Statement stmt = null;
    //         try {
    //             stmt = lig.createStatement();
    //             stmt.executeUpdate(createString);
    //         } catch (SQLException e) {
    //             System.out.println();
    //             System.out.println("Erro ao criar tabela NotasAluno \n" + stmt);
    //             System.out.println(e.getMessage());
    //             e.printStackTrace();
    //         } finally {
    //             if (stmt != null) { stmt.close(); }
    //         }
    //     }

    //     public void createTableNota() throws SQLException {
    //         String createString =
    //             "CREATE TABLE " + "Nota" +
    //             ".SUPPLIERS " +
    //             "(id INTEGER  PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
    //             "nome VARCHAR(40) NOT NULL, " +
    //             "numero VARCHAR(40) NOT NULL, " +
    //             "curso VARCHAR(40) NOT NULL, " +
    //             "ano VARCHAR(20) NOT NULL, " +
    //             "PRIMARY KEY (id))";
    //         Statement stmt = null;
    //         try {
    //             stmt = lig.createStatement();
    //             stmt.executeUpdate(createString);
    //         } catch (SQLException e) {
    //             System.out.println();
    //             System.out.println("Erro ao criar tabela Notas \n" + stmt);
    //             System.out.println(e.getMessage());
    //             e.printStackTrace();
    //         } finally {
    //             if (stmt != null) { stmt.close(); }
    //         }
    //     }
}