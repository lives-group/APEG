package GUI;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dataStructure.Memory;
import dataStructure.RegisterStatus;
import dataStructure.Registers;
import dataStructure.ReorderBuffer;
import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStation;
import dataStructure.ReservationStationNode;
import processador.ProcessadorMips;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class InterfaceMIPS{
	
	private JFrame frame;
	private JLabel label;
	private int pred = 4;

	private ArrayList<ReorderBufferNode> _robList = new ArrayList<ReorderBufferNode>();
	private Memory _memoria = new Memory();
	private ArrayList<ReservationStationNode> _rsLoadList = new ArrayList<ReservationStationNode>();
	private ArrayList<ReservationStationNode> _rsAddList = new ArrayList<ReservationStationNode>();
	private ArrayList<ReservationStationNode> _rsMultList = new ArrayList<ReservationStationNode>();
	private Registers _regs = new Registers();
	private RegisterStatus _regsStatus = new RegisterStatus();
	
	ProcessadorMips p;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JTable table;
	private JTable tableRS;
	private JLabel lblROB;
	private JTable tableROB;
	private JLabel lblNewLabel_1;
	private JTable tableRegs;
	private JTable tableClock;
	private JButton btnPlayFull;
	private JButton btnPaste;
	private JButton btnPrediNaoSalta;
	private JButton btnPred1Bit;
	private JButton btnPred2Bits;
	
	public int getPred(){
		return pred;
	}
	
	public void setTableROB(ArrayList<ReorderBufferNode> robList){
		_robList=robList;
		
		for (int i = 1; i < tableROB.getRowCount(); i++){
		      for(int j = 0; j < tableROB.getColumnCount(); j++) {
		    	  tableROB.setValueAt("", i, j);
		      }
		}
		
		for(int i = 0 ; i<10 && _robList.size()>i;i++){
			System.out.println("Rob list: " + _robList.get(i).ID);
			int j = i+1;
			System.out.println("Rob list: " + _robList.get(i).instruction + " linha : " + j); 
			tableROB.setValueAt(_robList.get(i).ID, j, 0);
			tableROB.setValueAt(_robList.get(i).busy, j, 1);
			tableROB.setValueAt(_robList.get(i).instruction, j, 2);
			tableROB.setValueAt(_robList.get(i).state, j, 3);
			tableROB.setValueAt(_robList.get(i).destination, j, 4);
			tableROB.setValueAt(_robList.get(i).value, j, 5);
		}
		
	}
	
	
	public void setTableRegs(RegisterStatus regsStatus){
		
		for (int i = 1; i < tableRegs.getRowCount(); i++){
		      for(int j = 1; j < tableRegs.getColumnCount(); j++) {
		    	  if (j!= 3 && j!= 6 && j!=9){
		    		  tableRegs.setValueAt("", i, j);
		    	  }
		      }
		}
		
		_regsStatus = regsStatus;
		int j = 0;
		for(int k = 1 ; k<11 ; k=k+3){
			for(int i = 1;i<9 ;i++){
				tableRegs.setValueAt(_regsStatus.getBusy().get(j).toString(), i, k);
				tableRegs.setValueAt(_regsStatus.getReorder().get(j).toString(), i, k+1);
				j++;
			}
		}
	}
	
	public void setTableClock(int sPointer, int clock, int nInst){
		tableClock.setValueAt(sPointer, 1, 1);
		tableClock.setValueAt(clock, 0, 1);
		tableClock.setValueAt(nInst, 2, 1);
		if(nInst!=0)
			tableClock.setValueAt(clock/nInst, 3, 1);
	}

	
	public void setTableRS(ArrayList<ReservationStationNode> rsLoadList,ArrayList<ReservationStationNode> rsAddList,ArrayList<ReservationStationNode> rsMultList){
		
		for (int i = 1; i < tableRS.getRowCount(); i++){
		      for(int j = 2; j < tableRS.getColumnCount(); j++) {
		    	  tableRS.setValueAt("", i, j);
		      }
		}	
		
		_rsLoadList = rsLoadList;
		_rsAddList=rsAddList;
		_rsMultList=rsMultList;
		for(int i = 0;i<5 && _rsLoadList.size()>i;i++){
			int j = i+1;
			System.out.println("RS list: " + _rsLoadList.get(i).getInstrucao());
			tableRS.setValueAt(_rsLoadList.get(i).getBusy(), j, 2);
			tableRS.setValueAt(_rsLoadList.get(i).getInstrucao().getInstrucao(), j, 3);
			tableRS.setValueAt(_rsLoadList.get(i).getDest(), j, 4);
			tableRS.setValueAt(_rsLoadList.get(i).getVj(), j, 5);
			tableRS.setValueAt(_rsLoadList.get(i).getVk(), j, 6);
			tableRS.setValueAt(_rsLoadList.get(i).getQj(), j, 7);
			tableRS.setValueAt(_rsLoadList.get(i).getQk(), j, 8);
			tableRS.setValueAt(_rsLoadList.get(i).getA(), j, 9);
		}
		for(int i = 0;i<3 && _rsAddList.size()>i;i++){
			int j = i+6;
			tableRS.setValueAt(_rsAddList.get(i).getBusy(), j, 2);
			tableRS.setValueAt(_rsAddList.get(i).getInstrucao().getInstrucao(), j, 3);
			tableRS.setValueAt(_rsAddList.get(i).getDest(), j, 4);
			tableRS.setValueAt(_rsAddList.get(i).getVj(), j, 5);
			tableRS.setValueAt(_rsAddList.get(i).getVk(), j, 6);
			tableRS.setValueAt(_rsAddList.get(i).getQj(), j, 7);
			tableRS.setValueAt(_rsAddList.get(i).getQk(), j, 8);
			tableRS.setValueAt(_rsAddList.get(i).getA(), j, 9);
		}
		
		for(int i = 0;i<2 && _rsMultList.size()>i;i++){
			int j = i+9;
			tableRS.setValueAt(_rsMultList.get(i).getBusy(), j, 2);
			tableRS.setValueAt(_rsMultList.get(i).getInstrucao().getInstrucao(), j, 3);
			tableRS.setValueAt(_rsMultList.get(i).getDest(), j, 4);
			tableRS.setValueAt(_rsMultList.get(i).getVj(), j, 5);
			tableRS.setValueAt(_rsMultList.get(i).getVk(), j, 6);
			tableRS.setValueAt(_rsMultList.get(i).getQj(), j, 7);
			tableRS.setValueAt(_rsMultList.get(i).getQk(), j, 8);
			tableRS.setValueAt(_rsMultList.get(i).getA(), j, 9);
		}
		
	}
	
	public void setTableMemoria(Memory memoria){
		_memoria=memoria;
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceMIPS window = new InterfaceMIPS();
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
	public InterfaceMIPS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1209, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		label = new JLabel("");
		label.setBounds(10, 221, 108, 25);
		frame.getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBounds(0, -26, 1193, 458);
		frame.getContentPane().add(panel);
		
		JLabel lblRS = new JLabel("Esta\u00E7\u00E3o de Reserva");
		lblRS.setHorizontalAlignment(SwingConstants.CENTER);
		lblRS.setBorder(BorderFactory.createLineBorder(Color.black));
		
		tableRS = new JTable();
		tableRS.setModel(new DefaultTableModel(
			new Object[][] {
				{"ID", "Tipo", "Busy", "Instru\u00E7\u00E3o", "Dest.", "Vj", "Vk", "Qj", "Qk", "A"},
				{"ER1", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER2", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER3", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER4", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER5", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER6", "Add", null, null, null, null, null, null, null, null},
				{"ER7", "Add", null, null, null, null, null, null, null, null},
				{"ER8", "Add", null, null, null, null, null, null, null, null},
				{"ER9", "Mult", null, null, null, null, null, null, null, null},
				{"ER10", "Mult", null, null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Tipo", "Busy", "Instru", "Dest", "Vj", "Vk", "Qj", "Qk", "A"
			}
		));
		tableRS.getColumnModel().getColumn(2).setPreferredWidth(64);
		tableRS.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableRS.setEnabled(false);
		tableRS.setBorder(BorderFactory.createLineBorder(Color.black));
		
		lblROB = new JLabel("Buffer de reordena\u00E7\u00E3o");
		lblROB.setHorizontalAlignment(SwingConstants.CENTER);
		lblROB.setBorder(BorderFactory.createLineBorder(Color.black));
		
		tableROB = new JTable();
		tableROB.setModel(new DefaultTableModel(
			new Object[][] {
				{"Entrada ", "Ocupado", "Instru\u00E7\u00E3o", "Estado", "Destino", "Valor"},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		tableROB.getColumnModel().getColumn(1).setPreferredWidth(56);
		tableROB.getColumnModel().getColumn(2).setPreferredWidth(151);
		tableROB.setEnabled(false);
		tableROB.setBorder(BorderFactory.createLineBorder(Color.black));
		
		lblNewLabel_1 = new JLabel("Registradores");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		tableRegs = new JTable();
		tableRegs.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "Ql", "Vl", null, "Ql", "Ql", null, "Ql", "Vl", null, "Ql", "Vl"},
				{"R0", null, null, "R8", null, null, "R16", null, null, "R24", null, null},
				{"R1", "", null, "R9", null, null, "R17", null, null, "R25", null, null},
				{"R2", null, "", "R10", "", "", "R18", "", "", "R26", "", null},
				{"R3", null, null, "R11", null, null, "R19", null, null, "R27", null, ""},
				{"R4", null, null, "R12", null, null, "R20", null, null, "R28", null, null},
				{"R5", null, null, "R13", null, null, "R21", null, null, "R29", null, null},
				{"R6", null, null, "R14", null, null, "R22", null, null, "R30", null, null},
				{"R7", null, null, "R15", null, null, "R23", null, null, "R31", null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		tableRegs.setEnabled(false);
		tableRegs.setBorder(BorderFactory.createLineBorder(Color.black));
		
		tableClock = new JTable();
		tableClock.setModel(new DefaultTableModel(
			new Object[][] {
				{"Clock Corrente:", null},
				{"PC:", null},
				{"N\u00FAmero de instru\u00E7\u00F5es conclu\u00EDdas:", null},
				{"Clock por instru\u00E7\u00F5es (CPI):", null},
				{"Predi\u00E7\u00E3o", null},
			},
			new String[] {
				"New column", "New column"
			}
		));
		tableClock.getColumnModel().getColumn(0).setPreferredWidth(171);
		tableClock.getColumnModel().getColumn(1).setPreferredWidth(92);
		tableClock.setEnabled(false);
		tableClock.setBorder(BorderFactory.createLineBorder(Color.black));
		tableClock.setBounds(602, 146, 272, 65);
		
		JButton btnPlay = new JButton("");
		btnPlay.setIcon(new ImageIcon(InterfaceMIPS.class.getResource("/Resources/Actions-arrow-right-icon.png")));
		btnPlay.setBounds(602, 9, 22, 25);
		btnPlay.addActionListener(new ClassListenerPlay(this));
		
		btnPlayFull = new JButton("");
		btnPlayFull.setIcon(new ImageIcon(InterfaceMIPS.class.getResource("/Resources/Actions-arrow-right-double-icon.png")));
		btnPlayFull.setBounds(630, 9, 22, 25);
		btnPlayFull.addActionListener(new ClassListenerPlayFull(this));
		
		btnPaste = new JButton("");
		btnPaste.setIcon(new ImageIcon(InterfaceMIPS.class.getResource("/Resources/folder-icon.png")));
		btnPaste.setBounds(655, 9, 22, 25);
		btnPaste.addActionListener(new ClassListener());
		
		btnPrediNaoSalta = new JButton("Preditor n\u00E3o salta");
		btnPrediNaoSalta.addActionListener(new ClassListenerp1(this));
		
		btnPred1Bit = new JButton("Preditor de 1 bit");
		btnPred1Bit.addActionListener(new ClassListenerp2(this));
		
		btnPred2Bits = new JButton("Preditor de 2 bits");
		btnPred2Bits.addActionListener(new ClassListenerp3(this));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRS, GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
								.addComponent(tableRS, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPrediNaoSalta, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
								.addComponent(btnPred1Bit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnPred2Bits, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnPlay)
									.addGap(18)
									.addComponent(btnPlayFull)
									.addGap(18)
									.addComponent(btnPaste)
									.addPreferredGap(ComponentPlacement.RELATED, 68, Short.MAX_VALUE))
								.addComponent(tableClock, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(tableROB, GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
								.addComponent(lblROB, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(tableRegs, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnPaste)
								.addComponent(btnPlayFull)
								.addComponent(btnPlay))
							.addGap(23)
							.addComponent(btnPrediNaoSalta, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPred1Bit, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPred2Bits, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(tableClock, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tableRegs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblRS, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tableRS, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblROB, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tableROB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
	}
	
	public class ClassListenerPlay implements ActionListener {
		public InterfaceMIPS _GUI;
		
		ClassListenerPlay(InterfaceMIPS GUI){
			_GUI = GUI;
		}
	
	
	    public void actionPerformed(ActionEvent e) {
	    	//if(p.dataStructure.getFilaDeIntrucao_().fila_.size() <= p.dataStructure.sPointer)
	    	//System.out.println("Tamanho da fila: "+p.dataStructure.getFilaDeIntrucao_().fila_.size());
	    	//System.out.println("StackPointer: " + p.dataStructure.sPointer);
	    	p.RunClock(_GUI);
	    	
	    }   
	}
	
	public class ClassListenerPlayFull implements ActionListener {
		public InterfaceMIPS _GUI;
		
		ClassListenerPlayFull(InterfaceMIPS GUI){
			_GUI = GUI;
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	int i = 0;
	    	System.out.println("Comecou o Full");
	    	while(p.dataStructure.getFilaDeIntrucao_().fila_.size() >= p.dataStructure.sPointer){
	    		System.out.println(i++);
	    		p.RunClock(_GUI);
	    	}
	    	
	    }   
	}
	
	public class ClassListenerp1 implements ActionListener {
		
		public InterfaceMIPS _GUI;
		ClassListenerp1(InterfaceMIPS GUI){
			_GUI = GUI;
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	tableClock.setValueAt("Nao salta", 4, 1);
	    	pred=0;
	    }   
	}
	
	public class ClassListenerp2 implements ActionListener {
		
		public InterfaceMIPS _GUI;
		ClassListenerp2(InterfaceMIPS GUI){
			_GUI = GUI;
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	tableClock.setValueAt("Pred. 1 bit", 4, 1);
	    	pred=1;
	    }   
	}
	
	public class ClassListenerp3 implements ActionListener {
		
		public InterfaceMIPS _GUI;
		ClassListenerp3(InterfaceMIPS GUI){
			_GUI = GUI;
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	tableClock.setValueAt("Pred. 2 bits", 4, 1);
	    	pred=2;
	    }   
	}
	
	public class ClassListener implements ActionListener {
		
	    public void actionPerformed(ActionEvent e) {
	    	JFileChooser fileChooser = new JFileChooser();
	    	int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            try {
	              String filePath = file.getAbsolutePath();
	              System.out.println("Caminho do arquivo: " + filePath);
	              p = new ProcessadorMips(filePath);
	            } catch (Exception ex) {
	              System.out.println("problem accessing file"+file.getAbsolutePath());
	            }
	        } 
	        else {
	            System.out.println("File access cancelled by user.");
	        }       
	    }   
	}
	public boolean getFrameEnabled() {
		return frame.isEnabled();
	}
	public void setFrameEnabled(boolean enabled) {
		frame.setEnabled(enabled);
	}
}

