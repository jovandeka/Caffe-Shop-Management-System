module com.example.cafeshopmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jasperreports;


    opens com.example.cafeshopmanagementsystem to javafx.fxml;
    exports com.example.cafeshopmanagementsystem;
}