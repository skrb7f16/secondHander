package sample.user;

import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;
import sample.database.MySqlOperations;
import sample.models.Requests;

public class SingleRequest extends ListCell<Requests> {
    MySqlOperations database;

    public SingleRequest(MySqlOperations database) {
        this.database = database;
    }
}
