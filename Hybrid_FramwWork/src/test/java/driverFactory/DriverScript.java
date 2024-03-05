package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {   


	String inputpath="./FileInput/DataEngine.xlsx";
	String outputpath="./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	public static WebDriver driver;
	@Test
	public void startTest() throws Throwable {


		String Modulestatus ="";
		//create object for Excelfile utils 
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		String Testcases = "MasterTestCases";
		for(int i=1;i<=xl.rowCount(Testcases);i++) //iterate all rows in testcases sheet
		{
			if(xl.getCellData(Testcases, i, 2).equalsIgnoreCase("Y"))
			{ 

				//read all testcase or correseponding sheets
				String TCModule= xl.getCellData(Testcases, i, 1);  
				//define path of html
				report=new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html"); 
				logger= report.startTest(TCModule);

				//iterate all rows in TCModule sheet


				for( int j=1;j<=xl.rowCount(TCModule);j++) {

					//read all cells from TCModule
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4); 
					try { 
						if(Object_Type.equalsIgnoreCase("startBrowser"))	
						{
							driver  = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))	{
							FunctionLibrary.openUrl();
						}
						if(Object_Type.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}

						if(Object_Type.equalsIgnoreCase("validateTitle"))	{
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						} 
						if(Object_Type.equalsIgnoreCase("closeBrowser")){
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}      

						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}

						if(Object_Type.equalsIgnoreCase("captureStockNum")) {
							FunctionLibrary.captureStockNum(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}

						if(Object_Type.equalsIgnoreCase("stockTable")) {
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
   
						
						if(Object_Type.equalsIgnoreCase("capturesup")) {
							
							FunctionLibrary.capturesup(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
   if(Object_Type.equalsIgnoreCase("supplierTable")) {
	   
	   
	   FunctionLibrary.supplierTable();
	   logger.log(LogStatus.INFO, Description);
   }  
   if(Object_Type.equalsIgnoreCase("capturecus"))
	{
		FunctionLibrary.capturecus(Locator_Type, Locator_Value);
		logger.log(LogStatus.INFO, Description);
	}
	if(Object_Type.equalsIgnoreCase("customerTable"))
	{
		FunctionLibrary.customerTable();
		logger.log(LogStatus.INFO, Description);
	}




						//write as pass into status cell in TCMOdule
						xl.setCellData(TCModule, j, 5, "Pass", outputpath); 
						logger.log(LogStatus.PASS, Description);

						Modulestatus = "True";		

					}catch(Exception e) {
						System.out.println(e.getMessage()); 
						//write as pass into status cell in TCMOdule
						xl.setCellData(TCModule, j, j, "Fail", outputpath); 
						logger.log(LogStatus.FAIL, Description);
						Modulestatus = "False";
					}
					if(Modulestatus.equalsIgnoreCase("True")) {
						//write as pass into test case sheet
						xl.setCellData(Testcases, i, 3, "Pass", outputpath);
					}

					else {
						//write as pass into test case sheet
						xl.setCellData(Testcases, i, 3, "Fail", outputpath);
					}
					report.endTest(logger);
					report.flush();

				}
			}  
			else {

				//write as blocked into status cell for flag N 
				xl.setCellData(Testcases, i, 3, "Blocked", outputpath);
			}
		}
	}



}
