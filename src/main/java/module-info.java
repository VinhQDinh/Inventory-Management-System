module wgu.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens wgu.c482 to javafx.fxml;
    exports wgu.c482;
    exports model;
    opens model to javafx.fxml;
}