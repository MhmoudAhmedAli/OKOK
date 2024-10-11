package com.demoblaze.test;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demoblaze.pages.demoBlaze_CartPage;
import com.demoblaze.pages.demoBlaze_HomePage;
import com.demoblaze.pages.demoBlaze_LoginPage;
import com.demoblaze.pages.demoBlaze_ProductDetailsPage;
import com.demoblaze.pages.demoBlaze_SignUpPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoBlaze_TestRunner {
	
WebDriver driver;
	
	demoBlaze_HomePage homePage;
	demoBlaze_SignUpPage signUpPage; 
	demoBlaze_LoginPage logInPage; 
	demoBlaze_ProductDetailsPage prdPage;
	demoBlaze_CartPage cartPage;
	String prd_Price="";
	String prdName="Nokia lumia 1520";
	String userName="test_u10";
	
	@BeforeClass
	  public void beforeClass() {
		  WebDriverManager.chromedriver().setup();
		  driver= new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(Duration.ofMinutes(1));
		  driver.manage().window().maximize();		
		  driver.get("https://demoblaze.com/index.html");
		 

	  }
	
		@Test(priority = 1)
		public void navigateToHomePage() {
				homePage=new demoBlaze_HomePage(driver);		  
				String Actualpagetitle=homePage.getPageTitle();
				AssertJUnit.assertEquals(Actualpagetitle, "STORE");
		}	
	
		/*
		 * @Test(priority = 2) public void user_SignUp() throws InterruptedException {
		 * homePage=new demoBlaze_HomePage(driver); signUpPage=new
		 * demoBlaze_SignUpPage(driver); homePage.navigateToSignUpPage();
		 * signUpPage.user_SignUp(userName, "test"); }
		 */
		 
	  @Test(priority = 3)
	  public void user_Login() throws InterruptedException {
		  homePage=new demoBlaze_HomePage(driver);		
		  logInPage=new demoBlaze_LoginPage(driver);
		  homePage.navigateToLoginPage();
		  logInPage.user_Login(userName, "test");	
		  String expectedWelcomeText = "Welcome " + userName;

	      // Verify if the welcome text is displayed correctly
	        String actualWelcomeText = logInPage.getWelcomeText(); // Assume getWelcomeText() retrieves the welcome text

	        AssertJUnit.assertEquals(actualWelcomeText, expectedWelcomeText, "Validate User Logged In Successfully & Welcome text displayed to user:");
		
	  }
	  @Test(priority = 4)
	  public void addProductToCart() throws InterruptedException {
		  homePage=new demoBlaze_HomePage(driver);
		  prdPage=new demoBlaze_ProductDetailsPage(driver);		  
		  homePage.selectProduct(prdName);
		  String actualProductName = prdPage.getstrProductName();
		  AssertJUnit.assertEquals(actualProductName,prdName,"Validate System displays the selected product details");
		  prd_Price=prdPage.getProductPrice();//get Product Price details.
		  String str_AlertMsg=prdPage.addProductToCart(); 
		  AssertJUnit.assertEquals("Product added.", str_AlertMsg,"Validate Product Added to Cart & Alert Message Displayed to User");
		   // Assuming this method retrieves the actual product name from the detail page
	  }
	  
	  @Test(priority=5)
	  public void placeOrder() throws InterruptedException {
		  homePage=new demoBlaze_HomePage(driver);
		  cartPage=new demoBlaze_CartPage(driver);		  
		  homePage.navigateToCartPage();
		  String act_Prod_Name=cartPage.checkProductNameInCart();
		    AssertJUnit.assertEquals(act_Prod_Name,prdName,"Verify Product Name is Matching successfully");
		    String act_Prod_Price=cartPage.checkProductPriceInCart();
		    AssertJUnit.assertEquals("Verify Product price is Matching successfully", true,prd_Price.contains(act_Prod_Price));
		  cartPage.clickPlaceOrder();
		  cartPage.fillPlaceOrderToSubmit();
		 
	  }

   @AfterClass
  public void afterClass() {
	  System.out.println("After Class method is executed");

	  driver.quit();
  }


}
