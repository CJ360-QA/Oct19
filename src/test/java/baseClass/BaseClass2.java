package baseClass;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass2 {

	public WebDriver driver;
	public Properties prop;
	
	@BeforeClass
	@Parameters ({"url","browser"})
	public void setup(String url, String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":driver=new ChromeDriver(); break;
		case "edge":driver=new EdgeDriver(); break;
		default: System.out.println("invalid browser:"+browser); return;
		}
		
		driver.get(url);
	}
}
