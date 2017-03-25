package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.util.Date;

public class bookController {
    private Main main;
    private String venue;
    @FXML
    private Label eventName;
    @FXML
    private TextArea description;
    @FXML
    private TextField url;
    @FXML
    private DatePicker date;
    @FXML
    private Label sorryLabel;
    @FXML
    private ComboBox<String> from;
    @FXML
    private ComboBox<String> to;
    private ObservableList<String> data = FXCollections.observableArrayList();
    private mainController mc;
    private Packet sent;
    @FXML
    public void handleBook(){
        if(description.getText().equals("") || url.getText().equals("")){
            sorryLabel.setText("Please fill all the fields");
            sorryLabel.setVisible(true);
        }else{
            sent = new Packet();
            sent.operation="book";
            sent.s1=eventName.getText();
            sent.s2=main.username;
            sent.s3=venue;
            sent.s4=description.getText();
            sent.s5=url.getText();
            sent.i=from.getSelectionModel().getSelectedIndex();
            sent.j=to.getSelectionModel().getSelectedIndex();
            sent.date = Date.from(date.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            if(main.request(sent).response){
                mc.closeBook();
            }else{
                sorryLabel.setText("Sorry, the slot is not available.");
                sorryLabel.setVisible(true);
            }
        }
    }

    public void setMain(Main main,mainController mc,String venue,String name){
        this.main=main;
        this.mc=mc;
        this.venue=venue;
        eventName.setText(name);
        data.addAll("00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00"
                ,"11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"
                ,"22:00","23:00");
        from.setItems(data);
        to.setItems(data);
    }
}
