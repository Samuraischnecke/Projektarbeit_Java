package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileReader {

	private static List<List<String>> script = new ArrayList<>();
	private Scanner file;
	private String line;

	public FileReader() {
		try {
			file = new Scanner(new FileInputStream("src/script/script.csv"));
			while (file.hasNextLine()) {
				line = file.nextLine();
				String[] values = line.split(";");
				script.add(Arrays.asList(values));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static List<List<String>> getScript() {
		return script;
	}

}
