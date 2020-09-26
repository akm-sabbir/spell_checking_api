package bangla.grammarchecker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ShaduCreationEngine {

	public static void main(String[] args) {
		//List<String> words = CreateShadhuVerbForm1.createVerb("খুলি"); 
		List<String> words = CreateShadhuVerbForm2.createVerb("থাকি"); 
		try {
			FileWriter myWriter = new FileWriter("থাকি.txt");
			for(String s: words) {
				myWriter.write(s + "\n");
			}
			myWriter.close();
			System.out.println("done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
