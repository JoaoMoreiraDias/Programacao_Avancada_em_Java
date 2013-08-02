/**
 * Write a description of class Curso here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;
import java.util.Iterator; 

public class Curso
{
    private String nome;
    private Integer codigo;
    private ArrayList<Cadeira> cadeiras = new ArrayList<Cadeira>();

    /**
     * Constructor for objects of class Curso
     */
    public Curso(String nome)
    {
        this.nome=nome;
    }
    
    public String getNome()
    {
        return nome;
    }
    
    public void setNome(String nome)
    {
        this.nome=nome;
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
