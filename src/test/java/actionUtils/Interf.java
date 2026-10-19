package actionUtils;

import org.openqa.selenium.WebElement;

public interface Interf {

	
	int data=200;
	public boolean isShowing(WebElement el1);
	public boolean isTextMatching(String txt, WebElement el2);
	public boolean isEnable(WebElement el1);
	public default void show(String txt, int num) {
		System.out.println(txt+num);
	}
	public default void show(String txt) {
		System.out.println(txt);
	}
	public static void show( int num,String txt) {
		System.out.println(txt+num);
	}
}
