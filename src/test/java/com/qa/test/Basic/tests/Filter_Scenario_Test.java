package com.qa.test.Basic.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.test.automation.TestSessionInitiator;
import com.qa.test.automation.utils.YamlReader;

public class Filter_Scenario_Test {
	TestSessionInitiator test;

	@Parameters("browser")
	@BeforeClass
	public void start_test_session(@Optional String browser) {
		test = new TestSessionInitiator("Filter_Scenario_Test", browser);
	}
	
	@Test
	public void Test001_Launch_Login_URL() {
		test.filterPage.is_User_Able_To_Launch_Login_URL(YamlReader.getData("base_url"));
	}
	@Test
	public void Test002_Verify_User_Can_Go_To_Women_Section() {
		test.filterPage.click_On_Women_Tab();
		//Incomplete: test.loginPage.drag_The_Price_Slider();
	}
	
	/*
	 * scenario 2a: Sort by Price Highest to Lowest
	 * Bug1: Sort Do3sn't work in Application 
	 */
	@Test
	public void Test003_Verify_Product_Is_Sorted_High_To_Low_In_Price_Range(){
		test.filterPage.click_On_Women_Tab();
		test.filterPage.click_On_Sort_ByDropDown();
		test.filterPage.sort_Price_Range_High_To_Low("1");
		test.filterPage.validate_if_Product_Sorted_As_Per_Price_Range();
	}
	
	@Test
	public void Test004_Verify_Product_Is_Sorted_By_Namae_From_A_To_z(){
		test.filterPage.sort_Name_By_A_To_Z("4");
		test.filterPage.validate_if_Product_Sorted_As_Per_Name();
	}
}
