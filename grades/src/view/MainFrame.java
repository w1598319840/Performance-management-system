package view;

import java.awt.AWTEvent;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.CheckInfo;


public class MainFrame extends JFrame implements ActionListener {
	/**
	 * 登陆主界面
	 */
	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JPasswordField passwdTextField;
	JLabel idLabel, passwdLabel;
	Choice choice;
	JButton login;
	JPanel contain;
	
	int count = 0;

	public MainFrame() {
		super("账号登陆");
		setLocation(300, 200);
		setSize(300, 340);
		contain = new JPanel();
		contain.setLayout(null);
		idLabel = new JLabel("ID号");
		passwdLabel = new JLabel("密码");
		idTextField = new JTextField();
		passwdTextField = new JPasswordField();
		login = new JButton("登陆");
		choice = new Choice();
		choice.addItem("学生");
		choice.addItem("教师");
		choice.addItem("系统管理员");
		idLabel.setBounds(42, 45, 75, 35);
		idTextField.setBounds(80, 45, 150, 35);
		passwdLabel.setBounds(40, 100, 75, 35);
		passwdTextField.setBounds(80, 100, 150, 35);
		choice.setBounds(80, 160, 150, 35);
		login.setBounds(102, 220, 70, 30);
		contain.add(idLabel);
		contain.add(idTextField);
		contain.add(passwdLabel);
		contain.add(passwdTextField);
		contain.add(choice);
		contain.add(login);
		login.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			String ch = (String) choice.getSelectedItem();
			if (ch == "学生") {
				if ((new CheckInfo().isMember("student", idTextField.getText(),
						new String(passwdTextField.getPassword()))) == 1) {
					setVisible(false);
					new StudentsPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "此用户不存在/密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						passwdTextField.setText("");
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			} else if (ch == "教师") {
				if ((new CheckInfo().isMember("teacher", idTextField.getText(),
						new String(passwdTextField.getPassword(), 0,
								passwdTextField.getPassword().length))) == 1) {
					setVisible(false);
					new TeachersPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "此用户不存在/密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						passwdTextField.setText("");
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			} else if (ch == "系统管理员") {
				if ((new CheckInfo().isMember("administrator", idTextField
						.getText(), new String(passwdTextField.getPassword(),
						0, passwdTextField.getPassword().length))) == 1) {
					setVisible(false);
					new AdministratorPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "此用户不存在/密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						passwdTextField.setText("");
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			}
		}
		
	}

	

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}