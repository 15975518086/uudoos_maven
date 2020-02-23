package keyword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class IEDriverDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.ie.driver", "WebDrivers/IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.get("http://www.uudoos.com/");
		
		
		
		

	}

}
