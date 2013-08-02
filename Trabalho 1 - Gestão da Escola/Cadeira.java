/**
 * Write a description of class Cadeira here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Cadeira
{
    private String nome;
    private Integer codigo;
    private Integer ano;
    private Integer semestre;

    /**
     * Constructor for objects of class Cadeira
     */
    public Cadeira(String nome, Integer ano, Integer semestre)
    {
        this.nome=nome;
        this.ano=ano;
        this.semestre=semestre;
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
