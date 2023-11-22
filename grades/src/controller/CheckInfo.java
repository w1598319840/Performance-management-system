package controller;

import java.io.BufferedReader;
import java.io.FileReader;

public class CheckInfo {
	/*
	 * 登陆时检查用户信息
	 */
	public int isMember(String table, String id, String passwd) {
		
		// String file = "D://test//".concat(table.concat(".txt"));
		String file = System.getProperty("user.dir")+"/data".concat("/").concat(table).concat(".txt");
		 //StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	String[] result = s.split(" ");
	            	if(result[0].equals(id) && result[1].equals(passwd)){
	            		return 1;
	            	}
	            	if(result[0].equals(id)){
	            		return 2;
	            	}
	            	
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }

		return 0;
	}
}
