package com.projeto.view.cliente;
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

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
import com.projeto.util.ProcessamentoDados;

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

public class BuscarCliente extends JDialog {
	
	private static final int CODIGO =   	0;
	private static final int NOME =     	1;
	private static final int TELEFONE =     2;
	private static final int EMAIL =     	3;
	private static final int RUA =    		4;
	private static final int NUMERO =     	5;
	private static final int CIDADE =     	6;
	private static final int ESTADO =     	7;
	private static final int BAIRRO =     	8;
	private static final int CEP =     		9;
	private static final int CPF =     		10;


	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaCliente;
	private JTextField textFieldBuscaCliente;
	private JScrollPane scrollPane;
	private JTable tabelaCliente;

	private TabelaClienteModel tabelaClienteModel;
	private TableRowSorter<TabelaClienteModel> sortTabelaCliente;
	private List<Cliente> listaCliente;
	private List<RowSorter.SortKey> sortKeys;
	
   	private boolean isConfirmado; 
	
	private Cliente cliente;
	
	private JButton btnInserirCliente;
	
	private int row = 0;
	private JPanel buttonPane;
	private JPanel panel;
	
	
	
	
	public BuscarCliente(JFrame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		iniciarDados();
	}


	private void iniciarDados(){
		listaCliente = new ArrayList<Cliente>();
	}
	

	private void initComponents() {
		setTitle("Busca cliente");
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
				okButton.setIcon(new ImageIcon(Cliente.class.getResource("/imagens/ok.png")));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
				okButton.setBounds(612, 5, 74, 31);
				okButton.setMnemonic(KeyEvent.VK_O);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selecionacliente();
					}
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("");
				cancelButton.setIcon(new ImageIcon(Cliente.class.getResource("/imagens/iconFechar.png")));
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

		tabelaClienteModel = new TabelaClienteModel();
	    listaCliente = carregarListaCliente();
		tabelaClienteModel.setListCliente(listaCliente);
		tabelaCliente = new JTable();
		tabelaCliente.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabelaCliente.setModel(tabelaClienteModel);
		tabelaCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaCliente.setFillsViewportHeight(true);
		sortTabelaCliente = new TableRowSorter<TabelaClienteModel>(tabelaClienteModel);
		tabelaCliente.setRowSorter(sortTabelaCliente);
		
		sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
		sortKeys.add(new RowSorter.SortKey(TELEFONE, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(EMAIL, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(RUA, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NUMERO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CIDADE, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(ESTADO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(BAIRRO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CEP, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CPF, SortOrder.ASCENDING));
	
		tabelaCliente.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tabelaCliente.getColumnModel().getColumn(CODIGO).setPreferredWidth(20);
		tabelaCliente.getColumnModel().getColumn(NOME).setPreferredWidth(40);
		tabelaCliente.getColumnModel().getColumn(TELEFONE).setPreferredWidth(40);
		tabelaCliente.getColumnModel().getColumn(EMAIL).setPreferredWidth(30);
		tabelaCliente.getColumnModel().getColumn(RUA).setPreferredWidth(50);
		tabelaCliente.getColumnModel().getColumn(NUMERO).setPreferredWidth(20);
		tabelaCliente.getColumnModel().getColumn(CIDADE).setPreferredWidth(40);
		tabelaCliente.getColumnModel().getColumn(ESTADO).setPreferredWidth(40);
		tabelaCliente.getColumnModel().getColumn(BAIRRO).setPreferredWidth(40);
		tabelaCliente.getColumnModel().getColumn(CEP).setPreferredWidth(30);
		tabelaCliente.getColumnModel().getColumn(CPF).setPreferredWidth(30);
		
   		scrollPane.setViewportView(tabelaCliente);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		
				btnInserirCliente = new JButton("");
				btnInserirCliente.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						inserirCliente();
					}
				});
				btnInserirCliente.setIcon(new ImageIcon(Cliente.class.getResource("/imagens/book_add.png")));
				btnInserirCliente.setBounds(528, 5, 74, 31);
				buttonPane.add(btnInserirCliente);
				btnInserirCliente.setFont(new Font("Tahoma", Font.PLAIN, 11));
				
				btnInserirCliente.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_C ) {
							inserirCliente();
						}
						
					}
				});
				
		btnInserirCliente.setMnemonic(KeyEvent.VK_C);
		contentPanel.add(scrollPane);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 781, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
				lblPesquisaCliente = new JLabel("Cliente:");
				lblPesquisaCliente.setBounds(14, 15, 129, 14);
				panel.add(lblPesquisaCliente);
				lblPesquisaCliente.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_P) {
							textFieldBuscaCliente.requestFocus();
						}
					}
				});
				lblPesquisaCliente.setDisplayedMnemonic(KeyEvent.VK_P);
				
						textFieldBuscaCliente = new JTextField();
						textFieldBuscaCliente.setBounds(146, 12, 608, 20);
						panel.add(textFieldBuscaCliente);
						textFieldBuscaCliente.getDocument().addDocumentListener(new DocumentListener() {
							
							@Override
							public void removeUpdate(DocumentEvent e) {
								filtraNomeCliente();
							}
							
							@Override
							public void insertUpdate(DocumentEvent e) {
								filtraNomeCliente();
							}
							
							@Override
							public void changedUpdate(DocumentEvent e) {
								filtraNomeCliente();
							}
						});
						
						textFieldBuscaCliente.setColumns(15);
						lblPesquisaCliente.setLabelFor(textFieldBuscaCliente);

	}



	protected void inserirCliente() {
		ClienteFrame clienteFrame = new ClienteFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaCliente, tabelaClienteModel, 0);
		clienteFrame.setLocationRelativeTo(null);
		clienteFrame.setVisible(true);
	    tabelaClienteModel.fireTableDataChanged();	
	}

	protected void selecionaCliente(MouseEvent e) {
		row = tabelaCliente.getSelectedRow();
		
		 if ( tabelaCliente.getRowSorter() != null ) {
			row =  tabelaCliente.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionacliente() {
		if ( tabelaCliente.getSelectedRow() != -1 && 
			 tabelaCliente.getSelectedRow() < tabelaClienteModel.getRowCount() ) {
			 cliente = new Cliente();
			 setConfirmado(true);
			 cliente = tabelaClienteModel.getCliente(row);
			 dispose();
		} else {
			setConfirmado(false);
		}
		
	}

	private List<Cliente> carregarListaCliente() {
		ClienteService clienteService = new ClienteService();
		listaCliente = clienteService.carregarListaCliente();
		return listaCliente;
	}
	
	private void filtraNomeCliente() {
		RowFilter<TabelaClienteModel, Object> rowFilter = null;
		String filter = textFieldBuscaCliente.getText();
		try {
			rowFilter = RowFilter.regexFilter(filter);
		} catch(PatternSyntaxException e) {
			return;
		}
		sortTabelaCliente.setRowFilter(rowFilter);
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}

}
