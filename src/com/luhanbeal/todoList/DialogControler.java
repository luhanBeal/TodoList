package com.luhanbeal.todoList;

import com.luhanbeal.todoList.datamodel.TodoData;
import com.luhanbeal.todoList.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogControler {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private DatePicker deadlinePicker;

    public TodoItem processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValues = deadlinePicker.getValue();

        // RETURN ITEM BECAUSE WHE NEED IT TO BE SELECTED AFTER WE SAVE IT(press OKAY).
        TodoItem newItem = new TodoItem(shortDescription, details, deadlineValues);
        TodoData.getInstance().addTodoItem(newItem);
        return newItem;
    }
}
