package financeGUI;

import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
		static Scene main;
		Stage window;
		Button createNew;
		Button show;
		Button save;
		static String file = "C:\\Users\\Desktop\\MortgagesInfo.csv";
		static List<FinancialAccounts> accounts = new LinkedList<FinancialAccounts>();

		public static void main(String[] args) {
			
			//Creating List of Accounts, reading from CSV file
			
			List<String[]> newAccounts = utilities.CSV.read(file); 
			for (String[]accountholders : newAccounts) {
				String name = accountholders[0];
				String dateStart = accountholders[1];
				Integer mortgage = Integer.parseInt(accountholders[2]);
				String mortType = accountholders[3].toUpperCase();
				Integer numOfRepayments = Integer.parseInt(accountholders[4]);
				String billing = accountholders[5].toLowerCase();
				String riskProfile = accountholders[6];
				if(riskProfile.equalsIgnoreCase("high risk")) {
					accounts.add(new HighRisk(name,dateStart,mortgage, mortType,numOfRepayments,billing));
				}
				else if (riskProfile.equalsIgnoreCase("low risk")){
					accounts.add(new LowRisk(name,dateStart,mortgage, mortType,numOfRepayments,billing));				
				}
				else {
					AlertBox.display("Error", "Error reading risk profiling.");
				}
			}
			launch(args);
		}
		public void start(Stage primaryStage) throws Exception {
			//Main stage
			window = primaryStage;
			window.setTitle("Mortgages App");
			createNew = new Button("Create New Mortgage");
			createNew.setOnAction(e->form1.display());
			show = new Button("Show Mortgages");
			show.setOnAction(e->showMortgages.show(accounts));

			save = new Button("Save PDFs");
			save.setOnAction(e->Save.display(accounts));
			VBox layout = new VBox(10);
			Text welcome = new Text("-----Welcome-----");
			layout.setPadding(new Insets(20,20,20,20));
			layout.getChildren().addAll(welcome,createNew,show,save);
			main = new Scene(layout, 200, 200);
			window.setScene(main);
			window.show();
			}

		public static void loadNewAccount(String name, String dateStart, int mortgage, String mortType, int numOfRepayments,
			//Loading new accounts created with the application opened
			String billing, String riskProfile) {
			if(riskProfile.equalsIgnoreCase("high risk")) {
				accounts.add(new HighRisk(name,dateStart,mortgage, mortType,numOfRepayments,billing));
				}
			else if (riskProfile.equalsIgnoreCase("low risk")){
				accounts.add(new LowRisk(name,dateStart,mortgage, mortType,numOfRepayments,billing));				
				}			
			}
		}
