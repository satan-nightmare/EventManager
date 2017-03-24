package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {
    private Main main;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Button loginButton;
    @FXML
    private Label invalidLabel;

    @FXML
    public void loginPressed(){
        if(usernameText.getText()=="" || passwordText.getText()=="")
            invalidLabel.setVisible(true);  //show error on empty field login
        else{
            Packet sent=new Packet();
            sent.operation="login";
            sent.s1=usernameText.getText();
            sent.s2=passwordText.getText();
            Packet received=main.request(sent); //Here we got response from server
            if(received.response){
                //Sending new packet to get user information
                Packet p1=new Packet();
                p1.operation="userInfo";
                p1.s1=sent.s1;
                main.setUser(main.request(p1)); //Setting same in static variable in mainController class
                //Sending new packet to get list of venues
                Packet p2=new Packet();
                p2.operation="venueList";
                Packet venueList=main.request(p2); //Main.request(sent);
                //Sending new packet to get list of user events
                Packet p3=new Packet();
                p3.operation="userEvents";
                Packet userEvents=null; //Main.request(p3); //Main.request(sent);
                Stage stage = (Stage) loginbtn.getScene().getWindow(); //selecting current window
                stage.close();  //then closing it
                try { //here opening new main window
                    Stage primaryStage=new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/main.fxml"));
                    Parent root=loader.load();
                    mainController controller = loader.<mainController>getController();
                    controller.initVariable(userInfo,venueList,userEvents);

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("common.css").toExternalForm());
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }


            }else{
                invalidLabel.setVisible(true);  //show error on invalid credentials
            }
        }
    }
    public void setMain(Main main){
        this.main=main;
    }
}
