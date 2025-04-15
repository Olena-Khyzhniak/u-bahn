module ok.ubahn {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens ok.ubahn to javafx.fxml;
    opens ok.ubahn.Controller to javafx.fxml;
    opens ok.ubahn.UI to javafx.fxml;
    opens ok.ubahn.Data to javafx.fxml;
    opens ok.ubahn.Model to javafx.fxml;
    opens ok.ubahn.Util to javafx.fxml;

    exports ok.ubahn.UI;
    exports ok.ubahn.Controller;
    exports ok.ubahn.Data;
    exports ok.ubahn.Model;
    exports ok.ubahn.Util;

}