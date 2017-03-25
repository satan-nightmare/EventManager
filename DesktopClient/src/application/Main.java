package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Main extends Application {

    Stage primaryStage;
    Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    public String username;
    public boolean admin;
    public Packet user;
    public Packet received;
    public List<String> venueList;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/connect.fxml"));
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Hello World");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/fxml/connect.fxml"));
        AnchorPane root = loader.load();
        connectController controller = loader.getController();
        controller.setMain(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public boolean connection(String ip){
        try {
            s=new Socket(ip,12345);
        } catch (Exception e){
            System.out.println("fuck1");
            return false;
        }

        try {
            oos = new ObjectOutputStream(s.getOutputStream());
        }catch (Exception e){
            System.out.println("fuck2");
            return false;
        }
        try {
            ois = new ObjectInputStream(s.getInputStream());
        }catch (Exception e){
            System.out.println("fuck3");
            return false;
        }
        return true;
    }

    public Packet request(Packet p){
        System.out.println(p.operation);
        try {
            oos.writeObject(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem in sending Packet");
        }
        System.out.println("Packet sent");

        try {
            received = (Packet)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem in receiving Packet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Problem in receiving Packet");
        } catch (Exception e){
            System.out.println("Problem in receiving Packet");
        }
        System.out.println("Packet received");
        return received;
    }

    public void setUser(Packet p){

    }

    public void launchLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/fxml/login.fxml"));
        AnchorPane root = loader.load();
        loginController controller = loader.getController();
        controller.setMain(this);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        //primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
