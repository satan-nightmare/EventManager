package application;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class mainController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label branchLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label mobileLabel;
    @FXML
    private Label acLabel;
    @FXML
    private Label capacityLabel;
    @FXML
    private TextField eventName;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private DatePicker date;
    @FXML
    private TableView<Column> table;
    @FXML
    TableColumn<Column,Integer> sno;
    @FXML TableColumn<Column,String> from;
    @FXML TableColumn<Column,String> to;
    @FXML TableColumn<Column,String> name;
    @FXML TableView<Column2> table1;
    @FXML TableColumn<Column2,Integer> sno1;
    @FXML TableColumn<Column2,String> from1;
    @FXML TableColumn<Column2,String> to1;
    @FXML TableColumn<Column2,String> name1;
    @FXML TableColumn<Column2,String> venue1;
    @FXML TableColumn<Column2,String> date1;
    @FXML
    public Stage bookStage;
    private ObservableList<String> l;
    private ObservableList<Column> l2;
    private ObservableList<Column2> l3;
    private Packet sent,received;
    @FXML
    private void initialize(){}

    private Main main;
    public void setMain(Main main){
        this.main=main;
        nameLabel.setText(main.user.s2);
        usernameLabel.setText(main.user.s1);
        branchLabel.setText(main.user.s6);
        genderLabel.setText(main.user.s4);
        emailLabel.setText(main.user.s5);
        mobileLabel.setText(main.user.s7);
        if(main.venueList!=null) {
            l = FXCollections.observableList(main.venueList);
            combobox.setItems(l);
        }
        combobox.setOnAction((event) -> {
            getVenueDetail(combobox.getValue());
            acLabel.setVisible(true);
            if(received.response){
                acLabel.setText("AC");
                acLabel.setTextFill(Color.web("#249713"));
            }else{
                acLabel.setText("Non AC");
                acLabel.setTextFill(Color.web("#d01d1a"));
            }
            capacityLabel.setText("Capacity: "+Integer.toString(received.i));
        });
    }

    private void getVenueDetail(String venue){
        sent=new Packet();
        sent.operation="venuedetail";
        sent.s1=venue;
        received=main.request(sent);
    }

    @FXML
    public void handleBook() throws IOException {
        bookStage = new Stage();
        bookStage.setTitle("Book Event");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/fxml/book.fxml"));
        AnchorPane root = loader.load();
        bookController controller = loader.getController();
        controller.setMain(main,this,combobox.getValue(),eventName.getText());

//        stage.setScene(new Scene(root));
//        stage.show();
        bookStage.initModality(Modality.WINDOW_MODAL);
        bookStage.initOwner(nameLabel.getScene().getWindow());
        bookStage.setScene(new Scene(root));
        bookStage.show();
    }
    public void closeBook(){
        bookStage.close();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(nameLabel.getScene().getWindow());
        alert.setTitle("Request Succesful");
        alert.setHeaderText("The event request has succesfully made");
        alert.setContentText("sumit");

        alert.showAndWait();

        //System.out.println((i+1)+" "+list.get(i).get(0)+" "+list.get(i).get(1)+" "+list.get(i).get(2));
        //c=new Column(i+1,list.get(i).get(0),list.get(i).get(1),list.get(i).get(2));

    }

    private void setTable(List<List<String>> list){
        List<Column> list2=new ArrayList<Column>();
        Column c;
        for(int i=0;i<list.size();i++){
            System.out.println((i+1)+" "+list.get(i).get(0)+" "+list.get(i).get(1)+" "+list.get(i).get(2));
            c=new Column(i+1,list.get(i).get(0),list.get(i).get(1),list.get(i).get(2));
            list2.add(c);
        }
        l2=FXCollections.observableList(list2);
        sno.setCellValueFactory(new PropertyValueFactory<Column, Integer>("sno"));
        from.setCellValueFactory(new PropertyValueFactory<Column, String>("from"));
        to.setCellValueFactory(new PropertyValueFactory<Column, String>("to"));
        name.setCellValueFactory(new PropertyValueFactory<Column, String>("name"));
        table.setItems(l2);
    }

    private void setTable2(List<List<String>> list){
        List<Column2> list2=new ArrayList<Column2>();
        Column2 c;
        for(int i=0;i<list.size();i++){
//            if(list.get(i).get(2)==null)
//                c=new Column(i+1,(i+9)+":00",(i+10)+":00","No Event");
//            else
//                c=new Column(i+1,(i+9)+":00",(i+10)+":00",list.get(i).get(2));
            System.out.println((i+1)+" "+list.get(i).get(0)+" "+list.get(i).get(1)+" "+list.get(i).get(2));
            c=new Column2(i+1,list.get(i).get(0),list.get(i).get(1)
                    ,list.get(i).get(3)+"/"+list.get(i).get(4)+"/"+list.get(i).get(5),list.get(i).get(6),list.get(i).get(2));
            list2.add(c);
        }
        l3=FXCollections.observableList(list2);
        sno1.setCellValueFactory(new PropertyValueFactory<Column2, Integer>("sno"));
        from1.setCellValueFactory(new PropertyValueFactory<Column2, String>("from"));
        to1.setCellValueFactory(new PropertyValueFactory<Column2, String>("to"));
        date1.setCellValueFactory(new PropertyValueFactory<Column2, String>("date"));
        venue1.setCellValueFactory(new PropertyValueFactory<Column2, String>("venue"));
        name1.setCellValueFactory(new PropertyValueFactory<Column2, String>("name"));
        table.setItems(l2);
    }

    @FXML
    public void infoHandle(){
        if(date.getValue()!=null && !combobox.getValue().equals("")){
            sent=new Packet();
            sent.operation="venueslotinfo";
            sent.s1=combobox.getValue();
            sent.date= Date.from(date.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            received=main.request(sent);
            //for(int i=0;i<12;i++){
            //    System.out.println(received.list2.get(i).get(2));
            //}
            //if()
            setTable(received.list2);
            //setTable2();
        }
        sent=new Packet();
        sent.operation="userevents";
        sent.s1=main.username;
        System.out.println(main.username);
        received=main.request(sent);
        setTable2(received.list2);
    }

    @FXML
    public void logoutHandle(){
        Packet sent=new Packet();
        sent.operation="logout";
        main.request(sent);
    }
}
