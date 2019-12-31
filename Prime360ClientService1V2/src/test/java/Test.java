import java.util.Arrays;


public class Test {
public static void main(String[] args) {
//	System.out.println(Long.parseLong("3107191013"));
//	String str = "608198|107|WIP_HTS|Error|Record|2|Male|22-JUL-1993|PANAU7657G|DRV608198|568638880856|VRT608198|29-MAY-1993|TAN608198|||Error|Venkatesh|PSPT608198|||1234123412341234|CIN608198|DIN608198|REG608198|||||HOUSE NO.39A SUBHASH NAGAR|YAMUNA NAGAR|MEDIUM - BEHRAM SINGH HOSPITAL|JAGADHARI|135001|HARYANA|BUSSTAND|RTC BUSSTAND|beside pundicherry guest house|3 line|k.k nagar|chennai|600021|TAMILNADU|k.k nagar|044-24127899|044-4123547|9466553593|CMC building|044|Errorhero007@gmail.com||||";
//	String[] s = str.split("\\|");
//	System.out.println(s.length);
	
	String strHeader = "empid|firstname|lastname||||P|";
    
 //   String[] strFields = strHeader.split("\\|"); //.split("[|]").
    String[] strFields = strHeader.split("[|]");
    System.out.println( strFields.length );
    
    
    int count = strHeader.length()-strHeader.replaceAll("\\|","").length();
    System.out.println(count);
    
    
    
    int asciiValue = 0;
  //  String custUnqId="NSEL0001024";
    String custUnqId="AXIS_HL_9893735";
  //  String custUnqId="A";
    for (int i = 0; i < custUnqId.length(); i++) {
		asciiValue += custUnqId.charAt(i);
	}
    System.out.println("asciiValue ::"+asciiValue+" ::::::::: "+(asciiValue%7));
	
	
}
}
