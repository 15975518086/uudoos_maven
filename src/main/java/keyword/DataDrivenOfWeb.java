package keyword;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import common.AutoLogger;
import common.ExcelWriter;


public class DataDrivenOfWeb {
	
	public WebDriver driver = null;
	public ExcelWriter webExcel = null;
	public int line =0;
	
	public DataDrivenOfWeb(ExcelWriter excel) {
		webExcel = excel;
	}
	
	public void setline(int rowNo) {
		line = rowNo;
	}
	
	/**
	 * 启动浏览器 
	 * @param browserType
	 * @return
	 */
	public  String openBrowser(String browserType) {
		try {
			switch (browserType) {
			case "chrome":
			   driver = new GoogleDriver("WebDrivers/chromedriver.exe").getdriver();
			   AutoLogger.logger.info("chrome浏览器启动");
			   driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			   break;
			case "firefox":
				driver = new FFDriver("D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe", "WebDrivers/geckodriver.exe").getdriver();
				AutoLogger.logger.info("firefox浏览器启动");
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				break;
							
			default:
				driver = new GoogleDriver("WebDrivers/chromedriver.exe").getdriver();
				AutoLogger.logger.info("chrome浏览器启动");
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				break;
			}
			
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AutoLogger.logger.error("启动浏览器失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";	
		}
				
	}
	
	
	/**
	 * 通过左上角左边和设置大小，来设置浏览器窗口位置和大小
	 */
	public String setWindowSize() {
		try {
			Point p = new Point(330, 0);
			Dimension d = new Dimension(1500, 1200);
			driver.manage().window().setPosition(p);
			driver.manage().window().setSize(d);
			AutoLogger.logger.info("调整浏览器窗口大小为1500*1200");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			AutoLogger.logger.error("调整浏览器窗口大小失败");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
			
		}
	}
	
	
	/**
	 * 访问某个页面
	 * @param url
	 */
	
	public String visitWeb(String url) {
		try {
			driver.get(url);
			AutoLogger.logger.info("访问"+url);
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AutoLogger.logger.error(url+"访问失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
			
		}
	}
	
	
	/**
	 * 通过xpath定位元素，并输入内容
	 * @param xpath
	 * @param content
	 */
	public String inputByXpath(String xpath,String content) {
		try {
			WebElement element =driver.findElement(By.xpath(xpath));
			element.clear();
			element.sendKeys(content);
			AutoLogger.logger.info("输入："+content);
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			AutoLogger.logger.error(xpath+"元素找不到");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("inputByXpath方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";

		}
	}
	
	

	/**
	 * 向某个元素输入内容 @param name 属性元素的name属性表达式 @param  输入元素的内容
	 */
	public String inputByName(String name,String content) {
		try {
			WebElement element = driver.findElement(By.name(name));
			element.sendKeys(content);
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(name+"name属性元素定位失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("inputByName方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
								
	}
	

	
/**
 * switch 同时输入定位访问和定位表达式、和内容
 * 
 */
	public String inputBy(String method,String locator,String content) {
		try {
			switch (method) {
			case "xpath":
				driver.findElement(By.xpath(locator)).sendKeys(content);
				break;

			case "id":
				driver.findElement(By.id(locator)).sendKeys(content);
				break;
			}
			
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("inputBy方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
		
	}
	
	
	
	/**
	 * xpath方法定位点击
	 */
	public String clickByXpath(String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).click();
			AutoLogger.logger.info("点击xpath元素："+xpath);
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			saveScreenShot("clickByXpath方法");
//			e.printStackTrace();
			AutoLogger.logger.error(xpath+"的元素定位不到");
			AutoLogger.logger.error(e, e.fillInStackTrace());
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	/**
	 * id方法定位点击
	 */
	public String clickById(String id) {
		try {
			driver.findElement(By.id(id)).click();
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(id+"id属性元素定位失败");
			saveScreenShot("clickById方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}

	
	
	/**
	 * 实现鼠标悬停到某个元素
	 */
	public String hover(String xpath) {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath(xpath))).build().perform();
			AutoLogger.logger.info("悬停到指定元素："+xpath);
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("元素悬停失败："+xpath);
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("hover方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	
	
	/**
	 * 获取当前网页的标题
	 */
	public String getTitle() {
		String title =null;
		try {
			title = driver.getTitle();
			return title;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(e,e.fillInStackTrace());
			AutoLogger.logger.error("获取页面标题失败");
			return null;
		}
	}

	
	
	
	/**
	 * 关闭浏览器
	 */
	public String closeBrowser() {
		try {
			driver.quit();
			driver=null;
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("关闭浏览器失败");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	

	
	
	/**
	 * 显式等待，语法规则使用expectedCondetion作为自定义等待条件时，需要实现一个apply方法，其返回值和ExpectedConditin<>类型的类型一致
	 * 
	 */
	public void explicitlyWait(String xpath) {
		try {
			WebDriverWait waitOB = new WebDriverWait(driver, 10);
			waitOB.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					return d.findElement(By.xpath(xpath));
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 显式等待使用预定义的方法来等待某个元素的出现（能够被定位到了）
	 */
	
	public void explicitlyWaitConditin(String xpath) {
		try {
			WebDriverWait waitOb = new WebDriverWait(driver, 10);
			waitOb.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 强制等待方法
	 */
	public String halt(String senconds) {
		try {
			int time =1000;
			time=Integer.parseInt(senconds)*1000;
			Thread.sleep(time);
			webExcel.writeCell(line, 10, "fass");
			return "pass";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(e,e.fillInStackTrace());
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	


	
	
	/**
	 * 使用fideelements方法，获取一组符合条件的元素，并且遍历输出其内容。
	 * 参考这个方法，当需要载网页种批量操作符合某个条件的一组元素时，进行操作
	 */
	public void multiElementLocate(String xpath) {
		List<WebElement> list = driver.findElements(By.xpath(xpath));
		for(WebElement e:list) {
			System.out.println(e.getText());
		}
	}
	
	

	
	
	/**
	 * 基于iframe的id或name切换
	 */
	public String switchIframeBynameOrid(String nameOrId) {
		try {
			driver.switchTo().frame(nameOrId);
			AutoLogger.logger.info("iframe切换成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("进入iframe切换失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("switchIframeBynameOrid方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}	
	}
	
	
	/**
	 * 基于xpath元素定位Iframe, 进入iframe子页面
	 */
	public String intoIframe(String xpath) {
		try {
			driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
			AutoLogger.logger.info("进入iframe成功！");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("进入iframe失败！");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("intoIframe方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	/**
	 * 切换到父级iframe
	 */
	public String switchOutIframe(String xpString) {
		try {
			driver.switchTo().parentFrame();
			AutoLogger.logger.info("切换父级Iframe成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("切换父级Iframe失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("switchToRoot方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
		
	}
	
	
	/**
	 * 切换到html主页面,退出子iframe页面
	 */
	public String switchToRoot() {
		try {
			driver.switchTo().defaultContent();
			AutoLogger.logger.info("退出子iframe成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("退出子iframe失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("switchToRoot方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	
	
	
	
	/**
	 * 基于页面标题切换窗口
	 */
	public String switchWindowByTitle(String targetTitle) {
		// 创建一个字符串便于之后存放句柄
		String targetHandle ="";
		// 获取当前页面中的句柄
		Set<String> windowHandles = driver.getWindowHandles();
		// 循环尝试，找到目标浏览器页面的句柄
		for(String handle : windowHandles) {
			// 遍历每一个句柄，判断窗口的标题是否包含预期字符
			System.out.println(handle);
			if (driver.switchTo().window(handle).getTitle().equals(targetTitle)) {
				targetHandle = handle;
				break;
			}
		}
		// 切换到目标句柄的页面中
		try {
			driver.switchTo().window(targetHandle);
			AutoLogger.logger.info(targetTitle+"通过页面标题切换窗口成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("通过页面标题切换窗口失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("switchWindowByTitle");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
			
		}
	}
	
	
	
	/**
	 * 关闭旧窗口切换到新窗口的操作
	 */
	public String closeOldWin() {
		List<String> handlelist = new ArrayList<String>();
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		while (it.hasNext()) {
			handlelist.add(it.next().toString());
			
		}
		//关闭第一个窗口	
		driver.close();
		//切换到新窗口
		try {
			driver.switchTo().window(handlelist.get(1));
			AutoLogger.logger.info("关闭旧窗口切换到新窗口成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("关闭旧窗口切换到新窗口的失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("closeNewWin");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	
	
	/**
	 * 关闭新窗口
	 */	
	public String closeNewWin() {
		List<String> handlelist = new  ArrayList<String>();
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		while (it.hasNext()) {
			handlelist.add(it.next().toString());
		}
		
		try {
			//切换新窗口
			driver.switchTo().window(handlelist.get(1));
			//关闭新窗口
			driver.close();
			//切换旧窗口
			driver.switchTo().window(handlelist.get(0));
			AutoLogger.logger.info("切换新窗口并关闭新窗口成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("切换新窗口并关闭新窗口失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("closeNewWin");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	

	
	
	/**
	 * 运行js脚本(执行无返回的js脚本)
	 */
	public String runJs(String jsScript) {
		JavascriptExecutor jsExe = (JavascriptExecutor)driver;
		try {
			jsExe.executeScript(jsScript);
			webExcel.writeCell(line, 10, "PASS");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(e, e.fillInStackTrace());
			AutoLogger.logger.error("JS脚本执行失败！");
			saveScreenShot("runJs");
			webExcel.writeFailCell(line, 10, "FAIL");
			return "fail";
		}
	}
	
	
	
	/**
	 * 获取js运行结果
	 */
	public String getJs(String text) {
		String t = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			t = js.executeScript("return " + text).toString();
			webExcel.writeCell(line, 10, "PASS");
		} catch (Exception e) {
			AutoLogger.logger.error(e, e.fillInStackTrace());
			AutoLogger.logger.error("JS脚本执行失败！");
			saveScreenShot("getJs");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
		return t;
	}
	
	
	
	public String runJsWithParam(String jsScript,String xpath) {
		try {
			JavascriptExecutor jsExe = (JavascriptExecutor)driver;
			jsExe.executeScript(jsScript, xpath);
			webExcel.writeCell(line, 10, "PASS");
			return "pass";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(e, e.fillInStackTrace());
			AutoLogger.logger.error("JS脚本执行失败！");
			saveScreenShot("runJsWithParam");
			webExcel.writeFailCell(line, 10, "FAIL");
			return "fail";
		}
		
		
	}
	
	
	
	/**
	 * 竖直滚动浏览器
	 */
	public void scrollStraight(String height) {
		try {
			JavascriptExecutor jsExe = (JavascriptExecutor)driver;
			jsExe.executeScript("window.scrollTo(0,"+height+")");
			webExcel.writeFailCell(line, 10, "PASS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(e, e.fillInStackTrace());
			AutoLogger.logger.error("操作浏览器滚动条失败");
			saveScreenShot("scroll");
			webExcel.writeFailCell(line, 10, "FAIL");
		}
	}
	

	/**
	 * 通过input[type='file']的元素完成文件上传，直接sendkeys即可
	 */
	
	public void uploadFile(String xpath,String filepath) {
		try {
			driver.findElement(By.xpath(xpath)).sendKeys(filepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 清空输入框
	 */
    public void clearByXpath(String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    
    
    
    
	// 实现select方法
	public String selectByText(String xpath, String text) {
		try {
			// 将webelement转换为select
			Select userSelect = new Select(driver.findElement(By.xpath(xpath)));
			userSelect.selectByVisibleText(text);
			webExcel.writeCell(line, 10, "PASS");
			return "pass";
		} catch (Exception e) {
			AutoLogger.logger.error(e, e.fillInStackTrace());
			AutoLogger.logger.error("通过文本选择Select失败！");
			saveScreenShot("Select");
			webExcel.writeFailCell(line, 10, "FAIL");
			return "fail";
		}
	}

	// 实现通过value选择select的选项
	public String selectByValue(String xpath, String value) {
		try {
			// 将webelement转换为select
			Select userSelect = new Select(driver.findElement(By.xpath(xpath)));
			userSelect.selectByValue(value);
			webExcel.writeCell(line, 10, "PASS");
			return "pass";
		} catch (Exception e) {
			AutoLogger.logger.error(e, e.fillInStackTrace());
			AutoLogger.logger.error("通过值选择Select失败！");
			saveScreenShot("Select");
			webExcel.writeFailCell(line, 10, "FAIL");
			return "fail";
		}
	}
    
    
   
    
    
    /**
	 * 报错时，截图方法，保存截图的文件格式为方法+当前时间.png
	 */
    public void saveScreenShot(String method) {
    	Date date = new Date();
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
    	String creatdate =df.format(date);
//    	拼接截图名称：工作目录路径+方法+当前时间.png
    	String scrName ="ScrShot/"+ method+creatdate+".png";
//    	以当前文件名创建一个空的png文件
    	File scrFile = new File(scrName);
    	
//    	将截图保存到临时文件中
    	File temp =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	
    	try {
			Files.copy(temp, scrFile);
			AutoLogger.logger.error("错误截图保存在"+scrName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("截图失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
		}

	}
  
    
	
	/**
	 * 断言标题中包含指定内容
	 */
	public String asserTitleContains(String target) {
		String result = driver.getTitle();
		if (result.contains(target)) {
			AutoLogger.logger.info("测试通过");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		}else {
			AutoLogger.logger.error("测试失败");
			saveScreenShot("asserTitleIs方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}	
	}
	
	
	
	/**
	 * 断言标题的内容符合预期
	 */
	public String asserTitleIs(String target) {
		String result = driver.getTitle();
		if (result.equals(target)) {
			AutoLogger.logger.info("测试通过");
			webExcel.writeCell(line, 10, "pass");
			return "pass";
		}else {
			AutoLogger.logger.error("测试失败");
			saveScreenShot("asserTitleIs方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	
	/**
	 * 断言页面中某个元素的文本内容是否符合预期
	 */
	public String assertContentIs(String xpath, String target) {
		try {
			WebElement ele = driver.findElement(By.xpath(xpath));
			String text = ele.getText();
			if (text.equals(target)) {
				AutoLogger.logger.info("测试通过");
				webExcel.writeCell(line, 10, "pass");
				return "pass";
			} else {
				AutoLogger.logger.info("测试失败");
				webExcel.writeFailCell(line, 10, "fail");
				return "fail";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.info("未找到指定元素！");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("assertContentIs方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}
	}
	
	
	/**
	 * 断言元素的某个值是否符合预期
	 * @param xpath 表达式
	 * @param attribute 属性值
	 * @param expectesContent 预期属性值
	 */
	public String assertElementAttrEquals(String xpath,String attibute,String expectedContent) {
		try {
			WebElement ele = driver.findElement(By.xpath(xpath));
			String value = ele.getAttribute(attibute);
			if (value.equals(expectedContent)) {
				AutoLogger.logger.info("测试通过");
				webExcel.writeCell(line, 10, "pass");
				return "pass";
			}else {
				AutoLogger.logger.info("测试失败");
				webExcel.writeFailCell(line, 10, "fail");
				return "fail";		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.info("未找到指定元素的指定属性！");
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("assertElementAttrEquals方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";		
		}
		
	}
	
	
	/**
	 * 断言页面源码中包含某个内容
	 */
	public String assertPageContains(String expectedContent) {
		String pageContent = driver.getPageSource();
		if (pageContent.contains(expectedContent)) {
			AutoLogger.logger.info("测试成功");
			webExcel.writeCell(line, 10, "pass");
			return "pass";

		} else {
			AutoLogger.logger.error("测试失败");
			saveScreenShot("assertPageContains方法");
			webExcel.writeFailCell(line, 10, "fail");
			return "fail";
		}

	}
	
	
    
    
    
    
}
	
	

	















