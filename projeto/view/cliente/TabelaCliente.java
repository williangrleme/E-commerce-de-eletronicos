package com.projeto.view.cliente;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
import com.projeto.util.ProcessamentoDados;

import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

public class TabelaCliente extends JInternalFrame{
	
	//CLASSES
		private ClienteService clienteService;
		private TabelaClienteModel tabelaClienteModel;
		private TableRowSorter<TabelaClienteModel> sortTabelaCliente;
		
		
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
	
		
		private JTextField textFieldBuscarNomeCliente;
		
		//VARIAVEIS 
		private Integer totalRegistros = 0;
		private Integer registrosPorPagina = 5;
		private Integer totalPaginas = 1;
		private Integer paginaAtual = 1;
		
		//BOTOES
		private JButton btnPrimeiro;
		private JButton btnProximo;
		private JButton btnAnterior;
		private JButton btnUltimo;
		private JButton btnInclusao;
		private JButton btnFechar;
		private JButton btnAlteracao;
		private JButton btnExclusao;
		private JButton btnConsulta;
		private JTable tabelaClienteDesign;
		private JScrollPane scrollPane;
		JComboBox<String> comboBoxRegistrosPorPagina;
		
		//LABELS

		private JLabel lblTotalPaginas;
		private JLabel lblPaginaAtual;
		private JLabel lblTotalRegistros;
		

	public TabelaCliente() {
		setTitle("Tabela de Clientes");
		
		setBounds(new Rectangle(100, 100, 800, 520));
		getContentPane().setBounds(new Rectangle(100, 100, 780, 520));
		initComponents();
		iniciarPaginacao();	//vai mostrar os regs
		createEvents();
		setResizable(false);		//nao deixa o usuario maximizar a tela
	}
	

	private void iniciarPaginacao() {
			List<Cliente> listaCliente = new ArrayList<Cliente>();//armazenar as informacoes que vai ser lida pelo usuario
			totalRegistros = buscarTotalRegistrosCliente();
			registrosPorPagina = Integer.valueOf(comboBoxRegistrosPorPagina.getSelectedItem().toString());
			
			Double totalPaginasTabela  = Math.ceil(totalRegistros.doubleValue()/ registrosPorPagina.doubleValue());	//quantas paginas existem
			totalPaginas = totalPaginasTabela.intValue();
			
			//PASSANDO OS VALORES PARA AS LABELS
			lblTotalRegistros.setText(totalRegistros.toString());
			lblTotalPaginas.setText(totalPaginas.toString());
			lblPaginaAtual.setText(paginaAtual.toString());
			

			//LOGICA DE BLOQUEAR OU LIBERAR OS BOTOES
				if(paginaAtual.equals(1)) {
					btnPrimeiro.setEnabled(false);
					btnProximo.setEnabled(false);
				}
				else {
					btnPrimeiro.setEnabled(true);
					btnProximo.setEnabled(true);
				}
				
				if(paginaAtual.equals(totalPaginas)) {
					btnUltimo.setEnabled(false);
					btnProximo.setEnabled(false);
				}
				else {
					btnUltimo.setEnabled(true);
					btnProximo.setEnabled(true);
				}
				
				if (paginaAtual > totalPaginas) {
					paginaAtual = totalPaginas;
				}
				
				listaCliente = carregarListaCliente(paginaAtual, registrosPorPagina);	
				
				tabelaClienteModel.setListCliente(listaCliente);
				tabelaClienteDesign.setModel(tabelaClienteModel);	//adiciona o modelo
				tabelaClienteDesign.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				sortTabelaCliente = new TableRowSorter<TabelaClienteModel>(tabelaClienteModel);
				tabelaClienteDesign.setRowSorter(sortTabelaCliente);
				tabelaClienteModel.fireTableDataChanged();  //pra atualizar a tabela caregando os dados
				tabelaClienteDesign.setAutoCreateRowSorter(true); //cada coluna eh autoclassificada, dai clica na coluna e dale
				
				tabelaClienteDesign.getColumnModel().getColumn(CODIGO).setPreferredWidth(20);
				tabelaClienteDesign.getColumnModel().getColumn(NOME).setPreferredWidth(30);
				tabelaClienteDesign.getColumnModel().getColumn(TELEFONE).setPreferredWidth(40);
				tabelaClienteDesign.getColumnModel().getColumn(EMAIL).setPreferredWidth(30);
				tabelaClienteDesign.getColumnModel().getColumn(RUA).setPreferredWidth(40);
				tabelaClienteDesign.getColumnModel().getColumn(NUMERO).setPreferredWidth(30);
				tabelaClienteDesign.getColumnModel().getColumn(CIDADE).setPreferredWidth(40);
				tabelaClienteDesign.getColumnModel().getColumn(ESTADO).setPreferredWidth(40);
				tabelaClienteDesign.getColumnModel().getColumn(BAIRRO).setPreferredWidth(40);
				tabelaClienteDesign.getColumnModel().getColumn(CEP).setPreferredWidth(20);
				tabelaClienteDesign.getColumnModel().getColumn(CPF).setPreferredWidth(50);
			
				
				scrollPane.setViewportView(tabelaClienteDesign);

	}


	private List<Cliente> carregarListaCliente(int paginaAtual, int registrosPorPagina) {
		ClienteService clienteService = new ClienteService();
		return clienteService.carregarListaCliente(( ( paginaAtual - 1) * registrosPorPagina),registrosPorPagina);	//busca paginada	
	}
	
	@SuppressWarnings("unused")
	private int buscarTotalRegistrosCliente() {
		return clienteService.countTotalRegistrosCliente();
	}
	
	private void createEvents() {
		
		//EVENTO BOTAO PRIMEIRA PAGINA
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = 1;
				iniciarPaginacao();
			}
		});
		
		//EVENTO BOTAO ANTERIOR
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(paginaAtual > 1) {
					paginaAtual = paginaAtual - 1;
					iniciarPaginacao(); 
				}
			}
		});
		
		//EVENTO BOTAO PROXIMO
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(paginaAtual < totalPaginas) {
					paginaAtual = paginaAtual + 1;
					iniciarPaginacao();
				}
			}
		});
		
		//EVENTO BOTAO ULTIMO
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual = totalPaginas;
				iniciarPaginacao();
			}
		});
		
		//EVENTO QUANTIDADED REGISTROS POR PAGINAS
		comboBoxRegistrosPorPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrosPorPagina = Integer.valueOf(comboBoxRegistrosPorPagina.getSelectedItem().toString());
				iniciarPaginacao();
			}
		});
		
		//EVENTO BOTAO INCLUSAO
		btnInclusao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluirNovoRegistro();
			}
		});
		//ATALHO BOTAO INCLUSAO
		btnInclusao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_I) {
					incluirNovoRegistro();
				}
		}
		});
		
		//EVENTO FECHAR
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		//ATALHO BOTAO FECHAR
		btnFechar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F) {
					dispose();
				}
			}
		});
		
		//EVENTO BOTAO ALTERACAO
		btnAlteracao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarRegistroCadastrado();
			}
		});
		
		//ATALHO BOTAO ALTERACAO
		btnAlteracao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A) {
					alterarRegistroCadastrado();
				}
			}
		});
		
		//EVENTO BOTAO EXCLUSAO
		btnExclusao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirRegistroCadastrado();
			}
		});
		//ATALHO BOTAO EXCLUSAO
		btnExclusao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_E) {
					excluirRegistroCadastrado();
				}
			}
		});
		
		//EVENTO BOTAO CONSULTA
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarRegistroCadastrado();
			}
		});
		//ATALHO BOTAO CONSULTA
		btnConsulta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_C) {
					consultarRegistroCadastrado();
				}
			}
		});
	}

	
	private void incluirNovoRegistro() {
		ClienteFrame clienteFrame = new ClienteFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaClienteDesign, tabelaClienteModel,0);
		clienteFrame.setLocationRelativeTo(null);
		clienteFrame.setVisible(true);
		
	}

	private void alterarRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaClienteDesign.getSelectedRow() != -1 && tabelaClienteDesign.getSelectedRow() < tabelaClienteModel.getRowCount()) {
			linha = tabelaClienteDesign.getSelectedRow();
			ClienteFrame clienteFrame = new ClienteFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaClienteDesign, tabelaClienteModel,linha);
			clienteFrame.setLocationRelativeTo(null);
			clienteFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void excluirRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaClienteDesign.getSelectedRow() != -1 && tabelaClienteDesign.getSelectedRow() < tabelaClienteModel.getRowCount()) {
			linha = tabelaClienteDesign.getSelectedRow();
			ClienteFrame clienteFrame = new ClienteFrame(new JFrame(),true,ProcessamentoDados.EXCLUIR_REGISTRO, tabelaClienteDesign, tabelaClienteModel,linha);
			clienteFrame.setLocationRelativeTo(null);
			clienteFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaClienteDesign.getSelectedRow() != -1 && tabelaClienteDesign.getSelectedRow() < tabelaClienteModel.getRowCount()) {
			linha = tabelaClienteDesign.getSelectedRow();
			ClienteFrame clienteFrame = new ClienteFrame(new JFrame(),true,ProcessamentoDados.CONSULTAR_REGISTRO, tabelaClienteDesign, tabelaClienteModel,linha);
			clienteFrame.setLocationRelativeTo(null);
			clienteFrame.setVisible(true);
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected void filtraNomeCliente() {
		RowFilter<TabelaClienteModel, Object> rowFilter = null;
		String buscarNomeCliente = textFieldBuscarNomeCliente.getText();
		try {
			rowFilter = RowFilter.regexFilter(buscarNomeCliente);
			
		}catch(PatternSyntaxException e){ 
			return;
		}
		sortTabelaCliente.setRowFilter(rowFilter);
		
	}
	
	private void initComponents() {
		clienteService = new ClienteService();
		tabelaClienteModel = new TabelaClienteModel();
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Quant. registros por pagina");
		lblNewLabel.setBounds(10, 11, 139, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Busca por nome");
		lblNewLabel_4.setBounds(400, 8, 108, 14);
		getContentPane().add(lblNewLabel_4);
		
		textFieldBuscarNomeCliente = new JTextField();
		textFieldBuscarNomeCliente.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeCliente();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeCliente();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeCliente();
			}
		});
		textFieldBuscarNomeCliente.setColumns(10);
		textFieldBuscarNomeCliente.setBounds(518, 5, 225, 20);
		getContentPane().add(textFieldBuscarNomeCliente);
		
		JPanel panelPaginacao = new JPanel();
		panelPaginacao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelPaginacao.setBounds(10, 312, 733, 47);
		getContentPane().add(panelPaginacao);
		panelPaginacao.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnPrimeiro.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 11, 69, 23);
		panelPaginacao.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAnterior.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(89, 11, 69, 23);
		panelPaginacao.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnProximo.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(168, 11, 69, 23);
		panelPaginacao.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnUltimo.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/go-last.png")));
		btnUltimo.setBounds(247, 11, 69, 23);
		panelPaginacao.add(btnUltimo);
		
		JLabel lblShowPaginaAtual = new JLabel("Pagina atual:");
		lblShowPaginaAtual.setBounds(326, 20, 76, 14);
		panelPaginacao.add(lblShowPaginaAtual);
		
		JLabel lblShowTotalPaginas = new JLabel("Total paginas:");
		lblShowTotalPaginas.setBounds(440, 20, 86, 14);
		panelPaginacao.add(lblShowTotalPaginas);
		
		JLabel lblShowTotalRegistros = new JLabel("Total Registros:");
		lblShowTotalRegistros.setBounds(562, 20, 92, 14);
		panelPaginacao.add(lblShowTotalRegistros);
		
		
		lblTotalPaginas = new JLabel("100");
		lblTotalPaginas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalPaginas.setBounds(521, 20, 26, 14);
		panelPaginacao.add(lblTotalPaginas);
		
		lblPaginaAtual = new JLabel("5");
		lblPaginaAtual.setBounds(412, 20, 32, 14);
		panelPaginacao.add(lblPaginaAtual);

		lblTotalRegistros = new JLabel("500");
		lblTotalRegistros.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalRegistros.setBounds(648, 20, 26, 14);
		panelPaginacao.add(lblTotalRegistros);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 380, 733, 47);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnInclusao = new JButton("");
		btnInclusao.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_add.png")));
		btnInclusao.setBounds(10, 11, 89, 23);
		panel.add(btnInclusao);
		
		btnAlteracao = new JButton("");
		btnAlteracao.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlteracao.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_edit.png")));
		btnAlteracao.setBounds(114, 11, 89, 23);
		panel.add(btnAlteracao);
		
		btnExclusao = new JButton("");
		btnExclusao.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setToolTipText("Excluir registro cadastrado\r\n");
		btnExclusao.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setBounds(220, 11, 89, 23);
		panel.add(btnExclusao);
		
		btnConsulta = new JButton("");
		btnConsulta.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnConsulta.setMnemonic(KeyEvent.VK_C);
		btnConsulta.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/search.png")));
		btnConsulta.setBounds(319, 11, 89, 23);
		panel.add(btnConsulta);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaCliente.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(418, 11, 89, 23);
		panel.add(btnFechar);
		
		comboBoxRegistrosPorPagina = new JComboBox<String>();
		comboBoxRegistrosPorPagina.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "20", "30", "40", "50"}));
		comboBoxRegistrosPorPagina.setBounds(159, 7, 45, 22);
		getContentPane().add(comboBoxRegistrosPorPagina);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 36, 733, 265);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 713, 243);
		panel_1.add(scrollPane);
		
		tabelaClienteDesign = new JTable();
		scrollPane.setViewportView(tabelaClienteDesign);
		
		tabelaClienteDesign = new JTable();
		scrollPane.setViewportView(tabelaClienteDesign);
		
	}

	public TabelaClienteModel getTabelaClienteModel() {
		return tabelaClienteModel;
	}

	public void setTabelaClienteModel(TabelaClienteModel tabelaClienteModel) {
		this.tabelaClienteModel = tabelaClienteModel;
	}

}
