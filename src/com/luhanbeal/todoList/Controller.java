package com.luhanbeal.todoList;

import com.luhanbeal.todoList.datamodel.TodoData;
import com.luhanbeal.todoList.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemsDetailsTextArea;
    @FXML
    private Label deadLineLabel;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {

        //Change handler from mouse click to events listener
        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                if (newValue !=null) {
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemsDetailsTextArea.setText(item.getDetailedDescription());
                    // we could format the date with the DateTimeFormatter();
                    deadLineLabel.setText(item.getDeadLine().toString());
                }
            }
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        //set the selection to SINGLE - just one selected at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //set the first item to start selected as default
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog() {
        //create an instance of a dialog class
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("todoItemDialog.fxml"));
            dialog.getDialogPane().setContent(root);
        } catch(IOException e) {
            System.out.println("Coudn't load the dialog");
            e.printStackTrace();
            return;
        }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("OK pressed");
            } else {
                System.out.println("Cancel pressed");
            }

    }

    @FXML
    public void handleClickListView () {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
      //  System.out.println("The selected item is " + item);
        itemsDetailsTextArea.setText(item.getDetailedDescription());
        deadLineLabel.setText(item.getDeadLine().toString());
    }
}
