package JDBC;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * @author Arijit
 *
 */
public class BankingSystem extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textField, textField_1, textField_2, usernameField;
	private static JLabel lblNewLabel, lblNewLabel_1, lblTryAgain, label;
	private static JPasswordField passwordField;
	private static JButton btnUpdateAccount, btnDeleteAccount, btnAllAccounts, btnNewButton, btnCreateAccount,
			btnWithdraw, btnConnect, button, btnDeposit, btnDelete, btnLogout, btnBack;
	private static List<JButton> buttons;
	private static AccountsDAO dao;
	private static JTable j;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankingSystem frame = new BankingSystem();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void login(boolean set)

	{
		for (JButton b : buttons) {
			b.setVisible(set);
		}

	}

	private static void loginButtons(boolean set)

	{
		usernameField.setVisible(set);
		usernameField.setText("");

		passwordField.setVisible(set);
		passwordField.setText("");

		lblNewLabel.setVisible(set);
		lblNewLabel.setText("Username");

		lblNewLabel_1.setVisible(set);
		lblNewLabel_1.setText("Password");

		btnConnect.setVisible(set);
		btnConnect.setText("Connect");

	}

	private static void hideLoginButtons(boolean set)

	{
		BankingSystem.loginButtons(set);
		textField.setVisible(set);
		textField.setText("");
		textField_1.setVisible(set);
		textField_2.setVisible(set);
		lblTryAgain.setVisible(set);
	}

	private static void createAccountButtons(boolean set)

	{
		textField.setVisible(set);
		textField.setText("");

		textField_1.setVisible(set);
		textField_1.setText("");

		textField_2.setVisible(set);
		textField_2.setText("");

		lblNewLabel.setVisible(set);
		lblNewLabel.setText("User Id");

		lblNewLabel_1.setVisible(set);
		lblNewLabel_1.setText("Full Name");

		label.setVisible(set);

		button.setVisible(set);
	}

	private static void depositAndWithdrawButtons(boolean set, String flag)

	{
		textField.setVisible(set);
		textField.setText("");

		textField_1.setVisible(set);
		textField_1.setText("");

		lblNewLabel.setVisible(set);
		lblNewLabel.setText("User Id");

		lblNewLabel_1.setVisible(set);
		lblNewLabel_1.setText("Amount");

		btnDeposit.setVisible(set);
		if (flag.equals("d")) {
			btnDeposit.setText("Deposit");
		} else {
			btnDeposit.setText("Withdraw");
		}
	}

	private static void checkAndDeleteButtons(boolean set, String flag)

	{
		textField.setVisible(set);
		textField.setText("");

		lblNewLabel.setVisible(set);
		lblNewLabel.setText("User Id");

		btnDelete.setVisible(set);
		btnDelete.setBounds(206, 189, 165, 39);

		if (flag.equals("c")) {
			btnDelete.setText("Get balance");
		} else if (flag.equals("d")) {
			btnDelete.setText("Delete");
		}
	}

	/**
	 * Create the frame.
	 * 
	 */
	public BankingSystem() {
		buttons = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 546, 573);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnUpdateAccount = new JButton("Deposit ");
		buttons.add(btnUpdateAccount);

		btnUpdateAccount.setBounds(277, 248, 188, 53);
		btnUpdateAccount.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnUpdateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BankingSystem.login(false);
				BankingSystem.depositAndWithdrawButtons(true, "d");
				btnBack.setVisible(true);

			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnUpdateAccount);

		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = textField.getText();
				Double amount = Double.parseDouble(textField_1.getText());
				if (btnDeposit.getText().equals("Deposit")) {
					try {
						String msg = dao.creditIntoAccount(id, amount);
						BankingSystem.depositAndWithdrawButtons(true, "d");
						lblTryAgain.setText(msg);
						lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 18));
						lblTryAgain.setBounds(76, 80, 581, 51);
						lblTryAgain.setForeground(Color.BLUE);
						lblTryAgain.setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						String msg = dao.debitFromAccount(id, amount);
						BankingSystem.depositAndWithdrawButtons(true, "w");
						lblTryAgain.setText(msg);
						lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 18));
						if (!msg.equals("Insufficient Balance")) {
							lblTryAgain.setBounds(76, 80, 581, 51);
							lblTryAgain.setForeground(Color.BLUE);
						} else {
							lblTryAgain.setBounds(166, 80, 581, 51);
							lblTryAgain.setForeground(Color.RED);
						}
						lblTryAgain.setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnDeposit.setBounds(206, 359, 165, 39);
		btnDeposit.setFont(new Font("Tahoma", Font.PLAIN, 19));
		contentPane.add(btnDeposit);
		btnDeposit.setVisible(false);

		btnDeleteAccount = new JButton("Delete Account");
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(true);
				BankingSystem.login(false);
				BankingSystem.checkAndDeleteButtons(true, "d");
			}
		});
		buttons.add(btnDeleteAccount);
		btnDeleteAccount.setBounds(71, 443, 394, 53);
		btnDeleteAccount.setFont(new Font("Tahoma", Font.PLAIN, 25));
		contentPane.add(btnDeleteAccount);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = textField.getText();
				if (btnDelete.getText().equals("Delete")) {
					try {
						String msg = dao.deleteAccount(id);
						BankingSystem.checkAndDeleteButtons(true, "d");
						lblTryAgain.setText(msg);
						lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 18));
						lblTryAgain.setBounds(86, 280, 581, 51);
						if (!msg.startsWith("Account")) {
							lblTryAgain.setForeground(Color.BLUE);
						} else {
							lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 20));
							lblTryAgain.setBounds(176, 250, 581, 51);
							lblTryAgain.setForeground(Color.RED);
						}
						lblTryAgain.setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						Double msg = dao.getBalance(id);
						BankingSystem.checkAndDeleteButtons(true, "c");
						lblTryAgain.setText("Available Balance:" + msg + "/-");
						lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 18));
						if(msg!=0.0) {
						lblTryAgain.setBounds(146, 280, 581, 51);
						lblTryAgain.setForeground(Color.BLUE);
						}
						else
						{
							lblTryAgain.setText("Account ID does'nt exists");
							lblTryAgain.setBounds(176, 250, 581, 51);
							lblTryAgain.setForeground(Color.RED);
						}
						lblTryAgain.setVisible(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnDelete.setBounds(206, 359, 165, 39);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 19));
		contentPane.add(btnDelete);
		btnDelete.setVisible(false);

		btnAllAccounts = new JButton("Show Accounts");
		btnAllAccounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(true);
				BankingSystem.login(false);
				try {
					ResultSet result = dao.getAllAccounts();
					ResultSetMetaData metadata = result.getMetaData();
					String[] columns = new String[metadata.getColumnCount()];
					int row = 0;
					while (result.next()) {
						row++;
					}
					result = dao.getAllAccounts();

					System.out.println(row);
					String rows[][] = new String[row][metadata.getColumnCount()];

					for (int i = 0; i < metadata.getColumnCount(); i++) {
						columns[i] = metadata.getColumnLabel(i + 1);
					}
					System.out.println(columns[0]);

					result.next();
					for (int i = 0; i < row; i++) {
						rows[i][0] = result.getString(1);
						rows[i][1] = result.getString(2);
						rows[i][2] = result.getDouble(3) + "";
						result.next();
					}
					j = new JTable(rows, columns);
					j.setBounds(11, 160, 500, 328);
					j.setFont(new Font("Tahoma", Font.PLAIN, 18));
					j.repaint();
					contentPane.add(j);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});
		buttons.add(btnAllAccounts);
		btnAllAccounts.setBounds(71, 341, 394, 53);
		btnAllAccounts.setFont(new Font("Tahoma", Font.PLAIN, 28));
		contentPane.add(btnAllAccounts);

		btnNewButton = new JButton("Check Balance");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(true);
				BankingSystem.login(false);
				BankingSystem.checkAndDeleteButtons(true, "c");
			}
		});
		buttons.add(btnNewButton);
		btnNewButton.setBounds(71, 63, 394, 53);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		contentPane.add(btnNewButton);

		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(true);
				BankingSystem.login(false);
				BankingSystem.createAccountButtons(true);

			}
		});
		buttons.add(btnCreateAccount);
		btnCreateAccount.setBounds(71, 155, 394, 53);
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 25));
		contentPane.add(btnCreateAccount);

		btnWithdraw = new JButton("Withdraw");
		buttons.add(btnWithdraw);
		btnWithdraw.setBounds(71, 248, 176, 53);
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(true);
				BankingSystem.login(false);
				BankingSystem.depositAndWithdrawButtons(true, "w");
			}
		});
		btnWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(btnWithdraw);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField.setBounds(135, 129, 312, 43);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setVisible(false);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		usernameField.setBounds(135, 129, 312, 43);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(11, 135, 165, 29);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(11, 248, 153, 29);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(lblNewLabel_1);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		passwordField.setBounds(135, 244, 312, 39);
		contentPane.add(passwordField);

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				dao = new AccountsDAO(username, password);
				if (AccountsDAO.getConn() != null) {
					BankingSystem.login(true);
					BankingSystem.hideLoginButtons(false);
				} else if (AccountsDAO.getConn() == null) {
					lblTryAgain.setForeground(Color.RED);
					lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 14));
					lblTryAgain.setBounds(135, 80, 381, 51);
					lblTryAgain.setText("Username/Password is wrong.Try Again");
					lblTryAgain.setVisible(true);
					usernameField.setText("");
					passwordField.setText("");
				}
			}
		});
		btnConnect.setBounds(206, 359, 165, 39);
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 19));
		contentPane.add(btnConnect);

		JLabel lblAbcBank = new JLabel("ABC BANK");
		lblAbcBank.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblAbcBank.setBounds(189, 0, 168, 37);
		contentPane.add(lblAbcBank);

		lblTryAgain = new JLabel("Username/Password is wrong.Try Again");
		lblTryAgain.setForeground(Color.RED);
		lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTryAgain.setBounds(135, 80, 381, 51);
		contentPane.add(lblTryAgain);
		lblTryAgain.setVisible(false);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField_1.setColumns(10);
		textField_1.setBounds(135, 238, 312, 43);
		contentPane.add(textField_1);
		textField_1.setVisible(false);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField_2.setColumns(10);
		textField_2.setBounds(135, 351, 312, 43);
		contentPane.add(textField_2);
		textField_2.setVisible(false);

		label = new JLabel("Deposit");
		label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		label.setBounds(11, 361, 153, 29);
		contentPane.add(label);
		label.setVisible(false);

		button = new JButton("Create");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_1.getText();
				String id = textField.getText();
				Double balance = Double.parseDouble(textField_2.getText());
				try {
					String msg = dao.createAccount(name, id, balance);
					BankingSystem.createAccountButtons(true);
					lblTryAgain.setText(msg);
					lblTryAgain.setFont(new Font("Tahoma", Font.BOLD, 18));
					lblTryAgain.setBounds(146, 80, 381, 51);
					if (!msg.startsWith("Minimum")) {
						lblTryAgain.setForeground(Color.GREEN);
					} else {
						lblTryAgain.setBounds(96, 80, 381, 51);

						lblTryAgain.setForeground(Color.RED);

					}
					lblTryAgain.setVisible(true);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 19));
		button.setBounds(206, 431, 157, 39);
		contentPane.add(button);
		button.setVisible(false);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBack.setVisible(false);
				BankingSystem.login(false);
				BankingSystem.hideLoginButtons(false);
				BankingSystem.createAccountButtons(false);
				BankingSystem.checkAndDeleteButtons(false, "a");
				BankingSystem.depositAndWithdrawButtons(false, "a");
				BankingSystem.loginButtons(true);
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogout.setBounds(433, 24, 83, 27);
		contentPane.add(btnLogout);
		btnLogout.setVisible(false);
		buttons.add(btnLogout);

		btnBack = new JButton("Back");
		btnBack.setBounds(433, 26, 83, 25);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (j != null) {
					j.setVisible(false);
				}
				BankingSystem.login(true);
				BankingSystem.hideLoginButtons(false);
				BankingSystem.createAccountButtons(false);
				BankingSystem.checkAndDeleteButtons(false, "a");
				BankingSystem.depositAndWithdrawButtons(false, "a");

			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(btnBack);
		btnBack.setVisible(false);

		BankingSystem.login(false);

	}

}
