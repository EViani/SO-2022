import java.util.ArrayList;

public class RoundRobin {
	int q;
	float ti;
	float reloj;
	ArrayList<Proceso> ronda;

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
		int tf;
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
	
	public String calcularT() {
		recorrer();
		float TRP,TR;
		float TEP,TE;
		TEP=0;
		TRP=0;
		for(int i=0;i<ronda.size();i++) {
			TR=ronda.get(i).getTR();
			TE=TR-ronda.get(i).getTS();
			System.out.printf("PID=%s TS=%d TR=%.2f TE=%.2f  %n",ronda.get(i).getPID(),ronda.get(i).getTS(),TR,TE);
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
