package commonFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

public class FunctionLibrary {   
	
	public static Properties conpro;
	public static WebDriver driver;
	//method for launching the browser 
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Enviroment.properties"));//load property file 
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{ 
			
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			
			driver = new FirefoxDriver();
			
		}
		
		else {
			
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
		
		
	}

	//method for launching url 
	
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
		
		
	}

	 //method for wait for element
	public static void waitForElement(String Locator_Type,String Locator_Value,String TestData)
	{
		WebDriverWait mywait= new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
		 
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));

		} 
		if(Locator_Type.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
			
		}
		
	}

	 //method for type action used to perform in textboxes
	 public static void typeAction(String Locator_Type,String Locator_Value,String Test_Data) {
		
		 if(Locator_Type.equalsIgnoreCase("xpath"))
		 {
			 driver.findElement(By.xpath(Locator_Value)).clear();
			 driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
			 
		 }
		 if(Locator_Type.equalsIgnoreCase("id")) { 
			 driver.findElement(By.xpath(Locator_Value)).clear();
			 driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		 }
		 if(Locator_Type.equalsIgnoreCase("name")) {
			 driver.findElement(By.name(Locator_Value)).clear();
			 driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		 }
	 }

	//method for click action to perform action on buttons,images,links,radiobuttons,and checkboxes
	 
	 public static void clickAction(String Locator_Type,String Locator_value)
	 {
		if(Locator_Type.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locator_value)).click();
		}
		 if(Locator_Type.equalsIgnoreCase("name")) { 
			 driver.findElement(By.xpath(Locator_value)).click();
			 
		 }
		 if(Locator_Type.equalsIgnoreCase("id")) {
			 driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
		 }
		 
		 
	 }

	//method for validation title 
	 public static void validateTitle( String Expected_Title ) { 
		 String Actual_Title = driver.getTitle();
		 try {
			 Assert.assertEquals(Actual_Title, Expected_Title,"title is not matching");
		} catch (AssertionError a) {
			System.out.println(a.getMessage());
		}
	 }
		//method for close browser
		 public static void closeBrowser() 
		 {
			 driver.quit();
		 } 
		 
		 //methodfor date generate 
		 public static String generateDate() {
			 
			 Date date = new Date();
			 DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
			return df.format(date);
		 }
		 
	 //method for listboxes/drop down 
		 
		 public static  void dropDownAction(String Locator_Type,String Locator_Value,String Test_Data)
		 {
			 
			 if(Locator_Type.equalsIgnoreCase("xpath"))
			 {
				 int value = Integer.parseInt(Test_Data);
				 Select element = new Select(driver.findElement(By.xpath(Locator_Value)));
				 element.selectByIndex(value);
			 }  
			 if(Locator_Type.equalsIgnoreCase("name"))
			 {
				 int value = Integer.parseInt(Test_Data);
				 Select element = new Select(driver.findElement(By.name(Locator_Value)));
				 element.selectByIndex(value); 
			 } 
			 if(Locator_Type.equalsIgnoreCase("id")) {
				 
				 int value = Integer.parseInt(Test_Data);
				 Select element = new Select(driver.findElement(By.id(Locator_Value)));
				 element.selectByIndex(value); 
			 }
			 
		 }

	 
	 //method for capture stock number into notepad
		 
		 public static void captureStockNum(String Locator_Type,String Locator_Value) throws Throwable {
			 
			 String StockNum = "";
			 
			 if(Locator_Type.equalsIgnoreCase("id")) {
				 
				 StockNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			 }
			 
			 if(Locator_Type.equalsIgnoreCase("name")) {
				 
				 StockNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
			 }
			 
			 if(Locator_Type.equalsIgnoreCase("xpath")) {
				 
				 StockNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
				 
				 
				 
			 }   
			 
			FileWriter fw = new FileWriter("./Capture Data/stocknumber.txt");
			BufferedWriter bw= new BufferedWriter(fw);
			bw.write(StockNum);
			bw.flush();
			bw.close();
		 }
		 
			// method for stocktable
			
	
			public static void stockTable() throws Throwable {
				
				FileReader fr = new FileReader("./Capture Data/stocknumber.txt");
				BufferedReader br = new BufferedReader(fr);
			 String Exp_Data =	br.readLine();
			 if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			  driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			 driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
			 driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
			 driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
				
			 Thread.sleep(4000);
				String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
				Reporter.log(Exp_Data+ "     " +Act_Data ,true); 
				
				try {
					Assert.assertEquals(Exp_Data, Act_Data,"Stock Number not matching");
				}
				catch(AssertionError a)
				{
					System.out.println(a.getMessage());
				}
				
			}
				
				//method for capture supplier number 
				
				public static void capturesup(String Locator_Type,String Locator_Value)throws Throwable
				{
					
					String SupplierNum = "";
					if(Locator_Type.equalsIgnoreCase("xpath")) {
						SupplierNum  =  driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
					}
					
					if(Locator_Type.equalsIgnoreCase("name")) {
						SupplierNum  =  driver.findElement(By.name(Locator_Value)).getAttribute("value");
					}
					
					
					
					if(Locator_Type.equalsIgnoreCase("id")) {
						SupplierNum  =  driver.findElement(By.id(Locator_Value)).getAttribute("value");
					}
					
					FileWriter fw = new FileWriter("./Capture Data/suppliernumber.txt");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(SupplierNum);
					bw.flush();
					bw.close();
					
					
					
					
					
					
				}
			
		//method for  suppliertable
				public static  void supplierTable() throws Throwable {
					
					FileReader fr = new FileReader("./Capture Data/suppliernumber.txt");
					BufferedReader br = new BufferedReader(fr);
					String Exp_Data = br.readLine();
					if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
						  driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
						 driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
						 driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
						 driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
							
						 Thread.sleep(4000);
					
					String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
					
					Reporter.log(Exp_Data+ "       "+Act_Data,true);  
					
					try{
					Assert.assertEquals(Exp_Data, Act_Data,"Supplier number is not matching");
				} catch(AssertionError a) {
					
					System.out.println(a.getMessage());
				}
	 
	 
	 
		} 
				
				//method for capture customer number
				public static void capturecus(String Locator_Type,String Locator_Value)throws Throwable
				{
				String CustomerNum="";
				if(Locator_Type.equalsIgnoreCase("xpath"))
				{
					CustomerNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
					
				}
				if(Locator_Type.equalsIgnoreCase("id"))
				{
					CustomerNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
					
				}
				if(Locator_Type.equalsIgnoreCase("name"))
				{
					CustomerNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
					
				}
				FileWriter fw = new FileWriter("./CaptureData/Customernumber.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CustomerNum);
				bw.flush();
				bw.close();
				}
				//method for customer table
				public static void customerTable()throws Throwable
				{
					FileReader fr = new FileReader("./CaptureData/Customernumber.txt");
					BufferedReader br = new BufferedReader(fr);
					String Exp_Data = br.readLine();
					if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
						driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
					driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
					driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
					driver.findElement(By.xpath(conpro.getProperty("search-button") )).click();
					Thread.sleep(4000);
					String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
					Reporter.log(Exp_Data+"     "+Act_Data,true);
					try {
					Assert.assertEquals(Exp_Data, Act_Data, "Customer number Not Matching");
					}catch(AssertionError a)
					{
						System.out.println(a.getMessage());
					}
				}	
		
				
	
				
				
				
				
				
				
				
}
