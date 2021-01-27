package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controler.Processamento;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JProgressBar;

public class Janela extends JFrame {

	private JPanel contentPane;
	private JTextField textRetorno;
	private JTextField textVenda;
	private JTextField textCompra;
	private JTextField textn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Janela frame = new Janela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public Janela() throws ParseException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 240);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTexto = new JLabel("Processamento Remessa");
		lblTexto.setBounds(10, 0, 187, 14);
		panel.add(lblTexto);
		
		JLabel lblInicial = new JLabel("Numero Nota Saida:");
		lblInicial.setBounds(10, 25, 144, 14);
		panel.add(lblInicial);
		
		JLabel lblFinal = new JLabel("Numero Nota Entrada:");
		lblFinal.setBounds(10, 70, 126, 14);
		panel.add(lblFinal);
		
		textVenda = new JTextField();
		textVenda.setBounds(10, 39, 113, 20);
		panel.add(textVenda);
		textVenda.setColumns(10);
		
		textCompra = new JTextField();
		textCompra.setBounds(10, 85, 377, 20);
		panel.add(textCompra);
		textCompra.setColumns(10);
		
		JButton btnProcessar = new JButton("Processar");
		btnProcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Processamento processo = new Processamento();
				
				try {
					processo.processo(textCompra.getText(), textVenda.getText(), textn.getText());
					textRetorno.setText("OK");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnProcessar.setBounds(285, 112, 103, 23);
		panel.add(btnProcessar);
		
		textRetorno = new JTextField();
		textRetorno.addActionListener(null);
		textRetorno.setEditable(false);
		textRetorno.setBounds(0, 209, 414, 20);
		panel.add(textRetorno);
		textRetorno.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("OBS: Numero da nota sem 0 separado por VIRGULA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_1.setToolTipText("");
		lblNewLabel_1.setBounds(10, 109, 291, 14);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 146, 377, 59);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nota Para Transforma Em Entrada:");
		lblNewLabel.setBounds(10, 11, 346, 14);
		panel_1.add(lblNewLabel);
		
		textn = new JTextField();
		textn.setBounds(10, 31, 131, 20);
		panel_1.add(textn);
		textn.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Processamento processo = new Processamento();
				processo.baixarDocumento(textn.getText());
			}
		});
		btnOk.setBounds(278, 30, 99, 23);
		panel_1.add(btnOk);
	
	}
}
