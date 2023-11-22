package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Scholarship;

public class ScholarshipView extends JFrame implements ActionListener {

	/*
	 * 学生查询申请状态
	 */

	private static final long serialVersionUID = 1L;
	JLabel idLabel, nameLabel, instLabel, claLabel, majorLabel, courseLabel, sexLabel, overviewLabel;
	String id, type;
	JPanel contain;
	JTextField overviewField;
	JTextArea list;
	JScrollPane scrollPane;
	JButton overviewButton;

	public ScholarshipView(String id, int flag) {
		super("奖学金申请情况");
		this.id = id;
		String file = System.getProperty("user.dir") + "/data/scholarship/scholarship.txt";

		if (flag == 1) {// 学生
			setSize(660, 300);
			setLocation(600, 200);
			contain = new JPanel();
			contain.setLayout(null);
			add(contain);
			list = new JTextArea();
			list.setBounds(20, 20, 610, 200);
			list.setEditable(false);
			scrollPane = new JScrollPane();
			scrollPane.setBounds(20, 20, 610, 200);
			scrollPane.setViewportView(list);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			list.append("学号\t姓名\t学院\t\t专业\t具体申请奖学金项目\t审核情况\n");

			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String s = null;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[0].equals(this.id)) {
						String name = result[1];
						String inst = result[2];
						String major = result[3];
						String type = result[4];
						String state = null;
						if (result[6].equals("Reviewing")) {
							state = "审核中……";
						} else if (result[6].equals("agree")) {
							state = "已通过";
						} else if (result[6].equals("reject")) {
							state = "已拒绝";
						}
						int stringLength = 0;
						for (int i = 0; i < type.length(); i++) {
							char temp = type.charAt(i);
							if ((temp + "").getBytes().length == 1) {
								stringLength++;
							} else {
								stringLength += 2;
							}
						}
						list.append("   " + this.id + "\t" + name + "\t" + inst + "\t" + major + "\t");
						if (stringLength >= 12) {
							list.append(type + " \t");
						} else {
							list.append(type + "\t\t");
						}
						list.append(state + "\n");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			contain.add(scrollPane);
		}
		if (flag == 0) {// 教师
			setSize(400, 400);
			setLocation(600, 200);
			contain = new JPanel();
			contain.setLayout(null);
			add(contain);
			list = new JTextArea();
			list.setBounds(30, 40, 320, 200);
			list.setEditable(false);

			scrollPane = new JScrollPane();
			scrollPane.setBounds(30, 40, 320, 200);
			scrollPane.setViewportView(list);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			list.append("学号\t姓名\t具体申请奖学金项目\n");
			overviewButton = new JButton("查看详细信息");
			overviewButton.setBounds(110, 300, 140, 30);
			overviewLabel = new JLabel("输入学生学号查看详细信息：");
			overviewLabel.setBounds(20, 250, 170, 30);
			overviewField = new JTextField();
			overviewField.setBounds(190, 250, 120, 30);

			contain.add(scrollPane);
			contain.add(overviewButton);
			overviewButton.addActionListener(this);
			contain.add(overviewLabel);
			contain.add(overviewField);

			BufferedReader br = null;
			String name = null;
			String studentId = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String s = null;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[6].equals("Reviewing")) {
						studentId = result[0];
						name = result[1];
						type = result[4];
						list.append(studentId + "\t" + name + "\t" + type + "\n");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setVisible(true);

	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == overviewButton) {
			if (overviewField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "输入学生学号不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String file = System.getProperty("user.dir") + "/data/scholarship/scholarship.txt";
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					String s = null;
					while ((s = br.readLine()) != null) {
						String[] result = s.split(" ");
						if (result[6].equals("Reviewing") && result[0].equals(overviewField.getText())) {
							new ScholarshipDetail(overviewField.getText(), type);
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
				JOptionPane.showMessageDialog(null, "该学生的申请不存在/已审核过！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
