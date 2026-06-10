module org.example.slipclass_demo_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires protobuf.java;
    requires javafx.base;
    requires javafx.graphics;

    exports org.example.slipclass_demo_1;
    exports org.example.slipclass_demo_1.configuration;
    opens org.example.slipclass_demo_1.configuration to javafx.fxml;

    opens org.example.slipclass_demo_1.model to javafx.base;
    opens org.example.slipclass_demo_1 to javafx.base, javafx.fxml;

}