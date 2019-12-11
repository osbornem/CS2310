/**
 * 
 */
package wordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Matthew Osborne
 *
 */
public class FileIO {
	
	/**
	 * 
	 * Extract the words from the text document and add them to an ArrayList
	 * 
	 * @return An ArrayList containing all English Words
	 */
	public static ArrayList<String> getWords() {

		ArrayList<String> words = new ArrayList<String>(70000);

		File file = new File("wordList.txt");

		BufferedReader br;
		try {
			// Read the file
			br = new BufferedReader(new FileReader(file));
			String s;
			try {
				// Add the words to the ArrayList
				while ((s = br.readLine()) != null) {
					words.add(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		return words;
	}
}
