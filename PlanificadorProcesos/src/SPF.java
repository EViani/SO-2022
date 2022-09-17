import java.util.ArrayList;

public class SPF {
	
	private float reloj;
	private ArrayList<Proceso> lista;
	private float ti;
	
	
	public SPF(ArrayList<Proceso> Lpro)  {
		lista = new ArrayList<Proceso>();
		cargarlista(Lpro);
		reloj=0;
		ti=0;
	}
	
	private void cargarlista(ArrayList<Proceso> lpro) {
		// TODO Auto-generated method stub
		for (int i=0;i<lpro.size();i++){
			Proceso p = new Proceso();
			p.crear(lpro.get(i).getPID(),lpro.get(i).getTLL() , lpro.get(i).getTS()); 
			lista.add(p);
		}
	}
	
	public void cargarTI(float ti) {
		this.ti=ti;
		
	}

	private ArrayList<Proceso> ordenar(float rel, ArrayList<Proceso> apro) {
		Proceso proc;
		
		for(int i=0;i<lista.size();i++) {
				for(int j=0;j<lista.size()-1;j++) {
					if(apro.get(j).getTF()>apro.get(j+1).getTF() && rel>=apro.get(j).getTLL() && rel>=apro.get(j+1).getTLL()) {
						proc=lista.get(j);
						apro.set(j,apro.get(j+1));
						apro.set(j+1, proc);
					}

				}
		}
		return apro;
	}
	
	
	private void procesar() {
		char pant;
		pant='$';
		resetear();
		reloj=0;
		int tf;
		tf=0;
		float r;
		System.out.printf("SPF%n");
		while(restan()>0) {
			r=reloj;
			ordenar(r,lista);
			for(int i=0;i<lista.size();i++) {
				tf=lista.get(i).getTF();
				if(tf>0 && lista.get(i).getTLL()<=r) {
					if(lista.get(i).getPID()!=pant && pant!='$') {
						System.out.println("call planificador de corto plazo");
						reloj+=ti;
					}
					reloj+=lista.get(i).getTS();
					lista.get(i).procesar(tf);
					lista.get(i).calcularTR(reloj);
					if(pant!= lista.get(i).getPID() ) {
						System.out.println("Call Scheduler corto plazo");
						reloj=reloj+ti;
					}
				
				}
				System.out.printf("PID=%s, Reloj=%.2f %n",lista.get(i).getPID(),reloj);
				pant=lista.get(i).getPID();
			}
		}
	}
	
	
	
	private int restan() {
		int r=0;
		for (int i=0; i<lista.size();i++ ) {
			r+=lista.get(i).getTF();
		}
		return r;
	}
	
	private void resetear() {
		for(Proceso p : lista) {
			p.reset();
		}
	}
	
	public String calcularT(ArrayList<Resumen> resu) {
		procesar();
		float TRP,TR;
		float TEP,TE;
		TEP=0;
		TRP=0;
		Resumen r;
		for(int i=0;i<lista.size();i++) {
			r = new Resumen();
			TR=lista.get(i).getTR();
			TE=TR-lista.get(i).getTS();
			//System.out.printf("PID=%s TS=%d TR=%.2f TE=%.2f  %n",lista.get(i).getPID(),lista.get(i).getTS(),TR,TE);
			r.setPID(lista.get(i).getPID());
			r.setTE(TE);
			r.setTS(lista.get(i).getTS());
			r.setTR(TR);
			resu.add(r);
			TEP+=TE;
			TRP+=TR;
		}
		TEP=TEP/lista.size();
		TRP=TRP/lista.size();
		String s = "";
		s = s.format("SPF TI=%.2f TRP=%.2f TEP=%.2f %n", ti, TRP, TEP);
		return s;
	}
	
	
	
	
}
