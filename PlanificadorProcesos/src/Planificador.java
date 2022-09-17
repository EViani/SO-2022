import java.util.ArrayList;

public class Planificador {
	private static ArrayList<Proceso>	procesos;
	private static RoundRobin rr;
	private static SPF spf;
	private static FCFS fcfs;
	private static ArrayList<Resumen> resu;
	
	public static void main(String[] args) {
		procesos = new ArrayList<Proceso>();
		String comp="";
		cargaDefault();
		
		
		rr = new RoundRobin( procesos);
		rr.Quantum(4);
		comp+=rr.calcularT(resu);
	
		System.out.printf("%n Agregando un tiempo de intercambio de Q/10, con Q=4 %n");
		rr.Quantum(4,10);
		comp+=rr.calcularT(resu);
		
		rr.Quantum(8);
		comp+=rr.calcularT(resu);
		
		
		System.out.printf("%n Agregando un tiempo de intercambio de Q/10, con Q=4 %n");
		rr.Quantum(8,20);
		comp+=rr.calcularT(resu);
		
		
		spf = new SPF(procesos);
		comp+=spf.calcularT(resu);
		
		
		spf.cargarTI((float)0.4);
		comp+=spf.calcularT(resu);
		
		fcfs = new FCFS(procesos);
		comp+=fcfs.calcularT(resu);
		
		System.out.printf("%n%s ",comp);
;	
		
		
		
		
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
	
	
}
