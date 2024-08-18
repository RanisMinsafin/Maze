module edu.school {
    requires javafx.controls;
    requires javafx.fxml;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens edu.school to javafx.fxml;
    exports edu.school;
    exports edu.school.controller;
    opens edu.school.controller to javafx.fxml;
}