package keyword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleDriver {
	private WebDriver driver;

	public GoogleDriver( String driverpath) {
//		设置chrome的路径
		System.setProperty("webdriver.chrome.driver", driverpath);
//		chrome浏览器参数对象
		ChromeOptions option =new ChromeOptions();
//		去除chrome浏览器上的被自动话软件操作警告【74版本以上，下面这句无没法去除警告】
//		option.addArguments("disable-infobars");
		/**
		 * 加载chrome用户文件，这里使用的是浏览器默认存储的用户文件目录。
		 * 在chrome浏览器里通过地址栏里输入chrome://version 进行访问，能够看到个人资料路径
		 * 注意个人资料路径中复制时，只需要到User Data这一级，不需要Default这一级
		 * 使用时会和手动打开的浏览器冲突，注意不要同时打开。
		 */
//		option.addArguments("--user-data-dir=C:\\Users\\hjm76\\AppData\\Local\\Google\\Chrome\\User Data");
		//也可以将浏览器路径下的User Data目录复制一份放到其它位置进行引用，这样不会和手动打开的浏览器产生冲突。
//		option.addArguments("--user-data-dir=D:\\chromeData\\copyData");
		
		
//		最大化浏览器窗口
		option.addArguments("--start-maximized");
		
		try {
			this.driver = new ChromeDriver(option);
			driver.get("about:blank");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("创建chromedriver失败");
		}
	}

	public WebDriver getdriver() {
		return this.driver;
	}

}
