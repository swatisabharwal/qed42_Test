## QED42
Test suite: src/test/java contains:
Package 1.  Session Initiator and Web Driver Factory
			Test Initiator:Initializing the action classes, Drivers , Setting yml ad config.properey file 			configuration.   
			WdFactory: Setting all the execution capabilities like browser, Selenium server.
			
Package 2. getPageObject cntains:
			Base Class: function which are generally used by all web application
			GetPage: Basic Selenium Functions for better understanding througout the project: Inherited by Base UI
			Locators: What all locators can be used and picked
			Object File Reader: Utility which will help reading oject Repository > files containing list of all 			web elements 
			
Package 3:  Standard Project Utilities
Package4:   Keyowrds: contains action classes , number of classes vary on the basis of module/Functionality/Page. 			They are mapped with test classes
Package 5:  Test Classes   

Test Suite 2: src.test.resources:

Suite contains all the test data to be used across the project:
			 ymlFile
			 config.properties
			 Driver folder
			 TestNg xml
			 
			 
*******
Execution can be done directly from test class or through TestNG Xml
*********    
# qed42_Test
