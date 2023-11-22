package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class CourseView extends JFrame {
	/*
	 * 学生查询课程，教师查询所教授课程
	 */
	
	JPanel contain;
	JTextArea list;
	
	public CourseView(String id, int flag) {
		super("课程");
		setSize(380, 400);
		contain = new JPanel();
		setLocation(600, 400);
		list = new JTextArea();
		list.setEditable(false);
		contain.add(list);
		list.append("课程编号\t课程名\t	学分\t学时\n");
		
		String courseid;
		String coursename;
		String credit = null;
		String classhour = null;
		
		if(flag == 0){   // 学生查询课程
			
			// String path = "D://test//course_student";
			String path = System.getProperty("user.dir")+"/data/course_student";
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
					// 这里就不递归了
				}
			}

			try {
				for (int i = 0; i < files.size(); i++) {
					BufferedReader br = new BufferedReader(new FileReader(files.get(i)));
					String s = null;
					while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
						String[] result = s.split(" ");
						if (result[2].equals(id)) {      // 学生学号相等时
							courseid = result[0];
							coursename = result[1];
							

							String path1 = System.getProperty("user.dir")+"/data/course.txt";
							BufferedReader br1 = new BufferedReader(
									new FileReader(path1));        // 构造一个BufferedReader类来读取文件

							while ((s = br1.readLine()) != null) { // 使用readLine方法，一次读一行
								String[] result1 = s.split(" ");
								if (result[0].equals(result1[0])) {
									credit = result1[2];
									classhour = result1[3];
								}
							}

							list.append(courseid + "\t");
							int stringLength = 0;
							for (int i1 = 0; i1 < coursename.length(); i1++) {
								char temp = coursename.charAt(i1);
								if ((temp + "").getBytes().length == 1) {
									stringLength++;
								} else {
									stringLength += 2;
								}
							}
//							System.out.println(stringLength);
							if (stringLength >= 12) {
								list.append(coursename + " \t");
							} else {
								list.append(coursename + "\t\t");
							}
							list.append(credit + "\t");
							list.append(classhour + "\n");

							br1.close();
						}

					}

					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(flag == 1){      // 教师查询自己教授课程
			String path = System.getProperty("user.dir")+"/data/course.txt";
			// String path = "D://test//course.txt";
			String s = null;
		     
            try {
            	BufferedReader br = new BufferedReader(new FileReader(path));
				while((s = br.readLine())!=null){   //使用readLine方法，一次读一行
					String[] result = s.split(" ");
					if(result[4].equals(id)){
						courseid = result[0];
						coursename = result[1];
						credit = result[2];
	            		classhour = result[3];
	            		
	            		list.append(courseid + "\t");
	    				int stringLength = 0;
	    				for (int i = 0; i < coursename.length(); i++) {
	    					char temp = coursename.charAt(i);
	    					if ((temp + "").getBytes().length == 1) {
	    						stringLength++;
	    					} else {
	    						stringLength += 2;
	    					}
	    				}
//	    				System.out.println(stringLength);
	    				if (stringLength >= 12) {
	    					list.append(coursename + " \t");
	    				} else {
	    					list.append(coursename + "\t\t");
	    				}
						list.append(credit + "\t");
						list.append(classhour + "\n");
	            		
					}
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		
		add(contain);
		setVisible(true);
	}
}
