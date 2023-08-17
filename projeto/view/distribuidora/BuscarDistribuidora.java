package com.projeto.view.distribuidora;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Distribuidora;
import com.projeto.model.service.DistribuidoraService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.distribuidora.BuscarDistribuidora;
import com.projeto.view.distribuidora.DistribuidoraFrame;
import com.projeto.view.distribuidora.TabelaDistribuidoraModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import javax.swing.border.LineBorder;

import javax.swing.JDialog;

public class BuscarDistribuidora  extends JDialog  {
	
	private static final long serialVersionUID = 8307230038706922136L;

	
	private static final int CODIGO =   0;
	private static final int NOME =     1;
	private static final int EMAIL =  	2;
	private static final int TELEFONE = 3;
	private static final int RUA = 4;
	private static final int NUMERO = 5;
	private static final int CIDADE = 6;
	private static final int ESTADO = 7;
	private static final int BAIRRO = 8;
	private static final int CEP = 9;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaDistribuidora;
	private JTextField textFieldBuscaDistribuidora;
	private JScrollPane scrollPane;
	private JTable tabelaDistribuidora;

	private TabelaDistribuidoraModel tabelaDistribuidoraModel;
	private TableRowSorter<TabelaDistribuidoraModel> sortTabelaDistribuidora;
	private List<Distribuidora> listaDistribuidora;
	private List<RowSorter.SortKey> sortKeys;
	
   	private boolean isConfirmado; 
	
	private Distribuidora distribuidora;
	
	private JButton btnInserirDistribuidora;
	
	private int row = 0;
	private JPanel buttonPane;
	private JPanel panel;
	
	
	
	
	public BuscarDistribuidora(JFrame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		iniciarDados();
	}


	private void iniciarDados(){
		listaDistribuidora = new ArrayList<Distribuidora>();
	}
	

	private void initComponents() {
		setTitle("Busca distribuidora");
		setResizable(false);
		setConfirmado(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 818, 436);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane.setBounds(18, 71, 784, 283);
		{
			buttonPane = new JPanel();
			buttonPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buttonPane.setBounds(0, 355, 812, 47);
			{
				JButton okButton = new JButton("");
				okButton.setIcon(new ImageIcon(BuscarDistribuidora.class.getResource("/imagens/ok.png")));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
				okButton.setBounds(612, 5, 74, 31);
				okButton.setMnemonic(KeyEvent.VK_O);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selecionadistribuidora();
					}
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("");
				cancelButton.setIcon(new ImageIcon(BuscarDistribuidora.class.getResource("/imagens/iconFechar.png")));
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
				cancelButton.setBounds(697, 5, 74, 31);
			
				cancelButton.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						setConfirmado(false);
						dispose();
					}
				});
				cancelButton.setMnemonic(KeyEvent.VK_A);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setConfirmado(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		tabelaDistribuidoraModel = new TabelaDistribuidoraModel();
	    listaDistribuidora = carregarListaDistribuidora();
		tabelaDistribuidoraModel.setListDistribuidora(listaDistribuidora);
		tabelaDistribuidora = new JTable();
		tabelaDistribuidora.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabelaDistribuidora.setModel(tabelaDistribuidoraModel);
		tabelaDistribuidora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaDistribuidora.setFillsViewportHeight(true);
		sortTabelaDistribuidora = new TableRowSorter<TabelaDistribuidoraModel>(tabelaDistribuidoraModel);
		tabelaDistribuidora.setRowSorter(sortTabelaDistribuidora);
		
		sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
		sortKeys.add(new RowSorter.SortKey(EMAIL, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(TELEFONE, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(RUA, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NUMERO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CIDADE, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(ESTADO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(BAIRRO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CEP, SortOrder.ASCENDING));
		tabelaDistribuidora.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tabelaDistribuidora.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
		tabelaDistribuidora.getColumnModel().getColumn(NOME).setPreferredWidth(50);
		tabelaDistribuidora.getColumnModel().getColumn(EMAIL).setPreferredWidth(50);
		tabelaDistribuidora.getColumnModel().getColumn(TELEFONE).setPreferredWidth(30);
		tabelaDistribuidora.getColumnModel().getColumn(RUA).setPreferredWidth(50);
		tabelaDistribuidora.getColumnModel().getColumn(NUMERO).setPreferredWidth(20);
		tabelaDistribuidora.getColumnModel().getColumn(CIDADE).setPreferredWidth(50);
		tabelaDistribuidora.getColumnModel().getColumn(ESTADO).setPreferredWidth(50);
		tabelaDistribuidora.getColumnModel().getColumn(BAIRRO).setPreferredWidth(50);
		tabelaDistribuidora.getColumnModel().getColumn(CEP).setPreferredWidth(30);
		

		
   		scrollPane.setViewportView(tabelaDistribuidora);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		
				btnInserirDistribuidora = new JButton("");
				btnInserirDistribuidora.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						inserirDistribuidora();
					}
				});
				btnInserirDistribuidora.setIcon(new ImageIcon(BuscarDistribuidora.class.getResource("/imagens/book_add.png")));
				btnInserirDistribuidora.setBounds(528, 5, 74, 31);
				buttonPane.add(btnInserirDistribuidora);
				btnInserirDistribuidora.setFont(new Font("Tahoma", Font.PLAIN, 11));
				
				btnInserirDistribuidora.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_C ) {
							inserirDistribuidora();
						}
						
					}
				});
				
		btnInserirDistribuidora.setMnemonic(KeyEvent.VK_C);
		contentPanel.add(scrollPane);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 781, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
				lblPesquisaDistribuidora = new JLabel("Distribuidora:");
				lblPesquisaDistribuidora.setBounds(14, 15, 129, 14);
				panel.add(lblPesquisaDistribuidora);
				lblPesquisaDistribuidora.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_P) {
							textFieldBuscaDistribuidora.requestFocus();
						}
					}
				});
				lblPesquisaDistribuidora.setDisplayedMnemonic(KeyEvent.VK_P);
				
						textFieldBuscaDistribuidora = new JTextField();
						textFieldBuscaDistribuidora.setBounds(146, 12, 608, 20);
						panel.add(textFieldBuscaDistribuidora);
						textFieldBuscaDistribuidora.getDocument().addDocumentListener(new DocumentListener() {
							
							@Override
							public void removeUpdate(DocumentEvent e) {
								filtraNomeDistribuidora();
							}
							
							@Override
							public void insertUpdate(DocumentEvent e) {
								filtraNomeDistribuidora();
							}
							
							@Override
							public void changedUpdate(DocumentEvent e) {
								filtraNomeDistribuidora();
							}
						});
						
						textFieldBuscaDistribuidora.setColumns(15);
						lblPesquisaDistribuidora.setLabelFor(textFieldBuscaDistribuidora);

	}



	protected void inserirDistribuidora() {
		DistribuidoraFrame distribuidoraFrame = new DistribuidoraFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaDistribuidora, tabelaDistribuidoraModel, 0);
		distribuidoraFrame.setLocationRelativeTo(null);
		distribuidoraFrame.setVisible(true);
	    tabelaDistribuidoraModel.fireTableDataChanged();	
	}

	protected void selecionaDistribuidora(MouseEvent e) {
		row = tabelaDistribuidora.getSelectedRow();
		
		 if ( tabelaDistribuidora.getRowSorter() != null ) {
			row =  tabelaDistribuidora.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionadistribuidora() {
		if ( tabelaDistribuidora.getSelectedRow() != -1 && 
			 tabelaDistribuidora.getSelectedRow() < tabelaDistribuidoraModel.getRowCount() ) {
			 distribuidora = new Distribuidora();
			 setConfirmado(true);
			 distribuidora = tabelaDistribuidoraModel.getDistribuidora(row);
			 dispose();
		} else {
			setConfirmado(false);
		}
		
	}

	private List<Distribuidora> carregarListaDistribuidora() {
		DistribuidoraService distribuidoraService = new DistribuidoraService();
		listaDistribuidora = distribuidoraService.carregarListaDistribuidora();
		return listaDistribuidora;
	}
	
	private void filtraNomeDistribuidora() {
		RowFilter<TabelaDistribuidoraModel, Object> rowFilter = null;
		String filter = textFieldBuscaDistribuidora.getText();
		try {
			rowFilter = RowFilter.regexFilter(filter);
		} catch(PatternSyntaxException e) {
			return;
		}
		sortTabelaDistribuidora.setRowFilter(rowFilter);
		
	}


	public Distribuidora getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}
}
