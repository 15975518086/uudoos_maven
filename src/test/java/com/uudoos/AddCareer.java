package com.uudoos;

import org.testng.annotations.Test;

import keyword.KeyWordOfWeb;

public class AddCareer {
 
  @Test
  public void addCareer() {
		// TODO Auto-generated method stub
	  
		KeyWordOfWeb  kw = LoginTest.kw;
		kw.clickByXpath("//a/span[text()='招聘会']");
		kw.halt("2");
		kw.clickByXpath("//button[contains(text(),'添加招聘会')]");

		kw.inputByXpath("//input[@name='recruitName']", "秋季大型招聘会18R");
		kw.inputByXpath("//textarea[@name='introduction']", "大学生秋季招聘会大型招聘会，欢迎参加");
			
		kw.halt("2");
//		保存
		kw.clickByXpath("//button[text()='保存']");
	}
  
  

  
  
  
  
  
}
