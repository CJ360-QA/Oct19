package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CommonPage {

	WebDriver driver;
	
	public CommonPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
}
