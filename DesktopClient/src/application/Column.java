package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Column {
    private final SimpleIntegerProperty sno;
    private final SimpleStringProperty from;
    private final SimpleStringProperty to;
    private final SimpleStringProperty name;

    public Column(Integer sno, String from, String to, String name) {
        this.sno = new SimpleIntegerProperty(sno);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
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

    public String getName() {
        return name.get();
    }
}
