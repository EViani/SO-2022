import java.util.ArrayList;

public class FCFS {
	private float ti;
	private float reloj;
	private ArrayList<Proceso> cola;

	public FCFS(ArrayList<Proceso> lista) {
		this.cola=new ArrayList<>();
		this.ti=0;
		cargarRonda(lista);
		reloj=0;
	}
	
	private void cargarRonda(ArrayList<Proceso> lista) {
		
		// TODO Auto-generated method stub
		for (int i=0;i<lista.size();i++){
			Proceso p = new Proceso();
			p.crear(lista.get(i).getPID(),lista.get(i).getTLL() , lista.get(i).getTS()); 
			cola.add(p);
		}
	}
	
	private void recorrer() {
		char pant;
		pant='$';
		resetear();
		int tf;
		tf=0;
		float r;
		System.out.printf("FCFS%n");
		
		reloj=0;
		for(Proceso p : cola) {
			if(p.getPID()!=pant && pant!='$') {
				System.out.println("call planificador de corto plazo");
				reloj+=ti;
			}
			reloj+=p.getTS();
			p.calcularTR(reloj);
			System.out.printf("PID=%s, Reloj=%.2f %n",p.getPID(),reloj);
			pant=p.getPID();
			
		}
		
	}

	public String calcularT(ArrayList<Resumen> resu) {
		recorrer();
		float TRP,TR;
		float TEP,TE;
		TEP=0;
		TRP=0;
		Resumen r;
		for(Proceso p : cola) {
			r = new Resumen();
			TR=p.getTR();
			TE=TR-p.getTS();
			//System.out.printf("PID=%s TS=%d TR=%.2f TE=%.2f  %n",p.getPID(),p.getTS(),TR,TE);
			r.setPID(p.getPID());
			r.setTE(TE);
			r.setTS(p.getTS());
			r.setTR(TR);
			resu.add(r);
			TEP+=TE;
			TRP+=TR;
		}
		TEP=TEP/cola.size();
		TRP=TRP/cola.size();
		String s = "";
		s = s.format("FCFS TI=%.2f TRP=%.2f TEP=%.2f %n", ti, TRP, TEP);
		return s;
	}
	
	private void resetear() {
		for(Proceso p : cola) {
			p.reset();
		}
	}
}
