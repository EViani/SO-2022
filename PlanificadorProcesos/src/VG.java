import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class VG {
	
	private JFrame frame;
	private JTable tProcesos;
	private static ArrayList<Proceso>	procesos;
	private static RoundRobin rr;
	private static SPF spf;
	private static FCFS fcfs;
	private static ArrayList<Resumen>	resuRR;
	private static ArrayList<Resumen>	resuSPF;
	private static ArrayList<Resumen>	resuFCFS;
	
	private JTextArea textSalida;
	private JTextField textQ;
	private JTable tRR;
	private JTable tSPF;
	private JTable tFCFS;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VG window = new VG();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VG() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		

		procesos = new ArrayList<Proceso>();
		//resumen = new ArrayList<Resumen>();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Planificador de procesos");
		frame.setBounds(100, 100, 876, 374);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 325, 161);
		scrollPane.setEnabled(false);
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel mTProcesos = new DefaultTableModel();
		tProcesos = new JTable(mTProcesos);
		tProcesos.setEnabled(true);
		mTProcesos.addColumn("PID");
		mTProcesos.addColumn("TLL");
		mTProcesos.addColumn("TS");
		scrollPane.setViewportView(tProcesos);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 158, 312, 176);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textSalida = new JTextArea();
		textSalida.setBounds(0, 11, 312, 165);
		panel.add(textSalida);
		
		JButton btnNewButton = new JButton("Ingresar procesos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
				DefaultTableModel modelo;
				modelo = (DefaultTableModel) tProcesos.getModel();
				modelo.setRowCount(0);
				String r = JOptionPane.showInputDialog("Ingrese cantidad de procesos",0);
				int row = Integer.parseInt(r);
				if (row >0) {
					modelo.setRowCount(row);
				}
				
				tProcesos.setEnabled(true);
				
			}
		});
		btnNewButton.setBounds(347, 0, 169, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnRR = new JButton("RR");
		btnRR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				armarProcesos();
				rr = new RoundRobin( procesos);
				int q =Integer.parseInt(textQ.getText());
				if (q>0) {
					rr.Quantum(q);
				} else {
					rr.Quantum(4);
				}
				textSalida.setText(rr.calcularT(resuRR));
				resumenRR(resuRR);
			}
		});
		btnRR.setBounds(345, 141, 142, 23);
		frame.getContentPane().add(btnRR);
		
		JLabel lblNewLabel = new JLabel("Quantum");
		lblNewLabel.setBounds(357, 42, 63, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textQ = new JTextField();
		textQ.setText("4");
		textQ.setBounds(430, 39, 42, 20);
		frame.getContentPane().add(textQ);
		textQ.setColumns(10);
		
		JButton btnSPF = new JButton("SPF");
		btnSPF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				armarProcesos();
				spf = new SPF(procesos);
				textSalida.setText(spf.calcularT(resuSPF));
				resumenSPF(resuSPF);
			}
		});
		btnSPF.setBounds(497, 141, 139, 23);
		frame.getContentPane().add(btnSPF);
		
		JButton btnFCFS = new JButton("FCFS");
		btnFCFS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				armarProcesos();
				fcfs = new FCFS(procesos);
				textSalida.setText(fcfs.calcularT(resuFCFS));
				resumenFCFS(resuFCFS);
			}
		});
		btnFCFS.setBounds(646, 141, 139, 23);
		frame.getContentPane().add(btnFCFS);
		
		JButton btnNewButton_1 = new JButton("RR - SPF - FCFS");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				armarProcesos();
				String s = "";
				rr = new RoundRobin(procesos);
				int q = Integer.parseInt(textQ.getText());
				if(q>0) {
					rr.Quantum(q);
				}else {
					rr.Quantum(4);
				}
				
				spf = new SPF(procesos);
				fcfs = new FCFS(procesos);
				s+= rr.calcularT(resuRR);
				resumenRR(resuRR);
				s+=spf.calcularT(resuSPF);
				resumenSPF(resuSPF);
				s+=fcfs.calcularT(resuFCFS);
				resumenFCFS(resuFCFS);
				textSalida.setText(s);
			
			}
		});
		btnNewButton_1.setBounds(345, 97, 440, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		DefaultTableModel mTRR = new DefaultTableModel();
		mTRR.addColumn("PID");
		mTRR.addColumn("TS");
		mTRR.addColumn("TR");
		mTRR.addColumn("TE");
		
		JScrollPane scrollRR = new JScrollPane();
		scrollRR.setBounds(347, 175, 139, 160);
		frame.getContentPane().add(scrollRR);
		
		tRR = new JTable(mTRR);
		scrollRR.setViewportView(tRR);
		
		JScrollPane scrollSPF = new JScrollPane();
		scrollSPF.setBounds(497, 174, 139, 160);
		frame.getContentPane().add(scrollSPF);
		
		DefaultTableModel mTSPF = new DefaultTableModel();
		mTSPF.addColumn("PID");
		mTSPF.addColumn("TS");
		mTSPF.addColumn("TR");
		mTSPF.addColumn("TE");
		
		tSPF = new JTable(mTSPF);
		scrollSPF.setViewportView(tSPF);
		
		JScrollPane scrollFCFS = new JScrollPane();
		scrollFCFS.setBounds(646, 174, 139, 160);
		frame.getContentPane().add(scrollFCFS);
		
		DefaultTableModel mTFCFS = new DefaultTableModel();
		mTFCFS.addColumn("PID");
		mTFCFS.addColumn("TS");
		mTFCFS.addColumn("TR");
		mTFCFS.addColumn("TE");
		
		tFCFS = new JTable(mTFCFS);
		scrollFCFS.setViewportView(tFCFS);
		
		
		
		
		
		cargaDefault();
		
	}
	
	private void cargaDefault() {
		cargaD();
		DefaultTableModel modelo;
		modelo = (DefaultTableModel) tProcesos.getModel();
		Object [] fila = new Object[3];
		int row=0;
		for(Proceso p:procesos) {
			modelo.addRow(fila);
			modelo.setValueAt(p.getPID(),row,0);
			modelo.setValueAt(p.getTLL(),row,1);
			modelo.setValueAt(p.getTS(),row,2);
			row++;
		}
		
		this.frame.setVisible(true);
		tProcesos.setEnabled(false);
		
	}
	
	private static void carga(char pid, int tl, int ts) {
		Proceso p = new Proceso();
		procesos.add(p.crear(pid,tl, ts));
	}

	private static void cargaD() {
		carga('A',0, 10);
		carga('B',0, 6);
		carga('C',0, 1);
		carga('D',2, 8);
		carga('E',3, 13);
		carga('F',3, 7);
	}
	
	private void armarProcesos() {
		resuRR = new ArrayList<Resumen>();
		resuSPF = new ArrayList<Resumen>();
		resuFCFS = new ArrayList<Resumen>();
		tProcesos.clearSelection();
		procesos = new ArrayList<Proceso>();
		DefaultTableModel modelo;
		modelo = (DefaultTableModel) tProcesos.getModel();
		int rc = modelo.getRowCount();
		for(int i =0; i<rc;i++) {
			carga( modelo.getValueAt(i, 0).toString().charAt(0),
					Integer.parseInt( modelo.getValueAt(i, 1).toString()),
					Integer.parseInt( modelo.getValueAt(i, 2).toString()));
		}
	}
	
	private void resumenRR(ArrayList<Resumen> resu) {
		DefaultTableModel modelo;
		modelo = (DefaultTableModel) tRR.getModel();
		Object [] fila = new Object[4];
		int row=0;
		modelo.setRowCount(0);
		for(Resumen r:resu) {
			modelo.addRow(fila);
			modelo.setValueAt(r.getPID(),row,0);
			modelo.setValueAt(r.getTS(),row,1);
			modelo.setValueAt(r.getTR(),row,2);
			modelo.setValueAt(r.getTE(),row,3);
			row++;
		}
		
		tRR.setEnabled(false);
	}
	
	private void resumenSPF(ArrayList<Resumen> resu) {
		DefaultTableModel modelo;
		modelo = (DefaultTableModel) tSPF.getModel();
		Object [] fila = new Object[4];
		modelo.setRowCount(0);
		int row=0;
		for(Resumen r:resu) {
			modelo.addRow(fila);
			modelo.setValueAt(r.getPID(),row,0);
			modelo.setValueAt(r.getTS(),row,1);
			modelo.setValueAt(r.getTR(),row,2);
			modelo.setValueAt(r.getTE(),row,3);
			row++;
		}
		
		tRR.setEnabled(false);
	}
	
	private void resumenFCFS(ArrayList<Resumen> resu) {
		DefaultTableModel modelo;
		modelo = (DefaultTableModel) tFCFS.getModel();
		Object [] fila = new Object[4];
		modelo.setRowCount(0);
		int row=0;
		for(Resumen r:resu) {
			modelo.addRow(fila);
			modelo.setValueAt(r.getPID(),row,0);
			modelo.setValueAt(r.getTS(),row,1);
			modelo.setValueAt(r.getTR(),row,2);
			modelo.setValueAt(r.getTE(),row,3);
			row++;
		}
		
		tRR.setEnabled(false);
	}
	
	private void limpiar() {
		DefaultTableModel modelo;
		modelo = (DefaultTableModel) tFCFS.getModel();
		modelo.setRowCount(0);
		modelo = (DefaultTableModel) tRR.getModel();
		modelo.setRowCount(0);
		modelo = (DefaultTableModel) tSPF.getModel();
		modelo.setRowCount(0);
		textSalida.setText("");
	}
	
}
