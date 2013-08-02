/**
 * Write a description of class Aluno here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList; 
import java.util.Calendar;
import java.util.Iterator;

public class Aluno
{
    private String nome;
    private Integer id;
    private Curso curso;
    private Integer ano;
    private ArrayList<Cadeira> cadeiras = new ArrayList<Cadeira>();
    private ArrayList<Nota> notas = new ArrayList<Nota>();

    /**
     * Constructor for objects of class Aluno
     */
    public Aluno(String nome, Curso curso, Integer anoInscrição)
    {
        this.nome=nome;
        this.curso=curso;
        ano = Calendar.getInstance().get(Calendar.YEAR)-anoInscrição;
    }

    public String getNome()
    {
        return nome;
    }

    public Integer getID()
    {
        return id;
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

    public void setID(Integer id)
    {
        this.id=id;
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
        for(Iterator<Cadeira> i = cadeiras.iterator(); i.hasNext(); ) {
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
        for(Iterator<Nota> i = notas.iterator(); i.hasNext(); ) {
            if (i.getCaeira().getCodigo()==codigoCadeira)
            {
                cadeiras.remove(i);
                break;
            }
        }
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void sampleMethod()
    {

    }
}
