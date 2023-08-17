package com.projeto.view.distribuidora;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.projeto.model.model.Distribuidora;
import com.projeto.model.service.DistribuidoraService;
import com.projeto.util.ProcessamentoDados;

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
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class DistribuidoraFrame extends JDialog {
	private JPanel contentPane;
	
	private JButton btnIncluir;
	private JButton btnFechar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaDistribuidoraDesign;
	private TabelaDistribuidoraModel tabelaDistribuidoraModel;
	private Distribuidora distribuidora;
	DistribuidoraService distribuidoraService;
	
	private JLabel lblNome;
	private JLabel lblTelefone;
	private JLabel lblEmail;
	private JLabel lblRua;
	private JLabel lblNumero;
	private JLabel lblCidade;
	private JLabel lblEstado ;
	private JLabel lblBairro;
	private JLabel lblCep ;
	private JLabel lblShowErroEmail;
	private JLabel lblShowErroTelefone;
	private JLabel lblShowErroNome;

	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefone;
	private JTextField textFieldRua;
	private JTextField textFieldNumero;
	private JTextField textFieldCidade;
	private JTextField textFieldEstado;
	private JTextField textFieldBairro;
	private JTextField textFieldCep;
	private JLabel lblShowErroRua;
	private JLabel lblShowErroNumero;
	private JLabel lblShowErroCidade;
	private JLabel lblShowErroEstado;
	private JLabel lblShowErroBairro;
	private JLabel lblShowErroCep;
	

	public DistribuidoraFrame(JFrame frame, boolean modal, int acao, JTable tabelaDistribuidoraDesign, TabelaDistribuidoraModel tabelaDistribuidoraModel, int linha) {
		super(frame,modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Distribuidora.class.getResource("/imagens/user.png")));
		setTitle("Cadastro Distribuidora");
	
		this.acao = acao;
		this.tabelaDistribuidoraDesign = tabelaDistribuidoraDesign;
		this.tabelaDistribuidoraModel = tabelaDistribuidoraModel;
		this.linha = linha;
		
		distribuidoraService = new DistribuidoraService();
		distribuidora = new Distribuidora();
		
		initComponents();
		createEvents(); 
		configuraAcao();
		setResizable(false);		//nao deixa o usuario maximizar a tela
		this.setLocationRelativeTo(null);	//centraliza a tela
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
				buscarDistribuidora();
			}
			else {
				if(this.acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnIncluir.setVisible(false);
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(true);
					buscarDistribuidora();
				}
				else {
					if(this.acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnIncluir.setVisible(false);
						btnAlterar.setVisible(false);
						btnExcluir.setVisible(false);
						buscarDistribuidora();
					}
				}
			}
		}
	}

	private void buscarDistribuidora(){
		distribuidora = new Distribuidora();
		distribuidora = this.tabelaDistribuidoraModel.getDistribuidora(this.linha);
		textFieldNome.setText(distribuidora.getNome());
		textFieldEmail.setText(distribuidora.getEmail());
		textFieldTelefone.setText(distribuidora.getTelefone());
		textFieldRua.setText(distribuidora.getRua());
		textFieldNumero.setText(distribuidora.getNumero());
		textFieldCidade.setText(distribuidora.getCidade());
		textFieldEstado.setText(distribuidora.getEstado());
		textFieldBairro.setText(distribuidora.getBairro());
		textFieldCep.setText(distribuidora.getCep());
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");	
		textFieldEmail.setText("");
		textFieldTelefone.setText("");
		textFieldRua.setText("");
		textFieldNumero.setText("");
		textFieldCidade.setText("");
		textFieldEstado.setText("");
		textFieldBairro.setText("");
		textFieldCep.setText("");
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
		
		
		//TIRAR O AVISO DE ERRO DO EMAIL
		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEmail.setVisible(false);
			}
		});
	
		//TIRAR O AVISO DE ERRO DO TELEFONE
		textFieldTelefone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroTelefone.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DE ERRO DA RUA
		textFieldRua.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroRua.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DO NUMERO
		textFieldNumero.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNumero.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DA CIDADE
		textFieldCidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroTelefone.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DO ESTADO
		textFieldEstado.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEstado.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DO BAIRRO
		textFieldBairro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroBairro.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DO CEP
		textFieldCep.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCep.setVisible(false);
			}
		});
		
		
		//TECLA VAI PRO PROXIMO
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldEmail.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldEmail.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldTelefone.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldTelefone.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldRua.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldRua.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldRua.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldNumero.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldNumero.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldCidade.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldCidade.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldEstado.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldEstado.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldEstado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldBairro.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldBairro.requestFocus();
				}
			}
		});
		
		textFieldBairro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldCep.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldCep.requestFocus();
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
					pegarDadosDistribuidora();
					incluirDistribuidora();
				}				
			}
		});
		
		//EVENTO ALTERAR
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosDistribuidora();
					alterarDistribuidora();
				}		
			}
		});
		
		//EVENTO EXCLUIR
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirDistribuidora(); 
			}
		});
		
	}
	
	//METODO DE INCLUIR
	private void incluirDistribuidora() {
		distribuidoraService.save(distribuidora); //salva no BD
		limparDadosDigitacao();
		tabelaDistribuidoraModel.saveDistribuidora(distribuidora);	//salva na Tabela
		tabelaDistribuidoraDesign.setModel(tabelaDistribuidoraModel); //atualiza a tabela
		tabelaDistribuidoraModel.fireTableDataChanged();	//atualiza a tela 
		}
	
	//METODO DE EXCLUIR
	private void excluirDistribuidora(){
		distribuidoraService.delete(distribuidora);	//exclui no BD
		limparDadosDigitacao();
		tabelaDistribuidoraModel.removeDistribuidora(this.linha);	//exclui na Tabela
		tabelaDistribuidoraDesign.setModel(tabelaDistribuidoraModel); //atualiza a tabela
		tabelaDistribuidoraModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO DE ALTERAR
	private void alterarDistribuidora() {
		distribuidoraService.update(distribuidora);	//Altera no BD
		limparDadosDigitacao();
		tabelaDistribuidoraModel.updateDistribuidora(distribuidora,this.linha);	//altera na Tabela
		tabelaDistribuidoraDesign.setModel(tabelaDistribuidoraModel);	//atualiza a tabela
		tabelaDistribuidoraModel.fireTableDataChanged();	//atualiza a tela 
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
			if(ProcessamentoDados.campoDigitacao(textFieldEmail.getText()) == true) {		//se n escreveu nada no campo
				JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
				textFieldEmail.requestFocus(); //volta pro produto
				lblShowErroEmail.setVisible(true);
				return true;
			}
			else {
				if(ProcessamentoDados.campoDigitacao(textFieldTelefone.getText()) == true) {		//se n escreveu nada no campo
					JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldNome.requestFocus(); //volta pro telefone
					lblShowErroTelefone.setVisible(true);
					return true;
				}
				else {
					if(ProcessamentoDados.campoDigitacao(textFieldRua.getText()) == true) {		//se n escreveu nada no campo
						JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
						textFieldRua.requestFocus(); //volta pro telefone
						lblShowErroRua.setVisible(true);
						return true;
					}
					else {
						if(ProcessamentoDados.campoDigitacao(textFieldNumero.getText()) == true) {		//se n escreveu nada no campo
							JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
							textFieldNumero.requestFocus(); //volta pro telefone
							lblShowErroNumero.setVisible(true);
							return true;
						}
						else {
							if(ProcessamentoDados.campoDigitacao(textFieldCidade.getText()) == true) {		//se n escreveu nada no campo
								JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
								textFieldCidade.requestFocus(); //volta pro telefone
								lblShowErroCidade.setVisible(true);
								return true;
							}
							else {
								if(ProcessamentoDados.campoDigitacao(textFieldEstado.getText()) == true) {		//se n escreveu nada no campo
									JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
									textFieldEstado.requestFocus(); //volta pro telefone
									lblShowErroEstado.setVisible(true);
									return true;
								}
								else {
									if(ProcessamentoDados.campoDigitacao(textFieldBairro.getText()) == true) {		//se n escreveu nada no campo
										JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
										textFieldBairro.requestFocus(); //volta pro telefone
										lblShowErroBairro.setVisible(true);
										return true;
									}
								}
							}
						}
					}
				}
			}
		}		
		
		//TRATAMENTO DO TAMANHO DAS STRINGS
		if(textFieldNome.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldNome.requestFocus(); //volta pro nome
			lblShowErroNome.setVisible(true);
			return true;
		}
		
		if(textFieldEmail.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldEmail.requestFocus(); //volta pro produto
			lblShowErroEmail.setVisible(true);
			return true;
		}
		
		if(textFieldTelefone.getColumns() >  30) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldTelefone.requestFocus(); //volta pro telefone
			lblShowErroTelefone.setVisible(true);
			return true;
		}
		
		if(textFieldRua.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldRua.requestFocus(); //volta pro telefone
			lblShowErroRua.setVisible(true);
			return true;
		}
		
		if(textFieldNumero.getColumns() >  10) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldNumero.requestFocus(); //volta pro telefone
			lblShowErroNumero.setVisible(true);
			return true;
		}
		
		if(textFieldCidade.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCidade.requestFocus(); //volta pro telefone
			lblShowErroCidade.setVisible(true);
			return true;
		}
		
		if(textFieldEstado.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldEstado.requestFocus(); //volta pro telefone
			lblShowErroEstado.setVisible(true);
			return true;
		}
		
		if(textFieldBairro.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldBairro.requestFocus(); //volta pro telefone
			lblShowErroBairro.setVisible(true);
			return true;
		}
		if(textFieldCep.getColumns() >  10) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCep.requestFocus(); //volta pro telefone
			lblShowErroCep.setVisible(true);
			return true;
		}
		
		
		return false;
	}
	
	private void pegarDadosDistribuidora() {	//pega tudo que o usuario digitou na tela 
		distribuidora.setNome(textFieldNome.getText());	
		distribuidora.setEmail(textFieldEmail.getText());	
		distribuidora.setTelefone(textFieldTelefone.getText());	
		distribuidora.setRua(textFieldRua.getText());	
		distribuidora.setNumero(textFieldNumero.getText());	
		distribuidora.setCidade(textFieldCidade.getText());	
		distribuidora.setEstado(textFieldEstado.getText());	
		distribuidora.setBairro(textFieldBairro.getText());	
		distribuidora.setCep(textFieldCep.getText());	
	}

	private void initComponents() {
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 749, 339);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNome = new JLabel("Nome: ");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNome.setBounds(26, 35, 66, 14);
		panel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(95, 32, 261, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefone.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTelefone.setBounds(26, 99, 66, 14);
		panel.add(lblTelefone);
		
		textFieldTelefone = new JTextField();
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBounds(95, 96, 261, 20);
		panel.add(textFieldTelefone);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroNome.setBounds(381, 35, 46, 14);
		panel.add(lblShowErroNome);
		
		
		lblShowErroTelefone = new JLabel("");
		lblShowErroTelefone.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroTelefone.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTelefone.setBounds(381, 99, 46, 14);
		panel.add(lblShowErroTelefone);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBackground(Color.WHITE);
		lblEmail.setBounds(26, 67, 46, 14);
		panel.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(95, 64, 261, 20);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		lblShowErroEmail = new JLabel("");
		lblShowErroEmail.setVisible(false);
		lblShowErroEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEmail.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroEmail.setBounds(381, 67, 46, 14);
		panel.add(lblShowErroEmail);
		
		textFieldRua = new JTextField();
		textFieldRua.setColumns(10);
		textFieldRua.setBounds(95, 128, 261, 20);
		panel.add(textFieldRua);
		
		lblRua = new JLabel("Rua:");
		lblRua.setHorizontalAlignment(SwingConstants.LEFT);
		lblRua.setAlignmentX(0.5f);
		lblRua.setBounds(26, 131, 66, 14);
		panel.add(lblRua);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setColumns(10);
		textFieldNumero.setBounds(95, 160, 261, 20);
		panel.add(textFieldNumero);
		
		lblNumero = new JLabel("Numero:");
		lblNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumero.setAlignmentX(0.5f);
		lblNumero.setBounds(26, 163, 66, 14);
		panel.add(lblNumero);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(95, 192, 261, 20);
		panel.add(textFieldCidade);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblCidade.setAlignmentX(0.5f);
		lblCidade.setBounds(26, 195, 66, 14);
		panel.add(lblCidade);
		
		textFieldEstado = new JTextField();
		textFieldEstado.setColumns(10);
		textFieldEstado.setBounds(95, 224, 261, 20);
		panel.add(textFieldEstado);
		
		lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstado.setAlignmentX(0.5f);
		lblEstado.setBounds(26, 227, 66, 14);
		panel.add(lblEstado);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(95, 256, 261, 20);
		panel.add(textFieldBairro);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.LEFT);
		lblBairro.setAlignmentX(0.5f);
		lblBairro.setBounds(26, 259, 66, 14);
		panel.add(lblBairro);
		
		textFieldCep = new JTextField();
		textFieldCep.setColumns(10);
		textFieldCep.setBounds(95, 288, 261, 20);
		panel.add(textFieldCep);
		
		lblCep = new JLabel("CEP:");
		lblCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblCep.setAlignmentX(0.5f);
		lblCep.setBounds(26, 291, 66, 14);
		panel.add(lblCep);
		
		lblShowErroRua = new JLabel("");
		lblShowErroRua.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroRua.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroRua.setBounds(381, 131, 46, 14);
		panel.add(lblShowErroRua);
		
		lblShowErroNumero = new JLabel("");
		lblShowErroNumero.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNumero.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroNumero.setBounds(381, 163, 46, 14);
		panel.add(lblShowErroNumero);
		
		lblShowErroCidade = new JLabel("");
		lblShowErroCidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCidade.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroCidade.setBounds(381, 195, 46, 14);
		panel.add(lblShowErroCidade);
		
		lblShowErroEstado = new JLabel("");
		lblShowErroEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEstado.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroEstado.setBounds(381, 227, 46, 14);
		panel.add(lblShowErroEstado);
		
		lblShowErroBairro = new JLabel("");
		lblShowErroBairro.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroBairro.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroBairro.setBounds(381, 259, 46, 14);
		panel.add(lblShowErroBairro);
		
		lblShowErroCep = new JLabel("");
		lblShowErroCep.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCep.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_error.png")));
		lblShowErroCep.setBounds(381, 291, 46, 14);
		panel.add(lblShowErroCep);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 348, 749, 50);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnIncluir = new JButton("");
		btnIncluir.setToolTipText("");
		btnIncluir.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/book_add.png")));
		btnIncluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnIncluir.setBounds(10, 11, 89, 23);
		panel_1.add(btnIncluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/application_edit.png")));
		btnAlterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlterar.setBounds(114, 11, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExcluir.setBounds(213, 11, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setIcon(new ImageIcon(Distribuidora.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(312, 11, 105, 23);
		panel_1.add(btnFechar);		
		
		//nao vai por bem, vai por mal
		lblShowErroNome.setVisible(false);
		lblShowErroTelefone.setVisible(false);
		lblShowErroRua.setVisible(false);
		lblShowErroNumero.setVisible(false);
		lblShowErroCidade.setVisible(false);
		lblShowErroEstado.setVisible(false);
		lblShowErroBairro.setVisible(false);
		lblShowErroCep.setVisible(false);
	}
}
