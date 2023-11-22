package controller;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ExamEnter extends JFrame implements ActionListener {
	/*
	 * 教师登陆考试信息
	 */
	String idd; // 教师号
	JPanel contain;
	JLabel id;
	JTextField idt, stuIdt, stuNamet, examNamet, examTimet, examAreat;

	String targetFile;

	JButton submit, bn, overview;
	ArrayList<String> modifiedContent = new ArrayList<String>();

	public ExamEnter(String idd) {
		super("查看");
		this.idd = idd;
		setSize(300, 300);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		id = new JLabel("课程号");
		idt = new JTextField();
		submit = new JButton("提交");
		id.setBounds(38, 50, 75, 35);
		idt.setBounds(80, 50, 150, 35);
		submit.setBounds(102, 125, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(submit);
		submit.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (hasThisCourse(idt.getText()) == 1) {
				enter(); // 进入考试信息输入界面
			} else {
				JOptionPane.showMessageDialog(null, "您未开设此课程！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == bn) {
			if (hasThisStu() == 1) { // 设置考试内容
				String path = System.getProperty("user.dir") + "/data/exam" + "/" + examNamet.getText() + ".txt";
				ArrayList<String> tempContent = new ArrayList<>(); // 创建临时内容列表
				String path1 = System.getProperty("user.dir") + "/data/teacher.txt";

				// 检查文件是否为空
				boolean isFileEmpty = false;
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {

					if (br.readLine() == null) {
						isFileEmpty = true;
					}
				} catch (IOException ee) {
					ee.printStackTrace();
				}

				// 如果文件为空，直接写入内容
				if (isFileEmpty) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(path));
							BufferedReader br = new BufferedReader(new FileReader(path1))) {

						String t = null;
						String[] result = null;
						while ((t = br.readLine()) != null) {// 使用readLine方法，对一个文件一次读一行
							result = t.split(" ");
						}
						// 写入新的内容
						String newExamInfo = idt.getText() + " " + examNamet.getText() + " " + result[0] + " "
								+ result[2] + " " + stuIdt.getText() + " " + stuNamet.getText() + " "
								+ examNamet.getText() + " " + examTimet.getText() + " " + examAreat.getText();
						writer.write(newExamInfo);
						writer.newLine(); // 添加换行符
						JOptionPane.showMessageDialog(null, "考试信息登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						System.err.println("Error writing to the file: " + e1.getMessage());
					}
					return; // 退出方法
				}

				// 如果文件不为空，修改内容并覆盖
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {
					String s;
					while ((s = br.readLine()) != null) {
						String[] result = s.split(" ");
						if (result[0].equals(idt.getText())) {
							// 修改特定的内容
							String newExamInfo = idt.getText() + " " + result[1] + " " + result[2] + " " + result[3]
									+ " " + stuIdt.getText() + " " + stuNamet.getText() + " " + examNamet.getText()
									+ " " + examTimet.getText() + " " + examAreat.getText();
							// 将修改后的内容存储在临时列表中
							tempContent.add(newExamInfo);
						} else {
							tempContent.add(s); // 将未修改的内容添加到临时列表中
						}
					}
				} catch (IOException ee) {
					ee.printStackTrace();
				}

				// 修改内容后，将其重新写入文件
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
					for (String line : tempContent) {
						writer.write(line);
						writer.newLine(); // 每行后添加换行符
					}
					JOptionPane.showMessageDialog(null, "考试信息登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					System.err.println("Error writing to the file: " + e1.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "课程号为" + idt.getText() + "无此学生", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(e.getSource() == overview) {
			allStudent(idt.getText());
		}
	}

	int hasThisStu() {

		@SuppressWarnings("unused")
		String stuId = stuIdt.getText();

		String path = System.getProperty("user.dir") + "/data/course_student";
		// String path = "D://test//course_student";

		List<String> files = new ArrayList<String>(); // 目录下所有文件
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				// 文件名，不包含路径
				// String fileName = tempList[i].getName();
			}
			if (tempList[i].isDirectory()) {
				// 这里就不递归了，
			}
		}

		try {
			for (int i = 0; i < files.size(); i++) {
				try (BufferedReader br = new BufferedReader(new FileReader(files.get(i)))) {
					String s = null;
					while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
						String[] result = s.split(" ");
						if (result[0].equals(idt.getText()) && result[2].equals(stuIdt.getText())) {
							return 1;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	void enter() {
		JFrame fm = new JFrame("登录考试信息");
		fm.setSize(300, 460);
		JPanel contain = new JPanel();
		fm.setLocation(600, 400);
		contain.setLayout(null);
		bn = new JButton("提交");
		overview = new JButton("查看本门课程所有学生");
		JLabel stuId = new JLabel("学号");
		JLabel examName = new JLabel("科目");
		JLabel examTime = new JLabel("时间");
		JLabel examArea = new JLabel("地点");
		JLabel stuName = new JLabel("姓名");

		stuIdt = new JTextField();
		examNamet = new JTextField();
		examTimet = new JTextField();
		examAreat = new JTextField();
		stuNamet = new JTextField();

		stuId.setBounds(38, 50, 75, 35);
		stuIdt.setBounds(80, 50, 150, 35);

		examName.setBounds(38, 110, 75, 35);
		examNamet.setBounds(80, 110, 150, 35);

		examTime.setBounds(38, 170, 75, 35);
		examTimet.setBounds(80, 170, 150, 35);

		examArea.setBounds(38, 230, 75, 35);
		examAreat.setBounds(80, 230, 150, 35);

		stuName.setBounds(38, 290, 75, 35);
		stuNamet.setBounds(80, 290, 150, 35);

		bn.setBounds(100, 380, 70, 30);
		overview.setBounds(60, 340, 160, 30);
		contain.add(stuId);
		contain.add(stuIdt);
		contain.add(examName);
		contain.add(examNamet);
		contain.add(examTime);
		contain.add(examTimet);
		contain.add(examArea);
		contain.add(examAreat);
		contain.add(stuName);
		contain.add(stuNamet);
		contain.add(bn);
		contain.add(overview);
		fm.add(contain);
		bn.addActionListener(this);
		overview.addActionListener(this);

		fm.setVisible(true);

	}

	int hasThisCourse(String idd) {

		String file = System.getProperty("user.dir") + "/data/course.txt";
		// String file = "D://test//course.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				String[] result = s.split(" ");
				if (result[0].equals(idd) && result[4].equals(this.idd)) {
					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // 如果没有找到匹配项，返回0
	}

	void allStudent(String courseId) {// 获取本门课程的所有学生信息
		JFrame newFrame = new JFrame("本课程所有学生");
		newFrame.setBounds(200, 300, 460, 300);
		JPanel contain = new JPanel();
		contain.setLayout(null);
		newFrame.add(contain);
		JTextArea list = new JTextArea();
		list.setBounds(20, 20, 410, 120);
		list.setEditable(false);
		JScrollPane listPane = new JScrollPane();
		listPane.setBounds(20, 20, 410, 120);
		listPane.setViewportView(list);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contain.add(listPane);
		list.append("课程编号\t课程名称\t\t学生学号\t学生姓名\n");

		String path = System.getProperty("user.dir") + "/data/course_student";
		List<String> files = new ArrayList<String>();// 获取目录下所有文件
		File file = new File(path);// 至course_student文件夹
		File[] tempList = file.listFiles();// 这个数组中有该目录下所有文件
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				// 文件名，不包含路径
			}
			if (tempList[i].isDirectory()) {
				// 这里就不递归了，
			}
		} // for循环后，files动态数组中就包括了所有的文件路径
		BufferedReader br = null;
		try {
			for (int i = 0; i < files.size(); i++) {
				br = new BufferedReader(new FileReader(files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[0].equals(courseId)) {// 找到了这门课程
						// 把所有学生展示出来
						list.append("      " + courseId + "\t");
						int stringLength = 0;
						for (int i1 = 0; i1 < result[1].length(); i1++) {
							char temp = result[1].charAt(i1);
							if ((temp + "").getBytes().length == 1) {
								stringLength++;
							} else {
								stringLength += 2;
							}
						}
//						System.out.println(stringLength);
						if (stringLength >= 12) {
							list.append(result[1] + " \t");
						} else {
							list.append(result[1] + "\t\t");
						}
						list.append(result[2] + "\t");
						list.append(result[3] + "\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		newFrame.setVisible(true);
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}