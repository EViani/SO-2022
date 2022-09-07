import java.util.ArrayList;

public class Planificador {
	private static ArrayList<Proceso>	procesos;
	private static RoundRobin rr;
	
	public static void main(String[] args) {
		procesos = new ArrayList<Proceso>();
		String comp="";
		cargaDefault();
		
		//System.out.println(procesos);
		
		rr = new RoundRobin( procesos);
		rr.Quantum(4);
		rr.recorrer();
		comp+=rr.calcularT();
		
		//System.out.println(procesos);
		
		System.out.printf("%n Agregando un tiempo de intercambio de Q/10, con Q=4 %n");
		rr.Quantum(4,10);
		rr.recorrer();
		comp+=rr.calcularT();
		
		System.out.printf("%n%s ",comp);
		
		//System.out.println(procesos);
		
		
		
		
		
	}
	
	static void carga(char pid, int tl, int ts) {
		Proceso p = new Proceso();
		procesos.add(p.crear(pid,tl, ts));
	}

	static void cargaDefault() {
		carga('A',0, 10);
		carga('B',0, 6);
		carga('C',0, 1);
		carga('D',2, 8);
		carga('E',3, 13);
		carga('F',3, 7);
		
	}
	
	static void reset() {
		for(int i=0;i<procesos.size();i++) {
		procesos.get(i).reset();	
		}
	}
	
	
}
