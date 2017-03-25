package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class registerController{
    @FXML
    TextField username;
    @FXML
    TextField name;
    @FXML
    TextField password;
    @FXML
    TextField confirmpassword;
    @FXML
    TextField email;
    @FXML
    TextField mobile;
    @FXML
    Button check;
    @FXML
    Button register;
    @FXML
    Label checkLabel;
    @FXML
    Label reg;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    RadioButton other;
    @FXML
    Label passast;
    @FXML
    ComboBox<String> branch;
    public Stage stage;

    private Main main;

    ObservableList<String> l;
    public void setMain(Main main,Stage stage){
        this.main=main;
        l = FXCollections.observableArrayList(
                "CS",
                "IT",
                "ECE"
        );
        branch.getItems().addAll(l);
        this.stage=stage;
    }

    @FXML
    public void checkHandle(){

        if(username.getText()==""){
            checkLabel.setTextFill(Color.web("#e42831"));
            checkLabel.setText("Unavailable");
        }else{
            Packet sent=new Packet();
            sent.operation="check";
            sent.s1=username.getText();
            Packet received=main.request(sent);
            if(received.response){
                checkLabel.setTextFill(Color.web("#13c652"));
                checkLabel.setText("Available");
            }else{
                checkLabel.setTextFill(Color.web("#e42831"));
                checkLabel.setText("Unvailable");
            }
        }
        checkLabel.setVisible(true);
    }

    @FXML
    public void registerHandle(){
        if(username.getText().equals("") || name.getText().equals("") || mobile.getText().equals("") || password.getText().equals("") || confirmpassword.getText().equals("") || email.getText().equals("")){
            reg.setTextFill(Color.web("#e42831"));
            reg.setText("Please fill all the details");
        }else{
            Packet sent=new Packet();
            sent.operation="signup";
            sent.s1=username.getText();
            sent.s2=name.getText();
            sent.s3=password.getText();
            sent.s5=email.getText();
            sent.s6=branch.getValue(); //branch
            sent.s7=mobile.getText();
            if(male.isSelected())
                sent.s4="Male";
            else if(female.isSelected())
                sent.s5="Female";
            else
                sent.s5="Other";
            Packet received=main.request(sent);
            System.out.println(received.response);
            if(received.response){
                stage.close();
                //reg.setTextFill(Color.web("#13c652"));
                //reg.setText("Congratulations, Registration Completed");
            }else{
                //reg.setTextFill(Color.web("#e42831"));
                //reg.setText("Congratulations, Registration Completed");
            }
        }
        //reg.setVisible(true);
    }

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmpassword.textProperty().addListener(observable -> {
            if(password.getText().equals(confirmpassword.getText())) {
                passast.setVisible(false);
                System.out.println("False");
            }else {
                passast.setVisible(true);
                System.out.println("True");
            }
        });
        cpassword.textPropert().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
    }*/

    /*public void passwordMatch(ActionEvent event){
        if(password.getText().equals(cpassword.getText())) {
            passast.setVisible(false);
            System.out.println("False");
        }else {
            passast.setVisible(true);
            System.out.println("True");
        }
    }*/



}
