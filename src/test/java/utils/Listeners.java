package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import baseClass.BaseClass;

public class Listeners implements ITestListener{

	
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest test;
	
	String fileName="";
	
	public void onStart(ITestContext context) {
		String time=new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
		spark=new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports\\"+fileName+time+Math.random()+".html");
		spark.config().setReportName("functional test");
		spark.config().setDocumentTitle("QA Report");
		spark.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("env", "QA");
		extent.setSystemInfo("browser", "chrome");
		extent.setSystemInfo("os:", "windows");
		
		
	}
	
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getName());
		test.log(Status.PASS,"passed"+ result.getName());
		try {
			String imgPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getName());
		test.log(Status.SKIP,"skipped"+ result.getName());
		test.log(Status.INFO,"reason"+ result.getThrowable());
	}
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getName());
		test.log(Status.FAIL,"failed "+ result.getName());
		test.log(Status.INFO,"reason of failure:"+ result.getThrowable());
		try {
			String imgPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
