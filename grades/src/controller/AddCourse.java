package controller;

import java.awt.AWTEvent;
import java.awt.HeadlessException;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Course;

@SuppressWarnings("serial")
public class AddCourse extends JFrame implements ActionListener {
	/*
	 * 教师增加课程
	 */
	JPanel contain;
	JButton submit;
	JLabel id, name, credit, classH, teacherId, teacherName, maxNum;
	JTextField idt, namet, creditt, classHt, teacherIdt, teacherNamet, maxNumt;

	public AddCourse() {
		super("增加课程");
		setSize(450, 430);
		setLocation(500, 300);
		contain = new JPanel();
		contain.setLayout(null);
		id = new JLabel("课程号");
		name = new JLabel("课程名");
		credit = new JLabel("学分");
		classH = new JLabel("学时");
		teacherId = new JLabel("教师号");
		teacherName = new JLabel("教师");
		maxNum = new JLabel("最大人数");

		submit = new JButton("提交");
		idt = new JTextField();
		namet = new JTextField();
		creditt = new JTextField();
		classHt = new JTextField();
		teacherIdt = new JTextField();
		teacherNamet = new JTextField();
		maxNumt = new JTextField();

		id.setBounds(45, 35, 75, 35);// 课程号
		idt.setBounds(90, 35, 150, 35);// 文本框
		name.setBounds(45, 90, 75, 35);// 课程名
		namet.setBounds(90, 90, 150, 35);
		credit.setBounds(45, 135, 75, 35);// 学分
		creditt.setBounds(80, 135, 150, 35);
		classH.setBounds(45, 180, 75, 35);// 学时
		classHt.setBounds(80, 180, 150, 35);
		teacherName.setBounds(45, 225, 75, 35);// 教师
		teacherNamet.setBounds(80, 225, 150, 35);
		maxNum.setBounds(45, 270, 75, 35);// 最大人数
		maxNumt.setBounds(110, 270, 150, 35);
		teacherId.setBounds(45, 315, 75, 35);// 教师号
		teacherIdt.setBounds(90, 315, 75, 35);

		submit.setBounds(180, 350, 70, 30);// 提交

		contain.add(id);
		contain.add(idt);
		contain.add(name);
		contain.add(namet);
		contain.add(credit);
		contain.add(creditt);
		contain.add(classH);
		contain.add(classHt);
		contain.add(teacherId);
		contain.add(teacherIdt);
		contain.add(teacherName);
		contain.add(teacherNamet);
		contain.add(maxNum);
		contain.add(maxNumt);
		contain.add(submit);
		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public int hasCourse(String id) { // 教师开课前检查课程是否已经存在

		String file = System.getProperty("user.dir") + "/data/course.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); // 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					return 1;

				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((idt.getText().equals("")) || (namet.getText().equals("")) || (creditt.getText().equals(""))
					|| (classHt.getText().equals("")) || teacherIdt.getText().equals("")
					|| teacherNamet.getText().equals("") || maxNumt.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if ((hasCourse(idt.getText())) == 1) {
					JOptionPane.showMessageDialog(null, "此课程已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String file = System.getProperty("user.dir") + "/data/course.txt";

					ArrayList<String> modifiedContent = new ArrayList<String>();
					// StringBuilder result = new StringBuilder();
					try {
						BufferedReader br = new BufferedReader(new FileReader(file));
						String s = null;
						while ((s = br.readLine()) != null) { // 先将原来存在的信息存储起来
							String[] result = s.split(" ");

							String s1 = "";
							for (int i = 0; i < result.length - 1; i++) {
								s1 = s1 + result[i];
								s1 = s1 + " ";
							}
							s1 = s1 + result[result.length - 1];
							// System.out.println(s1);
							modifiedContent.add(s1);
						}
						br.close();

					} catch (Exception e1) {
						e1.printStackTrace();
					}

					Course course = new Course(idt.getText(), namet.getText(), teacherIdt.getText(),
							teacherNamet.getText(), creditt.getText(), classHt.getText(), maxNumt.getText());

					modifiedContent.add(course.getCourseId() + " " + course.getCourseName() + " " + course.getCredit()
							+ " " + course.getHour() + " " + course.getTeacherId() + " " + course.getTeacherName() + " "
							+ course.getMaxNum() + " " + course.getCurrentNum());

					try {
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);

						for (int i = 0; i < modifiedContent.size(); i++) {
							bw.write(modifiedContent.get(i));
							bw.newLine();
						}

						bw.close();
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					// 除了添加对应课程文件外，还需要添加课程成绩文件、课程学生文件以及对应课程的考试信息文件
					File gradeFile = new File(
							System.getProperty("user.dir") + "/data/grade/" + course.getCourseName() + ".txt");
					File studentFile = new File(System.getProperty("user.dir") + "/data/course_student/"
							+ course.getCourseName() + "_student.txt");
					File examFile = new File(
							System.getProperty("user.dir") + "/data/exam/" + course.getCourseName() + ".txt");
					try {
						gradeFile.createNewFile();
						studentFile.createNewFile();
						examFile.createNewFile();
						JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
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
