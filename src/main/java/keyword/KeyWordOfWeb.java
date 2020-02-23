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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import common.AutoLogger;

public class KeyWordOfWeb {
	
	public WebDriver driver;
//启动浏览器 browserType 浏览器类型
	public  void openBrowser(String browserType) {
		try {
			switch (browserType) {
			case "chrome":
			   driver = new GoogleDriver("WebDrivers/chromedriver.exe").getdriver();
			   AutoLogger.logger.info("chrome浏览器启动");
			   driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
			   break;
			case "firefox":
				driver = new FFDriver("C:\\Program Files\\Mozilla Firefox\\firefox.exe", "WebDrivers/geckodriver.exe").getdriver();
				AutoLogger.logger.info("firefox浏览器启动");
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				break;
							
			default:
				driver = new GoogleDriver("WebDrivers/chromedriver.exe").getdriver();
				AutoLogger.logger.info("chrome浏览器启动");
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AutoLogger.logger.error("启动浏览器失败");
		}		
	}
	
//	访问某个页面
	public void visitWeb(String url) {
		try {
			driver.get(url);
			AutoLogger.logger.info("访问"+url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(url+"访问失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
		}
	}
	
//	向某个元素输入内容 @param name 属性元素的name属性表达式 @param  输入元素的内容
	public void inputByName(String name,String content) {
		try {
			WebElement element = driver.findElement(By.name(name));
			element.sendKeys(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			saveScreenShot("input方法");
			System.out.println("name属性为"+name+"的元素定位不到");
			
								}
	}
	
//	通过xpath定位元素，并输入内容
	public void inputByXpath(String xpath,String content) {
		try {
			driver.findElement(By.xpath(xpath)).sendKeys(content);
			AutoLogger.logger.info("输入："+content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("inputByXpath方法");
			AutoLogger.logger.error(xpath+"元素找不到");
			
		}
	}
	
	
/**
 * switch 同时输入定位访问和定位表达式、和内容
 * 
 */
	public void inputBy(String method,String locator,String content) {
		switch (method) {
		case "xpath":
			driver.findElement(By.xpath(locator)).sendKeys(content);
			break;

		case "id":
			driver.findElement(By.id(locator)).sendKeys(content);
			break;
		}
	}
	
	
	
	
//	id方法定位点击
	public void clickById(String id) {
		try {
			driver.findElement(By.id(id)).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error(id+"id属性元素定位失败");
			saveScreenShot("clickById方法");
		}
	}
	
//	xpath方法定位点击
	public void clickByXpath(String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).click();
			AutoLogger.logger.info("xpath元素点击："+xpath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			saveScreenShot("click方法");
//			e.printStackTrace();
			AutoLogger.logger.error(xpath+"的元素定位不到");
			AutoLogger.logger.error(e, e.fillInStackTrace());
		}
	}
	
//	获取当前网页的标题
	public String getTitle() {
		try {
			String title = driver.getTitle();
			return title;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "获取标题失败";
		}
	}

	
//	关闭浏览器
	
	public void closeBrowser() {
		try {
			driver.quit();
			driver = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AutoLogger.logger.error("关闭浏览器失败");
			
		}
	}
	
	
//	通过左上角左边和设置大小，来设置浏览器窗口位置和大小
	
	public void setWindowSize() {
		try {
			Point p = new Point(330, 0);
			Dimension d = new Dimension(1500, 1200);
			driver.manage().window().setPosition(p);
			driver.manage().window().setSize(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public void halt(String senconds) {
		try {
			int time =1000;
			time=Integer.parseInt(senconds)*1000;
			Thread.sleep(time);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 断言页面师傅包含某个内容
	 */
	
	public boolean assertPageContains(String expectedContent) {
		try {
			if (driver.getPageSource().contains(expectedContent)) {
				System.out.println("测试通过");
				return true;
				
			}else {
				System.out.println("测试失败");
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/**
	 * 断言元素的某个值是否符合预期
	 * @param xpath 表达式
	 * @param attribute 属性值
	 * @param expectesContent 预期属性值
	 */
	public boolean assertElementAttrEquals(String xpath,String attibute,String expectedContent) {
		try {
			if (driver.findElement(By.xpath(xpath)).getAttribute(attibute).equals(expectedContent)) {
				AutoLogger.logger.info("测试通过");
				return true;
			}else {
				AutoLogger.logger.info("测试失败");
				return false;		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AutoLogger.logger.error("未找到指定元素的指定属性！");
			return false;
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
	 * 企业添加岗位
	 * @param xpath
	 */
	
	
	public void multiElementLocate1(String xpath) {
		List<WebElement> list = driver.findElements(By.xpath(xpath));
		int i = 0;
		for(WebElement e:list) {
//			点击选择岗位			
			driver.findElement(By.xpath("//input[@placeholder='请选择岗位']")).click();
//			选择岗位
//			e.click();
			i++;
			System.out.println("打印element字符串为"+e.toString());
			
			String elementStr  = e.toString();
			System.out.println(e.getText());
			
			int startIndex = elementStr.indexOf("//");
			int endIndex =elementStr.indexOf("i]");
			
			String xpString  = elementStr.substring(startIndex,endIndex+1);
//			String xpString2 = xpString+"["+i+"]";
			String xpString2 = xpString+"[1]";
			System.out.println("=============="+xpString2);
			
			driver.findElement(By.xpath(xpString2)).click();
			
//			添加岗位
			driver.findElement(By.xpath("//span[text()='添加']")).click();
			halt("2");
			
			
			
		}
	}
	

	/**
	 * 企业添加岗位
	 * @param xpath
	 */
	
	
	public void clickMultiElement(String xpath) {
		List<WebElement> list = driver.findElements(By.xpath(xpath));
		for(WebElement e:list) {
//			点击选择岗位			
			clickByXpath("//input[@placeholder='请选择岗位']");
//			点击岗位
			e.click();
//			输入招聘人数
			clearByXpath("//input[@placeholder='填写招聘人数']");
			inputByXpath("//input[@placeholder='填写招聘人数']", "10");
//			添加岗位
			driver.findElement(By.xpath("//span[text()='添加']")).click();
			halt("2");
		}
	}
	
	
	/**
	 * 封装登录操作
	 */
	
	public void login() {
		clickByXpath("//a[text()='登录']");
		inputByXpath("//input[@name='username']", "13800138006");
		inputByXpath("//input[@name='password']", "123456");
		inputByXpath("//input[@placeholder='验证码']", "1");
		clickByXpath("//a[@name='sbtbutton']");
		
	}
	
	
	
	/**
	 * 实现鼠标悬停到某个元素
	 */
	public void hover(String xpath) {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath(xpath))).build().perform();
			AutoLogger.logger.info("悬停到指定元素："+xpath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("元素悬停失败："+xpath);
			AutoLogger.logger.error(e,e.fillInStackTrace());
			saveScreenShot("hover方法");

		}

	}
	
	
	
	/**
	 * 基于页面标题切换窗口
	 */
	
	public void switchWindowByTitle(String targetTitle) {
		String targetHandle ="";
		Set<String> windowHandles = driver.getWindowHandles();
		System.out.println(windowHandles);
		for(String handle : windowHandles) {
			if (driver.switchTo().window(handle).getTitle().equals(targetTitle)) {
				targetHandle = handle;
				break;
			}
		}
		
		driver.switchTo().window(targetHandle);
	}
	
	
	
	/**
	 * 关闭旧窗口切换到新窗口的操作
	 */
	public void closeOldWin() {
		List<String> handlelist = new ArrayList<String>();
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		while (it.hasNext()) {
			handlelist.add(it.next().toString());
			
		}
//		关闭本窗口	
		driver.close();
//		切换到新窗口
		try {
			driver.switchTo().window(handlelist.get(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 关闭新窗口
	 */	
	public void closeNewWin() {
		List<String> handlelist = new  ArrayList<String>();
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		while (it.hasNext()) {
			handlelist.add(it.next().toString());	
		}
		
//		切换新窗口
		driver.switchTo().window(handlelist.get(1));
//		关闭新窗口
		driver.close();
//		切换旧窗口
		driver.switchTo().window(handlelist.get(0));
		
		
	}
	
	
	/**
	 * 基于iframe的id或name切换
	 */
	public void switchIframeBynameOrid(String nameOrId) {
		try {
			driver.switchTo().frame(nameOrId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("切换Iframe失败");
		}	
	}
	
	
	/**
	 * 基于xpath元素定位Iframe, 进入iframe子页面
	 */
	public void intoIframe(String xpath) {
		try {
			driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
			AutoLogger.logger.info("进入iframe成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("进入iframe失败");
			AutoLogger.logger.error(e,e.fillInStackTrace());
		}
	}
	
	
	/**
	 * 切换到父级iframe
	 */
	public void switchOutIframe(String xpString) {
		try {
			driver.switchTo().parentFrame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("切换父级Iframe失败");
		}
		
	}
	
	
	/**
	 * 切换到html主页面
	 */
	
	public void switchToRoot() {
		try {
			driver.switchTo().defaultContent();
			AutoLogger.logger.info("退出子iframe成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			AutoLogger.logger.error("退出子iframe失败");
			saveScreenShot("switchToRoot方法");
		}
	}
	
	/**
	 * 运行js脚本
	 */
	
	public void runJs(String jsScript) {
		JavascriptExecutor jsExe = (JavascriptExecutor)driver;
		try {
			jsExe.executeScript(jsScript);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void runJsWithParam(String jsScript,String xpath) {
		try {
			JavascriptExecutor jsExe = (JavascriptExecutor)driver;
			jsExe.executeScript(jsScript, xpath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/**
	 * 竖直滚动浏览器
	 */
	public void scrollStraight(String yOffset) {
		try {
			JavascriptExecutor jsExe = (JavascriptExecutor)driver;
			jsExe.executeScript("window.scrollTo(0,"+yOffset+")");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			AutoLogger.logger.error(e,e.fillInStackTrace());
			AutoLogger.logger.error("截图失败");
		}

	}
  
    
    
    
}
	
	

	














