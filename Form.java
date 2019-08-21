package financeGUI;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class form1 {
	static Button button;
	static Button back;
	static Scene scene;
	static String file = "C:\\Users\\LuLele\\Desktop\\MortgagesInfo.csv";

		public static void display() {
			Stage window = new Stage();
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(200);
			window.setTitle("Form");
			button = new Button("Submit");
			back = new Button("Back");
			//Form and Button
			ChoiceBox<String> choiceBilling = new ChoiceBox<>();
			choiceBilling.getItems().addAll("Semestral","Trimestral","Bimestral","Monthly");
			choiceBilling.setValue("Semestral");
			TextField nameField = new TextField("Name");
			TextField mortField = new TextField("Mortgage value");
			TextField numOfrepField = new TextField("Number of repayments");
			ChoiceBox<String> choiceDay = new ChoiceBox<>();
			choiceDay.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17",
					"18","19","20","21","22","23","24","25","26","27","28");
			choiceDay.setValue("01");
			ChoiceBox<String> choiceMonth = new ChoiceBox<>();
			choiceMonth.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");
			choiceMonth.setValue("01");
			ChoiceBox<String> choiceYear = new ChoiceBox<>();
			choiceYear.getItems().addAll("2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017",
					"2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028");
			choiceYear.setValue("2001");
			ChoiceBox<String> choiceType= new ChoiceBox<>();
			choiceType.getItems().addAll("Italian","French");
			choiceType.setValue("Italian");
			ChoiceBox<String> choiceRisk= new ChoiceBox<>();
			choiceRisk.getItems().addAll("High Risk","Low Risk");
			choiceRisk.setValue("Low Risk");
			button.setOnAction(e-> getChoice(choiceBilling,nameField,mortField,numOfrepField,choiceDay,choiceMonth,choiceYear,choiceType,choiceRisk));
			//Layout
			AnchorPane anchorpane = new AnchorPane();
			HBox dateMenu = new HBox();
			HBox hb = new HBox();
			hb.getChildren().addAll(button);
			anchorpane.setBottomAnchor(hb, 8.0);
			dateMenu.getChildren().addAll(choiceDay,choiceMonth,choiceYear);
			VBox layout = new VBox(10);
			layout.setPadding(new Insets(20,20,20,20));
			layout.getChildren().addAll(nameField,mortField,numOfrepField,dateMenu,choiceBilling,choiceType,choiceRisk);
			anchorpane.getChildren().addAll(layout,hb);
			scene = new Scene(anchorpane, 200, 400);
			window.setScene(scene);
			window.showAndWait();
		}

	private static void getChoice(ChoiceBox<String> choiceBilling, TextField nameField, TextField mortField, TextField numOfrepField, ChoiceBox<String> choiceday,ChoiceBox<String> choiceMonth,ChoiceBox<String> choiceYear,ChoiceBox<String> choiceType,ChoiceBox<String> choiceRisk) {
		try {
			String billing = choiceBilling.getValue();
			String date = choiceYear.getValue() +"-"+choiceMonth.getValue()+"-"+choiceday.getValue();
			String name = nameField.getText();
			int mort = Integer.parseInt(mortField.getText());
			int numOfRep = Integer.parseInt(numOfrepField.getText());
			String mortType = choiceType.getValue();
			String RiskProfile = choiceRisk.getValue();
			String result = name+","+date+","+mort+","+mortType+","+numOfRep+","+billing+","+RiskProfile+"\n";
			Files.write(Paths.get(file), result.getBytes(), StandardOpenOption.APPEND);
			AlertBox.display(" ", "Mortgage Created for "+name);
			Main.loadNewAccount(name,date,mort, mortType,numOfRep,billing,RiskProfile);

	}
		catch(Exception e) {
			AlertBox.display("ERROR", e.getMessage());
		}
  }
}
