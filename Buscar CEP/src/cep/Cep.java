package cep;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

@SuppressWarnings("serial")
public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JLabel lblNewLabel_3;
	private JTextField txtCidade;
	private JLabel lblNewLabel_4;
	private JComboBox cboUf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cep() {
		setTitle("Buscar CEP");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/216242_home_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setBounds(31, 36, 46, 14);
		contentPane.add(lblNewLabel);

		txtCep = new JTextField();
		txtCep.setBounds(74, 33, 86, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Endereço");
		lblNewLabel_1.setBounds(10, 80, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(74, 77, 294, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Bairro");
		lblNewLabel_2.setBounds(10, 124, 46, 14);
		contentPane.add(lblNewLabel_2);

		txtBairro = new JTextField();
		txtBairro.setBounds(74, 121, 294, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);

		lblNewLabel_3 = new JLabel("Cidade");
		lblNewLabel_3.setBounds(10, 174, 46, 14);
		contentPane.add(lblNewLabel_3);

		txtCidade = new JTextField();
		txtCidade.setBounds(74, 171, 189, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		lblNewLabel_4 = new JLabel("UF");
		lblNewLabel_4.setBounds(273, 174, 46, 14);
		contentPane.add(lblNewLabel_4);

		cboUf = new JComboBox();
		cboUf.setBounds(308, 170, 60, 22);
		contentPane.add(cboUf);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(10, 213, 89, 23);
		contentPane.add(btnLimpar);

		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o CEP");
					txtCep.requestFocus();
				} else {
					buscarCep();
				}
			}
		});
		btnCep.setBounds(230, 32, 89, 23);
		contentPane.add(btnCep);

		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setToolTipText("Sobre");
		btnSobre.setIcon(new ImageIcon(Cep.class.getResource("/img/4016160_watch_about_smartwatch_help_icon.png")));
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBorder(null);
		btnSobre.setBackground(SystemColor.control);
		btnSobre.setBounds(335, 11, 48, 48);
		contentPane.add(btnSobre);
		/* Uso da biblioteca Atxy2k para validação do campo txtCep */
		RestrictedTextField validar = new RestrictedTextField(txtCep);
		validar.setOnlyNums(true);
		validar.setLimit(8);
	}// fim do construtor

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			 for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			        Element element = it.next();
			        if (element.getQualifiedName().equals("cidade")) {
			        	txtCidade.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("bairro")) {
			        	txtBairro.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("uf")) {
			        	cboUf.setSelectedItem(element.getText());
			        }
			        if (element.getQualifiedName().equals("tipo_logradouro")) {
			        	tipoLogradouro = element.getText();
			        }
			        if (element.getQualifiedName().equals("logradouro")) {
			        	logradouro = element.getText();
			        }
			        if(element.getQualifiedName().equals("resultado")) {
			        	resultado = element.getText();
			        	if (resultado.equals("1")) {
			        
			        	} else {
			        		JOptionPane.showMessageDialog(null, "CEP não encontrado");
			        	}
			        }
			 }  
			 //setar o campo endereco
			 txtEndereco.setText(tipoLogradouro + "" + logradouro);
			        
		} catch (Exception e) {
			System.out.println(e);
		}
	}
		
		private void limpar() {
			txtCep.setText(null);
			txtEndereco.setText(null);
			txtBairro.setText(null);
			txtCidade.setText(null);
			cboUf.setSelectedItem(null);
			txtCep.requestFocus();
			
	
	}
}
