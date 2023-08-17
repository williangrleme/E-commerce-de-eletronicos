package com.projeto.view.funcionario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

import com.projeto.model.model.Distribuidora;
import com.projeto.model.model.Funcionario;
import com.projeto.model.service.FuncionarioService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.funcionario.TabelaFuncionarioModel;
import com.projeto.view.distribuidora.BuscarDistribuidora;
import com.projeto.view.funcionario.FuncionarioFrame;
import javax.swing.border.EmptyBorder;

public class FuncionarioFrame  extends JDialog{

	private JPanel contentPane;
	private JButton btnIncluir;
	private JTextField textFieldNome;
	private JTextField textFieldTelefone;
	
	private JButton btnFechar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaFuncionarioDesign;
	private JLabel lblShowErroNome;
	private JLabel lblShowErroTelefone;
	
	private TabelaFuncionarioModel tabelaFuncionarioModel;
	
	FuncionarioService funcionarioService;
	private Funcionario funcionario;
	private Distribuidora distribuidora;
	
	private JLabel lblNome;
	private JLabel lblTelefone;
	private JLabel lblCargo;
	private JLabel lblRua;
	private JLabel lblNumero;
	private JLabel lblCidade;
	private JLabel lblEstado;
	private JLabel lblBairro;
	private JLabel lblCep ;
	private JLabel lblCpf;
	private JLabel lblGenero;
	private JLabel lblSalario;
	private JLabel lblDataNascimento;
	
	private JLabel lblShowErroCargo;
	private JLabel lblShowErroCpf;
	private JLabel lblShowErroGenero;
	private JLabel lblShowErroSalario;
	private JLabel lblShowErroDataNascimento;
	private JLabel lblShowErroRua;
	private JLabel lblShowErroNumero;
	private JLabel lblShowErroCidade;
	private JLabel lblShowErroEstado;
	private JLabel lblShowErroBairro;
	private JLabel lblShowErroCep;

	private JTextField textFieldCargo;
	private JTextField textFieldCpf;
	private JTextField textFieldGenero;
	private JTextField textFieldSalario;
	private JTextField textFieldDataNascimento;
	private JTextField textFieldRua;
	private JTextField textFieldNumero;
	private JTextField textFieldCidade;
	private JTextField textFieldEstado;
	private JTextField textFieldBairro;
	private JTextField textFieldCep;
	private JLabel lblDistribuidora;
	private JTextField textFieldDistribuidora;
	private JButton btnBuscarDistribuidora;
	
	

	public FuncionarioFrame(JFrame frame, boolean modal, int acao, JTable tabelaFuncionarioDesign, TabelaFuncionarioModel tabelaFuncionarioModel, int linha) {
		super(frame,modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FuncionarioFrame.class.getResource("/imagens/user.png")));
		setTitle("Cadastro Funcionario");
	
		this.acao = acao;
		this.tabelaFuncionarioDesign = tabelaFuncionarioDesign;
		this.tabelaFuncionarioModel = tabelaFuncionarioModel;
		this.linha = linha;
		
		funcionarioService = new FuncionarioService();
		funcionario = new Funcionario();
		
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
				buscarFuncionario();
			}
			else {
				if(this.acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnIncluir.setVisible(false);
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(true);
					buscarFuncionario();
				}
				else {
					if(this.acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnIncluir.setVisible(false);
						btnAlterar.setVisible(false);
						btnExcluir.setVisible(false);
						buscarFuncionario();
					}
				}
			}
		}
	}

	private void buscarFuncionario(){
		funcionario = new Funcionario();
		funcionario = this.tabelaFuncionarioModel.getFuncionario(this.linha);
		textFieldNome.setText(funcionario.getNome());	
		textFieldTelefone.setText(funcionario.getTelefone());
		textFieldCargo.setText(funcionario.getCargo());
		textFieldCpf.setText(funcionario.getCpf());
		textFieldSalario.setText(funcionario.getSalario().toString());
		textFieldDataNascimento.setText(funcionario.getDataNascimento());
		textFieldRua.setText(funcionario.getRua());
		textFieldNumero.setText(funcionario.getNumero());
		textFieldCidade.setText(funcionario.getCidade());
		textFieldEstado.setText(funcionario.getEstado());
		textFieldBairro.setText(funcionario.getBairro());
		textFieldCep.setText(funcionario.getCep());
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");	
		textFieldTelefone.setText("");	
		textFieldCargo.setText("");	
		textFieldCpf.setText("");	
		textFieldSalario.setText("");	
		textFieldDataNascimento.setText("");	
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
		
		//TIRAR AVISO DE ERRO TELEFONE
		textFieldTelefone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroTelefone.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO CARGO
		textFieldCargo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCargo.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO CPF
		textFieldCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCpf.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO GENERO
		textFieldGenero.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroGenero.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO SALARIO
		textFieldSalario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroSalario.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DATA NASCIMENTO
		textFieldDataNascimento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroDataNascimento.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO RUA
		textFieldRua.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroRua.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO NUMERO
		textFieldNumero.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroNumero.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO CIDADE
		textFieldCidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCidade.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO ESTADO
		textFieldEstado.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroEstado.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO BAIRRO
		textFieldBairro.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroBairro.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO CEP
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
		textFieldTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldCargo.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldCargo.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldCargo.addKeyListener(new KeyAdapter() {
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
		
		//TECLA VAI PRO PROXIMO
		textFieldCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldGenero.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldGenero.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldGenero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldSalario.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldSalario.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldSalario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldDataNascimento.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldDataNascimento.requestFocus();
				}
			}
		});
		
		//TECLA VAI PRO PROXIMO
		textFieldDataNascimento.addKeyListener(new KeyAdapter() {
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
		
		//TECLA VAI PRO PROXIMO
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
					pegarDadosFuncionario();
					incluirFuncionario();
				}				
			}
		});
		
		//EVENTO ALTERAR
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosFuncionario();
					alterarFuncionario();
				}		
			}
		});
		
		//EVENTO EXCLUIR
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFuncionario(); 
			}
		});
		
		//EVENTO BUSCAR DISTRIBUIDORA
		btnBuscarDistribuidora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarDistribuidora();
			}
		});
		
	}
	
	//METODO BUSCAR DISTRIBUIDORA
	protected void buscarDistribuidora() {
        BuscarDistribuidora buscarDistribuidora  = new BuscarDistribuidora (new JFrame(), true);
        buscarDistribuidora .setLocationRelativeTo(null);
        buscarDistribuidora .setVisible(true);

        if(buscarDistribuidora.isConfirmado()) {
            setDistribuidora(new Distribuidora());
            setDistribuidora(buscarDistribuidora.getDistribuidora());
            textFieldDistribuidora.setText(distribuidora.getNome());
        }
    }
	
	//METODO DE INCLUIR
	private void incluirFuncionario() {
		funcionarioService.save(funcionario); //salva no BD
		limparDadosDigitacao();
		tabelaFuncionarioModel.saveFuncionario(funcionario);	//salva na Tabela
		tabelaFuncionarioDesign.setModel(tabelaFuncionarioModel); //atualiza a tabela
		tabelaFuncionarioModel.fireTableDataChanged();	//atualiza a tela 
		}
	
	//METODO DE EXCLUIR
	private void excluirFuncionario(){
		funcionarioService.delete(funcionario);	//exclui no BD
		limparDadosDigitacao();
		tabelaFuncionarioModel.removeFuncionario(this.linha);	//exclui na Tabela
		tabelaFuncionarioDesign.setModel(tabelaFuncionarioModel); //atualiza a tabela
		tabelaFuncionarioModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO DE ALTERAR
	private void alterarFuncionario() {
		funcionarioService.update(funcionario);	//Altera no BD
		limparDadosDigitacao();
		tabelaFuncionarioModel.updateFuncionario(funcionario,this.linha);	//altera na Tabela
		tabelaFuncionarioDesign.setModel(tabelaFuncionarioModel);	//atualiza a tabela
		tabelaFuncionarioModel.fireTableDataChanged();	//atualiza a tela 
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
				if(ProcessamentoDados.campoDigitacao(textFieldCargo.getText()) == true) {		//se n escreveu nada no campo
					JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldCargo.requestFocus(); //volta pro telefone
					lblShowErroCargo.setVisible(true);
					return true;
				}
				else {
					if(ProcessamentoDados.campoDigitacao(textFieldGenero.getText()) == true) {		//se n escreveu nada no campo
						JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
						textFieldGenero.requestFocus(); //volta pro telefone
						lblShowErroGenero.setVisible(true);
						return true;
					}
					else {
						if(ProcessamentoDados.campoDigitacao(textFieldSalario.getText()) == true) {		//se n escreveu nada no campo
							JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
							textFieldSalario.requestFocus(); //volta pro telefone
							lblShowErroSalario.setVisible(true);
							return true;
						}
						else {
							if(ProcessamentoDados.campoDigitacao(textFieldDataNascimento.getText()) == true) {		//se n escreveu nada no campo
								JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
								textFieldDataNascimento.requestFocus(); //volta pro telefone
								lblShowErroDataNascimento.setVisible(true);
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
		}		
		
		//TRATAMENTO DO TAMANHO DAS STRINGS
		if(textFieldNome.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldNome.requestFocus(); //volta pro nome
			lblShowErroNome.setVisible(true);
			return true;
		}
		
		if(textFieldTelefone.getColumns() >  14) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldTelefone.requestFocus(); //volta pro produto
			lblShowErroTelefone.setVisible(true);
			return true;
		}
		
		if(textFieldCargo.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCargo.requestFocus(); //volta pro produto
			lblShowErroCargo.setVisible(true);
			return true;
		}
		
		if(textFieldCpf.getColumns() >  14) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCpf.requestFocus(); //volta pro produto
			lblShowErroCpf.setVisible(true);
			return true;
		}
		
		if(textFieldGenero.getColumns() >  10) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldGenero.requestFocus(); //volta pro produto
			lblShowErroGenero.setVisible(true);
			return true;
		}
		
		if(textFieldSalario.getColumns() >  10) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldSalario.requestFocus(); //volta pro produto
			lblShowErroSalario.setVisible(true);
			return true;
		}
		
		if(textFieldDataNascimento.getColumns() >  12) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldDataNascimento.requestFocus(); //volta pro produto
			lblShowErroDataNascimento.setVisible(true);
			return true;
		}
		
		if(textFieldRua.getColumns() >  14) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldRua.requestFocus(); //volta pro produto
			lblShowErroRua.setVisible(true);
			return true;
		}
		
		if(textFieldCidade.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCidade.requestFocus(); //volta pro produto
			lblShowErroCidade.setVisible(true);
			return true;
		}
		
		if(textFieldEstado.getColumns() >  50) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldEstado.requestFocus(); //volta pro produto
			lblShowErroEstado.setVisible(true);
			return true;
		}
		
		if(textFieldBairro.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldBairro.requestFocus(); //volta pro produto
			lblShowErroBairro.setVisible(true);
			return true;
		}
		
		if(textFieldCep.getColumns() >  20) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCep.requestFocus(); //volta pro produto
			lblShowErroCep.setVisible(true);
			return true;
		}
		
		return false;
	}
	
	private void pegarDadosFuncionario() {	//pega tudo que o usuario digitou na tela 
		funcionario.setNome(textFieldNome.getText());	
		funcionario.setTelefone(textFieldNome.getText());
		funcionario.setCargo(textFieldNome.getText());
		funcionario.setCpf(textFieldNome.getText());
		funcionario.setGenero(textFieldNome.getText());
		funcionario.setSalario(textFieldSalario.getText());
		funcionario.setDataNascimento(textFieldDataNascimento.getText());
		funcionario.setRua(textFieldRua.getText());
		funcionario.setNumero(textFieldNumero.getText());
		funcionario.setCidade(textFieldCidade.getText());
		funcionario.setEstado(textFieldEstado.getText());
		funcionario.setBairro(textFieldBairro.getText());
		funcionario.setCep(textFieldCep.getText());
		funcionario.setDistribuidora(distribuidora);

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
		panel.setBounds(10, 0, 770, 339);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNome = new JLabel("Nome: ");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNome.setBounds(10, 14, 66, 14);
		panel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(79, 11, 261, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefone.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTelefone.setBounds(10, 46, 66, 14);
		panel.add(lblTelefone);
		
		textFieldTelefone = new JTextField();
		textFieldTelefone.setBounds(79, 43, 261, 20);
		panel.add(textFieldTelefone);
		textFieldTelefone.setColumns(10);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroNome.setBounds(350, 14, 25, 14);
		panel.add(lblShowErroNome);
		
		lblShowErroTelefone = new JLabel("");
		lblShowErroTelefone.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroTelefone.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTelefone.setBounds(350, 46, 25, 14);
		panel.add(lblShowErroTelefone);
		
		lblCargo = new JLabel("Cargo:");
		lblCargo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCargo.setAlignmentX(0.5f);
		lblCargo.setBounds(10, 78, 66, 14);
		panel.add(lblCargo);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpf.setAlignmentX(0.5f);
		lblCpf.setBounds(10, 110, 66, 14);
		panel.add(lblCpf);
		
		lblGenero = new JLabel("Genero:");
		lblGenero.setHorizontalAlignment(SwingConstants.LEFT);
		lblGenero.setAlignmentX(0.5f);
		lblGenero.setBounds(10, 142, 66, 14);
		panel.add(lblGenero);
		
		lblSalario = new JLabel("Salario:");
		lblSalario.setHorizontalAlignment(SwingConstants.LEFT);
		lblSalario.setAlignmentX(0.5f);
		lblSalario.setBounds(10, 174, 66, 14);
		panel.add(lblSalario);
		
		lblDataNascimento = new JLabel("Nascimento:");
		lblDataNascimento.setHorizontalAlignment(SwingConstants.LEFT);
		lblDataNascimento.setAlignmentX(0.5f);
		lblDataNascimento.setBounds(10, 206, 66, 14);
		panel.add(lblDataNascimento);
		
		textFieldCargo = new JTextField();
		textFieldCargo.setColumns(10);
		textFieldCargo.setBounds(79, 75, 261, 20);
		panel.add(textFieldCargo);
		
		textFieldCpf = new JTextField();
		textFieldCpf.setColumns(10);
		textFieldCpf.setBounds(79, 107, 261, 20);
		panel.add(textFieldCpf);
		
		textFieldGenero = new JTextField();
		textFieldGenero.setColumns(10);
		textFieldGenero.setBounds(79, 139, 261, 20);
		panel.add(textFieldGenero);
		
		textFieldSalario = new JTextField();
		textFieldSalario.setColumns(10);
		textFieldSalario.setBounds(79, 169, 261, 20);
		panel.add(textFieldSalario);
		
		textFieldDataNascimento = new JTextField();
		textFieldDataNascimento.setColumns(10);
		textFieldDataNascimento.setBounds(79, 201, 261, 20);
		panel.add(textFieldDataNascimento);
		
		lblShowErroCargo = new JLabel("");
		lblShowErroCargo.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCargo.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCargo.setBounds(351, 78, 25, 14);
		panel.add(lblShowErroCargo);
		
		lblShowErroCpf = new JLabel("");
		lblShowErroCpf.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCpf.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCpf.setBounds(350, 110, 25, 14);
		panel.add(lblShowErroCpf);
		
		lblShowErroGenero = new JLabel("");
		lblShowErroGenero.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroGenero.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroGenero.setBounds(350, 142, 25, 14);
		panel.add(lblShowErroGenero);
		
		lblShowErroSalario = new JLabel("");
		lblShowErroSalario.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroSalario.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroSalario.setBounds(350, 174, 25, 14);
		panel.add(lblShowErroSalario);
		
		lblShowErroDataNascimento = new JLabel("");
		lblShowErroDataNascimento.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroDataNascimento.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroDataNascimento.setBounds(350, 206, 25, 14);
		panel.add(lblShowErroDataNascimento);
		
		lblRua = new JLabel("Rua:");
		lblRua.setHorizontalAlignment(SwingConstants.LEFT);
		lblRua.setAlignmentX(0.5f);
		lblRua.setBounds(406, 14, 60, 14);
		panel.add(lblRua);
		
		textFieldRua = new JTextField();
		textFieldRua.setColumns(10);
		textFieldRua.setBounds(474, 11, 251, 20);
		panel.add(textFieldRua);
		
		lblShowErroRua = new JLabel("");
		lblShowErroRua.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroRua.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroRua.setBounds(735, 14, 25, 14);
		panel.add(lblShowErroRua);
		
		lblNumero = new JLabel("Numero:");
		lblNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumero.setAlignmentX(0.5f);
		lblNumero.setBounds(406, 43, 60, 14);
		panel.add(lblNumero);
		
		lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblCidade.setAlignmentX(0.5f);
		lblCidade.setBounds(407, 75, 60, 14);
		panel.add(lblCidade);
		
		lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstado.setAlignmentX(0.5f);
		lblEstado.setBounds(406, 107, 60, 14);
		panel.add(lblEstado);
		
		lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.LEFT);
		lblBairro.setAlignmentX(0.5f);
		lblBairro.setBounds(406, 139, 60, 14);
		panel.add(lblBairro);
		
		lblCep = new JLabel("Cep:");
		lblCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblCep.setAlignmentX(0.5f);
		lblCep.setBounds(406, 171, 60, 14);
		panel.add(lblCep);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setColumns(10);
		textFieldNumero.setBounds(474, 40, 251, 20);
		panel.add(textFieldNumero);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(474, 72, 251, 20);
		panel.add(textFieldCidade);
		
		textFieldEstado = new JTextField();
		textFieldEstado.setColumns(10);
		textFieldEstado.setBounds(474, 104, 251, 20);
		panel.add(textFieldEstado);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(474, 136, 251, 20);
		panel.add(textFieldBairro);
		
		textFieldCep = new JTextField();
		textFieldCep.setColumns(10);
		textFieldCep.setBounds(474, 168, 251, 20);
		panel.add(textFieldCep);
		
		lblShowErroNumero = new JLabel("");
		lblShowErroNumero.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroNumero.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNumero.setBounds(735, 43, 25, 14);
		panel.add(lblShowErroNumero);
		
		lblShowErroCidade = new JLabel("");
		lblShowErroCidade.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCidade.setBounds(735, 75, 25, 14);
		panel.add(lblShowErroCidade);
		
		lblShowErroEstado = new JLabel("");
		lblShowErroEstado.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEstado.setBounds(735, 107, 25, 14);
		panel.add(lblShowErroEstado);
		
		lblShowErroBairro = new JLabel("");
		lblShowErroBairro.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroBairro.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroBairro.setBounds(735, 139, 25, 14);
		panel.add(lblShowErroBairro);
		
		lblShowErroCep = new JLabel("");
		lblShowErroCep.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCep.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCep.setBounds(735, 171, 25, 14);
		panel.add(lblShowErroCep);
		
		lblDistribuidora = new JLabel("Distribuidora");
		lblDistribuidora.setHorizontalAlignment(SwingConstants.LEFT);
		lblDistribuidora.setAlignmentX(0.5f);
		lblDistribuidora.setBounds(10, 242, 60, 14);
		panel.add(lblDistribuidora);
		
		textFieldDistribuidora = new JTextField();
		textFieldDistribuidora.setEditable(false);
		textFieldDistribuidora.setColumns(10);
		textFieldDistribuidora.setBounds(79, 237, 261, 20);
		panel.add(textFieldDistribuidora);
		
		btnBuscarDistribuidora = new JButton("Distribuidora");
		btnBuscarDistribuidora.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/search.png")));
		btnBuscarDistribuidora.setMnemonic(KeyEvent.VK_D);
		btnBuscarDistribuidora.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnBuscarDistribuidora.setBounds(350, 238, 98, 23);
		panel.add(btnBuscarDistribuidora);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 348, 770, 50);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnIncluir = new JButton("");
		btnIncluir.setToolTipText("");
		btnIncluir.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/book_add.png")));
		btnIncluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnIncluir.setBounds(10, 11, 89, 23);
		panel_1.add(btnIncluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/application_edit.png")));
		btnAlterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlterar.setBounds(114, 11, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExcluir.setBounds(213, 11, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setIcon(new ImageIcon(FuncionarioFrame.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(312, 11, 105, 23);
		panel_1.add(btnFechar);		
		
		lblShowErroNome.setVisible(false);
		lblShowErroTelefone.setVisible(false);
		lblShowErroCargo.setVisible(false);
		lblShowErroCpf.setVisible(false);
		lblShowErroGenero.setVisible(false);
		lblShowErroSalario.setVisible(false);
		lblShowErroDataNascimento.setVisible(false);
		lblShowErroRua.setVisible(false);
		lblShowErroNumero.setVisible(false);
		lblShowErroCidade.setVisible(false);
		lblShowErroEstado.setVisible(false);
		lblShowErroBairro.setVisible(false);
		lblShowErroCep.setVisible(false);
		 
		
	}

	public Distribuidora getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}
}
