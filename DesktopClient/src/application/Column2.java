package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Column2 {
    private final SimpleIntegerProperty sno;
    private final SimpleStringProperty from;
    private final SimpleStringProperty to;
    private final SimpleStringProperty date;
    private final SimpleStringProperty venue;
    private final SimpleStringProperty name;

    public Column2(Integer sno, String from, String to,String date,String venue, String name) {
        this.sno = new SimpleIntegerProperty(sno);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.date = new SimpleStringProperty(date);
        this.venue = new SimpleStringProperty(venue);
        this.name = new SimpleStringProperty(name);
    }

    public Integer getSno() {
        return sno.get();
    }

    public String getFrom() {
        return from.get();
    }

    public String getTo() {
        return to.get();
    }

    public String getDate() {return date.get();}

    public String getVenue() {
        return venue.get();
    }

    public String getName() {
        return name.get();
    }
}
