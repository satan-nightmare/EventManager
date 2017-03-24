package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class connectController {
    private Main main;
    @FXML
    private Label invalidLabel;
    @FXML
    private TextField ipText;
    @FXML
    public void handleConnect(){
        if(ipText.getText().equals(""))
            invalidLabel.setText("Enter ip address first");
        else{
            System.out.println(ipText.getText());
            if(main.connection(ipText.getText())){
                try {
                    main.launchLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else
                invalidLabel.setText("Cannot connect/Invalid IP");
        }
    }

    public void setMain(Main main){
        this.main=main;
    }
}
