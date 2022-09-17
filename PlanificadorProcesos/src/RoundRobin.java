import java.util.ArrayList;

public class RoundRobin {
	private int q;
	private float ti;
	private float reloj;
	private ArrayList<Proceso> ronda;
	

	public RoundRobin(ArrayList<Proceso> lista) {
		this.ronda=new ArrayList<>();
		this.ti=0;
		cargarRonda(lista);
		reloj=0;
	}
	
	private void cargarRonda(ArrayList<Proceso> lista) {
		
		// TODO Auto-generated method stub
		for (int i=0;i<lista.size();i++){
			Proceso p = new Proceso();
			p.crear(lista.get(i).getPID(),lista.get(i).getTLL() , lista.get(i).getTS()); 
			ronda.add(p);
		}
	}

	public void Quantum(int q) {
		this.q=q;
		this.ti=0;
	}
	
	public void Quantum(int q, int ti) {
		this.q=q;
		this.ti=(float)q/ti;
	}
	
	
	private void recorrer() {
		char pant;
		resetear();
		reloj=0;
		pant='$';
		int tf,tl;
		tf=0;
		System.out.printf("Quantum=%d - TI=%.2f %n",this.q,this.ti);
		while(restan()>0) {
			for(int i=0; i<ronda.size();i++) {
				tf=ronda.get(i).getTF();
				if(tf>0) {
					if(ronda.get(i).getPID()!=pant && pant!='$') {
						System.out.println("call planificador de corto plazo");
						reloj+=ti/2;
					}
					//comienza procesado
					if(tf>q) {
						if(ronda.get(i).getPID()!=pant) {
							System.out.println("call planificador de corto plazo");
							reloj+=ti/2;
							pant=ronda.get(i).getPID();
						}
						ronda.get(i).procesar(q);
						reloj+=q;
						System.out.printf("PID=%s, Reloj=%.2f %n",ronda.get(i).getPID(),reloj);
					}else {
						if(ronda.get(i).getPID()!=pant) {
							System.out.println("call planificador de corto plazo");
							reloj+=ti/2;
							pant=ronda.get(i).getPID();
						}
						reloj+=tf;
						ronda.get(i).procesar(tf);
						ronda.get(i).calcularTR(reloj);
						System.out.printf("PID=%s, Reloj=%.2f %n",ronda.get(i).getPID(),reloj);
					}
					
					//finaliza procesado
				}
			}
			
		}
	
	}
	
	private int restan() {
		int r=0;
		for (int i=0; i<ronda.size();i++ ) {
			r+=ronda.get(i).getTF();
		}
		return r;
	}
	
	public String calcularT(ArrayList<Resumen> resu) {
		recorrer();
		float TRP,TR;
		float TEP,TE;
		TEP=0;
		TRP=0;
		Resumen r;
		
		for(int i=0;i<ronda.size();i++) {
			r = new Resumen();
			TR=ronda.get(i).getTR();
			TE=TR-ronda.get(i).getTS();
			//System.out.printf("PID=%s TS=%d TR=%.2f TE=%.2f  %n",ronda.get(i).getPID(),ronda.get(i).getTS(),TR,TE);
			r.setPID(ronda.get(i).getPID());
			r.setTE(TE);
			r.setTS(ronda.get(i).getTS());
			r.setTR(TR);
			resu.add(r);
			TEP+=TE;
			TRP+=TR;
		}
		TEP=TEP/ronda.size();
		TRP=TRP/ronda.size();
		String s = "";
		s = s.format("RR Q=%d TI=%.2f TRP=%.2f TEP=%.2f %n", q, ti, TRP, TEP);
		return s;
	}
	
	private void resetear() {
		for(Proceso p : ronda) {
			p.reset();
		}
	}
	
	

}
