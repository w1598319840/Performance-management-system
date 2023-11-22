package controller;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ExamInfo extends JFrame { 
	/**
	 * 学生根据学号查询考试信息
	 */
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JTextArea list;
	String id;

	String courseid;
	String coursename;
	String teacherid;
	String teachername;
	String studentid;
	String studentname;
	String Examname;
	String Examtime;
	String Examarea;
	
	

	public ExamInfo(String id) {
		super("考试");
		this.id = id;
		setSize(760, 400);
		contain = new JPanel();
		setLocation(600, 400);
		list = new JTextArea();
		list.setEditable(false);
		contain.add(list);
		
		list.append("课程号" + "\t");
		list.append("课程名称" + "\t\t");
		list.append("教师号" + "\t");
		list.append("教师名称" + "\t");
		list.append("学生号" + "\t");
		list.append("学生名称" + "\t");
		list.append("考试科目" + "\t");
		list.append("考试时间" + "\t");
		list.append("考试地点" + "\n");

		// String path = "D://test//grade";
		String path = System.getProperty("user.dir")+"/data/exam";

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
				BufferedReader br = new BufferedReader(new FileReader(
						files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					String[] result = s.split(" ");
					if (result[4].equals(id)) { // 学生学号相等时
						courseid = result[0];
						coursename = result[1];
						teacherid = result[2];
						teachername = result[3];
						studentid = result[4];
						studentname = result[5];
						Examname = result[6];
						Examtime = result[7];
						Examarea = result[8];

						list.append("    " + courseid + "\t");
						int stringLength = 0;
						for (int i1 = 0; i1 < coursename.length(); i1++) {
							char temp = coursename.charAt(i1);
							if ((temp + "").getBytes().length == 1) {
								stringLength++;
							} else {
								stringLength += 2;
							}
						}
//						System.out.println(stringLength);
						if (stringLength >= 12) {
							list.append(coursename + " \t");
						} else {
							list.append(coursename + "\t\t");
						}
						list.append(teacherid + "\t");
						list.append(teachername + "\t");
						list.append(studentid + "\t");
						list.append(studentname + "\t");
						list.append(Examname + "\t");
						list.append(Examtime + "\t");
						list.append(Examarea + "\n");
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(contain);
		setVisible(true);

	}
}
