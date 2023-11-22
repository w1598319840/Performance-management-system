package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.CheckLeave;
import controller.ChooseCourse;
import controller.CourseView;
import controller.EditInfo;
import controller.ExamInfo;
import controller.GradeInfo;
import controller.Info;
import controller.EditScholarship;

@SuppressWarnings("serial")
public class StudentsPanel extends JFrame implements ActionListener {
	/*
	 * 学生登陆后操作主界面
	 */
	JPanel contain;
	String id;
	JButton infoButton, gradeButton, courseButton, editButton, courseChooseButton, examButton, checkLeaveButton,
			checkScholarshipButton, applyScholarShipButton;

	public StudentsPanel(String id) {
		super("学生");
		this.id = id;
		setLocation(300, 200);
		setSize(300, 450);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		infoButton = new JButton("信息查询");
		gradeButton = new JButton("成绩查询");
		courseButton = new JButton("课程查询");
		editButton = new JButton("修改信息");
		courseChooseButton = new JButton("选/退课程");
		examButton = new JButton("考试信息查询");
		checkLeaveButton = new JButton("请销假查询");
		applyScholarShipButton = new JButton("申请奖学金");
		checkScholarshipButton = new JButton("我的奖学金");

		infoButton.setBounds(70, 40, 140, 30);
		gradeButton.setBounds(70, 80, 140, 30);
		courseButton.setBounds(70, 120, 140, 30);
		editButton.setBounds(70, 160, 140, 30);
		courseChooseButton.setBounds(70, 200, 140, 30);
		examButton.setBounds(70, 240, 140, 30);
		checkLeaveButton.setBounds(70, 280, 140, 30);
		applyScholarShipButton.setBounds(70, 320, 140, 30);
		checkScholarshipButton.setBounds(70, 360, 140, 30);

		contain.add(infoButton);
		infoButton.addActionListener(this);
		contain.add(gradeButton);
		gradeButton.addActionListener(this);
		contain.add(courseButton);
		courseButton.addActionListener(this);
		contain.add(editButton);
		editButton.addActionListener(this);
		contain.add(courseChooseButton);
		courseChooseButton.addActionListener(this);
		contain.add(examButton);
		examButton.addActionListener(this);
		contain.add(checkLeaveButton);
		checkLeaveButton.addActionListener(this);
		contain.add(applyScholarShipButton);
		applyScholarShipButton.addActionListener(this);
		contain.add(checkScholarshipButton);
		checkScholarshipButton.addActionListener(this);

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == infoButton) {
			new Info(id, 1);
		}
		if (e.getSource() == gradeButton) {
			new GradeInfo(id);
		}
		if (e.getSource() == courseButton) {
			new CourseView(id, 0);
		}
		if (e.getSource() == editButton) {
			new EditInfo(id, 0);
		}
		if (e.getSource() == courseChooseButton) {
			new ChooseCourse(id);
		}
		if (e.getSource() == examButton) {
			new ExamInfo(id);
		}
		if (e.getSource() == checkLeaveButton) {
			new CheckLeave(id);
		}
		if(e.getSource() == applyScholarShipButton) {
			new EditScholarship(id);
		}
		if (e.getSource() == checkScholarshipButton) {
			new ScholarshipView(id, 1);
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
