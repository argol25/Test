package logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckLogs {
	public ArrayList<String> filesList;
	public static final String logsPath = "/home/arek/Dokumenty/Skrypty/";
	public static final String resultsPath = "/home/arek/Dokumenty/Skrypty/JavaLogsApp/chLogs.txt";
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static String currentDate = sdf.format(new Date());
	public static String yesterdaysDate = sdf.format(yesterdaysDate());

	public static String[] getFileList() {
		File dir = new File(logsPath);
		String[] fileList = dir.list();

		return fileList;
	}

	public static void readFiles(String fileName) throws IOException {
		String line;
		String searchTxt1 = "line :";
		String searchTxt2 = "tbd";
		String searchTxt3 = "tbd";
		String searchTxt4 = "##ERROR";

		try (BufferedReader br = new BufferedReader(new FileReader(logsPath + fileName))) {
			writeLogs("\n**************************\n" + "File name: " + fileName + "\n");
			while ((line = br.readLine()) != null) {
				if (line.contains(currentDate) || line.contains(yesterdaysDate)) {
					if (line.contains(searchTxt1)) {
						writeLogs("\n--------------------------\n");
						writeLogs(line);
					}
					if (searchPattern(line, searchTxt2)) {
						writeLogs(line);
					}
					if (searchPattern(line, searchTxt3)) {
						writeLogs(line);
					}
					if (line.contains(searchTxt4)) {
						writeLogs(line);
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		writeLogs("\n**************************\n");
	}

	private static boolean searchPattern(String line, String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(line);

		if (matcher.find())
			return true;
		else
			return false;
	}

	public static void writeLogs(String addLog) throws IOException {
		FileWriter fw = new FileWriter(resultsPath, true);
		System.out.println("Dodaję logi do pliku. Tekst: " + addLog);

		fw.append(addLog);
		fw.close();
	}

	public static void clearFile() throws IOException {
		FileWriter fw = new FileWriter(resultsPath, false);
		fw.flush();
		fw.close();
	}

	public static Date yesterdaysDate() {
		final Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DATE, -1);
		return cl.getTime();
	}

	public static void main(String args[]) {
		String[] filesList = getFileList();

		try {
			clearFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < filesList.length; i++) {

			if (filesList[i].contains(".txt")) {
				System.out.println("Plik nr:" + i + " " + filesList[i]);
				try {
					System.out.println("Przekazuję następujaca ścieżkę: " + logsPath + " oraz plik " + filesList[i]);
					readFiles(filesList[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
