package com.projeto.view.produto;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.projeto.model.model.Produto;
import com.projeto.model.service.ProdutoService;
import com.projeto.model.service.JasperReportsService;
import com.projeto.model.service.reports.RelatorioProduto;
import com.projeto.util.PrintJasperReports;
import com.projeto.util.ProcessamentoDados;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

import com.projeto.util.PdfViewer;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ProdutoReports extends JFrame {


	private static final long serialVersionUID = 3779067772056556805L;
	private JPanel contentPane;
	private JTextField textFieldProdutoIni;
	private JTextField textFieldProdutoFim;
	private JButton btnRelatorio;
	private JButton btnFechar;
	private JButton btnProdutoInicial;
	private JButton btnProdutoFinal;
	
	private JComboBox<String> comboBoxTipoRelatorio;
	
	private DefaultListModel<String> listProdutoModel;
	private Integer produtoSelecionado[] = {};
	
	private Produto produto;
	
	private Integer CODIGO_INICIAL 	= 0;
	private Integer CODIGO_FINAL 	= 0; 
	private Integer TOTAL_REGISTRO  = 0;
	private Integer totalRegistros 	= 0;	
	private JList<String> listProdutoInicial;
	private JPanel panel_1;
	private JList<String> listProdutoFinal;
	private JPanel panel_2;
	
	
	public ProdutoReports(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
		initComponents();
		createEvents();
	}
	
	private void viewReportsLista() {

		Integer numero = 0;
		
		if(CODIGO_INICIAL == 0 || CODIGO_FINAL == 0) {
			CODIGO_INICIAL = 1;
			CODIGO_FINAL = totalRegistros;
			TOTAL_REGISTRO = totalRegistros;
		}
		
		if(CODIGO_INICIAL > CODIGO_FINAL) {
			numero = CODIGO_FINAL;
			CODIGO_FINAL = CODIGO_INICIAL;
			CODIGO_INICIAL = numero;
		}
		
		
		ProdutoService produtoService = new ProdutoService();
		
		List<Produto> listaProduto = produtoService.carregarListaProdutoPorParamentro(CODIGO_INICIAL, CODIGO_FINAL);
		
		if(TOTAL_REGISTRO == 0) {
			TOTAL_REGISTRO = listaProduto.size();
		}
		
		RelatorioProduto relatorioProduto =  new RelatorioProduto();
		
		relatorioProduto.generateReports(listaProduto);
		showReports();
	}
	
	
	private void showReports() {
			try { 
				
				JFrame frame = new JFrame(); 
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				File file = new File("/temp/relatorio_produto.pdf"); 
				@SuppressWarnings("resource")
				RandomAccessFile raf = new RandomAccessFile(file, "r"); 
				FileChannel channel = raf.getChannel(); 
				ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size()); 
				final PDFFile pdffile = new PDFFile(buf); 
				PdfViewer pdfViewer = new PdfViewer(); 
				pdfViewer.setPDFFile(pdffile); 
				frame.add(pdfViewer); 
				frame.pack(); 
				frame.setVisible(true); 
				PDFPage page = pdffile.getPage(0); 
				pdfViewer.getPagePanel().showPage(page); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			}

			
	}
		

	private void viewReportsSQL() {
		
		Integer numero = 0;
		
		if(CODIGO_INICIAL == 0 || CODIGO_FINAL == 0) {
			CODIGO_INICIAL = 1;
			CODIGO_FINAL = totalRegistros;
		}
		
		if(CODIGO_INICIAL > CODIGO_FINAL) {
			numero = CODIGO_FINAL;
			CODIGO_FINAL = CODIGO_INICIAL;
			CODIGO_INICIAL = numero;
		}
		
		JasperReportsService jrp = new JasperReportsService();
		PrintJasperReports printJasperReports = new PrintJasperReports();
		
		printJasperReports.setFile("Produto");
		printJasperReports.addParametros("CODIGO_INICIAL", CODIGO_INICIAL);
		printJasperReports.addParametros("CODIGO_FINAL", CODIGO_FINAL);
		
		JasperPrint jasperPrint = jrp.gerarRelatorioPorSQL(printJasperReports);
		
		viewerReports(jasperPrint);
	}
	
	private void viewerReports(JasperPrint jasperPrint) {
		JFrame frameRelatorio = new JFrame();
		JRViewer viewer =  new JRViewer(jasperPrint);
		
		frameRelatorio.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		frameRelatorio.setExtendedState(MAXIMIZED_BOTH);
		frameRelatorio.setTitle("Vizualicao de Relatorio do Sistema");
		frameRelatorio.getContentPane().add(viewer);
		frameRelatorio.setVisible(true);
	}
	
	private void BuscarProdutoInicial() {
		BuscarProduto buscarProduto = new BuscarProduto(new JFrame(), true);
		buscarProduto.setLocationRelativeTo(null);
		buscarProduto.setVisible(true);
		
		if(buscarProduto.isConfirmado()) {
			produto = new Produto();
			produto = buscarProduto.getProduto();
			textFieldProdutoIni.setText(produto.getNome());
			CODIGO_INICIAL = produto.getId();
		}
	}
	
	private void pesquisarProdutoInicial() {
		
		listProdutoModel.removeAllElements();
		listProdutoInicial.setVisible(true);
		textFieldProdutoFim.setVisible(false);
		
		ProdutoService produtoService = new ProdutoService();
		
		List<Produto> listaProduto = produtoService.carregarListaProduto(textFieldProdutoIni.getText());
		
		produtoSelecionado = new Integer[listaProduto.size()];
		
		for(int i = 0; i < listaProduto.size(); i++) {
			listProdutoModel.addElement(listaProduto.get(i).getNome());
			produtoSelecionado[i] = listaProduto.get(i).getId();
		}
		
		listProdutoInicial.setModel(listProdutoModel);
		
	}
	
	
	
	
	private void BuscarProdutoFinal() {
		BuscarProduto buscarProduto = new BuscarProduto(new JFrame(), true);
		buscarProduto.setLocationRelativeTo(null);
		buscarProduto.setVisible(true);
		
		if(buscarProduto.isConfirmado()) {
			produto = new Produto();
			produto = buscarProduto.getProduto();
			textFieldProdutoFim.setText(produto.getNome());
			CODIGO_FINAL = produto.getId();
		}
	}
	
	private void pesquisarProdutoFinal() {
		
		listProdutoModel.removeAllElements();
		listProdutoFinal.setVisible(true);
		btnRelatorio.setVisible(false);
		btnFechar.setVisible(false);
		
		ProdutoService produtoService = new ProdutoService();
		
		List<Produto> listaProduto = produtoService.carregarListaProduto(textFieldProdutoFim.getText());
		
		produtoSelecionado = new Integer[listaProduto.size()];
		
		for(int i = 0; i < listaProduto.size(); i++) {
			listProdutoModel.addElement(listaProduto.get(i).getNome());
			produtoSelecionado[i] = listaProduto.get(i).getId();
		}
		
		listProdutoFinal.setModel(listProdutoModel);
		
	}
	
	private void escolhaRelatorio() {
		if(comboBoxTipoRelatorio.getSelectedItem() == "Lista Alfabética") {
			viewReportsLista();
		}
		else if(comboBoxTipoRelatorio.getSelectedItem() == "Lista ID") {
			viewReportsSQL();
		}
	}
	
	
	private void createEvents() {
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escolhaRelatorio();
				dispose();
			}
		});
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnProdutoInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarProdutoInicial();
			}
		});
		btnProdutoFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarProdutoFinal();
			}
		});
		textFieldProdutoIni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listProdutoInicial.setVisible(false);
					textFieldProdutoFim.setVisible(true);
					btnProdutoFinal.requestFocus();
				}
				else pesquisarProdutoInicial();
				
			}
		});
		textFieldProdutoFim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					listProdutoFinal.setVisible(false);
					btnRelatorio.setVisible(true);
					btnFechar.setVisible(true);
					btnRelatorio.requestFocus();
				}
				else pesquisarProdutoFinal();
				
			}
		});
		listProdutoInicial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listProdutoInicial.getSelectedIndex();
				Integer id = produtoSelecionado[item];
				
				Produto produto = new Produto();
				ProdutoService produtoService = new ProdutoService();
				
				produto = produtoService.findProdutoById(id);
				textFieldProdutoIni.setText(produto.getNome());
				CODIGO_INICIAL = produto.getId();
				listProdutoInicial.setVisible(false);
				textFieldProdutoFim.setVisible(true);
				btnProdutoFinal.requestFocus();
			}
		});
		listProdutoFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer item = listProdutoFinal.getSelectedIndex();
				Integer id = produtoSelecionado[item];
				
				Produto produto = new Produto();
				ProdutoService produtoService = new ProdutoService();
				
				produto = produtoService.findProdutoById(id);
				textFieldProdutoFim.setText(produto.getNome());
				CODIGO_FINAL = produto.getId();
				listProdutoFinal.setVisible(false);
				btnRelatorio.setVisible(true);
				btnFechar.setVisible(true);
				btnRelatorio.requestFocus();
			}
		});	
	}
	
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProdutoReports.class.getResource("/imagens/pdf.png")));
		setTitle("Relatorio Produto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listProdutoModel = new DefaultListModel<String>();
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 474, 238);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(99, 100, 254, 2);
		panel.add(separator);
		
		JLabel lblCodigoInicial = new JLabel("Produto Inicial:");
		lblCodigoInicial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCodigoInicial.setBounds(10, 84, 81, 14);
		panel.add(lblCodigoInicial);
		
		textFieldProdutoIni = new JTextField();
		
		textFieldProdutoIni.setBackground(SystemColor.menu);
		textFieldProdutoIni.setBorder(null);
		textFieldProdutoIni.setBounds(99, 80, 254, 20);
		panel.add(textFieldProdutoIni);
		textFieldProdutoIni.setColumns(10);
		
		JLabel lblProdutoFinal = new JLabel("Produto Final:");
		lblProdutoFinal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProdutoFinal.setBounds(10, 129, 81, 14);
		panel.add(lblProdutoFinal);
		
		textFieldProdutoFim = new JTextField();
		textFieldProdutoFim.setBackground(SystemColor.menu);
		textFieldProdutoFim.setBorder(null);
		textFieldProdutoFim.setBounds(99, 126, 254, 20);
		panel.add(textFieldProdutoFim);
		textFieldProdutoFim.setColumns(10);
		
		btnRelatorio = new JButton("Relatorio");
		btnRelatorio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRelatorio.setIcon(new ImageIcon(ProdutoReports.class.getResource("/imagens/pdf.png")));
		btnRelatorio.setBounds(99, 204, 109, 23);
		panel.add(btnRelatorio);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setIcon(new ImageIcon(ProdutoReports.class.getResource("/imagens/iconFechar.png")));
		btnFechar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnFechar.setBounds(244, 204, 109, 23);
		panel.add(btnFechar);
		
		btnProdutoInicial = new JButton("Buscar");
		btnProdutoInicial.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProdutoInicial.setIcon(new ImageIcon(ProdutoReports.class.getResource("/imagens/search.png")));
		btnProdutoInicial.setBounds(372, 80, 89, 23);
		panel.add(btnProdutoInicial);
		
		btnProdutoFinal = new JButton("Buscar");
		btnProdutoFinal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnProdutoFinal.setIcon(new ImageIcon(ProdutoReports.class.getResource("/imagens/search.png")));
		btnProdutoFinal.setBounds(372, 125, 89, 23);
		panel.add(btnProdutoFinal);
		
		JLabel lblTipoRelatorio = new JLabel("Tipo Relat\u00F3rio:");
		lblTipoRelatorio.setBounds(10, 34, 81, 14);
		panel.add(lblTipoRelatorio);
		
		comboBoxTipoRelatorio = new JComboBox<String>();
		comboBoxTipoRelatorio.setModel(new DefaultComboBoxModel<String>(new String[] {"Selecione um tipo", "Lista Alfabética", "Lista ID"}));
		comboBoxTipoRelatorio.setBounds(99, 30, 162, 22);
		panel.add(comboBoxTipoRelatorio);
		
		panel_1 = new JPanel();
		panel_1.setBounds(99, 100, 254, 69);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 45, 254, 2);
		panel_1.add(separator_1);
		
		listProdutoInicial = new JList<String>();
		listProdutoInicial.setBounds(0, 0, 254, 73);
		panel_1.add(listProdutoInicial);
		listProdutoInicial.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_2 = new JPanel();
		panel_2.setBounds(99, 145, 254, 82);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		listProdutoFinal = new JList<String>();
		listProdutoFinal.setBorder(new LineBorder(new Color(0, 0, 0)));
		listProdutoFinal.setBounds(0, 0, 254, 82);
		panel_2.add(listProdutoFinal);
		
		
		listProdutoFinal.setVisible(false);
		listProdutoInicial.setVisible(false);
		
	}
}
