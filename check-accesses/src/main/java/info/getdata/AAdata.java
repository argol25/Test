package info.getdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import info.model.Access;
import info.model.Account;
import info.model.CheckedAccesses;
import info.model.CheckedAccounts;

//class is responsible for importing all accesses and accounts data from update xls file
public class AAdata {

	Account acco;
	Access acce;
	public List<Account> accoList = new ArrayList<>();
	public List<Access> acceList = new ArrayList<>();
	String fileName;
	String s;

	public AAdata(String fileName) {
		this.fileName = fileName;

	}

	public List<Account> readAccountDataRef() {
		// open file with acccounts for reference
		try (FileInputStream fis = new FileInputStream(new File(fileName))) {
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("Account");
			Iterator<Row> iterator = sheet.iterator();
			int count = 0;

			// gets Account data
			while (iterator.hasNext()) {

				acco = new Account();
				Row currentRow = iterator.next();

				// gets ServiceName
				acco.setServiceName(currentRow.getCell(0).toString().toUpperCase());

				// gets CustomerName
				acco.setAccountStatus(currentRow.getCell(1).toString().toUpperCase());

				// add access element to the table
				if (count > 0)
					accoList.add(acco);

				// helps to avoid import of headers
				count++;

			}

		} catch (IOException exc) {
			System.out.println(exc);
		}
		
		return accoList;
	}

	// returns Accesses object list
	public List<Access> readAccessDataRef() {
		// open file with acccounts and accesses for reference
		try (FileInputStream fis = new FileInputStream(new File(fileName))) {
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("Access");
			Iterator<Row> iterator = sheet.iterator();
			int count = 0;

			// gets Accesses data
			while (iterator.hasNext()) {

				acce = new Access();
				Row currentRow = iterator.next();

				// gets ServiceName
				acce.setServiceName(currentRow.getCell(0).toString().toUpperCase());

				// gets AccessName
				acce.setAccessName(currentRow.getCell(1).toString().toUpperCase());

				// add access element to the table
				if (count > 0)
					acceList.add(acce);

				// helps to avoid import of headers
				count++;
			}

		} catch (IOException exc) {
			System.out.println(exc);
		} catch (Exception e) {
			System.out.println(e);
		}

		return acceList;
	}

	// returns Account object list
	public List<Account> readUserAccountData() {
		// open file with acccounts and accesses for reference
		try (FileInputStream fis = new FileInputStream(new File(fileName))) {
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			int count = 0;

			// gets Account data
			while (iterator.hasNext()) {

				acco = new Account();
				Row currentRow = iterator.next();

				// gets ServiceName
				acco.setServiceName(currentRow.getCell(0).toString().toUpperCase());

				// gets AccountStatus
				if (!currentRow.getCell(7).toString().equals(""))
					acco.setAccountStatus(currentRow.getCell(7).toString().toUpperCase());

				// add access element to the table
				if (count > 0)
					accoList.add(acco);

				// helps to avoid import of headers
				count++;
			}

		} catch (IOException exc) {

		}

		return accoList;
	}

	// returns Accesses object list
	public List<Access> readUserAccessData() {
		// open file with acccounts and accesses for reference
		try (FileInputStream fis = new FileInputStream(new File(fileName))) {
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = sheet.iterator();
			int count = 0;

			// gets Accesses data
			while (iterator.hasNext()) {
				acce = new Access();
				Row currentRow = iterator.next();

				// gets ServiceName
				acce.setServiceName(currentRow.getCell(0).toString().toUpperCase());

				// gets AccessName
				acce.setAccessName(currentRow.getCell(3).toString().toUpperCase());

				// add access element to the table
				if (count > 0)
					acceList.add(acce);

				// helps to avoid import of headers
				count++;
			}

		} catch (IOException exc) {
			System.out.println(exc);
		} finally {

		}
		return acceList;
	}

	public void writeData(List<CheckedAccounts> cacco, List<CheckedAccesses> cacce) {

		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Account");
		int rowNum = 0;

		for (CheckedAccounts ca : cacco) {

			Row row = sheet.createRow(rowNum);
			CheckedAccounts cas = cacco.get(rowNum);

			for (int colNum = 0; colNum < 3; colNum++) {
				if (colNum == 0) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(cas.getName());
				}
				if (colNum == 1) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(cas.getAccountName());
				}
				if (colNum == 2) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(cas.getStatusComment());
				}
			}
			rowNum++;

		}

		Sheet st = workbook.createSheet("Access");
		rowNum = 0;

		for (CheckedAccesses cs : cacce) {
			Row row = st.createRow(rowNum);

			CheckedAccesses cac = cacce.get(rowNum);

			for (int colNum = 0; colNum < 3; colNum++) {
				if (colNum == 0) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(cac.getName());
				}
				if (colNum == 1) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(cac.getAccessName());
				}
				if (colNum == 2) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(cac.getStatusComment());
				}
			}
			rowNum++;
		}

		try (FileOutputStream fos = new FileOutputStream(new File(fileName))) {
			workbook.write(fos);
		} catch (IOException exc) {
			System.out.println(exc);
		}
	}

}
