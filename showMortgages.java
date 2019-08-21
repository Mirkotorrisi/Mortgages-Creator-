package financeGUI;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class showMortgages {
	static Button button;
	static Button close;

	static Scene scene;
	public static void show(List<FinancialAccounts> accounts) {
		//This method takes accounts list as parameter to create a button for each account in it. Buttons land to info and mortgage view.
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Mortgages");
		window.setMinWidth(450);
		ScrollPane s1 = new ScrollPane();

		VBox box = new VBox();
		for(FinancialAccounts account : accounts) {
			account.adjustRate();
			account.calcDates();
			double[][] quotes = account.calculateMortgage();
			Button button = new Button(account.getName()+ " "+account.getDateStart()+ " "+quotes[0].length);
			button.setOnAction(e->{ account.showInfo();account.showMort(quotes);});
			box.getChildren().addAll(button);
			}
		s1.setContent(box);
		scene = new Scene (s1, 190, 400);
		window.setScene(scene);
		window.showAndWait();
	}
}
