package libs;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;




/*
 * createFileBookAndWriteHeader(ExcelFileLocation, SheetTitle, HederinFirstColumn, HeaderInSecondColumn);)
 * addHeader1(TitleOfTheHeader1)
 * addHeader2(TitleOfTheHeader2)
 * addHeader3(TitleOfTheHeader3) 
 * addRowPass(TestSubjectTitle);
 * addRowFail(TestSubjectTitle);
 * addRowFailComm(TestSubjectTitle, CommentTextWillGoTo3rdColumn);
 * 
 * 
 */

public class TestExcel {
	public static final String EXCEL_FILE_LOCATION = "C:\\tmp\\MyFirstExcel5.xls";
	private static final String rr = "C:\\tmp\\my.xls";
	public static Label label1;
	public static Label label2;
	public static int num1col = 0;
	public static int num2col = 1;
	public static int num3col = 2;
	

	
	
	public static void addHeader1(String fileLocation, String title) {
		appendIntoExcel(fileLocation, 1, title, "");
	}
	public static void addHeader2(String fileLocation, String title) {
		appendIntoExcel(fileLocation, 2, title, "");
	}
	public static void addHeader3(String fileLocation, String title) {
		appendIntoExcel(fileLocation, 3, title, "");
	}
	public static void addHeader1(String title) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 1, title, "");
	}
	public static void addHeader2(String title) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 2, title, "");
	}
	public static void addHeader3(String title) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 3, title, "");
	}
	
	public static void addRow(String fileLocation, String title, String result) {
		appendIntoExcel(fileLocation, 0, title, result);
	}
	public static void addRowPass(String fileLocation, String title) {
		appendIntoExcel(fileLocation, 0, title, "TRUE");
	}
	public static void addRowFail(String fileLocation, String title) {
		appendIntoExcel(fileLocation, 0, title, "FALSE");
	}
	public static void addRow(String title, String result) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 0, title, result);
	}
	public static void addRowPass(String title) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 0, title, "TRUE");
	}
	public static void addRowFail(String title) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 0, title, "FALSE");
	}
	public static void addRowFailComm(String title, String comments) {
		appendIntoExcel(EXCEL_FILE_LOCATION, 0, title, "FALSE", comments);
	}	
	
	
	
	
	public static void appendIntoExcel(String fileLocation, int HeaderRange /*[1, 2, 3, 0-normal text]*/, String firstText, String secondText) {
		appendIntoExcel(fileLocation, HeaderRange, firstText, secondText, "");
	}
	
	
	public static void appendIntoExcel(String fileLocation, int HeaderRange /*[1, 2, 3, 0-normal text]*/, String firstText, String secondText, String thirdColComments) {
    	WritableCellFormat firstFormat 	= null;
    	WritableCellFormat secFormat 	= null;
    	WritableCellFormat commentFormat = getCommentFormat();
		
		int i=getLastRowUsed(); 
		Workbook workbook1;
		WritableWorkbook copy = null;
		try {
			workbook1 = Workbook.getWorkbook(new File(fileLocation));
			copy = Workbook.createWorkbook(new File(fileLocation), workbook1);
		} catch ( IOException e) {
			
			e.printStackTrace();
		} catch (BiffException e) {
			
			e.printStackTrace();
		}
	    
		WritableSheet sheet2 = copy.getSheet(0); 
	    try {
	    	
	    	
	    	switch (HeaderRange) {
			case 1: 				firstFormat = getCellHeader1(); 				break;
			case 2: 				firstFormat = getCellHeader2(); 				break;
			case 3: 				firstFormat = getCellHeader3(); 				break;
			default: 				firstFormat = getCellFormatNone();				break;
			}
	    	
	    	if(secondText.contains("TRUE")) {
	    		secFormat = getCellFormatPass();
	    	}else if(secondText.contains("FALSE")){
	    		secFormat = getCellFormatFailed();
	    	}else {
	    		secFormat = getCellFormatNone();
	    	}
	    	
			sheet2.addCell(new Label(num1col, i, firstText, firstFormat));
			sheet2.addCell(new Label(num2col, i, secondText, secFormat));
			
			if(!thirdColComments.isEmpty()) {
				sheet2.addCell(new Label(num3col, i, thirdColComments, commentFormat));
			}
			
		} catch (WriteException e) {
			e.printStackTrace();
		}
	    try {
			copy.write();
			copy.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void  updateExcel(String fileLocation, int row, int col, String textToUpdate) {

//        String xlFilePath="test\\Resources\\Data\\dataExcel.xls";
//        String sheetName="loginSheet";

        WritableWorkbook workbookCopy = null;
        Workbook existingWorkbook;
		try {
			existingWorkbook 			= Workbook.getWorkbook(new File(fileLocation));
	        workbookCopy 				= Workbook.createWorkbook(new File(fileLocation), existingWorkbook);
	        WritableSheet sheetToEdit 	= workbookCopy.getSheet(0);
	        WritableCell cell 			= null;
	        Label label 				= new Label(row, col, textToUpdate);
	        cell 						= (WritableCell) label;
	        sheetToEdit.addCell(label);
	        workbookCopy.write();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				workbookCopy.close();
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
        
        
} 
	
	public static WritableCellFormat getCellFormatPass() {
		try {
			return getCellFormat(Colour.LIME, Colour.BLACK, Pattern.SOLID);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static WritableCellFormat getCellFormatFailed(){
		try {
			return getCellFormat(Colour.RED, Colour.WHITE, Pattern.SOLID);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static WritableCellFormat getCellFormatNone(){
		try {
			return getCellFormat(Colour.WHITE, Colour.BLACK, Pattern.SOLID);
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static WritableCellFormat getCommentFormat(){
		try {
			return getCellFormat(Colour.WHITE, Colour.GRAY_50, Pattern.SOLID);
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
	}	
	public static WritableCellFormat getCellHeaderSheet() {
		try {
			return getCellFormatHeader(13, Colour.DARK_BLUE, Colour.WHITE, Pattern.SOLID, true, true);
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
	}	public static WritableCellFormat getCellHeader1() {
		try {
			return getCellFormatHeader(12, Colour.AQUA, Colour.BLACK, Pattern.SOLID, false, false);
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static WritableCellFormat getCellHeader2() {
		try {
			return getCellFormatHeader(11, Colour.TURQOISE2, Colour.BLACK, Pattern.SOLID, false, false);
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static WritableCellFormat getCellHeader3() {
		try {
			return getCellFormatHeader(10, Colour.LIGHT_TURQUOISE2, Colour.BLACK, Pattern.SOLID, false, false);
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static WritableCellFormat getCellFormat(Colour bgColor, Colour ftColor, Pattern pattern) throws WriteException {
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, ftColor);
	    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	    cellFormat.setBackground(bgColor, pattern);
	    
	    if(bgColor.equals(Colour.RED) || bgColor.equals(Colour.LIME)) {
	    	cellFormat.setAlignment(Alignment.CENTRE);
	    }
	    return cellFormat;
	  }

	public static WritableCellFormat getCellFormatHeader(int size, Colour bgColor, Colour ftColor, Pattern pattern, boolean isBold, boolean isCenter) throws WriteException {
		WritableFont cellFont = new WritableFont(WritableFont.ARIAL, size, ((isBold) ? WritableFont.BOLD : WritableFont.NO_BOLD), false, UnderlineStyle.NO_UNDERLINE, ftColor);
	    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	    cellFormat.setBackground(bgColor, pattern);
	    if(isCenter) cellFormat.setAlignment(Alignment.CENTRE);
	    return cellFormat;
	  }	
	
	
	public static boolean createFileBookAndWriteHeader(String fileLocation, String titleOfTheSheet, String headerTitle1, String headerTitle2, String commentText) {
		WritableWorkbook myFirstWbook = null;
		WritableSheet excelSheet;
		// add something into the Excel sheet
        Label label1 = new Label(num1col, 0, headerTitle1);
        Label label2 = new Label(num2col, 0, headerTitle2);
        Label label3 = new Label(num3col, 0, commentText);
               try {
    		myFirstWbook 	= 	Workbook.createWorkbook(new File(fileLocation));
            // create an Excel sheet
            excelSheet = myFirstWbook.createSheet(titleOfTheSheet, 0);	
            excelSheet.setColumnView(num1col, 90);
            excelSheet.setColumnView(num2col, 20);
            excelSheet.setColumnView(num3col, 100);
            WritableCellFormat newFormat = getCellHeaderSheet();
			
			excelSheet.addCell(new Label(num1col, 0, headerTitle1, newFormat));
			excelSheet.addCell(new Label(num2col, 0, headerTitle2, newFormat));
			excelSheet.addCell(new Label(num3col, 0, commentText, newFormat));
			
			
			
			myFirstWbook.write();
			return true;
        } catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
		}
		return false;
        
	
	}
	
	public static int getLastRowUsed() {
		Workbook workbook 				= null;
		int i=0; 
		try {
			workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));
			Sheet sheet = workbook.getSheet(0);
	        i = sheet.getRows();

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         return i;
	}
	
	public static void main(String[] args) {
		
	updateExcel(rr, 2, 2, "Kloveringer");
		
	WritableCellFormat green 	= getCellFormatPass();
	WritableCellFormat red 		= getCellFormatFailed();

    //1. Create an Excel file
		createFileBookAndWriteHeader(EXCEL_FILE_LOCATION, "Tests-On-Firefox", "TEST-NAMES", "RESULTS","COMMENTS");
	
	addHeader1("Header rzedu 1 - TRAKTOWANIE POLIMERAMI");
	addHeader2("Header rzedu 2 - TRAKTOWANIE POLIMERAMI");
	addHeader3("Header rzedu 3 - TRAKTOWANIE POLIMERAMI");
	for(int i=1; i< 20; i++) {
		if((i%2)==0) {
			addRowPass("Test xyshio NUMBER "+i);
		}else {
			addRowFail("Test xyshio NUMBER "+i);
		}
	}
	addHeader1("Header rzedu 1 - WYTR¥CANIE WAPIENNO-POLIMEROWE");
	addHeader2("Header rzedu 2 - WYTR¥CANIE WAPIENNO-POLIMEROWE");
	addHeader3("Header rzedu 3 - WYTR¥CANIE WAPIENNO-POLIMEROWE");
	for(int i=1; i< 20; i++) {
		if((i%2)==0) {
			addRowPass("Test xyshio NUMBER "+i);
		}else {
			addRowFailComm("Test xyshio NUMBER "+i, "Bo zupa w "+i+ " by³a za s³ona. Bo zupa w xoxo by³a za s³ona. ");
		}
	}
	addHeader1("Header rzedu 1 - OCTANOWE ZAGÊSZCZANIE STRUMIENIA JONOWEGO");
	addHeader2("Header rzedu 2 - OCTANOWE ZAGÊSZCZANIE STRUMIENIA JONOWEGO");
	addHeader3("Header rzedu 3 - OCTANOWE ZAGÊSZCZANIE STRUMIENIA JONOWEGO");
	for(int i=1; i< 20; i++) {
		if((i%2)==0) {
			addRowPass("Test xyshio NUMBER "+i);
		}else {
			addRowFail("Test xyshio NUMBER "+i);
		}
	}
	System.out.println("done");
}
	

}
