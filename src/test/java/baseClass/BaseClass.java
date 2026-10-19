package baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public WebDriver driver;
	public Properties prop;
	public Logger logger;
	
	@BeforeClass
	public void setup() throws IOException {
		if(driver ==null) {
			logger= LogManager.getLogger(this.getClass());
			
			try {
				prop=new Properties();
				FileInputStream fis= new FileInputStream (System.getProperty("user.dir")+"\\resources\\configProp\\config.properties");
				prop.load(fis);
			}catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
			
			String browser=prop.getProperty("browser");
			
			if(browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				
				ChromeOptions opt=new ChromeOptions();
				opt.addArguments("--disable-notification");
				opt.addArguments("--starts-maximized");
				driver=new ChromeDriver(opt);
			}else if(browser.equalsIgnoreCase("edge")) {
				EdgeOptions opt=new EdgeOptions();
				opt.addArguments("--incognito");
				opt.addArguments("--headless");
				driver=new EdgeDriver(opt);
			}else {
				throw new RuntimeException("invalid browser:"+browser);
			}
			
			driver.get(prop.getProperty("url"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			Cookie coc=driver.manage().getCookieNamed("SessionId");
			
			FileOutputStream fos=new FileOutputStream (System.getProperty("user.dir")+"\\resources\\configProp");
		prop.setProperty("browser", "edge");
		prop.store(fos, null);
		}
	}
	
	@AfterClass
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	@DataProvider (name="dp", indices= {0,2,4,5})
	public Object [][] loginData(){
		Object data[][]={ {"username","password"},
				{"username","password"},
				{"username","password"},
				{"username","password"},
				{"username","password"},
				{"username","password"}
		}; 
		return data;
	}
	
	
	public String captureScreen(String ss) throws IOException {
		String time=new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
		TakesScreenshot ts=(TakesScreenshot) driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String fileDrop=System.getProperty("user.dir")+"\\screenshot\\"+ss+time+Math.random()+".png";
		File trg=new File(fileDrop);
		FileUtils.copyFile(src, trg);
		return (fileDrop);
	}
	
	
}
