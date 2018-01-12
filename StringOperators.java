import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringOperators {


  	  public static String getTextBetweenStringsUsingRegexp(String input, String startMatch, String endMatch){
          Matcher matcher = Pattern.compile(startMatch+"(.*)"+endMatch).matcher(input);
          if (matcher.find()) {
              return matcher.group(1);
          }
          return "";
      }

    
    public static String getSandwichUsingRegexp(String input){
          Matcher matcher = Pattern.compile("chleb(.*)chleb").matcher(input);
          if (matcher.find()) {
              return matcher.group(1);
          }
          return "";
      }
    
    
        public static String getTextBetweenUsingIndexOf(String input, String starter, String ender) {
          int firstIndex = input.indexOf(starter);
          int lastIndex = input.lastIndexOf(ender);
          if (firstIndex < lastIndex) {
              return input.substring(firstIndex + starter.length(), lastIndex);
          }
          return "";
      }
      public static  String getTextBetweenSameStartEndUsingSplit(String input, String sameStartEndText){
         String[] ingredients =input.split(sameStartEndText,-2);
          if(ingredients.length>=3) {
              String[] ingredientsInside = Arrays.copyOfRange(ingredients, 1, ingredients.length - 1);
              return Arrays.stream(ingredientsInside).collect(Collectors.joining(sameStartEndText));
          }
          return "";
      }
  public static int findHowManyTimesSubstringInFullString(String full, String n){
// 	    String full = "weababccfsdfabc asdabcweabcfsdfabc asdabc";
// 	    String n 	= "abc";
	    int pos 	= full.indexOf(n);
	    if(pos<0){
	    	 System.out.println("Not found substring in full text");
	    	 return;
	     }
	    int counter	= 0;
	    int pointer = 0;
	    do{
	    	if(pointer < 0) break;
	    	counter++;
	    	pos = full.indexOf(n, pointer + n.length());
	    	pointer = pos;
	    }while(pointer < full.length());
	    
	    System.out.println("Found number of substrings: " + counter);
}
}
