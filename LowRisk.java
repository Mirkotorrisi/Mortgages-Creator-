package Finance;

public class LowRisk extends FinancialAccounts {
	public LowRisk(String name, String dateStart, int mortgage, String mortType, int numOfRepayments, String typeOfMortgage) {
		super(name,dateStart, mortgage, mortType, numOfRepayments, typeOfMortgage);
		this.riskProfile = "Low Risk";
		}
	@Override
	public void setRate() {
		rate = getBaseRate() - 0.02;
	}
	public void showInfo() {
		System.out.println("----------Low risk account------------");
		super.showInfo();
	}
}
