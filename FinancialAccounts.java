package financeGUI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class FinancialAccounts implements IBaseRate {
	private String name;
	private String dateStart;
	protected int mortgage;
	protected double rate;
	protected String mortType;
	protected int numOfRepayments;
	protected double repayment;
	protected String billing;
	String riskProfile;
    DecimalFormat f = new DecimalFormat("##.00");

	public FinancialAccounts(String name, String dateStart, int mortgage, String mortType, int numOfRepayments, String billing ) {
		this.name = name;
		this.dateStart = dateStart;
		this.mortgage = mortgage;
		this.numOfRepayments = numOfRepayments;
		this.mortType = mortType;
		this.billing = billing;
		setRate();
	}
	public abstract void setRate();
	
	protected String[] calcDates(){
		//This method calculates each billing date
		
		String []dates = new String [numOfRepayments];
	    LocalDate date = LocalDate.parse(dateStart);

		if(billing.equalsIgnoreCase("semestral")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(6, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		else if(billing.equalsIgnoreCase("trimestral")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(3, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		else if(billing.equalsIgnoreCase("bimestral")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(2, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		else if(billing.equalsIgnoreCase("monthly")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(1, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		return dates;
	}
	protected void adjustRate() {
		//Adjust interest rate converting from annual rate to fractionate rate
		double fraction = 0;
		if(billing.equalsIgnoreCase("semestral")) {
			fraction = (double) 1/2;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
		else if(billing.equalsIgnoreCase("trimestral")) {
			fraction = (double) 1/4;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
		else if(billing.equalsIgnoreCase("bimestral")) {
			fraction = (double) 1/6;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
		else if(billing.equalsIgnoreCase("monthly")) {
			fraction = (double) 1/12;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
	}
	public void showInfo() {
		AlertBox.displayTable("", "NAME: \nMORTGAGE: \nACCOUNT OPENED IN: \nRATE: \nMORTGAGE WILL END IN: \nNUM OF REPAYMENTS: \nFIRST REPAYMENT: \nLAST REPAYMENT: \nTYPE OF MORTGAGE: ",
				getName()+"\n"
				+getMortgage()+"\n"
				+dateStart+"\n"
				+"0"+f.format(rate)+"%\n"
				+calcDates()[numOfRepayments-1]+"\n"
				+numOfRepayments+"\n"
				+f.format(calculateMortgage()[0][0])+"\n"
				+f.format(calculateMortgage()[0][numOfRepayments-1])+"\n"
				+mortType+", "+billing);
	}
	protected void showMort(double[][] quotes) {
		String mortView = "";
		for(int i = 0; i < quotes[0].length; i++) {
			mortView += "\nRepayment: "+f.format(quotes[0][i])+"\nCapital quote: "+f.format(quotes[1][i])+
					"\nInterest quote: "+f.format(quotes[2][i])+"\nDATE: "+calcDates()[i]+"\n";
			}
		AlertBox.displayScrollbar(getName(), mortView);
	}
	protected double[][] calculateMortgage() {
		//Method to calculate mortgages, french or italian
		double[][] quotes = new double[3][numOfRepayments];
		double capitalLeft = mortgage;
		double Interests = 0;
		double capitalQuote1 = 0;
		if (mortType.equalsIgnoreCase("Italian")){
			capitalQuote1 = mortgage/numOfRepayments;
			for(int n = 0; n < numOfRepayments; n++) {
				Interests = capitalLeft*(rate);
				repayment = capitalQuote1 + Interests;
				capitalLeft -= capitalQuote1;
				quotes[0][n] = repayment;
				quotes[1][n] = capitalQuote1;
				quotes[2][n] = Interests;
				}
		}
		else if(mortType.toUpperCase().equalsIgnoreCase("French")){
			repayment = mortgage/((1-(Math.pow(1+rate, -numOfRepayments)))/rate);
			for(int n = 0; n < numOfRepayments;n++) {
				Interests = capitalLeft * rate;
				capitalQuote1 = repayment - Interests;
				capitalLeft -= capitalQuote1;
				quotes[0][n] = repayment;
				quotes[1][n] = capitalQuote1;
				quotes[2][n] = Interests;
				}
		}
		return quotes;
	}
	public void savePdf(double[][] quotes) throws FileNotFoundException, DocumentException {
		//Method to save pdf for each mortgage selected. Uses Pdf Writer
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\LuLele\\Desktop\\mortgages\\"+getName()+" Mortgage.pdf"));	 
		document.open();

		PdfPTable table = new PdfPTable(4);
		table.addCell("DATE");
		table.addCell("REPAYMENT");
		table.addCell("CAPITAL QUOTE");
		table.addCell("INTEREST QUOTE");

		for(int i = 0; i < quotes[0].length; i++) {
			adjustRate();
		    table.addCell(calcDates()[i]);
			table.addCell(f.format(quotes[0][i]));
		    table.addCell(f.format(quotes[1][i]));
		    table.addCell(f.format(quotes[2][i]));
			}
		document.add(table);
		Phrase phrase = new Phrase();
		phrase.add("Name: "+ getName()+"\nMortgage: "+ mortgage+"$"); 
		phrase.add("\nAmmortization: "+ mortType);
		phrase.add("\n"+riskProfile + " profile");
		document.add(phrase);
		document.close();
	}
	protected int getMortgage() {
		return mortgage;
	}
	public String getName() {
		return name;
	}
	public String getDateStart() {
		return dateStart;
	}
}
