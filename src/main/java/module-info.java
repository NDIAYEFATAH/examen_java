module sn.dev.ramadanapps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires lombok;
    requires java.sql;


    opens sn.dev.ramadanapps to javafx.fxml;
    exports sn.dev.ramadanapps;
    exports sn.dev.ramadanapps.controller;
    opens sn.dev.ramadanapps.controller to javafx.fxml;
    opens sn.dev.ramadanapps.entities;
}