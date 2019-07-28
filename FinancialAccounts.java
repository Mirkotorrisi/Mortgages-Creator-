package Finance;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class FinancialAccounts implements IBaseRate {
	private String name;
	private String dateStart;
	protected int mortgage;
	protected double rate;
	protected String mortType;
	protected int numOfRepayments;
	protected double repayment;
	protected String typeOfMortgage;
	String riskProfile;
    DecimalFormat f = new DecimalFormat("##.00");

	public FinancialAccounts(String name, String dateStart, int mortgage, String mortType, int numOfRepayments, String typeOfMortgage ) {
		this.name = name;
		this.dateStart = dateStart;
		this.mortgage = mortgage;
		this.numOfRepayments = numOfRepayments;
		this.mortType = mortType;
		this.typeOfMortgage = typeOfMortgage;
		setRate();
	}
	public abstract void setRate();
	
	protected String[] calcDates(){
		
		String []dates = new String [numOfRepayments];
	    LocalDate date = LocalDate.parse(dateStart);

		if(typeOfMortgage.equals("semestral")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(6, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		else if(typeOfMortgage.equals("trimestral")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(3, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		else if(typeOfMortgage.equals("bimestral")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(2, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		else if(typeOfMortgage.equals("monthly")) {
			for(int i = 0; i < numOfRepayments; i++) {
			    date = date.plus(1, ChronoUnit.MONTHS);
			    dates[i] = date.toString();
				}
		}
		return dates;
	}
	protected void adjustRate() {
		double fraction = 0;
		if(typeOfMortgage.equals("semestral")) {
			fraction = (double) 1/2;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
		else if(typeOfMortgage.equals("trimestral")) {
			fraction = (double) 1/4;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
		else if(typeOfMortgage.equals("bimestral")) {
			fraction = (double) 1/6;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
		else if(typeOfMortgage.equals("monthly")) {
			fraction = (double) 1/12;
			rate = Math.pow(1 + rate,fraction) - 1;
		}
	}
	public void showInfo() {
		System.out.println("NAME: "+getName()+"\nMORTGAGE: "+getMortgage()
							+"\nACCOUNT OPENED IN: "+dateStart+"\nRATE: "+rate+
							 "\nMORTGAGE WILL END IN: "+calcDates()[numOfRepayments-1]+
							"\nNUM OF REPAYMENTS: "+numOfRepayments+"\nFIRST REPAYMENT: "+f.format(calculateMortgage()[0][0])+
							"\nLAST REPAYMENT: "+f.format(calculateMortgage()[0][numOfRepayments-1])+
							"\nTYPE OF MORTGAGE: "+mortType+", "+typeOfMortgage);
	}
	protected void showMort(double[][] quotes) {
		for(int i = 0; i < quotes[0].length; i++) {
			System.out.println("\nRepayment: "+f.format(quotes[0][i])+"\nCapital quote: "+f.format(quotes[1][i])+
					"\nInterest quote: "+f.format(quotes[2][i])+"\nDATE: "+calcDates()[i]);
			}
	}
	protected double[][] calculateMortgage() {
		double[][] quotes = new double[3][numOfRepayments];
		double capitalLeft = mortgage;
		double Interests = 0;
		double capitalQuote1 = 0;
		if (mortType.equals("ITALIAN")){
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
		else if(mortType.toUpperCase().equals("FRENCH")){
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
