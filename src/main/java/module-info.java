module com.example.cp2406_a1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports com.example.cp2406_a1.v1;
    opens com.example.cp2406_a1.v1 to javafx.fxml;
    exports com.example.cp2406_a1.v2;
    opens com.example.cp2406_a1.v2 to javafx.fxml;
    exports com.example.cp2406_a1.common;
    opens com.example.cp2406_a1.common to javafx.fxml;
}