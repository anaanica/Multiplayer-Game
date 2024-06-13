module hr.algebra.threerp3.tictactoe3rp3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.xml;
    requires java.naming;

    exports hr.algebra.threerp3.tictactoe3rp3.chat.service to java.rmi;
    opens hr.algebra.threerp3.tictactoe3rp3 to javafx.fxml;
    exports hr.algebra.threerp3.tictactoe3rp3;
    exports hr.algebra.threerp3.tictactoe3rp3.controller;
    opens hr.algebra.threerp3.tictactoe3rp3.controller to javafx.fxml;
    exports hr.algebra.threerp3.tictactoe3rp3.model;
    opens hr.algebra.threerp3.tictactoe3rp3.model to javafx.fxml;
}