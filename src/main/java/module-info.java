module com.polynomialcalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.polynomialcalculator to javafx.fxml;
    exports com.example.polynomialcalculator;
    exports com.example.polynomialcalculator.Controller;
    opens com.example.polynomialcalculator.Controller to javafx.fxml;
    exports com.example.polynomialcalculator.Model;
    opens com.example.polynomialcalculator.Model to javafx.fxml;

}