package com.projeto.Menu;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import com.projeto.view.cliente.TabelaCliente;
import com.projeto.view.distribuidora.TabelaDistribuidora;
import com.projeto.view.fornecedor.TabelaFornecedor;
import com.projeto.view.funcionario.TabelaFuncionario;
import com.projeto.view.pedido.TabelaPedido;
import com.projeto.view.produto.TabelaProduto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame{
	private static final long serialVersionUID = -5371086930319324051L;
	private JPanel panel;

	public MenuPrincipal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagens/application_home.png")));
		setTitle("MENU");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuCadastro = new JMenu("Cadastro");
		menuCadastro.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagens/user.png")));
		menuBar.add(menuCadastro);
		
		JMenuItem menuFornecedores = new JMenuItem("Fornecedores");
		menuFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaFornecedor tabelaFornecedor = new TabelaFornecedor();
				panel.add(tabelaFornecedor);
				tabelaFornecedor.setResizable(false);	// deixa alterar o tamanho
				tabelaFornecedor.setVisible(true);
				
			}
		});
		menuCadastro.add(menuFornecedores);
		
		JMenuItem menuProdutos = new JMenuItem("Produtos");
		menuProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaProduto tabelaProduto = new TabelaProduto();
				panel.add(tabelaProduto);
				tabelaProduto.setVisible(true);
			}
		});
		menuCadastro.add(menuProdutos);
		
		JMenuItem menuDistribuidora = new JMenuItem("Distribuidoras");
		menuDistribuidora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaDistribuidora tabelaDistribuidora = new TabelaDistribuidora();
				panel.add(tabelaDistribuidora);
				tabelaDistribuidora.setVisible(true);
			}
		});
		menuCadastro.add(menuDistribuidora);
		
		JMenuItem menuPedido = new JMenuItem("Pedidos");
		menuPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaPedido tabelaPedido = new TabelaPedido();
				panel.add(tabelaPedido);
				tabelaPedido.setVisible(true);
			}
		});
		menuCadastro.add(menuPedido);
		
		JMenuItem menuCliente = new JMenuItem("Clientes");
		menuCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaCliente tabelaCliente = new TabelaCliente();
				panel.add(tabelaCliente);
				tabelaCliente.setVisible(true);
			}
		});
		menuCadastro.add(menuCliente);
		
		JMenuItem menuFuncionario = new JMenuItem("Funcionarios");
		menuFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TabelaFuncionario tabelaFuncionario = new TabelaFuncionario();
				panel.add(tabelaFuncionario);
				tabelaFuncionario.setVisible(true);
			}
		});
		menuCadastro.add(menuFuncionario);
		
		JMenuItem menuSair = new JMenuItem("Sair");
		menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menuCadastro.add(menuSair);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 703, 341);
		
		setContentPane(panel);
        panel.setLayout(null);
	}
}
