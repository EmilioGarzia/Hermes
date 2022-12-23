module unipa.prog {
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

    opens unipa.prog3.view to javafx.fxml;
    exports unipa.prog3.view;
    exports unipa.prog3.view.controller;
    opens unipa.prog3.view.controller to javafx.fxml;
}