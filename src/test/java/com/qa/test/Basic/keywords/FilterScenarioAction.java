package com.qa.test.Basic.keywords;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qa.test.automation.getpageobjects.GetPage;

public class FilterScenarioAction extends GetPage {
		
	 WebDriver driver;
	   
		public FilterScenarioAction(WebDriver driver) {
			super(driver, "filterSetting");
			this.driver = driver;
			
		}
		public void is_User_Able_To_Launch_Login_URL(String LoginURL) {
			launchSpecificUrl(LoginURL);
		}
		
		public void click_On_Women_Tab(){
			element("women_tab").click();
		}
		
		/*public void drag_The_Price_Slider(){
			element("pr");
		}*/
		public void Sort_Option(String filterNo){
			element("sort_Option",filterNo).click();
			hardWait(2);
		}
		public void click_On_Sort_ByDropDown(){
			element("sort_By_DDL").click();
		}
		public void sort_Price_Range_High_To_Low(String filterNo){
			Sort_Option(filterNo);
			element("list_Icon").click();
		}
				
		public void validate_if_Product_Sorted_As_Per_Price_Range(){
			List<WebElement> priceForProduct=elements("price_list");
			int priceOfFirstProduct = Integer.parseInt(priceForProduct.get(0).getText());
			int priceOfSecondProduct = Integer.parseInt(priceForProduct.get(1).getText());
			Assert.assertTrue((priceOfFirstProduct>priceOfSecondProduct),"[ASSERT FAILED]: Sort By Highest To lowest doesn't work");
		       logMessage("[ASSERT PASSED]: Products are Filtered by Prce Range Highest to Lowest");
		}
		
		public void sort_Name_By_A_To_Z(String filterNo){
			Sort_Option(filterNo);
		}
		
		public void validate_if_Product_Sorted_As_Per_Name(){
			List<WebElement> NameOfProduct = elements("name_list");
		}
}
