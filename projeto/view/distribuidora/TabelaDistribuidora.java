package com.projeto.view.distribuidora;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.projeto.view.distribuidora.DistribuidoraFrame;
import com.projeto.model.model.Distribuidora;
import com.projeto.model.service.DistribuidoraService;
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

public class TabelaDistribuidora extends JInternalFrame{

	//CLASSES
		private DistribuidoraService distribuidoraService;
		private TabelaDistribuidoraModel tabelaDistribuidoraModel;
		private TableRowSorter<TabelaDistribuidoraModel> sortTabelaDistribuidora;
		
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
		
private JTextField textFieldBuscarNomeDistribuidora;
		
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
		private JTable tabelaDistribuidoraDesign;
		private JScrollPane scrollPane;
		JComboBox<String> comboBoxRegistrosPorPagina;
		
		//LABELS

		private JLabel lblTotalPaginas;
		private JLabel lblPaginaAtual;
		private JLabel lblTotalRegistros;
		

	public TabelaDistribuidora() {
		setBounds(new Rectangle(100, 100, 920, 520));
		getContentPane().setBounds(new Rectangle(100, 100, 780, 520));
		initComponents();
		iniciarPaginacao();	//vai mostrar os regs
		createEvents();
		setResizable(false);		//nao deixa o usuario maximizar a tela
	}
	
	private void iniciarPaginacao() {
		List<Distribuidora> listaDistribuidora = new ArrayList<Distribuidora>();//armazenar as informacoes que vai ser lida pelo usuario
		totalRegistros = buscarTotalRegistroDistribuidora();
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
			
			listaDistribuidora = carregarListaDistribuidora(paginaAtual, registrosPorPagina);	
			
			tabelaDistribuidoraModel.setListDistribuidora(listaDistribuidora);
			tabelaDistribuidoraDesign.setModel(tabelaDistribuidoraModel);	//adiciona o modelo
			tabelaDistribuidoraDesign.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaDistribuidoraModel.fireTableDataChanged();  //pra atualizar a tabela caregando os dados
			tabelaDistribuidoraDesign.setAutoCreateRowSorter(true); //cada coluna eh autoclassificada, dai clica na coluna e dale
			
			sortTabelaDistribuidora = new TableRowSorter<TabelaDistribuidoraModel>(tabelaDistribuidoraModel);
			tabelaDistribuidoraDesign.setRowSorter(sortTabelaDistribuidora);
			
			tabelaDistribuidoraDesign.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(NOME).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(EMAIL).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(TELEFONE).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(RUA).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(NUMERO).setPreferredWidth(11);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(CIDADE).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(ESTADO).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(BAIRRO).setPreferredWidth(50);
			tabelaDistribuidoraDesign.getColumnModel().getColumn(CEP).setPreferredWidth(11);
	
			scrollPane.setViewportView(tabelaDistribuidoraDesign);

}

	private List<Distribuidora> carregarListaDistribuidora(int paginaAtual, int registrosPorPagina) {
		DistribuidoraService DistribuidoraService = new DistribuidoraService();
		return distribuidoraService.carregarListaDistribuidora(( ( paginaAtual - 1) * registrosPorPagina),registrosPorPagina);	//busca paginada	
	}
	
	@SuppressWarnings("unused")
	private int buscarTotalRegistroDistribuidora() {
		return distribuidoraService.countTotalRegistrosDistribuidora();
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
		DistribuidoraFrame distribuidoraFrame = new DistribuidoraFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaDistribuidoraDesign, tabelaDistribuidoraModel,0);
		distribuidoraFrame.setLocationRelativeTo(null);
		distribuidoraFrame.setVisible(true);
		
	}

	private void alterarRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaDistribuidoraDesign.getSelectedRow() != -1 && tabelaDistribuidoraDesign.getSelectedRow() < tabelaDistribuidoraModel.getRowCount()) {
			linha = tabelaDistribuidoraDesign.getSelectedRow();
			DistribuidoraFrame distribuidoraFrame = new DistribuidoraFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaDistribuidoraDesign, tabelaDistribuidoraModel,linha);
			distribuidoraFrame.setLocationRelativeTo(null);
			distribuidoraFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void excluirRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaDistribuidoraDesign.getSelectedRow() != -1 && tabelaDistribuidoraDesign.getSelectedRow() < tabelaDistribuidoraModel.getRowCount()) {
			linha = tabelaDistribuidoraDesign.getSelectedRow();
			DistribuidoraFrame distribuidoraFrame = new DistribuidoraFrame(new JFrame(),true,ProcessamentoDados.EXCLUIR_REGISTRO, tabelaDistribuidoraDesign, tabelaDistribuidoraModel,linha);
			distribuidoraFrame.setLocationRelativeTo(null);
			distribuidoraFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void consultarRegistroCadastrado() {
		Integer linha = 0;
		if(tabelaDistribuidoraDesign.getSelectedRow() != -1 && tabelaDistribuidoraDesign.getSelectedRow() < tabelaDistribuidoraModel.getRowCount()) {
			linha = tabelaDistribuidoraDesign.getSelectedRow();
			DistribuidoraFrame distribuidoraFrame = new DistribuidoraFrame(new JFrame(),true,ProcessamentoDados.CONSULTAR_REGISTRO, tabelaDistribuidoraDesign, tabelaDistribuidoraModel,linha);
			distribuidoraFrame.setLocationRelativeTo(null);
			distribuidoraFrame.setVisible(true);
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected void filtraNomeDistribuidora() {
		RowFilter<TabelaDistribuidoraModel, Object> rowFilter = null;
		String buscarNomeDistribuidora = textFieldBuscarNomeDistribuidora.getText();
		try {
			rowFilter = RowFilter.regexFilter(buscarNomeDistribuidora);
			
		}catch(PatternSyntaxException e){ 
			return;
		}
		sortTabelaDistribuidora.setRowFilter(rowFilter);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "rawtypes" })
	private void initComponents() {
		distribuidoraService = new DistribuidoraService();
		tabelaDistribuidoraModel = new TabelaDistribuidoraModel();
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Quant. registros por pagina");
		lblNewLabel.setBounds(10, 11, 139, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("Busca por nome");
		lblNewLabel_4.setBounds(400, 8, 108, 14);
		getContentPane().add(lblNewLabel_4);
		
		textFieldBuscarNomeDistribuidora = new JTextField();
		textFieldBuscarNomeDistribuidora.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtraNomeDistribuidora();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtraNomeDistribuidora();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filtraNomeDistribuidora();
			}
		});
		textFieldBuscarNomeDistribuidora.setColumns(10);
		textFieldBuscarNomeDistribuidora.setBounds(518, 5, 225, 20);
		getContentPane().add(textFieldBuscarNomeDistribuidora);
		
		JPanel panelPaginacao = new JPanel();
		panelPaginacao.setBounds(10, 312, 733, 47);
		getContentPane().add(panelPaginacao);
		panelPaginacao.setLayout(null);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/go-first.png")));
		btnPrimeiro.setBounds(10, 11, 69, 23);
		panelPaginacao.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/go-previous.png")));
		btnAnterior.setBounds(89, 11, 69, 23);
		panelPaginacao.add(btnAnterior);
		
		btnProximo = new JButton("");
		btnProximo.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/go-next.png")));
		btnProximo.setBounds(168, 11, 69, 23);
		panelPaginacao.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/go-last.png")));
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
		panel.setBounds(10, 380, 733, 47);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnInclusao = new JButton("");
		btnInclusao.setMnemonic(KeyEvent.VK_I);
		btnInclusao.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/book_add.png")));
		btnInclusao.setBounds(10, 11, 89, 23);
		panel.add(btnInclusao);
		
		btnAlteracao = new JButton("");
		btnAlteracao.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/book_edit.png")));
		btnAlteracao.setBounds(114, 11, 89, 23);
		panel.add(btnAlteracao);
		
		btnExclusao = new JButton("");
		btnExclusao.setMnemonic(KeyEvent.VK_E);
		btnExclusao.setToolTipText("Excluir registro cadastrado\r\n");
		btnExclusao.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/book_delete.png")));
		btnExclusao.setBounds(220, 11, 89, 23);
		panel.add(btnExclusao);
		
		btnConsulta = new JButton("");
		btnConsulta.setMnemonic(KeyEvent.VK_C);
		btnConsulta.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/search.png")));
		btnConsulta.setBounds(319, 11, 89, 23);
		panel.add(btnConsulta);
		
		btnFechar = new JButton("");
		btnFechar.setToolTipText("Fechar programa");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.setIcon(new ImageIcon(TabelaDistribuidora.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(418, 11, 89, 23);
		panel.add(btnFechar);
		
		comboBoxRegistrosPorPagina = new JComboBox<String>();
		comboBoxRegistrosPorPagina.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "20", "30", "40", "50"}));
		comboBoxRegistrosPorPagina.setBounds(159, 7, 45, 22);
		getContentPane().add(comboBoxRegistrosPorPagina);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 36, 888, 265);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 868, 243);
		panel_1.add(scrollPane);
		
		tabelaDistribuidoraDesign = new JTable();
		scrollPane.setViewportView(tabelaDistribuidoraDesign);
		
		tabelaDistribuidoraDesign = new JTable();
		scrollPane.setViewportView(tabelaDistribuidoraDesign);
		
	}
}
