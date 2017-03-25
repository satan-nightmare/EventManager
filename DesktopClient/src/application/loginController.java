package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    public Packet sent,received;

    @FXML
    public void loginHandle(){
        if(usernameText.getText()=="" || passwordText.getText()=="")
            invalidLabel.setVisible(true);  //show error on empty field login
        else{
             login();
            if(received.response){
                main.username=received.s1;
                getUserInfo();
                getVenueList();
                 //Main.request(sent);
                //Sending new packet to get list of user events
                Packet p3=new Packet();
                p3.operation="userEvents";
                Packet userEvents=null; //Main.request(p3); //Main.request(sent);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/main.fxml"));
                try {
                    AnchorPane root = loader.load();
                    mainController controller = loader.getController();
                    controller.setMain(main);
                    main.primaryStage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*Stage stage = (Stage) loginbtn.getScene().getWindow(); //selecting current window
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
                }*/


            }else{
                invalidLabel.setVisible(true);  //show error on invalid credentials
            }
        }
    }

    @FXML
    public void registerHandle() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Register");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/fxml/register.fxml"));
        AnchorPane root = loader.load();
        registerController controller = loader.getController();
        controller.setMain(main,stage);
//        stage.setScene(new Scene(root));
//        stage.show();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(usernameText.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void login(){
        sent=new Packet();
        sent.operation="login";
        sent.s1=usernameText.getText();
        sent.s2=passwordText.getText();
        received=main.request(sent);
    }

    private void getUserInfo(){
        sent=new Packet();
        sent.operation="userinfo";
        sent.s1=received.s1;
        main.user = main.request(sent);
    }

    private void getVenueList(){
        sent = new Packet();
        sent.operation="venuelist";
        main.venueList=main.request(sent).list;
    }

    public void setMain(Main main){
        this.main=main;
    }
}
