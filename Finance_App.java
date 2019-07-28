package Finance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class Finance_app {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, DocumentException {
		boolean bool = true;
		Scanner np = new Scanner(System.in);
		System.out.println("WELCOME\n\n");
		List<FinancialAccounts> accounts = new LinkedList<FinancialAccounts>();
		DecimalFormat f = new DecimalFormat("##.00");
		String file = "C:\\Users\\Buzo 7\\Desktop\\MortgagesInfo.csv";

		List<String[]> newAccounts = utilities.CSV.read(file); 
		for (String[]accountholders : newAccounts) {
			String name = accountholders[0];
			String dateStart = accountholders[1];
			Integer mortgage = Integer.parseInt(accountholders[2]);
			String mortType = accountholders[3].toUpperCase();
			Integer numOfRepayments = Integer.parseInt(accountholders[4]);
			String typeOfMortgage = accountholders[5].toLowerCase();
			String riskProfile = accountholders[6];
			if(riskProfile.toLowerCase().equals("high risk")) {
				accounts.add(new HighRisk(name,dateStart,mortgage, mortType,numOfRepayments,typeOfMortgage));
			}
			else if (riskProfile.toLowerCase().equals("low risk")){
				accounts.add(new LowRisk(name,dateStart,mortgage, mortType,numOfRepayments,typeOfMortgage));				
			}
			else {
				System.out.println("Error reading risk profiling.");
			}
		}
		while(bool == true) {
		System.out.println("\n1 - CREATE NEW MORTGAGE\n2 - SHOW A MORTGAGE\n3 - SAVE AND QUIT");
		int choice = np.nextInt();
		np.nextLine();
		
		if(choice == 1) {
			System.out.println("Insert name: ");
			String name = np.nextLine();
			System.out.println("Insert start date (yyyy-mm-dd): ");
			String dateStart = np.nextLine();
			System.out.println("Insert mortgage value: ");
			int mortgage = np.nextInt();
			np.nextLine();
			System.out.println("Insert mortgage type (Italian, French): ");
			String mortType = np.nextLine().toUpperCase();
			System.out.println("Insert number of repayments: ");
			int numOfRepayments = np.nextInt();
			np.nextLine();
			System.out.println("Insert type of repayments(Semestral, Trimestral, Bimestral, Monthly): ");
			String typeOfMortgage = np.nextLine().toLowerCase();
			System.out.println("Insert risk profile (1 - High, 2 - Low): ");
			int choice2 = np.nextInt();
			if(choice2 == 1) {
				accounts.add(new HighRisk(name,dateStart,mortgage, mortType,numOfRepayments,typeOfMortgage));
				System.out.println("High Risk Mortgage created for: "+name);
			}
			else {
				accounts.add(new LowRisk(name,dateStart,mortgage, mortType,numOfRepayments,typeOfMortgage));
				System.out.println("Low Risk Mortgage created for: "+name);
			}			
		}
		else if(choice == 2) {
			for(int i = 0; i < accounts.size(); i++) {
				System.out.println("----------\nACCOUNT NUMBER: "+i);
				accounts.get(i).adjustRate();
				accounts.get(i).calcDates();
				accounts.get(i).calculateMortgage();
				accounts.get(i).showInfo();
			}
			System.out.println("SELECT ACCOUNT TO SHOW:");
			int choice3 = np.nextInt();
			accounts.get(choice3).showMort(accounts.get(choice3).calculateMortgage());
		}
		else if(choice == 3) {
			System.out.println("\nHAVE A NICE DAY");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for(int n = 0; n < accounts.size(); n++) {
				writer.print(accounts.get(n).getName()+",");
				writer.print(accounts.get(n).getDateStart()+",");
				writer.print(accounts.get(n).mortgage+",");
				writer.print(accounts.get(n).mortType+",");
				writer.print(accounts.get(n).numOfRepayments+",");
				writer.print(accounts.get(n).typeOfMortgage+",");
				writer.println(accounts.get(n).riskProfile);
				
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Buzo 7\\Desktop\\mortgages\\"+accounts.get(n).getName()+" Mortgage.pdf"));	 
				document.open();


				PdfPTable table = new PdfPTable(4);
				table.addCell("DATE");
				table.addCell("REPAYMENT");
				table.addCell("CAPITAL QUOTE");
				table.addCell("INTEREST QUOTE");

				for(int i = 0; i < accounts.get(n).calculateMortgage()[0].length; i++) {
				    table.addCell(accounts.get(n).calcDates()[i]);
					table.addCell(f.format(accounts.get(n).calculateMortgage()[0][i]));
				    table.addCell(f.format(accounts.get(n).calculateMortgage()[1][i]));
				    table.addCell(f.format(accounts.get(n).calculateMortgage()[2][i]));
				}
				document.add(table);
				Phrase phrase = new Phrase();
				phrase.add("Name: "+ accounts.get(n).getName()+"\nMortgage: "+ accounts.get(n).mortgage+"$"); 
				phrase.add("\nAmmortization: "+ accounts.get(n).mortType);
				phrase.add("\n"+accounts.get(n).riskProfile + " profile");
				document.add(phrase);
				document.close();
			}
			writer.close();
			np.close();
			bool = false;
			}
		}
	}
}

