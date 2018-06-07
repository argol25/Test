package info.checkdata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import info.getdata.AAdata;
import info.model.Access;
import info.model.Account;
import info.model.CheckedAccesses;
import info.model.CheckedAccounts;
import info.model.Person;

public class checkAA {

	List<Account> accoRefData = new ArrayList<>();
	List<Access> acceRefData = new ArrayList<>();
	List<Account> accoBeforeCheck = new ArrayList<>();
	List<Access> acceBeforeCheck = new ArrayList<>();
	List<String> refFiles = new ArrayList<>();
	List<String> chFiles = new ArrayList<>();
	List<Person> person = new ArrayList<>();

	String pathToRefFile;

	String refFileName;
	String chFileName;

	CheckedAccounts cacco;
	CheckedAccesses cacce;
	List<CheckedAccounts> accoAfterCheck = new ArrayList<>();
	List<CheckedAccesses> acceAfterCheck = new ArrayList<>();

	AAdata aad;
	AAdata aad2;

	public static boolean rowCheck = true;

	public checkAA(String p1) {

		// checks if provided path ends properly
		if (p1.contains("\\")) {
			if (!p1.endsWith("\\")) {
				p1 = p1 + "\\";
			}
		} else if (!p1.endsWith("/")) {
			p1 = p1 + "/";
		}

		this.pathToRefFile = p1;

		// sets ref file and files to check
		getFiles(pathToRefFile);

		// sets up reference file path
		this.refFileName = pathToRefFile + refFiles.get(0);

		// reads reference data from file
		this.accoRefData = new AAdata(refFileName).readAccountDataRef();
		this.acceRefData = new AAdata(refFileName).readAccessDataRef();
	}

	public void mainCheck() {

		String name = "";

		getNamesToCheck();

		for (Person pe : person) {
			for (String ch : chFiles) {
				if (ch.toLowerCase().contains(pe.getName().toLowerCase())
						&& ch.toLowerCase().contains(pe.getSurname().toLowerCase())) {
					aad2 = new AAdata(pathToRefFile + ch);

					name = pe.getName() + " " + pe.getSurname();

					// set account and access data to check
					accoBeforeCheck.addAll(aad2.readUserAccountData());
					acceBeforeCheck.addAll(aad2.readUserAccessData());
				}
			}

			// provide name of a person as an argument
			if (!name.isEmpty()) {
				accountsCheck(name);
				accessesCheck(name);

			}

			// clears check data for next iteration (next file)
			name = "";
			accoBeforeCheck.clear();
			acceBeforeCheck.clear();
		}

		// save received data
		saveData();

	}

	public void accountsCheck(String s) {

		boolean accoPresent;
		boolean checked;

		for (Account a : accoRefData) {
			checked = false;
			accoPresent = false;

			for (Account ac : accoBeforeCheck) {
				if (a.getServiceName().equals(ac.getServiceName())) {
					if (ac.getAccountStatus().equalsIgnoreCase("active")) {
						accoPresent = true;
						checked = true;
						break;
					} else {
						cacco = new CheckedAccounts();
						cacco.setAccountName(a.getServiceName().toString());
						cacco.setName(s);
						cacco.setStatusComment("Not active.");
						checked = true;
						break;
					}

				}
			}

			if (!checked) {
				cacco = new CheckedAccounts();
				cacco.setAccountName(a.getServiceName().toString());
				cacco.setName(s);
				cacco.setStatusComment("No Account.");
			}

			if (!accoPresent) {
				accoAfterCheck.add(cacco);
			}
		}

	}

	public void accessesCheck(String s) {

		boolean accePresent;
		boolean checked;

		for (Access a : acceRefData) {
			checked = false;
			accePresent = false;

			for (Access ac : acceBeforeCheck) {
				if (a.getAccessName().equals(ac.getAccessName())) {
					accePresent = true;
					checked = true;
					break;
				}
			}

			if (!checked) {
				cacce = new CheckedAccesses();
				cacce.setAccessName(a.getAccessName().toString());
				cacce.setName(s);
				cacce.setStatusComment("No access.");
			}

			if (!accePresent) {
				acceAfterCheck.add(cacce);
			}
		}

	}

	public void saveData() {
		AAdata aad3 = new AAdata(pathToRefFile + "!Summary.xls");
		aad3.writeData(accoAfterCheck, acceAfterCheck);
		accoAfterCheck.clear();
		acceAfterCheck.clear();
	}

	// gets list of files to check and ref file from specific location
	public void getFiles(String path) {
		File dir = new File(path);

		// import all files from specified directory
		File[] fiList = dir.listFiles();
		for (File file : fiList) {
			if (file.toString().toLowerCase().endsWith("xls")) {
				// add file to 'To check' list
				if (file.toString().contains("ISIM-EHN")) {
					chFiles.add(file.getName());
				}
				// add file to 'Ref' list
				if (file.toString().contains("Accesses")) {
					refFiles.add(file.getName());
				}
			}
		}
	}

	public void getNamesToCheck() {
		String nameToCheck;
		int start = 15;
		boolean flag = false;

		for (String ch : chFiles) {

			int stop = ch.length() - 18;

			if (ch.contains("FP_*A")) {
				nameToCheck = ch.substring(27, stop);
			} else
				nameToCheck = ch.substring(start, stop);

			int index = 0;
			String name = null;
			String surname = null;
			flag = false;

			if (nameToCheck.contains("@pl.ibm.com")) {
				nameToCheck = nameToCheck.substring(0, nameToCheck.length() - 11);
			}

			if (nameToCheck.endsWith("_")) {
				nameToCheck = nameToCheck.substring(0, (nameToCheck.length() - 1));
			}

			if (nameToCheck.contains(".")) {
				index = nameToCheck.lastIndexOf(".");
				name = nameToCheck.substring(0, index);
				surname = nameToCheck.substring(index + 1);
			} else if (nameToCheck.contains("_")) {
				index = nameToCheck.lastIndexOf("_");
				if (index < 1) {
					name = nameToCheck;
					surname = "";
				} else {
					name = nameToCheck.substring(0, index);
					surname = nameToCheck.substring(index + 1);
				}

			} else {
				name = nameToCheck;
				surname = "";
			}

			// check if such person already exists in list
			for (Person pe : person) {
				if (pe.getSurname().equalsIgnoreCase(surname)) {
					flag = true;
					break;
				}
			}

			// if person don't exist in person list, add him/her
			if (flag == false) {
				Person p = new Person();
				p.setName(name);
				p.setSurname(surname);
				person.add(p);
			}

		}

	}
}
