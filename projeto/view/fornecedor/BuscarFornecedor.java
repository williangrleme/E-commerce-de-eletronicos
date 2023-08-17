package com.projeto.view.fornecedor;

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

import com.projeto.model.model.Fornecedor;
import com.projeto.model.service.FornecedorService;
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
public class BuscarFornecedor extends JDialog {

	private static final long serialVersionUID = 6490954951618300209L;
	
	private static final int CODIGO =   0;
	private static final int NOME =     1;
	private static final int CNPJ =  	2;
	private static final int CPF = 		3;
	private static final int EMAIL =  	4;
	private static final int TELEFONE = 5;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblPesquisaFornecedor;
	private JTextField textFieldBuscaFornecedor;
	private JScrollPane scrollPane;
	private JTable tabelaFornecedor;

	private TabelaFornecedorModel tabelaFornecedorModel;
	private TableRowSorter<TabelaFornecedorModel> sortTabelaFornecedor;
	private List<Fornecedor> listaFornecedor;
	private List<RowSorter.SortKey> sortKeys;
	
   	private boolean isConfirmado; 
	
	private Fornecedor fornecedor;
	
	private JButton btnInserirFornecedor;
	
	private int row = 0;
	private JPanel buttonPane;
	private JPanel panel;
	
	
	public BuscarFornecedor(JFrame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		iniciarDados();
		
	}
	
	private void iniciarDados(){
		listaFornecedor = new ArrayList<Fornecedor>();
	}
	

	private void initComponents() {
		setTitle("Busca fornecedor");
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
				okButton.setIcon(new ImageIcon(BuscarFornecedor.class.getResource("/imagens/ok.png")));
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
				okButton.setBounds(612, 5, 74, 31);
				okButton.setMnemonic(KeyEvent.VK_O);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selecionafornecedor();
					}
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("");
				cancelButton.setIcon(new ImageIcon(BuscarFornecedor.class.getResource("/imagens/iconFechar.png")));
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

		tabelaFornecedorModel = new TabelaFornecedorModel();
	    listaFornecedor = carregarListaFornecedor();
		tabelaFornecedorModel.setListFornecedor(listaFornecedor);
		tabelaFornecedor = new JTable();
		tabelaFornecedor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		tabelaFornecedor.setModel(tabelaFornecedorModel);
		tabelaFornecedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaFornecedor.setFillsViewportHeight(true);
		sortTabelaFornecedor = new TableRowSorter<TabelaFornecedorModel>(tabelaFornecedorModel);
		tabelaFornecedor.setRowSorter(sortTabelaFornecedor);
		
		sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(CODIGO, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(NOME, SortOrder.ASCENDING));	
		sortKeys.add(new RowSorter.SortKey(CNPJ, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(CPF, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(EMAIL, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(TELEFONE, SortOrder.ASCENDING));
		
		tabelaFornecedor.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		tabelaFornecedor.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
		tabelaFornecedor.getColumnModel().getColumn(NOME).setPreferredWidth(100);
		tabelaFornecedor.getColumnModel().getColumn(CNPJ).setPreferredWidth(100);
		tabelaFornecedor.getColumnModel().getColumn(CPF).setPreferredWidth(100);
		tabelaFornecedor.getColumnModel().getColumn(EMAIL).setPreferredWidth(100);
		tabelaFornecedor.getColumnModel().getColumn(TELEFONE).setPreferredWidth(100);

		
   		scrollPane.setViewportView(tabelaFornecedor);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.setLayout(null);
		contentPanel.add(buttonPane);
		
				btnInserirFornecedor = new JButton("");
				btnInserirFornecedor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						inserirFornecedor();
					}
				});
				btnInserirFornecedor.setIcon(new ImageIcon(BuscarFornecedor.class.getResource("/imagens/book_add.png")));
				btnInserirFornecedor.setBounds(528, 5, 74, 31);
				buttonPane.add(btnInserirFornecedor);
				btnInserirFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 11));
				
				btnInserirFornecedor.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_C ) {
							inserirFornecedor();
						}
						
					}
				});
				
		btnInserirFornecedor.setMnemonic(KeyEvent.VK_C);
		contentPanel.add(scrollPane);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(19, 16, 781, 44);
		contentPanel.add(panel);
		panel.setLayout(null);
		
				lblPesquisaFornecedor = new JLabel("Fornecedor:");
				lblPesquisaFornecedor.setBounds(14, 15, 129, 14);
				panel.add(lblPesquisaFornecedor);
				lblPesquisaFornecedor.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_P) {
							textFieldBuscaFornecedor.requestFocus();
						}
					}
				});
				lblPesquisaFornecedor.setDisplayedMnemonic(KeyEvent.VK_P);
				
						textFieldBuscaFornecedor = new JTextField();
						textFieldBuscaFornecedor.setBounds(146, 12, 608, 20);
						panel.add(textFieldBuscaFornecedor);
						textFieldBuscaFornecedor.getDocument().addDocumentListener(new DocumentListener() {
							
							@Override
							public void removeUpdate(DocumentEvent e) {
								filtraNomeFornecedor();
							}
							
							@Override
							public void insertUpdate(DocumentEvent e) {
								filtraNomeFornecedor();
							}
							
							@Override
							public void changedUpdate(DocumentEvent e) {
								filtraNomeFornecedor();
							}
						});
						
						textFieldBuscaFornecedor.setColumns(15);
						lblPesquisaFornecedor.setLabelFor(textFieldBuscaFornecedor);

	}



	protected void inserirFornecedor() {
		FornecedorFrame fornecedorFrame = new FornecedorFrame(new JFrame(), true, ProcessamentoDados.INCLUIR_REGISTRO, tabelaFornecedor, tabelaFornecedorModel, 0);
		fornecedorFrame.setLocationRelativeTo(null);
		fornecedorFrame.setVisible(true);
	    tabelaFornecedorModel.fireTableDataChanged();	
	}

	protected void selecionaFornecedor(MouseEvent e) {
		row = tabelaFornecedor.getSelectedRow();
		
		 if ( tabelaFornecedor.getRowSorter() != null ) {
			row =  tabelaFornecedor.getRowSorter().convertRowIndexToModel(row);
		 }
		
	}

	protected void selecionafornecedor() {
		if ( tabelaFornecedor.getSelectedRow() != -1 && 
			 tabelaFornecedor.getSelectedRow() < tabelaFornecedorModel.getRowCount() ) {
			 fornecedor = new Fornecedor();
			 setConfirmado(true);
			 fornecedor = tabelaFornecedorModel.getFornecedor(row);
			 dispose();
		} else {
			setConfirmado(false);
		}
		
	}

	private List<Fornecedor> carregarListaFornecedor() {
		FornecedorService fornecedorService = new FornecedorService();
		listaFornecedor = fornecedorService.carregarListaFornecedor();
		return listaFornecedor;
	}
	
	
	private void filtraNomeFornecedor() {
		RowFilter<TabelaFornecedorModel, Object> rowFilter = null;
		String filter = textFieldBuscaFornecedor.getText();
		try {
			rowFilter = RowFilter.regexFilter(filter);
		} catch(PatternSyntaxException e) {
			return;
		}
		sortTabelaFornecedor.setRowFilter(rowFilter);
		
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean isConfirmado() {
		return isConfirmado;
	}

	public void setConfirmado(boolean isConfirmado) {
		this.isConfirmado = isConfirmado;
	}


}