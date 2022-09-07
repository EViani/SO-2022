import java.util.ArrayList;

public class SPF {
	
	float reloj;
	ArrayList<Proceso> lista;
	float ti;
	
	
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

	private ArrayList<Proceso> ordenar(float rel, ArrayList<Proceso> apro) {
		Proceso proc;
		
		for(int i=0;i<lista.size();i++) {
				for(int j=0;j<lista.size()-1;j++) {
					if(apro.get(j).getTF()>apro.get(j+1).getTF() && reloj>=apro.get(j).getTLL() && reloj>=apro.get(j+1).getTLL()) {
						proc=lista.get(j);
						apro.set(j,apro.get(j+1));
						apro.set(j+1, proc);
					}

				}
		}
		return apro;
	}
	
	
	public void procesar() {
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
				if(tf>0) {
					if(lista.get(i).getPID()!=pant && pant!='$') {
						System.out.println("call planificador de corto plazo");
						reloj+=ti/2;
					}
					
					
				
				}
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
	
	
	
}
