package com.projeto.view.fornecedor;

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
import com.projeto.model.service.FornecedorService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.distribuidora.BuscarDistribuidora;

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

public class FornecedorFrame extends JDialog {

	private static final long serialVersionUID = 1250262615670412492L;

	private JPanel contentPane;
	private JButton btnIncluir;
	private JTextField textFieldNome;
	private JTextField textFieldCnpj;
	private JTextField textFieldTelefone;
	
	private JButton btnFechar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaFornecedorDesign;
	private JLabel lblShowErroNome;
	private JLabel lblShowErroCnpj;
	private JLabel lblShowErroTelefone;
	
	private TabelaFornecedorModel tabelaFornecedorModel;
	private Distribuidora distribuidora;
	
	FornecedorService fornecedorService;
	private Fornecedor fornecedor;
	private JTextField textFieldCpf;
	private JLabel lblEmail;
	private JLabel lblCpf;
	private JTextField textFieldEmail;
	private JLabel lblShowErroEmail;
	private JLabel lblShowErroCpf;
	private JTextField textFieldDistribuidora;
	private JButton btnBuscarDistribuidora;
		
	public FornecedorFrame(JFrame frame, boolean modal, int acao, JTable tabelaFornecedorDesign, TabelaFornecedorModel tabelaFornecedorModel, int linha) {
		
		super(frame,modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FornecedorFrame.class.getResource("/imagens/user.png")));
		setTitle("Cadastro Fornecedor");
	
		this.acao = acao;
		this.tabelaFornecedorDesign = tabelaFornecedorDesign;
		this.tabelaFornecedorModel = tabelaFornecedorModel;
		this.linha = linha;
		
		fornecedorService = new FornecedorService();
		fornecedor = new Fornecedor();
		
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
				buscarFornecedor();
			}
			else {
				if(this.acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnIncluir.setVisible(false);
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(true);
					buscarFornecedor();
				}
				else {
					if(this.acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnIncluir.setVisible(false);
						btnAlterar.setVisible(false);
						btnExcluir.setVisible(false);
						buscarFornecedor();
					}
				}
			}
		}
	}

	private void buscarFornecedor(){
		fornecedor = new Fornecedor();
		fornecedor = this.tabelaFornecedorModel.getFornecedor(this.linha);
		textFieldNome.setText(fornecedor.getNome());	
		textFieldCnpj.setText(fornecedor.getCnpj());
		textFieldCpf.setText(fornecedor.getCpf());
		textFieldEmail.setText(fornecedor.getEmail());
		textFieldTelefone.setText(fornecedor.getTelefone());
	}
	
	private void limparDadosDigitacao() {
		textFieldNome.setText("");	
		textFieldCnpj.setText("");
		textFieldCpf.setText("");
		textFieldEmail.setText("");
		textFieldTelefone.setText("");
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
		
		//TIRAR AVISO DE ERRO CNPJ
		textFieldCnpj.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCnpj.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DO CPF
		textFieldCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroCpf.setVisible(false);
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
		
		//TECLA VAI PRO PROXIMO
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					textFieldCnpj.requestFocus();
				}
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					textFieldCnpj.requestFocus();
				}
			}
		});
		//TECLA VAI PRO PROXIMO
		textFieldCnpj.addKeyListener(new KeyAdapter() {
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
					pegarDadosFornecedor();
					incluirFornecedor();
				}				
			}
		});
		
		//EVENTO ALTERAR
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosFornecedor();
					alterarFornecedor();
				}		
			}
		});
		
		//EVENTO EXCLUIR
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor(); 
				dispose();
			}
		});
		
		//EVENTO BUSCAR DISTRIBUIDORA
		btnBuscarDistribuidora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarDistribuidora();
			}
		});
		
	}
	
	//METODO DE INCLUIR
	private void incluirFornecedor() {
		fornecedorService.save(fornecedor); //salva no BD
		limparDadosDigitacao();
		tabelaFornecedorModel.saveFornecedor(fornecedor);	//salva na Tabela
		tabelaFornecedorDesign.setModel(tabelaFornecedorModel); //atualiza a tabela
		tabelaFornecedorModel.fireTableDataChanged();	//atualiza a tela 
		}
	
	//METODO DE EXCLUIR
	private void excluirFornecedor(){
		fornecedorService.delete(fornecedor);	//exclui no BD
		limparDadosDigitacao();
		tabelaFornecedorModel.removeFornecedor(this.linha);	//exclui na Tabela
		tabelaFornecedorDesign.setModel(tabelaFornecedorModel); //atualiza a tabela
		tabelaFornecedorModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO DE ALTERAR
	private void alterarFornecedor() {
		fornecedorService.update(fornecedor);	//Altera no BD
		limparDadosDigitacao();
		tabelaFornecedorModel.updateFornecedor(fornecedor,this.linha);	//altera na Tabela
		tabelaFornecedorDesign.setModel(tabelaFornecedorModel);	//atualiza a tabela
		tabelaFornecedorModel.fireTableDataChanged();	//atualiza a tela 
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
			if(ProcessamentoDados.campoDigitacao(textFieldCnpj.getText()) == true) {		//se n escreveu nada no campo
				JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
				textFieldCnpj.requestFocus(); //volta pro produto
				lblShowErroCnpj.setVisible(true);
				return true;
			}
			else {
				if(ProcessamentoDados.campoDigitacao(textFieldTelefone.getText()) == true) {		//se n escreveu nada no campo
					JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldTelefone.requestFocus(); //volta pro telefone
					lblShowErroTelefone.setVisible(true);
					return true;
				}
				else {
					if(ProcessamentoDados.campoDigitacao(textFieldCpf.getText()) == true) {		//se n escreveu nada no campo
						JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
						textFieldCpf.requestFocus(); //volta pro telefone
						lblShowErroCpf.setVisible(true);
						return true;
					}
					else {
						if(ProcessamentoDados.campoDigitacao(textFieldEmail.getText()) == true) {		//se n escreveu nada no campo
							JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
							textFieldEmail.requestFocus(); //volta pro telefone
							lblShowErroEmail.setVisible(true);
							return true;
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
		
		if(textFieldCnpj.getColumns() >  14) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCnpj.requestFocus(); //volta pro produto
			lblShowErroCnpj.setVisible(true);
			return true;
		}
		
		if(textFieldCpf.getColumns() >  11) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldCpf.requestFocus(); //volta pro telefone
			lblShowErroCpf.setVisible(true);
			return true;
		}
		
		if(textFieldEmail.getColumns() >  80) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldEmail.requestFocus(); //volta pro telefone
			lblShowErroEmail.setVisible(true);
			return true;
		}
		
		if(textFieldTelefone.getColumns() >  20) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldTelefone.requestFocus(); //volta pro telefone
			lblShowErroTelefone.setVisible(true);
			return true;
		}
		
		return false;
	}
	
	private void pegarDadosFornecedor() {	//pega tudo que o usuario digitou na tela 
		fornecedor.setNome(textFieldNome.getText());	
		fornecedor.setCnpj(textFieldCnpj.getText());
		fornecedor.setCpf(textFieldCpf.getText());
		fornecedor.setEmail(textFieldEmail.getText());
		fornecedor.setTelefone(textFieldTelefone.getText());
		fornecedor.setDistribuidora(distribuidora);
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
		lblNome.setBounds(26, 35, 66, 14);
		panel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(121, 35, 261, 20);
		panel.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblCpnj = new JLabel("CNPJ:");
		lblCpnj.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpnj.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCpnj.setBounds(26, 67, 66, 14);
		panel.add(lblCpnj);
		
		textFieldCnpj = new JTextField();
		textFieldCnpj.setBounds(121, 67, 261, 20);
		panel.add(textFieldCnpj);
		textFieldCnpj.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		lblTelefone.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTelefone.setBounds(26, 166, 66, 14);
		panel.add(lblTelefone);
		
		textFieldTelefone = new JTextField();
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBounds(121, 163, 261, 20);
		panel.add(textFieldTelefone);
		
		lblShowErroNome = new JLabel("");
		lblShowErroNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroNome.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroNome.setBounds(407, 38, 46, 14);
		panel.add(lblShowErroNome);
		
		lblShowErroCnpj = new JLabel("");
		lblShowErroCnpj.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCnpj.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCnpj.setBounds(407, 70, 46, 14);
		panel.add(lblShowErroCnpj);
		
		lblShowErroTelefone = new JLabel("");
		lblShowErroTelefone.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroTelefone.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroTelefone.setBounds(407, 166, 46, 14);
		panel.add(lblShowErroTelefone);
		
		lblCpf = new JLabel("CPF");
		lblCpf.setBounds(26, 99, 66, 14);
		panel.add(lblCpf);
		
		textFieldCpf = new JTextField();
		textFieldCpf.setBounds(121, 99, 261, 20);
		panel.add(textFieldCpf);
		textFieldCpf.setColumns(10);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(26, 131, 66, 14);
		panel.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(121, 131, 261, 20);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		lblShowErroEmail = new JLabel("");
		lblShowErroEmail.setVisible(false);
		lblShowErroEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroEmail.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroEmail.setBounds(407, 134, 46, 14);
		panel.add(lblShowErroEmail);
		
		lblShowErroCpf = new JLabel("");
		lblShowErroCpf.setVisible(false);
		lblShowErroCpf.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroCpf.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroCpf.setBounds(407, 102, 46, 14);
		panel.add(lblShowErroCpf);
		
		JLabel lblDistribuidora = new JLabel("Distribuidora");
		lblDistribuidora.setHorizontalAlignment(SwingConstants.LEFT);
		lblDistribuidora.setAlignmentX(0.5f);
		lblDistribuidora.setBounds(26, 198, 66, 14);
		panel.add(lblDistribuidora);
		
		textFieldDistribuidora = new JTextField();
		textFieldDistribuidora.setEditable(false);
		textFieldDistribuidora.setColumns(10);
		textFieldDistribuidora.setBounds(121, 194, 358, 20);
		panel.add(textFieldDistribuidora);
		
		btnBuscarDistribuidora = new JButton("Distribuidora");
		btnBuscarDistribuidora.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/search.png")));
		btnBuscarDistribuidora.setMnemonic(KeyEvent.VK_D);
		btnBuscarDistribuidora.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnBuscarDistribuidora.setBounds(489, 194, 98, 23);
		panel.add(btnBuscarDistribuidora);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 348, 749, 50);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnIncluir = new JButton("");
		btnIncluir.setToolTipText("");
		btnIncluir.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/book_add.png")));
		btnIncluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnIncluir.setBounds(10, 11, 89, 23);
		panel_1.add(btnIncluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/application_edit.png")));
		btnAlterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlterar.setBounds(114, 11, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExcluir.setBounds(213, 11, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setIcon(new ImageIcon(FornecedorFrame.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(312, 11, 105, 23);
		panel_1.add(btnFechar);		
		
		lblShowErroNome.setVisible(false);
		lblShowErroTelefone.setVisible(false);
		lblShowErroCnpj.setVisible(false);
		 
	}
	
	public Distribuidora getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(Distribuidora distribuidora) {
		this.distribuidora = distribuidora;
	}
}
