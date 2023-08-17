  package com.projeto.view.produto;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.projeto.model.model.Distribuidora;
import com.projeto.model.model.Fornecedor;
import com.projeto.model.model.Produto;
import com.projeto.model.service.FornecedorService;
import com.projeto.model.service.ProdutoService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.distribuidora.BuscarDistribuidora;
import com.projeto.view.fornecedor.BuscarFornecedor;
import com.projeto.view.fornecedor.FornecedorFrame;
import com.projeto.view.fornecedor.TabelaFornecedorModel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProdutoFrame extends JDialog {
	private static final long serialVersionUID = -5420856984724447760L;
	
	private JPanel contentPane;
	private JButton btnIncluir;
	private JTextField textFieldNome;
	private JTextField textFieldCategoria;
	private JTextField textFieldQuantidade;
	
	private JButton btnFechar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnBuscaFornecedor;
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaProdutoDesign;
	
	private JLabel lblShowErroNome;
	private JLabel lblShowErroCategoria;
	private JLabel lblShowErroQuantidade;
	private JLabel lblShowErroPreco;
	private JLabel lblShowErroCodigoDeBarras;
	private JLabel lblShowErroMarca;
	private JLabel lblShowErroModelo;
	private JLabel lblShowErroPeso;
	private JLabel lblShowErroGarantia;
	
	private TabelaProdutoModel tabelaProdutoModel;
	
	ProdutoService produtoService;
	private Produto produto;
	private JTextField textFieldPreco;
	private JTextField textFieldCodigoDeBarras;
	private JTextField textFieldMarca;
	private JTextField textFieldModelo;
	private JLabel lblPeso;
	private JTextField textFieldPeso;
	private JLabel lblGarantia;
	private JTextField textFieldGarantia;
	private JTextField textFieldFornecedor;
	private int codigoFornecedor = 0;
	private Fornecedor fornecedor;
	private Distribuidora distribuidora;

		
	public ProdutoFrame(JFrame frame, boolean modal, int acao, JTable tabelaProdutoDesign, TabelaProdutoModel tabelaProdutoModel, int linha) {
		
		super(frame,modal);
		setResizable(false);
		this.setLocationRelativeTo(null);
	
		this.acao = acao;
		this.tabelaProdutoDesign = tabelaProdutoDesign;
		this.tabelaProdutoModel = tabelaProdutoModel;
		this.linha = linha;
		
		produtoService = new ProdutoService();
		produto = new Produto();
		
		initComponents();
		createEvents(); 
		configuraAcao();
		setResizable(false);		//nao deixa o usuario maximizar a tela
		this.setLocationRelativeTo(null);	//centraliza a tela
		
		
		//da o foco pro nome
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				textFieldNome.requestFocus();
			}
		});
		 
	}
	
	
	private void configuraAcao() {
		if(this.acao == ProcessamentoDados.INCLUIR_REGISTRO) {
			btnIncluir.setVisible(true);
			btnAlterar.setVisible(false);
			btnExcluir.setVisible(false);
		}
		else {
			if(this.acao == ProcessamentoDados.ALTERAR_REGISTRO) {
				btnIncluir.setVisible(false);
				btnAlterar.setVisible(true);
				btnExcluir.setVisible(false);
				buscarProduto();
			}
			else {
				if(this.acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnIncluir.setVisible(false);
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(true);
					buscarProduto();
				}
				else {
					if(this.acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnIncluir.setVisible(false);
						btnAlterar.setVisible(false);
						btnExcluir.setVisible(false);
						buscarProduto();
					}
				}
			}
		}
	}
	
	private void buscarProduto(){
		produto = new Produto();
		produto = this.tabelaProdutoModel.getProduto(this.linha);
		textFieldNome.setText(produto.getNome());	
		textFieldCategoria.setText(produto.getCategoria());
		textFieldQuantidade.setText(produto.getQuantidade().toString());
		textFieldPreco.setText(produto.getPreco().toString());
		textFieldCodigoDeBarras.setText(produto.getCodigoBarras().toString());
		textFieldMarca.setText(produto.getMarca());
		textFieldModelo.setText(produto.getModelo());
		textFieldPeso.setText(produto.getPeso().toString());
		textFieldGarantia.setText(produto.getGarantia());
		
		
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");	
		textFieldCategoria.setText("");
		textFieldQuantidade.setText("");
		textFieldPreco.setText("");
		textFieldCodigoDeBarras.setText("");
		textFieldMarca.setText("");
		textFieldModelo.setText("");
		textFieldPeso.setText("");
		textFieldGarantia.setText("");
	}
	
	//EVENTOS
	private void createEvents() {
		
		//TIRAR O AVISO DE ERRO DO NOME
		textFieldNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNome.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DE ERRO DA CATEGORIA
		textFieldCategoria.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCategoria.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DE ERRO DA QUANTIDADE
		textFieldQuantidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroQuantidade.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DE ERRO DO PRECO
				textFieldPreco.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						lblShowErroPreco.setVisible(false);
					}
				});

		//TIRAR O AVISO DE ERRO DO CODIGO DE BARRAS
				textFieldCodigoDeBarras.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						lblShowErroCodigoDeBarras.setVisible(false);
					}
				});
			
		//TIRAR O AVISO DE ERRO DA MARCA
				textFieldMarca.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						lblShowErroMarca.setVisible(false);
					}
				});
				
		//TIRAR O AVISO DE ERRO DO MODELO
				textFieldModelo.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						lblShowErroModelo.setVisible(false);
					}
				});
				
		//TIRAR O AVISO DE ERRO DO PESO
				textFieldPeso.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						lblShowErroPeso.setVisible(false);
					}
				});
				
		//TIRAR O AVISO DE ERRO DA QUANTIDADE
				textFieldGarantia.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						lblShowErroGarantia.setVisible(false);
					}
				});		
		
		//TECLA VAI PRO PROXIMO
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldCategoria.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldCategoria.requestFocus();
				}
			}
		});
		//TECLA VAI PRO PROXIMO
		textFieldCategoria.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldQuantidade.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldQuantidade.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
				textFieldQuantidade.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textFieldPreco.requestFocus();
						}
						if(e.getKeyCode() == KeyEvent.VK_TAB) {
							textFieldPreco.requestFocus();
						}
					}
				});
				
		//TECLA VAI PRO PROXIMO
				textFieldPreco.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textFieldCodigoDeBarras.requestFocus();
						}
						if(e.getKeyCode() == KeyEvent.VK_TAB) {
							textFieldCodigoDeBarras.requestFocus();
						}
					}
				});
				
		//TECLA VAI PRO PROXIMO
				textFieldCodigoDeBarras.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textFieldMarca.requestFocus();
						}
						if(e.getKeyCode() == KeyEvent.VK_TAB) {
							textFieldMarca.requestFocus();
						}
					}
				});
				
		//TECLA VAI PRO PROXIMO
				textFieldMarca.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textFieldModelo.requestFocus();
						}
						if(e.getKeyCode() == KeyEvent.VK_TAB) {
							textFieldModelo.requestFocus();
						}
					}
				});
			
		//TECLA VAI PRO PROXIMO
				textFieldModelo.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textFieldPeso.requestFocus();
						}
						if(e.getKeyCode() == KeyEvent.VK_TAB) {
							textFieldPeso.requestFocus();
						}
					}
				});
				
		//TECLA VAI PRO PROXIMO
				textFieldPeso.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER) {
							textFieldGarantia.requestFocus();
						}
						if(e.getKeyCode() == KeyEvent.VK_TAB) {
							textFieldGarantia.requestFocus();
						}
					}
				});
		
		
		//EVENTO FECHAR
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//EVENTO INCLUIR 
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosProduto();
					incluirProduto();
				}				
			}
		});
		
		//EVENTO ALTERAR
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosProduto();
					alterarProduto();
				}		
			}
		});
		
		//EVENTO EXCLUIR
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProduto(); 
			}
		});
		
		//EVENTO BUSCAR FORNECEDOR
		btnBuscaFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarFornecedor();
			}
		});
		
		//ATALHO BUSCAR FORNECEDOR
		btnBuscaFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_D) {
					buscarFornecedor();
				}
			}
		});
		
	}
	
	//METODO DE INCLUIR
	private void incluirProduto() {
		produtoService.save(produto); //salva no BD
		limparDadosDigitacao();
		tabelaProdutoModel.saveProduto(produto);	//salva na Tabela
		tabelaProdutoDesign.setModel(tabelaProdutoModel); //atualiza a tabela
		tabelaProdutoModel.fireTableDataChanged();	//atualiza a tela 
		}
	
	//METODO DE EXCLUIR
	private void excluirProduto(){
		produtoService.delete(produto);	//exclui no BD
		limparDadosDigitacao();
		tabelaProdutoModel.removeProduto(this.linha);	//exclui na Tabela
		tabelaProdutoDesign.setModel(tabelaProdutoModel); //atualiza a tabela
		tabelaProdutoModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO DE ALTERAR
	private void alterarProduto() {
		produtoService.update(produto);	//Altera no BD
		limparDadosDigitacao();
		tabelaProdutoModel.updateProduto(produto,this.linha);	//altera na Tabela
		tabelaProdutoDesign.setModel(tabelaProdutoModel);	//atualiza a tabela
		tabelaProdutoModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO BUSCAR FORNECEDOR
	protected void buscarFornecedor() {
        BuscarFornecedor buscarFornecedor = new BuscarFornecedor(new JFrame(), true);
        buscarFornecedor.setLocationRelativeTo(null);
        buscarFornecedor.setVisible(true);

        if(buscarFornecedor.isConfirmado()) {
            fornecedor = new Fornecedor();
            fornecedor = buscarFornecedor.getFornecedor();
            textFieldFornecedor.setText(fornecedor.getNome());
        }
    }
	
	//VENDO SE TA COM CAMPO EM BRANCO
	private boolean verDigitacao() {
		
		//TRATAMENTO DOS CAMPOS NULOS
		if(ProcessamentoDados.campoDigitacao(textFieldNome.getText()) == true) {		//se n escreveu nada no campo
			JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldNome.requestFocus(); //volta pro nome
			lblShowErroNome.setVisible(true);
			return true;
		}
		else {
			if(ProcessamentoDados.campoDigitacao(textFieldCategoria.getText()) == true) {		//se n escreveu nada no campo
				JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
				textFieldCategoria.requestFocus(); //volta pra categoria
				lblShowErroCategoria.setVisible(true);
				return true;
			}
			else {
				if(ProcessamentoDados.campoDigitacao(textFieldQuantidade.getText()) == true) {		//se n escreveu nada no campo
					JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldQuantidade.requestFocus(); //volta pra quantidade
					lblShowErroQuantidade.setVisible(true);
					return true;
				}
				else {
					if(ProcessamentoDados.campoDigitacao(textFieldPreco.getText()) == true) {		//se n escreveu nada no campo
						JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
						textFieldPreco.requestFocus(); //volta pro preco
						lblShowErroPreco.setVisible(true);
						return true;
					}
					else {
						if(ProcessamentoDados.campoDigitacao(textFieldCodigoDeBarras.getText()) == true) {		//se n escreveu nada no campo
							JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
							textFieldCodigoDeBarras.requestFocus(); //volta pro codigo de barras
							lblShowErroCodigoDeBarras.setVisible(true);
							return true;
						}
						else {
							if(ProcessamentoDados.campoDigitacao(textFieldMarca.getText()) == true) {		//se n escreveu nada no campo
								JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
								textFieldMarca.requestFocus(); //volta pra marca
								lblShowErroMarca.setVisible(true);
								return true;
							}
							else {
								if(ProcessamentoDados.campoDigitacao(textFieldModelo.getText()) == true) {		//se n escreveu nada no campo
									JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
									textFieldModelo.requestFocus(); //volta pro modelo
									lblShowErroModelo.setVisible(true);
									return true;
								}
								else{
									if(ProcessamentoDados.campoDigitacao(textFieldPeso.getText()) == true) {		//se n escreveu nada no campo
										JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
										textFieldPeso.requestFocus(); //volta pro peso
										lblShowErroPeso.setVisible(true);
										return true;
									}
									else {
										if(ProcessamentoDados.campoDigitacao(textFieldGarantia.getText()) == true) {		//se n escreveu nada no campo
											JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
											textFieldGarantia.requestFocus(); //volta pra garantia
											lblShowErroGarantia.setVisible(true);
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}		
		
		//TRATAMENTO DO TAMANHO DAS STRINGS
		if(textFieldNome.getColumns() >  90) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldNome.requestFocus(); //volta pro nome
			lblShowErroNome.setVisible(true);
			return true;
		}
		else {
			if(textFieldCategoria.getColumns() >  90) {
				JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
				textFieldNome.requestFocus(); //volta pro produto
				lblShowErroCategoria.setVisible(true);
				return true;
			}
			else {
				if(textFieldQuantidade.getColumns() >  100) {
					JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldNome.requestFocus(); //volta pro telefone
					lblShowErroQuantidade.setVisible(true);
					return true;
				}
				else {

					if(textFieldPreco.getColumns() >  20) {
						JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
						textFieldPreco.requestFocus(); //volta pro telefone
						lblShowErroPreco.setVisible(true);
						return true;
					}
					else {
						if(textFieldMarca.getColumns() >  90) {
							JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
							textFieldMarca.requestFocus(); //volta pro telefone
							lblShowErroMarca.setVisible(true);
							return true;
						}
						else {
							if(textFieldModelo.getColumns() >  90) {
								JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
								textFieldModelo.requestFocus(); //volta pro telefone
								lblShowErroModelo.setVisible(true);
								return true;
							}
							else {
								if(textFieldPeso.getColumns() >  10) {
									JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
									textFieldPeso.requestFocus(); //volta pro telefone
									lblShowErroPeso.setVisible(true);
									return true;
								}
								else {
									if(textFieldGarantia.getColumns() >  30) {
										JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
										textFieldGarantia.requestFocus(); //volta pro telefone
										lblShowErroGarantia.setVisible(true);
										return true;
									}
								}
							}
						}
					}
					
				}	
			}
		}
		
		return false;
	}
	
	private void pegarDadosProduto() {	//pega tudo que o usuario digitou na tela 
		produto.setNome(textFieldNome.getText());	
		produto.setCategoria(textFieldCategoria.getText());
		produto.setQuantidade(Integer.valueOf(textFieldQuantidade.getText()));
		produto.setPreco(Float.valueOf(textFieldPreco.getText()));
		produto.setCodigoBarras(Integer.valueOf(textFieldCodigoDeBarras.getText()));
		produto.setMarca(textFieldMarca.getText());
		produto.setModelo(textFieldModelo.getText());
		produto.setPeso(Float.valueOf(textFieldPeso.getText()));
		produto.setGarantia(textFieldGarantia.getText());
		produto.setFornecedor(fornecedor);
	}

	private void initComponents() {
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 798, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 0, 749, 383);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNome.setBounds(36, 27, 84, 14);
		panel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(157, 24, 358, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setHorizontalAlignment(SwingConstants.LEFT);
		lblCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCategoria.setBounds(36, 59, 84, 14);
		panel.add(lblCategoria);
		
		textFieldCategoria = new JTextField();
		textFieldCategoria.setBounds(157, 56, 358, 20);
		panel.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblQuantidade.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblQuantidade.setBounds(36, 87, 84, 14);
		panel.add(lblQuantidade);
		
		textFieldQuantidade = new JTextField();
		textFieldQuantidade.setColumns(10);
		textFieldQuantidade.setBounds(157, 84, 358, 20);
		panel.add(textFieldQuantidade);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroNome.setBounds(416, 27, 68, 14);
		panel.add(lblShowErroNome);
		
		lblShowErroCategoria = new JLabel("");
		lblShowErroCategoria.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCategoria.setBounds(416, 59, 68, 14);
		panel.add(lblShowErroCategoria);
		
		lblShowErroQuantidade = new JLabel("");
		lblShowErroQuantidade.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroQuantidade.setBounds(416, 87, 68, 14);
		panel.add(lblShowErroQuantidade);
		
		textFieldPreco = new JTextField();
		textFieldPreco.setColumns(10);
		textFieldPreco.setBounds(157, 111, 358, 20);
		panel.add(textFieldPreco);
		
		JLabel lblPreco = new JLabel("Preco");
		lblPreco.setHorizontalAlignment(SwingConstants.LEFT);
		lblPreco.setAlignmentX(0.5f);
		lblPreco.setBounds(36, 114, 84, 14);
		panel.add(lblPreco);
		
		textFieldCodigoDeBarras = new JTextField();
		textFieldCodigoDeBarras.setColumns(10);
		textFieldCodigoDeBarras.setBounds(157, 142, 358, 20);
		panel.add(textFieldCodigoDeBarras);
		
		JLabel lblCodigoDeBarras = new JLabel("Codigo de Barras");
		lblCodigoDeBarras.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigoDeBarras.setAlignmentX(0.5f);
		lblCodigoDeBarras.setBounds(36, 145, 98, 14);
		panel.add(lblCodigoDeBarras);
		
		textFieldMarca = new JTextField();
		textFieldMarca.setColumns(10);
		textFieldMarca.setBounds(157, 173, 358, 20);
		panel.add(textFieldMarca);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setHorizontalAlignment(SwingConstants.LEFT);
		lblMarca.setAlignmentX(0.5f);
		lblMarca.setBounds(36, 176, 84, 14);
		panel.add(lblMarca);
		
		textFieldModelo = new JTextField();
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(157, 204, 358, 20);
		panel.add(textFieldModelo);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setHorizontalAlignment(SwingConstants.LEFT);
		lblModelo.setAlignmentX(0.5f);
		lblModelo.setBounds(36, 207, 84, 14);
		panel.add(lblModelo);
		
		lblPeso = new JLabel("Peso");
		lblPeso.setHorizontalAlignment(SwingConstants.LEFT);
		lblPeso.setAlignmentX(0.5f);
		lblPeso.setBounds(36, 238, 84, 14);
		panel.add(lblPeso);
		
		textFieldPeso = new JTextField();
		textFieldPeso.setColumns(10);
		textFieldPeso.setBounds(157, 235, 358, 20);
		panel.add(textFieldPeso);
		
		lblGarantia = new JLabel("Garantia");
		lblGarantia.setHorizontalAlignment(SwingConstants.LEFT);
		lblGarantia.setAlignmentX(0.5f);
		lblGarantia.setBounds(36, 266, 84, 14);
		panel.add(lblGarantia);
		
		textFieldGarantia = new JTextField();
		textFieldGarantia.setColumns(10);
		textFieldGarantia.setBounds(157, 263, 358, 20);
		panel.add(textFieldGarantia);
		
		lblShowErroPreco = new JLabel("");
		lblShowErroPreco.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroPreco.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroPreco.setBounds(416, 114, 68, 14);
		panel.add(lblShowErroPreco);
		
		lblShowErroCodigoDeBarras = new JLabel("");
		lblShowErroCodigoDeBarras.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCodigoDeBarras.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCodigoDeBarras.setBounds(416, 145, 68, 14);
		panel.add(lblShowErroCodigoDeBarras);
		
		lblShowErroMarca = new JLabel("");
		lblShowErroMarca.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroMarca.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroMarca.setBounds(416, 176, 68, 14);
		panel.add(lblShowErroMarca);
		
		lblShowErroModelo = new JLabel("");
		lblShowErroModelo.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroModelo.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroModelo.setBounds(416, 207, 68, 14);
		panel.add(lblShowErroModelo);
		
		lblShowErroPeso = new JLabel("");
		lblShowErroPeso.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroPeso.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroPeso.setBounds(416, 238, 68, 14);
		panel.add(lblShowErroPeso);
		
		lblShowErroGarantia = new JLabel("");
		lblShowErroGarantia.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroGarantia.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroGarantia.setBounds(416, 266, 68, 14);
		panel.add(lblShowErroGarantia);
		
		JLabel lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setHorizontalAlignment(SwingConstants.LEFT);
		lblFornecedor.setAlignmentX(0.5f);
		lblFornecedor.setBounds(36, 294, 84, 14);
		panel.add(lblFornecedor);
		
		textFieldFornecedor = new JTextField();
		textFieldFornecedor.setEditable(false);
		textFieldFornecedor.setColumns(10);
		textFieldFornecedor.setBounds(157, 291, 358, 20);
		panel.add(textFieldFornecedor);
		
		btnBuscaFornecedor = new JButton("Fornecedor");
		btnBuscaFornecedor.setMnemonic(KeyEvent.VK_D);
		btnBuscaFornecedor.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnBuscaFornecedor.setIcon(new ImageIcon(ProdutoFrame.class.getResource("/imagens/search.png")));
		btnBuscaFornecedor.setBounds(534, 290, 98, 23);
		panel.add(btnBuscaFornecedor);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(11, 400, 749, 50);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnIncluir = new JButton("");
		btnIncluir.setToolTipText("");
		btnIncluir.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/book_add.png")));
		btnIncluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnIncluir.setBounds(10, 11, 89, 23);
		panel_1.add(btnIncluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_edit.png")));
		btnAlterar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAlterar.setBounds(114, 11, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnExcluir.setBounds(213, 11, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(312, 11, 105, 23);
		panel_1.add(btnFechar);		
		
		lblShowErroNome.setVisible(false);
		lblShowErroQuantidade.setVisible(false);
		lblShowErroCategoria.setVisible(false);
		 
		
	}


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public Distribuidora getDistribuidora() {
		return distribuidora;
	}


	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}
}
