package com.uudoos;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.ExcelReader;
import common.ExcelWriter;
import keyword.DataDrivenOfWeb;

public class DDTofWeb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//读取用例文件
		ExcelReader caseExcel = new ExcelReader("Cases/Login.xlsx");
		ExcelWriter resultExcel = new ExcelWriter("Cases/Login.xlsx", "Cases/result"+createDate()+"Login.xlsx");
		//实例化关键字类
		DataDrivenOfWeb web = new DataDrivenOfWeb(resultExcel);
		//循环遍历excel文件
		for(int sheetNo=0; sheetNo < resultExcel.getTotalSheetNo(); sheetNo++) {
			//循环遍历便利所有的sheet页
			caseExcel.useSheetByIndex(sheetNo);
			resultExcel.useSheetByIndex(sheetNo);
			List<String> caseline = new ArrayList<String>();
			//遍历sheet页中的每一行
			for (int rowNo = 1; rowNo < caseExcel.rows; rowNo++) {
				//读取对应的行
				caseline = caseExcel.readLine(rowNo);
//				AutoLogger.logger.info(caseline);
				//设置关键字类中操作的行为当前遍历的行
				web.setline(rowNo);
				
				//反射机制实现测试用例的调用
				//实现方式，基于方法名和参数列表的差异来查找对应的方法，查找时，使用try catch机制
	
				//获取对应应用的类中没有参数列表，且能够匹配方法名的方法
				try {
					Method webMethod = web.getClass().getDeclaredMethod(caseline.get(3).toString());
					webMethod.invoke(web);
				} catch (Exception e) {

				}
				
				//基于方法名获取带有一个参数的方法
				try {
					Method webMethod = web.getClass().getDeclaredMethod(caseline.get(3).toString(), String.class);
					webMethod.invoke(web, caseline.get(4));
				} catch (Exception e) {
				}
				//基于方法名获取带有两个个参数的方法
				try {
					Method weMethod = web.getClass().getDeclaredMethod(caseline.get(3).toString(), String.class, String.class);
					weMethod.invoke(web, caseline.get(4),caseline.get(5));
				} catch (Exception e) {

				}
				//基于方法名获取带有三个参数的方法
				try {
					Method webMethod = web.getClass().getDeclaredMethod(caseline.get(3).toString(), String.class, String.class);
					webMethod.invoke(web, caseline.get(4), caseline.get(5), caseline.get(6));
				} catch (Exception e) {

				}
	
			}
		}
		resultExcel.save();
	}
	
	//日期
	public static String createDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
		String createDate = sdf.format(date);
		return createDate;
	}
}
