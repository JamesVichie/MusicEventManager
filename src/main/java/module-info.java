module com.idk.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

//    requires javafx.controls;
//    requires javafx.fxml;

    opens com.idk.demo.Controllers to javafx.fxml; // Allow FXML to access controllers

    exports com.idk.demo;
    exports com.idk.demo.Controllers; // If other modules need access to controllers

    opens com.idk.demo to javafx.fxml;

//    exports com.idk.demo;
}