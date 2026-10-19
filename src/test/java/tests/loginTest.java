package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import pages.LoginPage;
import utils.RetryAnalyzer;

public class loginTest extends BaseClass{

	
	
	@Test (priority=1, dataProvider="dp", groups={"smoke","reg","batch 1"}, description="loginTest" , alwaysRun=false, enabled=false,retryAnalyzer=RetryAnalyzer.class, expectedExceptions=IOException.class, timeOut=5000, invocationCount=4)
	public void t1() {
	LoginPage lp=new LoginPage(driver);
	lp.login(null, null);
	lp.isShowing(driver.findElement(null));
	lp.isTextMatching("txt", driver.findElement(null));
	lp.login(null, null);
	
	
	}
	
	@Test (dependsOnMethods="t1", dependsOnGroups="smoke")
	public void t2() {
		String tab1=driver.getWindowHandle();
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("url1");
		
		String tab2=driver.getWindowHandle();
		driver.switchTo().window(tab1);
		Set<String> tabs=driver.getWindowHandles();
		Iterator<String> itt=tabs.iterator();
		
		for(String t:tabs) {
			driver.switchTo().window(t);
		}
	}
	
	@Test (priority=1, groups="batch2")
	public void t3() throws IOException {
		List<WebElement> links=driver.findElements(By.tagName("a"));
		
		int dup=0;
		
		for(int i=0; i<links.size(); i++) {
			String txt=links.get(i).getText();
			String href=links.get(i).getText();
			for(int j=i+1; j<links.size(); j++) {
				String txt2=links.get(j).getText();
				String href2=links.get(j).getText();
				
				if(txt.equals(txt2)) {
					dup++;
					System.out.println("this is dup:"+txt);
				}
			}
		}
		System.out.println("total dup:"+dup);
		
		for(int i=0; i<links.size(); i++) {
			if(i%2==0) {
				links.get(i).click();
			}
			String txt=links.get(i).getText();
			System.out.println(txt);
			links.get(i).click();
			if(txt.contains("india") || txt.length()==4 || txt.endsWith("ia") || !txt.isEmpty()) {
				links.get(i).click();
				System.out.println(txt);
			}
			if(links.get(i).isDisplayed()) {
				System.out.println(txt);
			}
			if(links.get(i).isSelected()) {
				System.out.println(txt);
				links.get(i).clear();
			}
			if(links.get(i).isEnabled()) {
				System.out.println(txt);
			}
			for(int j=i+1; j<links.size(); i++) {
				String href2=links.get(i).getAttribute("href");
				
				if(href2.isEmpty() || href2==null) {
					System.out.println("this link value is empty:"+href2);
					continue;
				}
				 URL linkUrl=new URL(href2);
				 HttpURLConnection conn=(HttpURLConnection) linkUrl.openConnection();
				 conn.setReadTimeout(3000);
				 conn.setConnectTimeout(5000);
				 conn.connect();
				 
				 if(conn.getResponseCode()<400) {
					 System.out.println("valid link:"+href2+" with response msg:"+conn.getResponseMessage());
				 }else {
					 System.out.println("broken link:"+href2+" with response msg:"+conn.getResponseMessage());
				 }
				 
			}
			
			
		}
	}
	
	@Test
	public void t05() throws IOException {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
		WebDriverWait myWait=new WebDriverWait(driver, Duration.ofSeconds(30));
		
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\testData\\cj.xlsx");
		
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet sheet=wb.getSheet("Sheet 1");
		
		int rows=sheet.getLastRowNum();
		int cells=sheet.getRow(0).getLastCellNum();
		
		for(int i=1; i<=rows; i++) {
			XSSFRow row=sheet.getRow(i);
			for(int j=1; j<=cells; j++) {
				XSSFCell cell=row.getCell(j);
				
				System.out.println(cell+"/t");		
			}
		}
		wb.close();
		fis.close();
		
		FileOutputStream fos=new FileOutputStream(System.getProperty("user.dir")+"\\testData\\cj.xlsx");
		XSSFWorkbook wb1=new XSSFWorkbook();
		XSSFSheet sheet1=wb1.createSheet();
		
		XSSFRow row=sheet1.createRow(0);
		row.createCell(0).setCellValue("21@");
		row.createCell(1).setCellValue("21");
		
		XSSFRow row2=sheet1.createRow(1);
		row2.createCell(0).setCellValue("sas");
		
		List<WebElement> Row=driver.findElements(By.xpath("//table[@id='txt']/tbody/tr"));
		List<WebElement> Header=driver.findElements(By.xpath("//table[@id='txt']/thead/tr/th"));
		for(int i=1; i<=Row.size(); i++) {
			
			for(int j=1; j<=Header.size(); j++) {
				String data=driver.findElement(By.xpath("//table[@id='txt']/tbody/tr["+i+"]/td["+j+"]")).getText();
				System.out.print(data+"\t");
			}
			System.out.println();
		}
	}
	
	
}
