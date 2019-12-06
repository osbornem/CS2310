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

	static ArrayList<String> words;

	public static ArrayList<String> getWords() {

		words = new ArrayList<String>(70000);

		File file = new File("wordList.txt");

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			try {
				while ((st = br.readLine()) != null) {
					words.add(st);
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
