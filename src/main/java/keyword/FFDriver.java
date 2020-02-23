package keyword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FFDriver {
	private WebDriver driver =null;
	public  FFDriver(String propath,String driverpath) {
		
		
//		设置Firefox的安装路径
		 System.setProperty("webdriver.gecko.driver", driverpath);
//	     设置Firefox的安装目录，如果不需要设置，那么参数给一个空字符串
		 if(propath !=null && propath.length()>0) {
			 System.setProperty("webdriver.firefox.bin",propath); 
		 }
//		 加载火狐的用户文件
		 FirefoxOptions firefoxOptions =new FirefoxOptions() {
			 FirefoxProfile profile =new FirefoxProfile();
		 };
		 
		 try {
			driver = new FirefoxDriver(firefoxOptions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("创建driver失败");
		}
		 
		 
	}

	 public WebDriver getdriver() {
		
		 return this.driver;
	}

}
