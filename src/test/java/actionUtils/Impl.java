package actionUtils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Impl implements Interf{

	public boolean isShowing(WebElement el1) {
		boolean fl=el1.isDisplayed();
		
		return fl;
	}

	@Override
	public boolean isTextMatching(String txt, WebElement el2) {
		boolean fl=el2.getText().contains(txt);
		return fl;
	}

	@Override
	public boolean isEnable(WebElement el1) {
	
		Select slt=new Select(el1);
		boolean fl=slt.isMultiple();
		
		return fl;
	}
	
	public static void main(String[] args) {
		Interf ii=new Impl();
		ii.show("sa");
		Interf.show(data, "sa");
	}
}
