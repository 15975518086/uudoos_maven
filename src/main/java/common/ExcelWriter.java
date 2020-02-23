package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
// 用于存放结果表内容的xlsx格式的工作簿
private XSSFWorkbook xssfWorkbook = null;
//用于存放结果表内容的xls格式的工作簿
private HSSFWorkbook hssfWorkbook = null;
private Sheet sheet;
public int rows;
//用于存储结果表的路径的成员变量，便于在保存结果时进行判断
private String path = null;

// 用于读取用例表内容复制到结果标的文件输出流
private FileOutputStream stream = null;

// 单元格格式
private CellStyle style = null;

//根据用例表testPath ，创建结果表resultPath，讲testPath的内容复制到resultPath中
public  ExcelWriter(String testPath,String resultPath) {
	String originType = testPath.substring(testPath.lastIndexOf("."));
	//判断是xls还是xlsx格式，完成再内存中创建用列表的工作簿
	XSSFWorkbook xssfWorkbookRead = null;
	if (originType.equals(".xlsx")) {
		try {
			xssfWorkbookRead = new XSSFWorkbook(new File(testPath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	HSSFWorkbook hssfWorkbookRead =null;
	if (originType.equals(".xls")) {
		try {
			hssfWorkbookRead = new HSSFWorkbook(new FileInputStream(new File(testPath)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//如果两种格式均不符合，则文件打开失败
	if (hssfWorkbookRead == null && xssfWorkbookRead ==null) {
		System.out.println("Excel文件打开失败！");
		return;
	}
	
	//截取结果不后缀名
	String resultType = resultPath.substring(resultPath.lastIndexOf("."));
	//确定结果表中的格式
	if (resultType.equals(".xls") || resultType.equals(".xlsx")) {
		try {
			// 根据结果表的文件名，为该文件在内存中开辟空间
			File file = new File(resultPath);
			//在磁盘上创建该文件
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// 创建失败，提示路径非法，并停止创建
				System.out.println("文件路径非法，请检查！");
				return;
			}
			
			//基于结果表创建输出流stream
			 stream = new FileOutputStream(file);
			// 将用例表中的内容写入文件输出流stream
			if (hssfWorkbookRead != null) {
				hssfWorkbookRead.write(stream);
				//关闭用列表所在内存的副本
				hssfWorkbookRead.close();
			}else {
				xssfWorkbookRead.write(stream);
				xssfWorkbookRead.close();
			}
			//关闭已经写入了用例表内容的文件流
			stream.close();
			
			//基于结果表，创建文件输入流
			FileInputStream in = new FileInputStream(file);
			if (resultType.equals(".xls")) {
				try {
					hssfWorkbook = new HSSFWorkbook(in);
					sheet = hssfWorkbook.getSheetAt(0);
					rows = sheet.getPhysicalNumberOfRows();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				
			}
			if (resultType.equals(".xlsx")) {
				try {
					xssfWorkbook = new XSSFWorkbook(in);
					sheet = xssfWorkbook.getSheetAt(0);
					rows = sheet.getPhysicalNumberOfRows();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
			
			
			//关闭文件输入流
			in.close();
			// 将成员变量结果文件路径赋值为path，表示结果表已经成功创建。
			path = resultPath;
			// 设置默认样式为第一个单元格的样式
			setStyle(0, 0);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}else {
		System.out.println("写入的文件格式错误！");
	}

}


// 设置样式为Excel中指定单元格的样式
public void setStyle(int rowNo, int column) {
	Row row = null;
	Cell cell = null;
	try {
		// 获取指定行
		row = sheet.getRow(rowNo);
		// 获取指定列
		cell = row.getCell(column);
		// System.out.println(cell.getStringCellValue());
		// 保存指定单元格样式
		style = cell.getCellStyle();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//当用例执行结果失败时，使用该方法，以红色字体写入excel
public void writeFailCell(int rowNo, int columNo, String value) {
	Row row =null;
	try {
		row = sheet.getRow(rowNo);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	//判断行是否存在,不存在就创建
	if (row == null) {
		row = sheet.createRow(rowNo);
	}
	Cell cell = row.createCell(columNo);
	
	
	// 设置单元格值
	cell.setCellValue(value);
	//设置 单元格的文字颜色为红色
	Font font = null;
	CellStyle failStyle = null;
	
	
	if (hssfWorkbook != null) {
		font = hssfWorkbook.createFont();
		failStyle = hssfWorkbook.createCellStyle();
	}else {
		font = xssfWorkbook.createFont();
		failStyle = xssfWorkbook.createCellStyle();	
	}
	// 设置字体颜色为红色
	font.setColor(IndexedColors.RED.index);
	// 将字体颜色作为单元格样式
	failStyle.setFont(font);
	// 设置对应单元格样式
	cell.setCellStyle(failStyle);
	
}


//通过sheet的名称来指定工作的sheet
public void useSheet(String sheetName) {
	
	if (sheet != null) {
		if (hssfWorkbook != null) {
			sheet = hssfWorkbook.getSheet(sheetName);
		}else {
			sheet = xssfWorkbook.getSheet(sheetName);
		}
		rows =sheet.getPhysicalNumberOfRows();
	}else {
		System.out.println("未打开Excel文件！");
	}
	
}


//根据sheet序号指定使用的sheet
public void useSheetByIndex(int sheetIndex) {
	if (sheet != null) {
		try {
			if (hssfWorkbook != null) {
				sheet = hssfWorkbook.getSheetAt(sheetIndex);
			}else {
				sheet = xssfWorkbook.getSheetAt(sheetIndex);
			}
			rows = sheet.getPhysicalNumberOfRows();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error::sheet页面不存在！");
		}
		
	}else {
		System.out.println("未打开Excel文件！");
	}
	
}

//获取sheet的name
public String getSheetName(int sheetIndex) {
	String name = "";
	if (sheet != null ) {
		if (hssfWorkbook !=  null) {
			name = hssfWorkbook.getSheetName(sheetIndex);
		}else {
			name = xssfWorkbook.getSheetName(sheetIndex);
		}
	}else {
		System.out.println("未打开Excel文件！");
	}
	return name;
}


// 获取当前Excel的所有sheet页数
public int getTotalSheetNo() {
	int sheets = 0;
	if (hssfWorkbook != null) {
		sheets = hssfWorkbook.getNumberOfSheets();
	}else {
		sheets = xssfWorkbook.getNumberOfSheets();
	}	
	return sheets;
}

//以默认格式向单元格写入内容，参数使用与writeFailCell一致
public void writeCell(int rowNo, int columNo, String value) {
	
	Row row = null;
	try {
		row = sheet.getRow(rowNo);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (row == null) {
		row = sheet.createRow(rowNo);
	}
	
	Cell cell = row.createCell(columNo);
	cell.setCellValue(value);
	cell.setCellStyle(style);
	
}


// 写入一整行的内容
public void writeLine(int rowNo, List<String> list) {
	Row row = null;
	row = sheet.getRow(rowNo);
	if (row == null) {
		row = sheet.createRow(rowNo);
	}
	
	Cell cell =null;
	
	for (int i = 0; i < list.size(); i++) {
		 // 在该行，新建指定列的单元格
		 cell = row.createCell(i);
		 //设置单元格的值
		 cell.setCellValue(list.get(i));
		 //设置的单元格的格式
		 cell.setCellStyle(style);
	}

}

// 将结果表在内存中的工作簿内容保存到磁盘文件中
public void save() {
	if (path != null) {
		try {
			stream = new FileOutputStream(new File(path));
			if (hssfWorkbook != null) {
				hssfWorkbook.write(stream);
				hssfWorkbook.close();
			}else {
				if (xssfWorkbook != null) {
					xssfWorkbook.write(stream);
					xssfWorkbook.close();
				}else {
					System.out.println("未打开Excel文件！");
				}
			}
			// 关闭输出流，完成将内存中workbook写入文件的过程，保存文件。
			stream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

}
