package com.projeto.view.pedido;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import com.projeto.model.model.Cliente;
import com.projeto.model.model.Fornecedor;
import com.projeto.model.model.Pedido;
import com.projeto.model.service.PedidoService;
import com.projeto.util.ProcessamentoDados;
import com.projeto.view.cliente.BuscarCliente;
import com.projeto.view.fornecedor.BuscarFornecedor;

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


public class PedidoFrame extends JDialog {

	private static final long serialVersionUID = -2540494189402715146L;
	

	private JPanel contentPane;
	private JButton btnIncluir;
	private JTextField textFieldStatus;
	private JTextField textFieldQuantidade;
	
	private JButton btnFechar;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	private int acao = 0;
	private int linha = 0;
	
	private JTable tabelaPedidoDesign;
	private JLabel lblShowErroStatus;
	private JLabel lblShowErroQuantidade;
	
	private TabelaPedidoModel tabelaPedidoModel;
	private Cliente cliente;
	
	PedidoService pedidoService;
	private Pedido pedido;
	private JLabel lblStatus;
	private JLabel lblQuantidade;
	private JLabel lblCliente;
	private JTextField textFieldCliente;
	private JButton btnCliente;

	public PedidoFrame(JFrame frame, boolean modal, int acao, JTable tabelaPedidoDesign, TabelaPedidoModel tabelaPedidoModel, int linha)  {
		super(frame,modal);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PedidoFrame.class.getResource("/imagens/user.png")));
		setTitle("Cadastro Pedido");
	
		this.acao = acao;
		this.tabelaPedidoDesign = tabelaPedidoDesign;
		this.tabelaPedidoModel = tabelaPedidoModel;
		this.linha = linha;
		
		pedidoService = new PedidoService();
		pedido = new Pedido();
		
		initComponents();
		createEvents(); 
		configuraAcao();
		setResizable(false);		//nao deixa o usuario maximizar a tela
		this.setLocationRelativeTo(null);	//centraliza a tela
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				textFieldStatus.requestFocus();
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
				buscarPedido();
			}
			else {
				if(this.acao == ProcessamentoDados.EXCLUIR_REGISTRO) {
					btnIncluir.setVisible(false);
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(true);
					buscarPedido();
				}
				else {
					if(this.acao == ProcessamentoDados.CONSULTAR_REGISTRO) {
						btnIncluir.setVisible(false);
						btnAlterar.setVisible(false);
						btnExcluir.setVisible(false);
						buscarPedido();
					}
				}
			}
		}
	}
	
	
	private void buscarPedido(){
		pedido = new Pedido();
		pedido = this.tabelaPedidoModel.getPedido(this.linha);
		textFieldStatus.setText(pedido.getStatus());
		textFieldQuantidade.setText(pedido.getQuantidade());
	}
	
	private void limparDadosDigitacao() {
		textFieldStatus.setText("");
		textFieldQuantidade.setText("");
	}
	
	//EVENTOS
	private void createEvents() {
		
		//TIRAR AVISO DE ERRO STATUS
		textFieldStatus.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroStatus.setVisible(false);
			}
		});
		
		//TIRAR AVISO DE ERRO DO QUANTIDADE
		textFieldQuantidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblShowErroQuantidade.setVisible(false);
			}
		});
		//TECLA VAI PRO PROXIMO
		textFieldStatus.addKeyListener(new KeyAdapter() {
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
					pegarDadosPedido();
					incluirPedido();
				}				
			}
		});
		
		//EVENTO ALTERAR
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(verDigitacao() == false) {
					pegarDadosPedido();
					alterarPedido();
				}		
			}
		});
		
		//EVENTO EXCLUIR
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirPedido(); 
			}
		});
		
		//EVENTO BUSCAR CLIENTE
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCliente();
			}
		});
		
	}
	
	
	
	//METODO DE INCLUIR
	private void incluirPedido() {
		pedidoService.save(pedido); //salva no BD
		limparDadosDigitacao();
		tabelaPedidoModel.savePedido(pedido);	//salva na Tabela
		tabelaPedidoDesign.setModel(tabelaPedidoModel); //atualiza a tabela
		tabelaPedidoModel.fireTableDataChanged();	//atualiza a tela 
		}
	
	//METODO DE EXCLUIR
	private void excluirPedido(){
		pedidoService.delete(pedido);	//exclui no BD
		limparDadosDigitacao();
		tabelaPedidoModel.removePedido(this.linha);	//exclui na Tabela
		tabelaPedidoDesign.setModel(tabelaPedidoModel); //atualiza a tabela
		tabelaPedidoModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO DE ALTERAR
	private void alterarPedido() {
		pedidoService.update(pedido);	//Altera no BD
		limparDadosDigitacao();
		tabelaPedidoModel.updatePedido(pedido,this.linha);	//altera na Tabela
		tabelaPedidoDesign.setModel(tabelaPedidoModel);	//atualiza a tabela
		tabelaPedidoModel.fireTableDataChanged();	//atualiza a tela 
	}
	
	//METODO BUSCAR CLIENTE
	protected void buscarCliente() {
        BuscarCliente buscarCliente = new BuscarCliente(new JFrame(), true);
        buscarCliente.setLocationRelativeTo(null);
        buscarCliente.setVisible(true);

        if(buscarCliente.isConfirmado()) {
            cliente = new Cliente();
            cliente = buscarCliente.getCliente();
            textFieldCliente.setText(cliente.getNome());
        }
    }
	
	//VENDO SE TA COM CAMPO EM BRANCO
	private boolean verDigitacao() {
		
		//TRATAMENTO DOS CAMPOS NULOS
			if(ProcessamentoDados.campoDigitacao(textFieldStatus.getText()) == true) {		//se n escreveu nada no campo
				JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
				textFieldStatus.requestFocus(); //volta pro produto
				lblShowErroStatus.setVisible(true);
				return true;
			}
			else {
				if(ProcessamentoDados.campoDigitacao(textFieldQuantidade.getText()) == true) {		//se n escreveu nada no campo
					JOptionPane.showMessageDialog(null, "O campo nao pode estar vazio", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
					textFieldQuantidade.requestFocus(); //volta pro telefone
					lblShowErroQuantidade.setVisible(true);
					return true;
				}
			}
			
		
		//TRATAMENTO DO TAMANHO DAS STRINGS
		if(textFieldStatus.getColumns() >  20) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldStatus.requestFocus(); //volta pro produto
			lblShowErroStatus.setVisible(true);
			return true;
		}
		
		if(textFieldQuantidade.getColumns() >  11) {
			JOptionPane.showMessageDialog(null, "Voce ultrapassou o limite de caractres permitidos nesse campo", "Erro na digitacao", JOptionPane.ERROR_MESSAGE);
			textFieldQuantidade.requestFocus(); //volta pro telefone
			lblShowErroQuantidade.setVisible(true);
			return true;
		}
		
		return false;
	}
	
	private void pegarDadosPedido() {	//pega tudo que o usuario digitou na tela 
		pedido.setStatus(textFieldStatus.getText());
		pedido.setQuantidade(textFieldQuantidade.getText());
		pedido.setCliente(cliente);
		
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
		
		lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblStatus.setBounds(26, 27, 82, 14);
		panel.add(lblStatus);
		
		textFieldStatus = new JTextField();
		textFieldStatus.setBounds(111, 24, 261, 20);
		panel.add(textFieldStatus);
		textFieldStatus.setColumns(10);
		
		lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
		lblQuantidade.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblQuantidade.setBounds(26, 59, 82, 14);
		panel.add(lblQuantidade);
		
		textFieldQuantidade = new JTextField();
		textFieldQuantidade.setColumns(10);
		textFieldQuantidade.setBounds(111, 56, 261, 20);
		panel.add(textFieldQuantidade);
		
		lblShowErroStatus = new JLabel("");
		lblShowErroStatus.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroStatus.setBounds(397, 27, 46, 14);
		panel.add(lblShowErroStatus);
		
		lblShowErroQuantidade = new JLabel("");
		lblShowErroQuantidade.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/application_error.png")));
		lblShowErroQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowErroQuantidade.setBounds(397, 59, 46, 14);
		panel.add(lblShowErroQuantidade);
		
		lblCliente = new JLabel("Cliente");
		lblCliente.setHorizontalAlignment(SwingConstants.LEFT);
		lblCliente.setAlignmentX(0.5f);
		lblCliente.setBounds(26, 91, 84, 14);
		panel.add(lblCliente);
		
		textFieldCliente = new JTextField();
		textFieldCliente.setEditable(false);
		textFieldCliente.setColumns(10);
		textFieldCliente.setBounds(111, 88, 261, 20);
		panel.add(textFieldCliente);
		
		btnCliente = new JButton("Cliente");
		btnCliente.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/search.png")));
		btnCliente.setMnemonic(KeyEvent.VK_D);
		btnCliente.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnCliente.setBounds(382, 84, 98, 23);
		panel.add(btnCliente);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 348, 749, 50);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnIncluir = new JButton("");
		btnIncluir.setToolTipText("");
		btnIncluir.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/book_add.png")));
		btnIncluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnIncluir.setBounds(10, 11, 89, 23);
		panel_1.add(btnIncluir);
		
		btnAlterar = new JButton("");
		btnAlterar.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/application_edit.png")));
		btnAlterar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnAlterar.setBounds(114, 11, 89, 23);
		panel_1.add(btnAlterar);
		
		btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/book_delete.png")));
		btnExcluir.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnExcluir.setBounds(213, 11, 89, 23);
		panel_1.add(btnExcluir);
		
		btnFechar = new JButton("");
		btnFechar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		btnFechar.setIcon(new ImageIcon(PedidoFrame.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBounds(312, 11, 105, 23);
		panel_1.add(btnFechar);
		lblShowErroStatus.setVisible(false);
		lblShowErroQuantidade.setVisible(false);
		 
		
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
