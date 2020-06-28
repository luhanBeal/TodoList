package com.luhanbeal.todoList;

import com.luhanbeal.todoList.datamodel.TodoData;
import com.luhanbeal.todoList.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.util.List;

public class Controller {

    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemsDetailsTextArea;
    @FXML
    private Label deadLineLabel;

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
    public void handleClickListView () {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
      //  System.out.println("The selected item is " + item);
        itemsDetailsTextArea.setText(item.getDetailedDescription());
        deadLineLabel.setText(item.getDeadLine().toString());
    }
}
