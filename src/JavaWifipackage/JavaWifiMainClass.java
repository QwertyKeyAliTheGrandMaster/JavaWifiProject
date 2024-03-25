/**
 * 
 */
package JavaWifipackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 */
public class JavaWifiMainClass {

	 private static final String cmdCommand = "cmd /c";
	 private static List<String> wifiList = new ArrayList<>();
	 private static List<String> passwordList = new ArrayList<>();
	 private static Map<String, String> wifiPassord = new HashMap<>();
	 private static Scanner scan = new Scanner(System.in);
	 private static File document = new File("wifiPass.txt");
	 private List dialog;
	 String[] faces;
	/**
	 * 
	 */
	public JavaWifiMainClass() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getWifiPassword();
		choiceBox();
	}
	public static void getWifiPassword()
	{
        getWifiList();
        getPassWordList();
	}
	  public static void getWifiList(){
	        try {
	            Runtime runtime = Runtime.getRuntime();
	            Process process = runtime.exec(cmdCommand + "netsh wlan show profile ");
	            InputStream input = process.getInputStream();
	            BufferedReader read = new BufferedReader(new InputStreamReader(input));
	            for(int i = 1 ; i <= 9; i++){
	                read.readLine();
	                System.out.println(read.readLine());
	            }
	            String line ="";
	            while((line = read.readLine()) != null){
	                line =line.substring(line.indexOf(":")+1);
	                System.out.println(line + " line1");
	                wifiList.add(line);
	            }
	            input.close();
	        } catch (Exception e) {
	            System.out.println("Something went wrong " + e.getMessage());
	        }
	    }

	    public static void getPassWordList(){
	        Process process;
	        BufferedReader reader;
	        String line;
	        String password = "";
	        try {
	            Runtime runtime = Runtime.getRuntime();
	            for(String wifi: wifiList) {
	                process = runtime.exec(cmdCommand + "netsh wlan show profile " + wifi + " key=clear");
	                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                while ((line = reader.readLine()) != null) {
	                    if (line.contains("Security key"))
	                    	System.out.println(wifi + " line2");
	                        //System.out.println(process + " procss");
	                        break;
	                }
	                
	                
	                while ((line = reader.readLine()) != null)
	                {
	                    if (line.contains("Present")) {
	                        line = reader.readLine();
	                        password = line.substring(line.indexOf(":")+1);
	                        System.out.println(password + " passssword");
	                    } else password = "There is no password for this wifi";
	                    wifiPassord.put(wifi, password);
	                    break;
	                }
	                	
	                	
	                System.out.println(line);
	                if (line != null) {
	                    if (line.contains("Present")) {
	                        line = reader.readLine();
	                        password = line.substring(line.indexOf(":")+1);
	                        System.out.println(password);
	                    } else password = "There is no password for this wifi";
	                    wifiPassord.put(wifi, password);
	                }
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        }

	    }    private static void choiceBox(){
	        wifiList = new ArrayList<>(wifiPassord.keySet());
	       // ChoiceDialog<String> dialog = new ChoiceDialog<>("",wifiList);
	       // dialog = new JList( (ListModel) wifiList );
	        for ( int counter = 0; counter < wifiList.size(); counter++ )
	        	System.out.printf( " %s", counter, wifiList.get(counter ));
	        
	      //  Optional<String> result = dialog.showAndWait();
	      //  if (result.isPresent()){
	        //    String answer = wifiPassord.get(result.get());
	          //  this.resultBox(result.get(), answer);
	        }
}
