package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import actionUtils.Impl;

public class LoginPage extends Impl{

	WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy (xpath ="(//button[contains(@id, 'text')]/following-sibling::input[starts-with(text(), 'ind')])[2]") WebElement input; 
	@FindBy (xpath ="//img[normalize-space()='text']/preceding::*/following::select[@id='text']/child::option[@id='tex' and @name='txt']") WebElement option; 
	@FindBy (xpath ="//span[@id='text']/descendant::*/parent::div[@id='text']/ancestor::li") WebElement li; 
	@FindBy (xpath ="//table[@id='txt']/thead/tr/th") List<WebElement> header; 
	@FindBy (xpath ="//table[@id='txt']/tbody/tr") List<WebElement> rows; 
	@FindBy (xpath ="//table[@id='txt']/tbody/tr/td") List<WebElement> data; 
	@FindBy (css ="div[id^='txt']") WebElement StartsWith; 
	@FindBy (className =".indi") WebElement classn; 
	@FindBy (css ="div[id$='rc']") WebElement endsWith; 
	@FindBy (css ="div[id*='txt']") WebElement contains;
	
	
	
	public MyAccountPage login(String name, String pass) {
		input.sendKeys(name);
		input.sendKeys(pass);
		Impl ii=new Impl();
		ii.isTextMatching(name, input);
		ii.isEnable(option);
		return new MyAccountPage(driver);
	}
	
	public boolean isMultiple(WebElement el1) {
		Select slt=new Select(el1);
		boolean fl=slt.isMultiple();
		return fl;
	}
	
	public List<WebElement> options(WebElement el1){
		Select slt=new Select(el1);
	List<WebElement> options=slt.getOptions();
	
	return options;
	}
	public boolean isSorted(WebElement el1) {
		Select slt=new Select(el1);
		List<WebElement> opts=slt.getOptions();
		
		List<String> orignal=new ArrayList<>();
		
		for(int i=0; i<opts.size(); i++) {
			orignal.add(opts.get(i).getText());
		}
		List<String> sorted=new ArrayList<>(orignal);
		
		Collections.sort(sorted);
		boolean fl=false;
		if(orignal.equals(sorted)) {
			fl=true;
		}
		Assert.assertTrue(fl,"options are not sorted");
		
		return fl;
	}
	
	public void product(WebElement el1) {
		List<WebElement> links=driver.findElements(By.xpath("//span[@id='text']"));
		
		List<String> price=new ArrayList<>();
		List<String> desc=new ArrayList<>();
		
		for(int i=0; i<links.size(); i++) {
			price.add(links.get(i).findElement(By.xpath("./div[@id='price']")).getText());
			desc.add(links.get(i).findElement(By.xpath("./div[@id='desc']")).getText());
		}
		System.out.println(desc+" "+price);
	}
	
	public void stale() {
		int attempt=0;
		int maxRetry=5;
		while(attempt<maxRetry) {
try {
	
		input.click();
	}catch (StaleElementReferenceException e) {
		attempt++;
	}
}
		Set<String> tabs=driver.getWindowHandles();
		List<String> tabList=new ArrayList<>(tabs);
		driver.switchTo().window(tabList.get(3));
		
}
	
	public void table() {
		
		for(int i=1; i<rows.size(); i++) {
			List<WebElement> collumns=driver.findElements(By.xpath("//table[@id='txt']/tbody/tr["+i+"]/td"));
			for(int j=1; j<collumns.size(); j++) {
				
				String value=driver.findElement(By.xpath("//table[@id='txt']/tbody/tr["+i+"]/td["+j+"]")).getText();
				
				System.out.println(value);
			}
		}
	}
	
	public void js() {
		Wait<WebDriver> wait=new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(30)).ignoring(NoSuchElementException.class);
		WebDriverWait myWait=new WebDriverWait(driver, Duration.ofSeconds(30));
		
		WebElement el1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(null)));
				WebElement el2=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(null)));
				Alert a =wait.until(ExpectedConditions.alertIsPresent());
				String msg=a.getText();
				
				if(msg.contains("ins") || msg.endsWith("in")|| msg.length()==4 || !msg.isEmpty()) {
					a.sendKeys("ok");
					a.accept();
				}else {
					a.dismiss();
				}
				
				boolean fl=wait.until(ExpectedConditions.textToBePresentInElement(el2, msg));
				wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(null, 39));
				wait.until(ExpectedConditions.titleContains(msg));
				wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(null), msg));
				wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(".sks")));
				
				WebElement el3=wait.until(new Function<WebDriver, WebElement>(){
					public WebElement apply (WebDriver driver) {
						return driver.findElement(By.xpath(null));
					};
				});
				WebElement el4= driver.findElement(By.xpath(null));
				
				JavascriptExecutor js=(JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", el1);
				js.executeScript("arguments[0].style.display='block';", el1);
				js.executeScript("arguments[0].removeAttribute('readOnly');", el1);
				js.executeScript("window.scrollTo(0,500);");
				js.executeScript("arguments[0].scrollIntoView(true);", el1);
				
				Actions act=new Actions(driver);
				act.dragAndDrop(el1, el4).perform();
				act.moveToElement(el1).moveToElement(el4).click().perform();
				act.moveToElement(el1).clickAndHold(el1).moveToElement(el2).release(el1).perform();
				act.sendKeys(Keys.PAGE_DOWN).perform();
				el1.sendKeys("path of file");
				act.moveToElement(el1).keyDown(Keys.SHIFT).click(el1).click(el2).click(el3).moveToElement(el4).keyUp(Keys.SHIFT).release(el4).build().perform();
				act.doubleClick(el1).perform();
				act.contextClick(el4).perform();
				act.click(el4).perform();
				
				
				Select slt=new Select(el1);
			List<WebElement> opts=slt.getOptions();
			for(int i=0; i<opts.size(); i++) {
				String txt=opts.get(i).getText();
				if(opts.get(i).isSelected()) {
					slt.deselectByVisibleText(txt);
				}
				if(!opts.get(i).isEnabled()) {
				if(txt.contains("ind") || txt.length()==4) {
					slt.selectByVisibleText(txt);
				}
				}
			}

	
	}
}