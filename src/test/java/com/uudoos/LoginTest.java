package com.uudoos;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import keyword.KeyWordOfWeb;


public class LoginTest {
	
 public static KeyWordOfWeb kw;

  @BeforeSuite
  public void initiallize() {
	    kw = new KeyWordOfWeb();
	    kw.openBrowser("chrome");
  }
	
	
  @Test(dataProvider = "dp")
  public void login(String name,String pwd) { 
	    
	    kw.visitWeb("http://datainsights.biz:10143/");
	    kw.clickByXpath("//input[@placeholder='请选择']");
		kw.clickByXpath("//li[text()='学校']");
		kw.inputByXpath("//input[@name='account']", name);
		kw.inputByXpath("//input[@name='password']", pwd);
		kw.clickByXpath("//button[@class='yd-button login-btn-main primary']");
		kw.halt("2");  
		 //退出登录
		kw.hover("//div[@class='image-wrapper']");
		kw.halt("2");
		kw.clickByXpath("//li[contains(text(),'注销')]");

  }
  
  @AfterClass
  public void afterClass() {
	    kw.visitWeb("http://datainsights.biz:10143/");
	    kw.clickByXpath("//input[@placeholder='请选择']");
		kw.clickByXpath("//li[text()='学校']");
		kw.inputByXpath("//input[@name='account']", "sch001");
		kw.inputByXpath("//input[@name='password']", "1234567a");
		kw.clickByXpath("//button[@class='yd-button login-btn-main primary']");
		kw.halt("2");  
	  
  }

  @AfterSuite
	public void afterSuite() {
		kw.closeBrowser();
	}
  
  
  @DataProvider
  public Object[][] dp(){
	  return new Object[][] {
		  {"sch001","1234567a"},
//		  {"sch002","1234567a"}
		  
	  };
  }
 
}
