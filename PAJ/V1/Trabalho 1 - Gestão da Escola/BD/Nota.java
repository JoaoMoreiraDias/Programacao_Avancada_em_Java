package BD;

public class Nota
{
    private Cadeira cadeira;
    private double nota;
    
    public Nota(Cadeira cadeira, double nota)
    {
        this.cadeira=cadeira;
        this.nota=nota;
    }

    public Cadeira getCadeira()
    {
        return cadeira;
    }
    
    public double getNota()
    {
        return nota;
    }
    
    public void setCadeira(Cadeira cadeira)
    {
        this.cadeira=cadeira;
    }
   
    public void setNota(double nota)
    {
        this.nota=nota;
    }
}