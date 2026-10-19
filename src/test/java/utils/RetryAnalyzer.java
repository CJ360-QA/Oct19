package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{

	int attempt=0;
	int maxRetry=5;
	
	public boolean retry(ITestResult result) {
		
		if(attempt<maxRetry) {
			attempt++;
			return true;
		}
		
		return false;
	}
}
