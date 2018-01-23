package libs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import libs.*;

/**
* The helper set of static methods to call on webdriver objects.
*
* @author  Krzysztof Gumulak
* @version 1.0
* @since   2015-03-31 
*/
public class ax {
	
	
	public static void wait(int seconds){
		int s;
		if(seconds==0){
			s = 500;
		}else{
			s = 1000 * seconds;	
		}
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 	} 
	
	public static void sleep(int seconds){
			sleeper(seconds); 	}
  	public static void sleeper(int ValueToSleep){
//		System.out.print("[...] Counting down [from "+ValueToSleep+"].");
//		System.out.println("");
		String pro = ">-";
		for(int i=1; i<ValueToSleep; i++){
			ax.wait(1);
			System.out.print(pro);
            if ( ( i % 40 ) == 0 )
            {
            	System.out.print(" "+i);
            	System.out.println(pro);
            }
		}
//		System.out.println("");
	} 
    public static boolean returnWhenFalse(String info){
    	System.out.println("RETURN FALSE: Problem with: " + info); return false;
    } 	
    public static boolean returnWhenFalse(WebDriver driver, String info){
    	TakePhoto.takePhoto(driver, info);
    	System.out.println("RETURN FALSE: Problem with: " + info); return false;
    } 	    public static WebElement findMe(WebDriver driver, String xp) {
    	return _findMe(driver, By.xpath(xp));
    }
	 public static WebElement _findMe(WebDriver driver, By by) {
		  List<WebElement> w = driver.findElements(by);
		  if(w.size() < 1) {
			  System.out.println("not found element ["+by.toString()+"]");
			  return null;
		  }else if(w.size()>1) {
			  System.out.println("Warning: there are more than 1 element found for ["+by.toString()+"]");
		  }
		  if(w.get(0)==null){
			  System.out.println("found element ["+by.toString()+"], but is null");
			  return null;
		  }else if(w.get(0)!=null && (
				  !driver.findElement(by).isDisplayed()
				  ||
				  !driver.findElement(by).isEnabled()
				  )){
			  System.out.println("element ["+by.toString()+"], is not Displayed or not Enabled!");
			  return null;
		  }else{
			  return w.get(0);
		  }
	 }
	 public static boolean _clickMe(WebDriver driver, String xp){
		 return _clickMe(driver, By.xpath(xp));
	 }
	 public static boolean _clickMe(WebDriver driver, By by){
		 WebElement w = ax._findMe(driver, by);
		 if(w!=null) {
			 	try {
				 	w.click();	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with clicking on ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2click on ["+by.toString()+"]");
	}
	 
	 public static boolean _clickMeWaitForElement(WebDriver driver, By elementToClick, By ElementToWaitFor) {
		 if(_clickMe(driver, elementToClick)) {
			 return wait_elm(driver, ElementToWaitFor);
		 }
		 ax.fail(driver, "Problem with waiting for element " + ElementToWaitFor.toString());
		 return false;
	 }
	 
		public static boolean wait_elm(WebDriver driver, By by){
			return wait4element(driver, by, 10)!=null;
		}
		public static WebElement wait4element(WebDriver driver, By by, int limitWait){
	    	int i=0;
	    	while(i<limitWait){
	    		try{
	    			if(driver.findElements(by).size()>0){ // element exists
//	    				WebElement el = driver.findElement(by); 
	    				WebDriverWait wait = new WebDriverWait(driver, 10);
	    				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	    				if(element.isDisplayed()){
	    					return element;
	    				}else{
	    					System.out.print(" "+i);
	    				}
	    			}else{
	    				System.out.print(" "+i);
	    			}
	    		}catch(Exception e){
	    			if(e.getMessage().contains("element is not attached")){ 
						System.out.print("still element is stale.."); System.out.print(" > " + i );
	    			}
	    			System.out.print(" "+i);
	    		}
	    		ax.wait(1);
	    		i++;
	    	}
	    	return null;
	    }
	    public static boolean wait_for_ayax_1(WebDriver driver){ return WaitForAjaxToComplete_Quick(driver);  } // SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
	    public static boolean wait_for_ayax_2(WebDriver driver){ return WaitForAjaxToComplete_Slower(driver);  }// SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
		public static boolean WaitForAjaxToComplete_Quick(WebDriver driver) // SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
	    {int i=0;
	        while (true)
	        {
	            if ((Boolean) ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0")){
	                break;
	            }
	            if(i > Config.TIME_SYNCH_30) return ax.returnWhenFalse(driver, "Waiting for completing ayax-load");
	            ax.sleep(1);
	            i++;
	        }
	        return true;
	    }
		public static boolean WaitForAjaxToComplete_Slower(WebDriver driver) { // SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
			Boolean we = null;
			Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				    .withTimeout(30, TimeUnit.SECONDS)
				    .pollingEvery(5, TimeUnit.SECONDS)
				    .ignoring( StaleElementReferenceException.class ) ;
			try {
			ExpectedCondition<Boolean> isLoadingFalse = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
				Object obj = ((JavascriptExecutor) driver).executeScript("return !window.ajaxActive");
				Object jQueryActive = ((JavascriptExecutor) driver).executeScript("return jQuery.active;");
		
					if (obj != null && obj.toString().equals("true") && jQueryActive.toString().equals("0")){
					  return Boolean.valueOf(true);
					}else{
					  return false;
					}
				}
			};
				we = wait.until(isLoadingFalse);
			} catch (Exception e) {
				return false;
			}
			return we;
		} 		
	     public static boolean waitForPageLoaded(WebDriver driver) {
	    	  final long startTime = System.currentTimeMillis();
//	    	  if(ax.isAlertPresent(driver)){ax.closeAlertAndGetItsText(driver);}
	    	     ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
	    	        public Boolean apply(WebDriver driver) {
	    	          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	    	        }
	    	      };
	    	     Wait<WebDriver> wait = new WebDriverWait(driver,30);
	    	      try {
	    	              wait.until(expectation);
	    	      } catch(Throwable error) {
	    	              //assertFalse("Timeout waiting for Page Load Request to complete.",true);
	    	    	  ax.reportInfo("!!! Page was not loaded !!!");
	    	    	  
	    	    	  if(Config.EUCR_BROWSER.equals("HTMLUNIT")){return true;}
	    	    	  if(!Config.EUCR_BROWSER.equals("MSIE")){
	    	    		  TakePhoto.takePhoto(driver, "Page "+ driver.getTitle() +" could not be loaded -bool- in a timely fashion range");  
	    	    	  }
	    	      }
	    	      long endTime   = System.currentTimeMillis();
	    	      long totalTime = endTime - startTime;
	    	      ax.reportInfo("CURRENT PAGE: ["+ driver.getTitle() +"].......[wait time takes: "+totalTime+"]");
				return true;
	    	 }  		
		
		
	 public static boolean _clearMe(WebDriver driver, String xp){
		 return _clearMe(driver, By.xpath(xp));
	 }
	public static boolean _clearMe(WebDriver driver, By by){
		 WebElement w = ax._findMe(driver, by);
		 if(w!=null) {
			 	try {
				 	w.clear();	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with clearing in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2clear in ["+by.toString()+"]");
	}
	public static boolean _typeMe(WebDriver driver, String xp, String text){
		return _typeMe(driver, By.xpath(xp), text);
	}
	public static boolean _typeMe(WebDriver driver, By by, String text){
		 WebElement w = ax._findMe(driver, by);
		 if(w!=null) {
			 	try {
				 	w.sendKeys(text);	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with typing ["+text+"] in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2type ["+text+"] in ["+by.toString()+"]");
	}
	public static boolean _typeMeWithClear(WebDriver driver, String xp, String text){
		return _typeMeWithClear(driver, By.xpath(xp), text);
	}
	public static boolean _typeMeWithClear(WebDriver driver, By by, String text){
		 WebElement w = ax._findMe(driver, by);
		 if(w!=null) {
			 	try {
			 		w.clear();
				 	w.sendKeys(text);	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with typing ["+text+"] in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2type ["+text+"] in ["+by.toString()+"]");
	}
	public static boolean _selectMe(WebDriver driver, String xp, String type /*text,value,index*/, String value){
		return _selectMe(driver, By.xpath(xp), type, value);
	}
	public static boolean _selectMe(WebDriver driver, By by, String type /*text,value,index*/, String value){
		 WebElement w = ax._findMe(driver, by);
		 if(w!=null) {
			 	try {
					switch (type) {
					case "index": 						
						new Select(driver.findElement(by)).selectByIndex(Integer.parseInt(value)); 
						return true;
					case "text": 						
						new Select(driver.findElement(by)).selectByVisibleText(value); 
						return true;
					case "value":						
						new Select(driver.findElement(by)).selectByValue(value); 
						return true;
					}
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with selecting ["+value+"] by ["+type+"] in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2select ["+value+"] by ["+type+"] in ["+by.toString()+"]");
	}
	
	 
	 
	public static void print_ars(ArrayList<String> ars){
		for(String a: ars){
			System.out.println("- "+a.trim());
		}
	}
	public static String print_ars_one_line(ArrayList<String> ars){
		String out = "";
		for(String a: ars){
			out += a.trim()+". ";
		}
		return out;
	}
	public static String print_ars_as_parenthesed_params(ArrayList<String> ars){
		String f = "";
		for(String a: ars){
			f += "{"+a.trim()+"} ";
		}
		return f;
	}	
	public static void print_ars_double(ArrayList<ArrayList<String>> ars_double){
		for(ArrayList<String> ars_line: ars_double){
			for(String ars: ars_line){
			System.out.print(""+ars.trim()+ "\t");
			}
			System.out.println("");
		}
	}
	
	public static void reportInfo(String info) {
		reportInfo(info, 0);
	}
	public static void reportInfo(String info, int level) {
		if(Config.TEST_MODE.equals("NORMAL")) return;
		if (level==0){
			ax.reportInfo("<div style='background-color:#DEE8FC;color:black;font-style:italic;font-size:110%'>"+info+"</div>"); // Reporter.log
		}else if (level==1){
			ax.reportInfo("<div style='background-color:aqua;color:black;font-style:italic;font-size:110%'>"+info+"</div>");
		}else if (level==2){
			ax.reportInfo("<div style='background-color:tan;color:black;font-style:italic;font-size:110%'>"+info+"</div>");
		}else{
			ax.reportInfo("<div style='background-color:#DEE8FC;color:silver;font-style:normal;font-size:100%'>"+info+"</div>");
		}
	}
	
	public static void excelInit() {
		TestExcel.createFileBookAndWriteHeader(TestExcel.EXCEL_FILE_LOCATION, "oner", "title", "result", "comment");
	}
	public static void h1(String txt) {
		TestExcel.addHeader1(txt);
	}
	public static void h2(String txt) {
		TestExcel.addHeader2(txt);
	}
	public static void h3(String txt) {
		TestExcel.addHeader3(txt);
	}
	public static void result(boolean resultTruFale, String txt) {
		if(resultTruFale) {
			TestExcel.addRowPass(txt);	
		}
		TestExcel.addRowFail(txt);
	}

	
	public static void pass(String txt) {
		TestExcel.addRowPass(txt);
	}
	public static void fail(String txt) {
		TestExcel.addRowFail(txt);
	}
	public static void fail(WebDriver driver, String info) {
		TakePhoto.takePhoto(driver, info);
		ax.fail(info);
	}
	public static void info(WebDriver driver, String info) {
		TakePhoto.takePhoto(driver, info);
		ax.reportInfo(info);
	}
	public static void fail_comment(String txt, String comment) {
		TestExcel.addRowFailComm(txt, comment);
	}
	public static void note(String txt) {
		TestExcel.addRow(txt, "");
	}	

}
