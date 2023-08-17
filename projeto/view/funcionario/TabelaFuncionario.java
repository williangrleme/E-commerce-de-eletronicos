package com.projeto.view.funcionario;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.projeto.model.model.Funcionario;
import com.projeto.model.service.FuncionarioService;
import com.projeto.util.ProcessamentoDados;


public class TabelaFuncionario extends JInternalFrame {
	
	//CLASSES
	private FuncionarioService funcionarioService;
	private TabelaFuncionarioModel tabelaFuncionarioModel;
	private TableRowSorter<TabelaFuncionarioModel> sortTabelaFuncionario;
	
	private static final int CODIGO =   		0;
	private static final int NOME =     		1;
	private static final int TELEFONE =     	2;
	private static final int CARGO =     		3;
	private static final int CPF =     			4;
	private static final int GENERO =     		5;
	private static final int SALARIO =     		6;
	private static final int DATANASCIMENTO =   7;
	private static final int RUA =     			8;
	private static final int NUMERO =     		9;
	private static final int CIDADE =     		10;
	private static final int ESTADO =     		11;
	private static final int BAIRRO =     		12;
	private static final int CEP =     			13;

private JTextField textFieldBuscarNomeFuncionario;
	
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
	private JTable tabelaFuncionarioDesign;
	private JScrollPane scrollPane;
	JComboBox<String> comboBoxRegistrosPorPagina;
	
	//LABELS

	private JLabel lblTotalPaginas;
	private JLabel lblPaginaAtual;
	private JLabel lblTotalRegistros;
	
	
	
	public TabelaFuncionario() {
		setTitle("Tabela de Funcionarios");
		
		setBounds(new Rectangle(100, 100, 800, 520));
		getContentPane().setBounds(new Rectangle(100, 100, 780, 520));
		initComponents();
		iniciarPaginacao();	//vai mostrar os regs
		createEvents();
		setResizable(false);		//nao deixa o usuario maximizar a tela
		
	}
	
	
	
	private void iniciarPaginacao() {
			List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();//armazenar as informacoes que vai ser lida pelo usuario
			totalRegistros = buscarTotalRegistrosFuncionario();
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
				
				listaFuncionario = carregarListaFuncionario(paginaAtual, registrosPorPagina);	
				
				tabelaFuncionarioModel.setListFuncionario(listaFuncionario);
				tabelaFuncionarioDesign.setModel(tabelaFuncionarioModel);	//adiciona o modelo
				tabelaFuncionarioDesign.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				sortTabelaFuncionario = new TableRowSorter<TabelaFuncionarioModel>(tabelaFuncionarioModel);
				tabelaFuncionarioDesign.setRowSorter(sortTabelaFuncionario);
				tabelaFuncionarioModel.fireTableDataChanged();  //pra atualizar a tabela caregando os dados
				tabelaFuncionarioDesign.setAutoCreateRowSorter(true); //cada coluna eh autoclassificada, dai clica na coluna e dale
				
				tabelaFuncionarioDesign.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
				tabelaFuncionarioDesign.getColumnModel().getColumn(NOME).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(TELEFONE).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(CARGO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(CPF).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(GENERO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(SALARIO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(DATANASCIMENTO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(RUA).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(NUMERO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(CIDADE).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(ESTADO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(BAIRRO).setPreferredWidth(30);
				tabelaFuncionarioDesign.getColumnModel().getColumn(CEP).setPreferredWidth(30);
				

				
				scrollPane.setViewportView(tabelaFuncionarioDesign);

	}


	private List<Funcionario> carregarListaFuncionario(int paginaAtual, int registrosPorPagina) {
		FuncionarioService funcionarioService = new FuncionarioService();
		return funcionarioService.carregarListaFuncionario(( ( paginaAtual - 1) * registrosPorPagina),registrosPorPagina);	//busca paginada	
	}
	
	
	private int buscarTotalRegistrosFuncionario() {
		return funcionarioService.countTotalRegistrosFuncionario();
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
		FuncionarioFrame funcionarioFrame = new FuncionarioFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaFuncionarioDesign, tabelaFuncionarioModel,0);
		funcionarioFrame.setLocationRelativeTo(null);
		funcionarioFrame.setVisible(true);
		
	}

	private void alterarRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaFuncionarioDesign.getSelectedRow() != -1 && tabelaFuncionarioDesign.getSelectedRow() < tabelaFuncionarioModel.getRowCount()) {
			linha = tabelaFuncionarioDesign.getSelectedRow();
			FuncionarioFrame funcionarioFrame = new FuncionarioFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaFuncionarioDesign, tabelaFuncionarioModel,linha);
			funcionarioFrame.setLocationRelativeTo(null);
			funcionarioFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void excluirRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaFuncionarioDesign.getSelectedRow() != -1 && tabelaFuncionarioDesign.getSelectedRow() < tabelaFuncionarioModel.getRowCount()) {
			linha = tabelaFuncionarioDesign.getSelectedRow();
			FuncionarioFrame funcionarioFrame = new FuncionarioFrame(new JFrame(),true,ProcessamentoDados.EXCLUIR_REGISTRO, tabelaFuncionarioDesign, tabelaFuncionarioModel,linha);
			funcionarioFrame.setLocationRelativeTo(null);
			funcionarioFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaFuncionarioDesign.getSelectedRow() != -1 && tabelaFuncionarioDesign.getSelectedRow() < tabelaFuncionarioModel.getRowCount()) {
			linha = tabelaFuncionarioDesign.getSelectedRow();
			FuncionarioFrame funcionarioFrame = new FuncionarioFrame(new JFrame(),true,ProcessamentoDados.CONSULTAR_REGISTRO, tabelaFuncionarioDesign, tabelaFuncionarioModel,linha);
			funcionarioFrame.setLocationRelativeTo(null);
			funcionarioFrame.setVisible(true);
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected void filtraNomeFuncionario() {
		RowFilter<TabelaFuncionarioModel, Object> rowFilter = null;
		String buscarNomeFuncionario = textFieldBuscarNomeFuncionario.getText();
		try {
			rowFilter = RowFilter.regexFilter(buscarNomeFuncionario);
			
		}catch(PatternSyntaxException e){ 
			return;
		}
		sortTabelaFuncionario.setRowFilter(rowFilter);
		
	}
	
	private void initComponents() {
		funcionarioService = new FuncionarioService();
		tabelaFuncionarioModel = new TabelaFuncionarioModel();
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Quant. registros por pagina");
		lblNewLabel.setBounds(10, 11, 139, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Busca por nome");
		lblNewLabel_4.setBounds(400, 8, 108, 14);
		getContentPane().add(lblNewLabel_4);
		
		textFieldBuscarNomeFuncionario = new JTextField();
		textFieldBuscarNomeFuncionario.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeFuncionario();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeFuncionario();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeFuncionario();
			}
		});
		textFieldBuscarNomeFuncionario.setColumns(10);
		textFieldBuscarNomeFuncionario.setBounds(518, 5, 225, 20);
		getContentPane().add(textFieldBuscarNomeFuncionario);
		
		JPanel panelPaginacao = new JPanel();
		panelPaginacao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelPaginacao.setBounds(10, 312, 733, 47);
		getContentPane().add(panelPaginacao);
		panelPaginacao.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnPrimeiro.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 11, 69, 23);
		panelPaginacao.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAnterior.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(89, 11, 69, 23);
		panelPaginacao.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnProximo.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(168, 11, 69, 23);
		panelPaginacao.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnUltimo.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/go-last.png")));
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
		btnInclusao.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/book_add.png")));
		btnInclusao.setBounds(10, 11, 89, 23);
		panel.add(btnInclusao);
		
		btnAlteracao = new JButton("");
		btnAlteracao.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlteracao.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/book_edit.png")));
		btnAlteracao.setBounds(114, 11, 89, 23);
		panel.add(btnAlteracao);
		
		btnExclusao = new JButton("");
		btnExclusao.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setToolTipText("Excluir registro cadastrado\r\n");
		btnExclusao.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setBounds(220, 11, 89, 23);
		panel.add(btnExclusao);
		
		btnConsulta = new JButton("");
		btnConsulta.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnConsulta.setMnemonic(KeyEvent.VK_C);
		btnConsulta.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/search.png")));
		btnConsulta.setBounds(319, 11, 89, 23);
		panel.add(btnConsulta);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaFuncionario.class.getResource("/imagens/iconFechar.png")));
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
		
		tabelaFuncionarioDesign = new JTable();
		scrollPane.setViewportView(tabelaFuncionarioDesign);
		
		tabelaFuncionarioDesign = new JTable();
		scrollPane.setViewportView(tabelaFuncionarioDesign);
		
	}

	public TabelaFuncionarioModel getTabelaFuncionarioModel() {
		return tabelaFuncionarioModel;
	}

	public void setTabelaFuncionarioModel(TabelaFuncionarioModel tabelaFuncionarioModel) {
		this.tabelaFuncionarioModel = tabelaFuncionarioModel;
	}
}
