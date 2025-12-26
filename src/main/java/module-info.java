module vsu.oop.motorko.omegachess {
    requires javafx.controls;
    requires javafx.fxml;


    opens vsu.oop.motorko.omegachess to javafx.fxml;
    exports vsu.oop.motorko.omegachess;
    exports vsu.oop.motorko.omegachess.core;
    opens vsu.oop.motorko.omegachess.core to javafx.fxml;
}