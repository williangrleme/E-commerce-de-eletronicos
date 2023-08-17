package com.projeto.view.cliente;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.projeto.model.model.Cliente;
import com.projeto.model.service.ClienteService;
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

public class ClienteFrame extends JDialog  {
	private JPanel contentPane;
	private JButton btnIncluir;
	
	private JTextField textFieldNome;

	private JLabel lblNome;
	private JLabel lblTelefone;
	private JLabel lblEmail;
	private JLabel lblRua;
	private JLabel lblNumero;
	private JLabel lblCidade;
	private JLabel lblEstado;
	private JLabel lblBairro;
	private JLabel lblCep;
	private JLabel lblCpf;

	
	private JLabel lblShowErroNome;

	private JButton btnFechar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaClienteDesign;
	private TabelaClienteModel tabelaClienteModel;
	
	ClienteService clienteService;
	private Cliente cliente;
	private JTextField textFieldTelefone;
	private JTextField textFieldEmail;
	private JTextField textFieldRua;
	private JTextField textFieldNumero;
	private JTextField textFieldCpf;
	private JTextField textFieldCep;
	private JTextField textFieldEstado;
	private JTextField textFieldCidade;
	private JTextField textFieldBairro;

	private JLabel lblShowErroTelefone;
	private JLabel lblShowErroEmail;
	private JLabel lblShowErroRua;
	private JLabel lblShowErroNumero;
	private JLabel lblShowErroCidade;
	private JLabel lblShowErroEstado;
	private JLabel lblShowErroBairro;
	private JLabel lblShowErroCep;
	private JLabel lblShowErroCpf;


	public ClienteFrame(JFrame frame, boolean modal, int acao, JTable tabelaClienteDesign, TabelaClienteModel tabelaClienteModel, int linha) {
		super(frame,modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClienteFrame.class.getResource("/imagens/user.png")));
		setTitle("Cadastro Cliente");
	
		this.acao = acao;
		this.tabelaClienteDesign = tabelaClienteDesign;
		this.tabelaClienteModel = tabelaClienteModel;
		this.linha = linha;
		
		clienteService = new ClienteService();
		cliente = new Cliente();
		
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
				buscarCliente();
			}
			else {
				if(this.acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnIncluir.setVisible(false);
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(true);
					buscarCliente();
				}
				else {
					if(this.acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnIncluir.setVisible(false);
						btnAlterar.setVisible(false);
						btnExcluir.setVisible(false);
						buscarCliente();
					}
				}
			}
		}
	}

	private void buscarCliente(){
		cliente = new Cliente();
		cliente = this.tabelaClienteModel.getCliente(this.linha);
		textFieldNome.setText(cliente.getNome());	
		textFieldTelefone.setText(cliente.getTelefone());	
		textFieldEmail.setText(cliente.getEmail());	
		textFieldRua.setText(cliente.getRua());	
		textFieldNumero.setText(cliente.getNumero());	
		textFieldCidade.setText(cliente.getCidade());	
		textFieldEstado.setText(cliente.getEstado());	
		textFieldBairro.setText(cliente.getBairro());	
		textFieldCep.setText(cliente.getCep());	
		textFieldCpf.setText(cliente.getCpf());	

	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");	
		textFieldTelefone.setText("");	
		textFieldEmail.setText("");	
		textFieldRua.setText("");	
		textFieldNumero.setText("");	
		textFieldCidade.setText("");	
		textFieldEstado.setText("");	
		textFieldBairro.setText("");	
		textFieldCep.setText("");	
		textFieldCpf.setText("");	
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
		
		//TIRAR O AVISO DA RUA
		textFieldRua.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroRua.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DO NUMERO
		textFieldNumero.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNumero.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DA CIDADE
		textFieldCidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCidade.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DO ESTADO
		textFieldEstado.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEstado.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DO BAIRRO
		textFieldBairro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroBairro.setVisible(false);
			}
		});
		
		//TIRAR O AVISO DO CEP
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
					textFieldTelefone.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldTelefone.requestFocus();
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
		
		//TECLA VAI PRO PROXIMO
		textFieldCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldCpf.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldCpf.requestFocus();
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
					pegarDadosCliente();
					incluirCliente();
				}				
			}
		});
		
		//EVENTO ALTERAR
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosCliente();
					alterarCliente();
				}		
			}
		});
		
		//EVENTO EXCLUIR
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente(); 
			}
		});
		
	}
	
	//METODO DE INCLUIR
	private void incluirCliente() {
		clienteService.save(cliente); //salva no BD
		limparDadosDigitacao();
		tabelaClienteModel.saveCliente(cliente);	//salva na Tabela
		tabelaClienteDesign.setModel(tabelaClienteModel);
		tabelaClienteModel.fireTableDataChanged();
		}
	
	//METODO DE EXCLUIR
	private void excluirCliente(){
		clienteService.delete(cliente);	//exclui no BD
		limparDadosDigitacao();
		tabelaClienteModel.removeCliente(this.linha);
		tabelaClienteDesign.setModel(tabelaClienteModel);
		tabelaClienteModel.fireTableDataChanged();
	}
	
	//METODO DE ALTERAR
	private void alterarCliente() {
		clienteService.update(cliente);	//Altera no BD
		limparDadosDigitacao();
		tabelaClienteModel.updateCliente(cliente, this.linha);
		tabelaClienteDesign.setModel(tabelaClienteModel);
		tabelaClienteModel.fireTableDataChanged();
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
			if(ProcessamentoDados.campoDigitacao(textFieldTelefone.getText()) == true) {		//se n escreveu nada no campo
				JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
				textFieldTelefone.requestFocus(); //volta pro produto
				lblShowErroTelefone.setVisible(true);
				return true;
			}
			else {
				if(ProcessamentoDados.campoDigitacao(textFieldEmail.getText()) == true) {		//se n escreveu nada no campo
					JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldEmail.requestFocus(); //volta pro telefone
					lblShowErroEmail.setVisible(true);
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
									else {
										if(ProcessamentoDados.campoDigitacao(textFieldCep.getText()) == true) {		//se n escreveu nada no campo
											JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
											textFieldCep.requestFocus(); //volta pro telefone
											lblShowErroCep.setVisible(true);
											return true;
										}
										else {
											if(ProcessamentoDados.campoDigitacao(textFieldCpf.getText()) == true) {		//se n escreveu nada no campo
												JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
												textFieldCpf.requestFocus(); //volta pro telefone
												lblShowErroCpf.setVisible(true);
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
		}		
		
		//TRATAMENTO DO TAMANHO DAS STRINGS
		if(textFieldNome.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldNome.requestFocus(); //volta pro nome
			lblShowErroNome.setVisible(true);
			return true;
		}
		
		if(textFieldTelefone.getColumns() > 14) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldTelefone.requestFocus(); //volta pro produto
			lblShowErroTelefone.setVisible(true);
			return true;
		}
		
		if(textFieldEmail.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldEmail.requestFocus(); //volta pro telefone
			lblShowErroEmail.setVisible(true);
			return true;
		}
		
		if(textFieldRua.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldRua.requestFocus(); //volta pro telefone
			lblShowErroRua.setVisible(true);
			return true;
		}
		
		if(textFieldCidade.getColumns() >  40) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCidade.requestFocus(); //volta pro telefone
			lblShowErroCidade.setVisible(true);
			return true;
		}
		
		if(textFieldEstado.getColumns() >  40) {
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
		
		if(textFieldCep.getColumns() >  20) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCep.requestFocus(); //volta pro telefone
			lblShowErroCep.setVisible(true);
			return true;
		}
		
		if(textFieldCpf.getColumns() >  14) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCpf.requestFocus(); //volta pro telefone
			lblShowErroCpf.setVisible(true);
			return true;
		}
		
		return false;
	}
	
	private void pegarDadosCliente() {	//pega tudo que o usuario digitou na tela 
		cliente.setNome(textFieldNome.getText());	
		cliente.setTelefone(textFieldTelefone.getText());	
		cliente.setEmail(textFieldEmail.getText());	
		cliente.setRua(textFieldRua.getText());	
		cliente.setNumero(textFieldNumero.getText());	
		cliente.setCidade(textFieldCidade.getText());	
		cliente.setEstado(textFieldEstado.getText());	
		cliente.setBairro(textFieldBairro.getText());	
		cliente.setCep(textFieldCep.getText());	
		cliente.setCpf(textFieldCpf.getText());	
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
		panel.setBounds(10, 0, 749, 339);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNome.setBounds(20, 14, 66, 14);
		panel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(89, 11, 261, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroNome.setBounds(360, 14, 25, 14);
		panel.add(lblShowErroNome);
		

		textFieldTelefone = new JTextField();
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBounds(89, 43, 261, 20);
		panel.add(textFieldTelefone);
		
		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefone.setAlignmentX(0.5f);
		lblTelefone.setBounds(20, 46, 66, 14);
		panel.add(lblTelefone);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmail.setAlignmentX(0.5f);
		lblEmail.setBounds(20, 78, 66, 14);
		panel.add(lblEmail);
		
		lblRua = new JLabel("Rua:");
		lblRua.setHorizontalAlignment(SwingConstants.LEFT);
		lblRua.setAlignmentX(0.5f);
		lblRua.setBounds(20, 110, 66, 14);
		panel.add(lblRua);
		
		lblNumero = new JLabel("Numero:");
		lblNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumero.setAlignmentX(0.5f);
		lblNumero.setBounds(20, 142, 66, 14);
		panel.add(lblNumero);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblCidade.setAlignmentX(0.5f);
		lblCidade.setBounds(20, 174, 66, 14);
		panel.add(lblCidade);
		
		lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstado.setAlignmentX(0.5f);
		lblEstado.setBounds(20, 206, 66, 14);
		panel.add(lblEstado);
		
		lblCep = new JLabel("CEP:");
		lblCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblCep.setAlignmentX(0.5f);
		lblCep.setBounds(20, 270, 66, 14);
		panel.add(lblCep);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.LEFT);
		lblBairro.setAlignmentX(0.5f);
		lblBairro.setBounds(20, 238, 66, 14);
		panel.add(lblBairro);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(89, 75, 261, 20);
		panel.add(textFieldEmail);
		
		textFieldRua = new JTextField();
		textFieldRua.setColumns(10);
		textFieldRua.setBounds(89, 107, 261, 20);
		panel.add(textFieldRua);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setColumns(10);
		textFieldNumero.setBounds(89, 139, 261, 20);
		panel.add(textFieldNumero);
		
		textFieldCpf = new JTextField();
		textFieldCpf.setColumns(10);
		textFieldCpf.setBounds(89, 299, 261, 20);
		panel.add(textFieldCpf);
		
		textFieldCep = new JTextField();
		textFieldCep.setColumns(10);
		textFieldCep.setBounds(89, 267, 261, 20);
		panel.add(textFieldCep);
		
		textFieldEstado = new JTextField();
		textFieldEstado.setColumns(10);
		textFieldEstado.setBounds(89, 203, 261, 20);
		panel.add(textFieldEstado);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(89, 171, 261, 20);
		panel.add(textFieldCidade);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(89, 235, 261, 20);
		panel.add(textFieldBairro);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpf.setAlignmentX(0.5f);
		lblCpf.setBounds(20, 302, 66, 14);
		panel.add(lblCpf);
		
		lblShowErroTelefone = new JLabel("");
		lblShowErroTelefone.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTelefone.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroTelefone.setBounds(360, 46, 25, 14);
		panel.add(lblShowErroTelefone);
		
		lblShowErroEmail = new JLabel("");
		lblShowErroEmail.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEmail.setBounds(360, 78, 25, 14);
		panel.add(lblShowErroEmail);
		
		lblShowErroRua = new JLabel("");
		lblShowErroRua.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroRua.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroRua.setBounds(360, 110, 25, 14);
		panel.add(lblShowErroRua);
		
		lblShowErroNumero = new JLabel("");
		lblShowErroNumero.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroNumero.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNumero.setBounds(360, 142, 25, 14);
		panel.add(lblShowErroNumero);
		
		lblShowErroCidade = new JLabel("");
		lblShowErroCidade.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCidade.setBounds(360, 174, 25, 14);
		panel.add(lblShowErroCidade);
		
		lblShowErroEstado = new JLabel("");
		lblShowErroEstado.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEstado.setBounds(360, 206, 25, 14);
		panel.add(lblShowErroEstado);
		
		lblShowErroBairro = new JLabel("");
		lblShowErroBairro.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroBairro.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroBairro.setBounds(360, 238, 25, 14);
		panel.add(lblShowErroBairro);
		
		lblShowErroCep = new JLabel("");
		lblShowErroCep.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCep.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCep.setBounds(360, 270, 25, 14);
		panel.add(lblShowErroCep);
		
		lblShowErroCpf = new JLabel("");
		lblShowErroCpf.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCpf.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCpf.setBounds(360, 302, 25, 14);
		panel.add(lblShowErroCpf);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 348, 749, 50);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnIncluir = new JButton("");
		btnIncluir.setToolTipText("");
		btnIncluir.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/book_add.png")));
		btnIncluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnIncluir.setBounds(10, 11, 89, 23);
		panel_1.add(btnIncluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/application_edit.png")));
		btnAlterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlterar.setBounds(114, 11, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExcluir.setBounds(213, 11, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setIcon(new ImageIcon(ClienteFrame.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(312, 11, 105, 23);
		panel_1.add(btnFechar);		
		
		lblShowErroNome.setVisible(false);
		lblShowErroTelefone.setVisible(false);
		lblShowErroEmail.setVisible(false);
		lblShowErroRua.setVisible(false);
		lblShowErroNumero.setVisible(false);
		lblShowErroCidade.setVisible(false);
		lblShowErroEstado.setVisible(false);
		lblShowErroBairro.setVisible(false);
		lblShowErroCep.setVisible(false);
		lblShowErroCpf.setVisible(false);
		
	}
}
