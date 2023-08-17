package com.projeto.view.produto;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.projeto.view.produto.ProdutoFrame;
import com.projeto.model.model.Produto;
import com.projeto.model.service.ProdutoService;
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

public class TabelaProduto extends JInternalFrame {
	
	private static final long serialVersionUID = 4860522297398405820L;
	
	//CLASSES
		private ProdutoService produtoService;
		private TabelaProdutoModel tabelaProdutoModel;
		private TableRowSorter<TabelaProdutoModel> sortTabelaProduto;
		
		
		private static final int CODIGO   = 	    0;
		private static final int NOME      =        1;
		private static final int CATEGORIA =        2;
		private static final int QUANTIDADE =       3;
		private static final int PRECO =            4;
		private static final int CODIGO_DE_BARRAS = 5;
		private static final int MARCA =    	    6;
		private static final int MODELO =    	    7;
		private static final int PESO =     		8;
		private static final int GARANTIA =     	9;
		
		private JTextField textFieldBuscarNomeProduto;
		
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
		private JTable tabelaProdutoDesign;
		private JScrollPane scrollPane;
		JComboBox<String> comboBoxRegistrosPorPagina;
		
		//LABELS

		private JLabel lblTotalPaginas;
		private JLabel lblPaginaAtual;
		private JLabel lblTotalRegistros;
		
		
		
		public TabelaProduto() {
			setBounds(new Rectangle(100, 100, 920, 520));
			getContentPane().setBounds(new Rectangle(100, 100, 780, 520));
			initComponents();
			iniciarPaginacao();	//vai mostrar os regs
			createEvents();
			setResizable(false);		//nao deixa o usuario maximizar a tela
		}
		
		
		
		private void iniciarPaginacao() {
				List<Produto> listaProduto = new ArrayList<Produto>();//armazenar as informacoes que vai ser lida pelo usuario
				totalRegistros = buscarTotalRegistroProdutos();
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
					
					listaProduto = carregarListaProduto(paginaAtual, registrosPorPagina);	
					
					tabelaProdutoModel.setListProduto(listaProduto);
					tabelaProdutoDesign.setModel(tabelaProdutoModel);	//adiciona o modelo
					tabelaProdutoDesign.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tabelaProdutoModel.fireTableDataChanged();  //pra atualizar a tabela caregando os dados
					tabelaProdutoDesign.setAutoCreateRowSorter(true); //cada coluna eh autoclassificada, dai clica na coluna e dale
					
					sortTabelaProduto = new TableRowSorter<TabelaProdutoModel>(tabelaProdutoModel);
					tabelaProdutoDesign.setRowSorter(sortTabelaProduto);
					
					tabelaProdutoDesign.getColumnModel().getColumn(CODIGO).setPreferredWidth(11);
					tabelaProdutoDesign.getColumnModel().getColumn(NOME).setPreferredWidth(10);
					tabelaProdutoDesign.getColumnModel().getColumn(CATEGORIA).setPreferredWidth(50);
					tabelaProdutoDesign.getColumnModel().getColumn(QUANTIDADE).setPreferredWidth(50);
					tabelaProdutoDesign.getColumnModel().getColumn(PRECO).setPreferredWidth(20);
					tabelaProdutoDesign.getColumnModel().getColumn(CODIGO_DE_BARRAS).setPreferredWidth(80);
					tabelaProdutoDesign.getColumnModel().getColumn(MARCA).setPreferredWidth(20);
					tabelaProdutoDesign.getColumnModel().getColumn(MODELO).setPreferredWidth(20);
					tabelaProdutoDesign.getColumnModel().getColumn(PESO).setPreferredWidth(10);
					tabelaProdutoDesign.getColumnModel().getColumn(GARANTIA).setPreferredWidth(30);
					
					scrollPane.setViewportView(tabelaProdutoDesign);

		}


		private List<Produto> carregarListaProduto(int paginaAtual, int registrosPorPagina) {
			ProdutoService produtoService = new ProdutoService();
			return produtoService.carregarListaProduto(( ( paginaAtual - 1) * registrosPorPagina),registrosPorPagina);	//busca paginada	
		}
		
		@SuppressWarnings("unused")
		private int buscarTotalRegistroProdutos() {
			return produtoService.countTotalRegistrosProduto();
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
			ProdutoFrame produtoFrame = new ProdutoFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaProdutoDesign, tabelaProdutoModel,0);
			produtoFrame.setLocationRelativeTo(null);
			produtoFrame.setVisible(true);
			
		}

		private void alterarRegistroCadastrado() {
			Integer linha = 0;
			if(tabelaProdutoDesign.getSelectedRow() != -1 && tabelaProdutoDesign.getSelectedRow() < tabelaProdutoModel.getRowCount()) {
				linha = tabelaProdutoDesign.getSelectedRow();
				ProdutoFrame produtoFrame = new ProdutoFrame(new JFrame(),true,ProcessamentoDados.INCLUIR_REGISTRO, tabelaProdutoDesign, tabelaProdutoModel,linha);
				produtoFrame.setLocationRelativeTo(null);
				produtoFrame.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void excluirRegistroCadastrado() {
			Integer linha = 0;
			if(tabelaProdutoDesign.getSelectedRow() != -1 && tabelaProdutoDesign.getSelectedRow() < tabelaProdutoModel.getRowCount()) {
				linha = tabelaProdutoDesign.getSelectedRow();
				ProdutoFrame produtoFrame = new ProdutoFrame(new JFrame(),true,ProcessamentoDados.EXCLUIR_REGISTRO, tabelaProdutoDesign, tabelaProdutoModel,linha);
				produtoFrame.setLocationRelativeTo(null);
				produtoFrame.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void consultarRegistroCadastrado() {
			Integer linha = 0;
			if(tabelaProdutoDesign.getSelectedRow() != -1 && tabelaProdutoDesign.getSelectedRow() < tabelaProdutoModel.getRowCount()) {
				linha = tabelaProdutoDesign.getSelectedRow();
				ProdutoFrame produtoFrame = new ProdutoFrame(new JFrame(),true,ProcessamentoDados.CONSULTAR_REGISTRO, tabelaProdutoDesign, tabelaProdutoModel,linha);
				produtoFrame.setLocationRelativeTo(null);
				produtoFrame.setVisible(true);
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Selecione um registro", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		protected void filtraNomeProduto() {
			RowFilter<TabelaProdutoModel, Object> rowFilter = null;
			String buscarNomeProduto = textFieldBuscarNomeProduto.getText();
			try {
				rowFilter = RowFilter.regexFilter(buscarNomeProduto);
				
			}catch(PatternSyntaxException e){ 
				return;
			}
			sortTabelaProduto.setRowFilter(rowFilter);
			
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked", "rawtypes" })
		private void initComponents() {
			produtoService = new ProdutoService();
			tabelaProdutoModel = new TabelaProdutoModel();
			
			getContentPane().setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Quant. registros por pagina");
			lblNewLabel.setBounds(10, 11, 139, 14);
			getContentPane().add(lblNewLabel);
			
			JLabel lblNewLabel_4 = new JLabel("Busca por nome");
			lblNewLabel_4.setBounds(400, 8, 108, 14);
			getContentPane().add(lblNewLabel_4);
			
			textFieldBuscarNomeProduto = new JTextField();
			textFieldBuscarNomeProduto.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					filtraNomeProduto();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					filtraNomeProduto();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					filtraNomeProduto();
				}
			});
			textFieldBuscarNomeProduto.setColumns(10);
			textFieldBuscarNomeProduto.setBounds(518, 5, 225, 20);
			getContentPane().add(textFieldBuscarNomeProduto);
			
			JPanel panelPaginacao = new JPanel();
			panelPaginacao.setBounds(10, 312, 733, 47);
			getContentPane().add(panelPaginacao);
			panelPaginacao.setLayout(null);
			
			btnPrimeiro = new JButton("");
			btnPrimeiro.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/go-first.png")));
			btnPrimeiro.setBounds(10, 11, 69, 23);
			panelPaginacao.add(btnPrimeiro);
			
			btnAnterior = new JButton("");
			btnAnterior.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/go-previous.png")));
			btnAnterior.setBounds(89, 11, 69, 23);
			panelPaginacao.add(btnAnterior);
			
			btnProximo = new JButton("");
			btnProximo.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/go-next.png")));
			btnProximo.setBounds(168, 11, 69, 23);
			panelPaginacao.add(btnProximo);
			
			btnUltimo = new JButton("");
			btnUltimo.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/go-last.png")));
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
			btnInclusao.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/book_add.png")));
			btnInclusao.setBounds(10, 11, 89, 23);
			panel.add(btnInclusao);
			
			btnAlteracao = new JButton("");
			btnAlteracao.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/book_edit.png")));
			btnAlteracao.setBounds(114, 11, 89, 23);
			panel.add(btnAlteracao);
			
			btnExclusao = new JButton("");
			btnExclusao.setMnemonic(KeyEvent.VK_E);
			btnExclusao.setToolTipText("Excluir registro cadastrado\r\n");
			btnExclusao.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/book_delete.png")));
			btnExclusao.setBounds(220, 11, 89, 23);
			panel.add(btnExclusao);
			
			btnConsulta = new JButton("");
			btnConsulta.setMnemonic(KeyEvent.VK_C);
			btnConsulta.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/search.png")));
			btnConsulta.setBounds(319, 11, 89, 23);
			panel.add(btnConsulta);
			
			btnFechar = new JButton("");
			btnFechar.setToolTipText("Fechar programa");
			btnFechar.setMnemonic(KeyEvent.VK_F);
			btnFechar.setIcon(new ImageIcon(TabelaProduto.class.getResource("/imagens/iconFechar.png")));
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
			
			tabelaProdutoDesign = new JTable();
			scrollPane.setViewportView(tabelaProdutoDesign);
			
			tabelaProdutoDesign = new JTable();
			scrollPane.setViewportView(tabelaProdutoDesign);
			
		}

}
