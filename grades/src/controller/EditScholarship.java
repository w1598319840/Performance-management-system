
package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EditScholarship extends JFrame implements ActionListener {

	// 奖学金申请页面

	String id, idt;
	JPanel contain;
	JButton submit;
	JLabel name, inst, major, reason, type;
	JTextField namet, instt, majort, typet;
	JTextArea reasont;

	public EditScholarship(String id) {

		super("奖学金申请");
		setSize(300, 600);
		setLocation(600, 150);
		this.id = id;
		// 0老师，1学生
		contain = new JPanel();
		add(contain);
		contain.setLayout(null);
		name = new JLabel("姓名：");
		inst = new JLabel("学院：");
		major = new JLabel("专业：");
		type = new JLabel("申请奖项：");
		reason = new JLabel("申请原因：");// 应当留空最多
		submit = new JButton("提交");
		namet = new JTextField();
		instt = new JTextField();
		majort = new JTextField();
		typet = new JTextField();
		reasont = new JTextArea();

		reasont.setLineWrap(true);
		reasont.setWrapStyleWord(true);

		name.setBounds(20, 20, 75, 35);
		inst.setBounds(20, 70, 75, 35);
		major.setBounds(20, 120, 75, 35);
		type.setBounds(20, 170, 75, 35);
		reason.setBounds(20, 220, 150, 35);
		// 下面是文本框
		namet.setBounds(60, 25, 180, 30);
		instt.setBounds(60, 75, 180, 30);
		majort.setBounds(60, 125, 180, 30);
		typet.setBounds(80, 175, 160, 30);
		reasont.setBounds(30, 260, 210, 200);
		submit.setBounds(90, 500, 100, 30);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 260, 210, 200);
		scrollPane.setViewportView(reasont);

		contain.add(name);
		contain.add(inst);
		contain.add(major);
		contain.add(type);
		contain.add(reason);
		contain.add(submit);
		contain.add(namet);
		contain.add(instt);
		contain.add(majort);
		contain.add(typet);
		contain.add(scrollPane);
		contain.add(submit);
		submit.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == submit) {
			if (inst.getText().equals("") || namet.getText().equals("") || majort.getText().equals("")
					|| reasont.getText().equals("") || typet.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "输入不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String path = System.getProperty("user.dir") + "/data/scholarship/scholarship.txt";
				BufferedReader br = null;
				BufferedWriter bw = null;
				try {
					br = new BufferedReader(new FileReader(path));
					String s = null;
					while ((s = br.readLine()) != null) {
						String[] result = s.split(" ");
						if (result[4].equals(typet.getText())) {
							JOptionPane.showMessageDialog(null, "您已申请过该项奖学金，请勿重复提交申请！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				try {
					bw = new BufferedWriter(new FileWriter(path, true));
					// 学号 姓名 学院 专业 申请奖项 原因
					String name, inst, major, type, reason;
					name = namet.getText();
					inst = instt.getText();
					major = majort.getText();
					type = typet.getText();
					reason = reasont.getText().replaceAll(" +","");//去掉所有空格
					String modifiedContent = id + " " + name + " " + inst + " " + major + " " + type + " " + reason
							+ " Reviewing";
					bw.write(modifiedContent);
					bw.newLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "奖学金申请提交成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}