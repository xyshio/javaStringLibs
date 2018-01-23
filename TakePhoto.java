package libs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmentable;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Reporter;


@Augmentable
public class TakePhoto {

	private static final String PASSED = "PASSED";
	private static final String VERIFY_PASSED = "VERIFY_PASSED";

	private static final String PASSED_TEST_UTILS = "!!! PASSED !!!";
	private static final String FAILED_TEST_UTILS = "!!! FAILED !!!";

	private static String SCREENSHOT_FILE_DIRECTORY = Config.SCREENSHOT_FILE_DIRECTORY;

	private static int screenShotCounter = 1;
	private static Date now;

	static {
		now = new Date();
	}	
	
	
	public static String takePhoto(WebDriver driver, String extraInfo) {
		//System.out.println("no photo");
		return takePhoto(driver, extraInfo, "");
	}
	
	public static String takePhoto(WebDriver driver, String extraInfo, String inLine) {
		if (Config.TYPE_OF_RUN.contains("smoke")){
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_HH_mm");
			String formattedDate = formatter.format(now);
			File scrFile = null;
			if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("GRID") ){
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("REGULAR") ){
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("MSIE") ){
				scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("CHROME") ){
				
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
//				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("HTMLUNIT")){
				return "";
			}
			else{
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			}
			
			File destFile = new File(SCREENSHOT_FILE_DIRECTORY + "\\" + formattedDate + "\\screenshot_" + screenShotCounter++ + ".png");
			Reporter.log("Screenshot saved at " + destFile);
			Reporter.log("<a href='" + destFile + "' target='_blank' style='background-color:yellow;color:red' /><img src='" + destFile + "' height='100' width='100' style='border:2px black solid;padding:5px;'> View Screenshot</a>");
			try {
				ax.reportInfo("<BR>PHOTO-OF: ["+extraInfo+"]: ");
				ax.reportInfo(destFile.getAbsolutePath());
				ax.reportInfo("<HR>");
				FileUtils.copyFile(scrFile, destFile);
				
				if(inLine.isEmpty()){
					System.out.println("");
					System.out.println(""+destFile.toString().trim()+"");
				}else{
					System.out.print(""+destFile.toString().trim()+"");
				}
				System.out.println("");
				return destFile.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
	}		
	
	
	public static void takePhoto(WebDriver driver, String extraInfo, File destination) {
		if (Config.TYPE_OF_RUN.contains("smoke")){
			return;
		}
		int widthWindow	= driver.manage().window().getSize().getWidth();
		int heightWindow	= driver.manage().window().getSize().getHeight();
		driver.manage().window().maximize();
		SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_HH_mm");
			String formattedDate = formatter.format(now);
			File scrFile = null;
			if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("GRID") ){
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("REGULAR") ){
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("MSIE") ){
				scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("CHROME") ){
				
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
//				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("HTMLUNIT")){
				driver.manage().window().setSize(new Dimension(widthWindow,heightWindow));
				return;
			}
			else{
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			}
			
			File destFile = destination; //new File(SCREENSHOT_FILE_DIRECTORY + "\\" + formattedDate + "\\screenshot_" + screenShotCounter++ + ".png");
			Reporter.log("Screenshot saved at " + destFile);
			Reporter.log("<a href='" + destFile + "' target='_blank' style='background-color:yellow;color:red' /><img src='" + destFile + "' height='100' width='100' style='border:2px black solid;padding:5px;'> View Screenshot</a>");
			try {
				ax.reportInfo("<BR>PHOTO-OF: ["+extraInfo+"]: ");
				ax.reportInfo(destFile.getAbsolutePath());
				ax.reportInfo("<HR>");
				FileUtils.copyFile(scrFile, destFile);
				System.out.println("");
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver.manage().window().setSize(new Dimension(widthWindow,heightWindow));
			
	}
	
	
	public static void takePhoto(WebDriver driver, boolean allowed, String extraInfo) {
		if (Config.TYPE_OF_RUN.contains("smoke")){
			return;
		}

		if (allowed) {

			SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_HH_mm");
			File scrFile = null;
			String formattedDate = formatter.format(now);
//			WebDriver augmentedDriver = new Augmenter().augment(driver);
//			File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("GRID") ){
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("REGULAR") ){
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}else if(Config.EUCR_BROWSER.contains("CHROME") ){
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
//				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}
			
			File destFile = new File(SCREENSHOT_FILE_DIRECTORY + "\\" + formattedDate + "\\screenshot_" + screenShotCounter++ + ".png");

			Reporter.log("Screenshot saved at " + destFile);
			Reporter.log("<a href='" + destFile + "' height='100' width='100' target='_blank' style='background-color:yellow;color:red' /><img src='" + destFile + "' height='100' width='100' style='border:2px black solid;padding:5px;'> View Screenshot</a>");

			try {
				ax.reportInfo("<BR>Taking photo of exceptional situation ["+extraInfo+"]: ");
				ax.reportInfo(destFile.getAbsolutePath());
				ax.reportInfo("<HR>");

				FileUtils.copyFile(scrFile, destFile);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public static void takePhotoOnly(WebDriver driver) {
		if(Config.TYPE_OF_RUN.contains("smoke"))	{return;}
		if(Config.EUCR_BROWSER.equals("MSIE"))	{return;}
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM_dd_HH_mm");
		String formattedDate = formatter.format(now);
		File scrFile = null;
		
		if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("GRID") ){
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		}else if(Config.EUCR_BROWSER.contains("FIREFOX") &&  Config.REGULAR_GRID.contains("REGULAR") ){
			scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		}else if(Config.EUCR_BROWSER.contains("CHROME") ){
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
//			scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		}		
		
//		WebDriver augmentedDriver = new Augmenter().augment(driver);
//		File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		
		File destFile = new File(SCREENSHOT_FILE_DIRECTORY + "\\" + formattedDate + "\\screenshot_" + screenShotCounter++ + ".png");
		Reporter.log("Screenshot saved at " + destFile);
		Reporter.log("<a href='" + destFile + "' target='_blank' style='background-color:yellow;color:red' /><img src='" + destFile + "' height='100' width='100' style='border:2px black solid;padding:5px;'> View Screenshot</a>");
		try {
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
}
