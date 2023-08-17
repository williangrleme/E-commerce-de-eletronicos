package com.projeto.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.Menu.MenuPrincipal;
import com.projeto.model.model.Fornecedor;
import com.projeto.model.model.Produto;
import com.projeto.model.service.FornecedorService;
import com.projeto.view.fornecedor.FornecedorFrame;
import com.projeto.view.fornecedor.TabelaFornecedor;
import com.projeto.view.produto.TabelaProduto;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Projeto extends JFrame {

	private static final long serialVersionUID = -1087741612689558266L;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Projeto frame = new Projeto();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Projeto() {
		initEvents();
	}
	private void initEvents() {

		MenuPrincipal menu  = new MenuPrincipal();
		menu.setLocationRelativeTo(null);	//centraliza a janela
		menu.setExtendedState(JFrame.MAXIMIZED_BOTH);	//tela cheia
		menu.setVisible(true);
	}
}
