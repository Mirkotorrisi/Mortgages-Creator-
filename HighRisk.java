package Finance;

public class HighRisk extends FinancialAccounts {
	public HighRisk(String name, String dateStart, int mortgage, String mortType, int numOfRepayments, String typeOfMortgage) {
		super(name,dateStart, mortgage, mortType, numOfRepayments, typeOfMortgage);
		this.riskProfile = "High Risk";
		}
	@Override
	public void setRate() {
		rate = getBaseRate() + 0.04;
	}
	public void showInfo() {
		System.out.println("----------High risk account------------");
		super.showInfo();
	}
}
