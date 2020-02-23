package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	public Sheet sheet;
	public XSSFWorkbook xssfWorkbook;
	public HSSFWorkbook hssfWorkbook;
	public int rows;
	public int lineNow;
	public void name() throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\DI\\Desktop\\HTTPLogin.xlsx");
		workbook.createSheet();
	}
	
	public static void main(String[] args) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\DI\\Desktop\\HTTPLogin.xlsx")));
		
		
//		Sheet sheet = workbook.getSheetAt(0);
		Sheet sheet = workbook.getSheet("冒烟用例");
		System.out.println(workbook.getSheetName(0));
		System.out.println(sheet.getPhysicalNumberOfRows());
		System.err.println(sheet.getRow(1).getPhysicalNumberOfCells());
		System.out.println(workbook.getNumberOfSheets());
	}
	
	
	public ExcelReader(String filePath) {
		
		String type = filePath.substring(filePath.lastIndexOf("."));
		FileInputStream in = null;
			try {
				in = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
		try {
			// 判断是xls还是xlsx格式 ,如果是xlsx格式，通过文件流，在内存中创建xssfworkbook工作簿
			if (type.equals(".xlsx")) {
				xssfWorkbook = new XSSFWorkbook(in);
				// 初始化sheet页
				sheet = xssfWorkbook.getSheetAt(0);
				//获取最大行
				rows = sheet.getPhysicalNumberOfRows();
				lineNow = 0;
						
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//如果是xls格式，通过文件流创建hssfworkbook工作簿
			if (type.equals(".xls")) {
				hssfWorkbook = new HSSFWorkbook(in);
				// 初始化sheet页
				sheet = hssfWorkbook.getSheetAt(0);
				//获取最大行
				rows = sheet.getPhysicalNumberOfRows();
				lineNow = 0;
						
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//关闭文件流
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 如果没有sheet，则输出打开Excel失败的提示
		if (sheet == null) {
			System.out.println("Excel文件打开失败!");
		}
		
		
		
	}
	
	
	//名称读取sheet页
	public void useSheetByName(String sheetName) {
		if (sheet != null) {
			if (hssfWorkbook != null) {
				sheet = hssfWorkbook.getSheet(sheetName);
			}else {
				sheet = xssfWorkbook.getSheet(sheetName);	
			}
			rows= sheet.getPhysicalNumberOfRows();
			lineNow = 0;
		}else {
			System.out.println("未打开Excel文件！");
		}
	}
	
	
	//通过index读取sheet页
	public void useSheetByIndex(int sheetIndex) {
		if (sheet != null) {
			try {
				if (hssfWorkbook != null)
					sheet = hssfWorkbook.getSheetAt(sheetIndex);
				else
					sheet = xssfWorkbook.getSheetAt(sheetIndex);

				rows = sheet.getPhysicalNumberOfRows();
				lineNow = 0;
			} catch (Exception e) {
				System.out.println("error::sheet页面不存在！");
				System.out.println(e.fillInStackTrace());
			}
		} else
			System.out.println("error::未打开Excel文件！");
	}
	
	
	//获取当前sheet页名称
	public String getSheetName(int sheetIndex) {
		String sheetName = "";
		if (hssfWorkbook != null) {
			sheetName = hssfWorkbook.getSheetName(sheetIndex);
		}else {
			sheetName = xssfWorkbook.getSheetName(sheetIndex);
		}
		return sheetName;
	}
	
	//获取sheet页总数
	public int getTotalSheetNo() {
		int sheets = 0;
		if (hssfWorkbook != null) {
			sheets = hssfWorkbook.getNumberOfSheets();
			
		}else {
			sheets = xssfWorkbook.getNumberOfSheets();
		}
		return sheets;
	}
	
	
	//读取完成，关闭excel
	public void closeExcel() {
		try {
			if (hssfWorkbook != null) {
				hssfWorkbook.close();
			}else {
				xssfWorkbook.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//读取指定行
	public List<String> readLine(int rowNo) {
		List<String> line = new ArrayList<String>();
		Row row=sheet.getRow(rowNo);
		int cells = row.getPhysicalNumberOfCells();
		for (int cell = 0; cell <cells; cell++) {
			//
			line.add(getCellValu(row.getCell(cell)));
		}
		return line;
	}
	
	
	//读取指定列
	public List<String> readColumn(int columnNo) {
		List<String> column = new ArrayList<String>();
		for (int i = 0; i <rows; i++) {
			Row row = sheet.getRow(i);
			column.add(getCellValu(row.getCell(columnNo)));
		}
		return column;
	}
	
	//读取指定单元格
	public String readCell(int rowNo,int columnNo) {
		String cellContent = "";
		Row row = sheet.getRow(rowNo);
		cellContent =getCellValu(row.getCell(columnNo));
		return cellContent;
	}

	
	//以二维数组形式读取excel文件内容[第一行是表头,不是数据不需要读取，直接从第2行开始读]
	public Object[][] readAsMatrix() {
		//获取当前sheet页中第一行的最大单元格数。
		int colcount = sheet.getRow(0).getPhysicalNumberOfCells();
		Object[][] matrix = new Object[rows-1][colcount];
		//用例从excel中的第2行开始读取，遍历到最后一行
		for (int rowNo = 1; rowNo < rows; rowNo++) {
			for (int colNo = 0; colNo < colcount; colNo++) {
				matrix[rowNo-1][colNo] = readCell(rowNo, colNo);
			}
		}
		return matrix;
	}
	
	
	//以二维数组形式读取excel中的一行，二维数组里面只有一个一维数组了[二维数组里面一个元素是一条测试数据，也就是一行，只读一行作为二维数组没有应用场景]
	public Object[][] readLineAsMatrix(int lineNo) {
		int cellcount = sheet.getRow(0).getPhysicalNumberOfCells();
		
		Object[][] matrix = new Object[1][cellcount];
		for (int colNo = 0; colNo < cellcount; colNo++) {
			matrix[0][colNo-1] = readCell(lineNo, colNo);
		}
		return matrix;
	}	
	
	// 针对单元格内容不同格式进行读取
	@SuppressWarnings("deprecation")
	public String getCellValu(Cell cell) {
		//当单元格内容为空时，返回null，这是由于poi读取某些xls格式的excel表时，针对某些空白格会报空指针异常
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}else {
			int cellType = cell.getCellType();
			
			// 将所有格式转为字符串
			switch (cellType) {
			case Cell.CELL_TYPE_STRING:	//文本
				cellValue = cell.getStringCellValue();
				break;
		
			case Cell.CELL_TYPE_NUMERIC:	//数字、日期
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = fmt.format(cell.getDateCellValue()); // 日期型
				} else {
					Double d = cell.getNumericCellValue();
					DecimalFormat df = new DecimalFormat("#.##");
					cellValue = df.format(d);
				}
				break;
			
			case Cell.CELL_TYPE_BOOLEAN://布尔型
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
				
			case Cell.CELL_TYPE_BLANK://空白
				cellValue = cell.getStringCellValue();
			    break;
			
			case Cell.CELL_TYPE_ERROR://错误
				cellValue = "错误";
				break;
			
			case Cell.CELL_TYPE_FORMULA://公式
				cellValue = "错误";
				break;
			
			default:
				cellValue = "错误";
			}
		}
		return cellValue;
		
	}
	
	//设置日期显示格式
	public void setDateFormat(String dateFormat) {
		fmt = new SimpleDateFormat(dateFormat);
		
	}


	
}
