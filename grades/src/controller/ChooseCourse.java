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

public class ChooseCourse extends JFrame implements ActionListener {
    /**
     * 学生选课
     */
    private static final long serialVersionUID = 1L;
    JPanel contain;
    JTextArea list;
    JLabel chooseCourseId, allCourse;
    JTextField chooseCourseIdt;
    JButton submit, dropOut;
    String id;

    public ChooseCourse(String id) {
        super("选课界面");
        this.id = id;
        contain = new JPanel();
        contain.setLayout(null);
        setLocation(600, 200);
        setSize(650, 450);
        add(contain);
        allCourse = new JLabel("所有可选课程:");
        allCourse.setBounds(10, 10, 200, 20);
        contain.add(allCourse);
        list = new JTextArea();
        list.setBounds(30, 40, 570, 200);
        list.setEditable(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 40, 570, 200);
        scrollPane.setViewportView(list);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contain.add(scrollPane);
        list.append("课程编号\t课程名\t\t学分\t学时\t教师\t已选人数/最大人数\n");
        String courseId;
        String courseName;
        String credit = null;
        String classHour = null;
        String maxNum = null;
        String currentNum = null;
        String teacherName = null;
        String path = System.getProperty("user.dir") + "/data/course.txt";
        String s = null;
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(path));
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                String[] result = s.split(" ");
                courseId = result[0];
                courseName = result[1];
                credit = result[2];
                classHour = result[3];
                teacherName = result[5];
                maxNum = result[6];
                currentNum = result[7];

                int stringLength = 0;
                for (int i = 0; i < courseName.length(); i++) {
                    char temp = courseName.charAt(i);
                    if ((temp + "").getBytes().length == 1) {
                        stringLength++;
                    } else {
                        stringLength += 2;
                    }
                }
//				System.out.println(stringLength);
                list.append("      " + courseId + "\t");
                if (stringLength >= 12) {
                    list.append(courseName + " \t");
                } else {
                    list.append(courseName + "\t\t");
                }
                list.append(credit + "\t");
                list.append(classHour + "\t");
                list.append(teacherName + "\t");
                list.append("            " + currentNum + "/");
                list.append(maxNum + "\n");
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

        chooseCourseId = new JLabel("想要选课/退选的课程编号：");
        chooseCourseIdt = new JTextField();

        chooseCourseId.setBounds(70, 280, 160, 35);
        chooseCourseIdt.setBounds(235, 280, 130, 35);

        contain.add(chooseCourseId);
        contain.add(chooseCourseIdt);

        submit = new JButton("选课");
        dropOut = new JButton("退选");
        submit.setBounds(180, 350, 100, 30);
        submit.addActionListener(this);
        dropOut.setBounds(320, 350, 100, 30);
        dropOut.addActionListener(this);
        contain.add(dropOut);
        contain.add(submit);

        setVisible(true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }

    public boolean hasStudent(String id, String courseId) {
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
                    if (result[0].equals(courseId)) {
                        if (result[2].equals(id)) {
                            return true;// 有这个学生返回true
                        }
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
        return false;// 找不到这个学生返回false
    }

    public void editCurrentNum(String courseId, boolean condition) {
        // condtion用来判断是当前人数 +1(true) 还是当前人数 -1(false)
        String coursePath = System.getProperty("user.dir") + "\\data\\course.txt";
        ArrayList<String> modifiedContent = new ArrayList<String>();
        BufferedReader br = null;// 用来再次读入课程的数据，这次是为了修改其中的currentNum
        BufferedWriter bw = null;// 用来把数据重新写入，在读入读出的过程之中，currentNum得到了修改
        try {
            br = new BufferedReader(new FileReader(coursePath));
            String s = null;
            while ((s = br.readLine()) != null) {
                String[] result = s.split(" ");// 把原来的信息存起来
                if (result[0].equals(courseId)) {
                    if (condition) {
                        result[7] = "" + (Integer.parseInt(result[7]) + 1);
                    } else {
                        result[7] = "" + (Integer.parseInt(result[7]) - 1);
                    }
                } // 找到了这门课，就把这门课的currentNum + 1
                String s1 = "";
                for (int i = 0; i < result.length - 1; i++) {
                    s1 = s1 + result[i];
                    s1 = s1 + " ";
                }
                s1 = s1 + result[result.length - 1];
                modifiedContent.add(s1);
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
        try {
            bw = new BufferedWriter(new FileWriter(coursePath));
            for (int i = 0; i < modifiedContent.size(); i++) {
                bw.write(modifiedContent.get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void editGrade(String courseId, String studentId, boolean flag) {
        String coursePath = System.getProperty("user.dir") + "\\data\\course.txt";
        String studentPath = System.getProperty("user.dir") + "\\data\\student.txt";
        BufferedReader br1 = null;// 用来读取课程信息
        BufferedReader br2 = null;// 用来读取学生信息
        BufferedReader br3 = null;
        BufferedWriter bw = null;
        String s = null;
        String courseName = null;// 对应课程编号的课程名
        String teacherId = null;// 对应的老师编号
        String teacherName = null;// 对应的老师姓名
        String studentName = null;
        ArrayList<String> modifiedContent = new ArrayList<String>();
        try {
            br1 = new BufferedReader(new FileReader(coursePath));
            while ((s = br1.readLine()) != null) {
                String[] result = s.split(" ");
                if (result[0].equals(courseId)) {
                    courseName = result[1];
                    teacherId = result[4];
                    teacherName = result[5];
                }
            }
            br2 = new BufferedReader(new FileReader(studentPath));
            while ((s = br2.readLine()) != null) {
                String result[] = s.split(" ");
                if (result[0].equals(studentId)) {
                    studentName = result[2];
                }
            }
            String gradePath = System.getProperty("user.dir") + "\\data\\grade\\" + courseName + ".txt";// 找到对应课程的成绩文件

            if (flag) {
                bw = new BufferedWriter(new FileWriter(gradePath, true));
                // 课程编号 课程名称 老师编号 老师姓名 学生学号 学生姓名 成绩
                // courseId courseName teacherId teacherName studentId studentName 暂未无考试记录
                bw.write(courseId + " " + courseName + " " + teacherId + " " + teacherName + " " + studentId + " "
                        + studentName + " " + "暂无考试记录");
                bw.newLine();
            }
            else {
                try {
                    br3 = new BufferedReader(new FileReader(gradePath));
                    String s2 = null;
                    while ((s2 = br3.readLine()) != null) {
                        String[] result2 = s2.split(" ");
                        if (result2[4].equals(id)) {
                            continue;
                        }
                        s = "";
                        for (int i = 0; i < result2.length - 1; i++) {
                            s = s + result2[i];
                            s = s + " ";
                        }
                        s = s + result2[result2.length - 1];
                        modifiedContent.add(s);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    try {
                        br3.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                bw = new BufferedWriter(new FileWriter(gradePath));
                for (int i = 0; i < modifiedContent.size(); i++) {
                    bw.write(modifiedContent.get(i));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br1.close();
                br2.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {// 判断提交按钮是否按下
            if (chooseCourseIdt.getText().equals("")) {// 判断选课框内容是否为空
                JOptionPane.showMessageDialog(null, "选课的编号不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (hasStudent(id, chooseCourseIdt.getText())) {// 判断这个学生是否选过这门课
                    JOptionPane.showMessageDialog(null, "您已加入过此课程，请勿重复选课！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    chooseCourseIdt.setText("");
                } else {
                    String coursePath = System.getProperty("user.dir") + "/data/course.txt";
                    String studentPath = System.getProperty("user.dir") + "/data/student.txt";
                    BufferedReader br1 = null;// 用来读入课程的数据
                    BufferedReader br2 = null;// 用来读入学生的数据
                    BufferedWriter bw = null;// 用来写course_student
                    String[] result2 = null;
                    boolean flag = false;// 用来标记这门课存不存在
                    try {
                        br1 = new BufferedReader(new FileReader(coursePath));
                        br2 = new BufferedReader(new FileReader(studentPath));
                        String s1 = null;// 用来记录课程信息
                        String s2 = null;// 用来记录学生信息
                        while ((s2 = br2.readLine()) != null) {
                            result2 = s2.split(" ");// 用来记录学生信息
                            if (result2[0].equals(id)) {
                                break;
                            }
                        }
                        while ((s1 = br1.readLine()) != null) {
                            String[] result1 = s1.split(" ");// 用来记录课程信息
                            if (result1[0].equals(chooseCourseIdt.getText())) {// 找到了要加入的那门课
                                flag = true;
                                String course_studentPath = System.getProperty("user.dir") + "/data/course_student/"
                                        + result1[1] + "_student.txt";// 创建要加入课程的学生名单的路径
                                bw = new BufferedWriter(new FileWriter(course_studentPath, true));
                                if (Integer.parseInt(result1[7]) >= Integer.parseInt(result1[6])) {// 判断课程人数是否已满
                                    JOptionPane.showMessageDialog(null, "抱歉，该课程人数已达上限！", "提示",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                } else {
                                    bw.write(result1[0] + " " + result1[1] + " " + result2[0] + " " + result2[2] + " "
                                            + result2[3] + " " + result2[4] + " " + result2[5] + " " + result2[6]);
                                    bw.newLine();
                                    editCurrentNum(chooseCourseIdt.getText(), true);
                                    editGrade(chooseCourseIdt.getText(), id, true);// 选完课以后，需要添加相应的成绩文件内容
                                    JOptionPane.showMessageDialog(null, "选课 " + result1[1] + " 成功!", "提示",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    chooseCourseIdt.setText("");
                                    bw.close();
                                }
                            }
                        }
                        if (!(flag)) {
                            JOptionPane.showMessageDialog(null, "抱歉，该课程不存在，请重新选择。", "提示",
                                    JOptionPane.INFORMATION_MESSAGE);
                            chooseCourseIdt.setText("");
                            return;
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            br1.close();
                            br2.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
        if (e.getSource() == dropOut) {
            if (chooseCourseIdt.getText().equals("")) {// 判断选课框内容是否为空
                JOptionPane.showMessageDialog(null, "退课的编号不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!(hasStudent(id, chooseCourseIdt.getText()))) {// 判断这门课中有没有这个学生
                    JOptionPane.showMessageDialog(null, "您还未加入这门课程/这门课程不存在，无法退课！", "提示",
                            JOptionPane.INFORMATION_MESSAGE);
                    chooseCourseIdt.setText("");
                } else {
                    String coursePath = System.getProperty("user.dir") + "/data/course.txt";
                    BufferedReader br1 = null;// br1用来获取course.txt文件的路径
                    BufferedReader br2 = null;// br2用来读取对应的course_student文件中的内容
                    BufferedWriter bw = null;// bw用来将删减后的内容重新写入
                    ArrayList<String> modifiedContent = new ArrayList<String>();
                    String course_studentPath = null;
                    String[] result1 = null;
                    try {
                        br1 = new BufferedReader(new FileReader(coursePath));
                        String s1 = null;
                        while ((s1 = br1.readLine()) != null) {
                            result1 = s1.split(" ");
                            if (result1[0].equals(chooseCourseIdt.getText())) {
                                course_studentPath = System.getProperty("user.dir") + "/data/course_student/"
                                        + result1[1] + "_student.txt";// 创建要加入课程的学生名单的路径
                                break;
                            }
                        }
                        br2 = new BufferedReader(new FileReader(course_studentPath));
                        String s2 = null;
                        while ((s2 = br2.readLine()) != null) {
                            String[] result2 = s2.split(" ");
                            if (result2[2].equals(id)) {
                                continue;
                            }
                            String s = "";
                            for (int i = 0; i < result2.length - 1; i++) {
                                s = s + result2[i];
                                s = s + " ";
                            }
                            s = s + result2[result2.length - 1];
                            modifiedContent.add(s);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            br1.close();
                            br2.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    try {
                        bw = new BufferedWriter(new FileWriter(course_studentPath));
                        for (int i = 0; i < modifiedContent.size(); i++) {
                            bw.write(modifiedContent.get(i));
                            bw.newLine();
                        }
                        JOptionPane.showMessageDialog(null, "退课 " + result1[1] + " 成功!", "提示",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            bw.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    editGrade(chooseCourseIdt.getText(),this.id,false);
                    editCurrentNum(chooseCourseIdt.getText(), false);
                    chooseCourseIdt.setText("");
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
