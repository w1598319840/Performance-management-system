package controller;

import java.awt.AWTEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CheckLeave extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JTextArea list;
	JLabel  allCourse;
	JTextField chooseCourseIdt;
	String id;

	public CheckLeave(String id) {
		super("请假状态查询");
		this.id = id;
		contain = new JPanel();
		contain.setLayout(null);
		setLocation(600, 200);
		setSize(600,450);//600,450
		add(contain);
		allCourse = new JLabel("请假状态查询：");
		allCourse.setBounds(10, 10, 200, 20);
		contain.add(allCourse);
		list = new JTextArea();
		list.setBounds(50, 40, 500, 200);
		list.setEditable(false);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 40, 500, 200);
		scrollPane.setViewportView(list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contain.add(scrollPane);
		list.append("学生id\t开始时间\t结束时间\t请假理由\n");
		String studentId;
		String startTime;
		String overTime = null;
		String reason = null;
		String path = System.getProperty("user.dir") + "/data/leave_records/" + id + ".txt";
		String s = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				String[] result = s.split(" ");
				studentId = result[0];
				startTime = result[1];
				overTime = result[2];
				reason = result[3];

				list.append("      " + studentId + "\t");
				list.append(startTime + "\t");
				list.append(overTime + "\t");
				list.append(reason + "\n");
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

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}
}