package CustomExceptions;

public class InvalidTypesException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2562249288002400442L;
	private int tip;
    private int valoare;
        
	public InvalidTypesException(int tip, int valoare){
		this.tip = tip;
        this.valoare = valoare;
	}
	
        @Override
	public String getMessage(){
		if(tip == 0){
                    return "Functie cu tipul input-ului " + this.valoare + " nu exista";
                }else
                if(tip == 1){
                    return "Nu exista mod de a primi output-ul " + this.valoare;
                }
                return "Eroare";
	}
}
