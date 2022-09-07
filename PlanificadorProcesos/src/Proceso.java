
public class Proceso {

	private char PID;
	private int TS; // tiempo de servicio
	private int TF; // tiempo de salida
	private float TR; // tiempo de retorno
	private int TLL;
	
	//se  crea un proceso pon el PID y el tiempo de servicio que necesitar
	public Proceso crear(char PID, int TL, int TS) {
		this.PID=PID;
		this.TLL=TL;
		this.TS=TS;
		this.TF=TS;
		this.TR=0;
		return this;
	}
	
	public int getTLL() {
		return this.TLL;
	}
	
	
	//se retorna el tiempo de servicio que necesita el proceso
	public int getTS() {
		return this.TS;
	}
	
	//se retorna el PID
	public char getPID() {
		return this.PID;
	}
	
	//se actualiza el tiempo final, por el tiempo de procesado
	public void procesar(int q) {
		this.TF= this.TF-q;
	}
	
	//se devuelve el tiempo que necesita el proceso para culminar
	public int getTF() {
		return this.TF;
	}
	
	//se setea el tiempo de retorno como el reloj r
	public void calcularTR(float r) {
		this.TR= r;
	}
	
	//se muestra el tiempo de retorno
	public float getTR() {
		return this.TR-this.TLL;
	}
	
	//Se define el tiempo final como el tiempo de servicio, para una nueva corrida
	public void reset() {
		this.TF=this.TS;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = String.format("PID: %s" ,this.PID);
		return s 	;
	}
	
}
