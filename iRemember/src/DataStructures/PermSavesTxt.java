package DataStructures;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PermSavesTxt {
	
	String notizText;
	long timestamp;
	
	FileWriter fw;
	BufferedWriter bw;
	
	public PermSavesTxt(String notizText, long timestamp) {
		this.notizText = notizText;
		this.timestamp = timestamp;
		
		try {
			fw = new FileWriter("ausgabe.txt");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
     

}
