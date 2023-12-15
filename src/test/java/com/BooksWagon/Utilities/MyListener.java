package com.BooksWagon.Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class MyListener implements ITestListener{
	
	public static ExtentReports extentreports;
	public static ExtentTest extentTest;
	public ScreenshotUtility s;
	public void onTestFailure(ITestResult result) {
	    try {
	    	String screenshotPath = ScreenshotUtility.takeScreenshot();
	    	//extentTest.fail("Test Case failed check screenshot "+extentTest.addScreenCaptureFromPath(screenshotPath,"Failed Test Screenshots"));
	    	extentTest.fail("Testcase is Failed! Check Screenshot",  MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath, "Failed Test Screenshot").build());
	    	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	  }
	public void onStart(ITestContext context) {
		 s=new ScreenshotUtility();
		 
		 System.out.println("Testing has Started for BooksWagon Application");
		 ExtentSparkReporter sparkreporter = new ExtentSparkReporter("Test-Report.html");
		 File file = new File("src/test/resources/extent-report-config.json");
			try {
				sparkreporter.loadJSONConfig(file);
				//sparkreporter.config().setTheme(Theme.DARK);
				//sparkreporter.config().setReportName("Test Results for BooksWagon Application");
				//sparkreporter.config().setCss(".detail-body img+.title{background:#cf0000}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 extentreports = new ExtentReports();
			extentreports.attachReporter(sparkreporter);	
			extentreports.setSystemInfo("OS", System.getProperty("os.name"));
			extentreports.setSystemInfo("JAVA", System.getProperty("java.version"));
			 extentreports.setSystemInfo("Host Name", System.getProperty("user.name"));
			 extentreports.setSystemInfo("Browser Name", "Chrome");
			 extentreports.setSystemInfo("Application URL", "https://www.bookswagon.com/");
			extentTest=	extentreports.createTest(context.getName());
			
				
		  }
	public  void onTestSuccess(ITestResult result) {
		  extentTest.log(Status.PASS, " Testcase is Passed Successfully");
	  }
	  
	public void onFinish(ITestContext context) {
		extentreports.flush();
		try {
			Desktop.getDesktop().browse(new File("Test-Report.html").toURI());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
