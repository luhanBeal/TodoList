package com.luhanbeal.todoList;

import com.luhanbeal.todoList.datamodel.TodoData;
import com.luhanbeal.todoList.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
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
    @FXML
    private ContextMenu listContextMenu;


    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        // Override event handler
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
        // add the delete item to the context menu
        listContextMenu.getItems().addAll(deleteMenuItem);

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

        todoListView.setItems(TodoData.getInstance().getTodoItems());
        //set the selection to SINGLE - just one selected at a time
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //set the first item to start selected as default
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> todoItemListView) {
                ListCell<TodoItem> cell = new ListCell<TodoItem>() {
                    @Override
                    protected void updateItem(TodoItem todoItem, boolean b) {
                        super.updateItem(todoItem, b);
                        if (b) {
                            setText(null);
                        } else {
                            setText(todoItem.getShortDescription());
                            if (todoItem.getDeadLine().equals(LocalDate.now())) {
                                setTextFill(Color.RED);
                            } else if (todoItem.getDeadLine().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.BROWN);
                            } else if (todoItem.getDeadLine().isBefore(LocalDate.now())) {
                                setTextFill(Color.BLACK);
                            } else if (todoItem.getDeadLine().isAfter(LocalDate.now())) {
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };
                /* here, before the return, we need to add the the context menu to the
                * cell factory. We need to use an anonimous function (lambda expression) */
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );

                return cell;
            }
        });
    }

    @FXML
    public void showNewItemDialog() {
        //create an instance of a dialog class
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        //set name of the new box
        dialog.setTitle("Adicionar nova tarefa");
        dialog.setHeaderText("Informe novo item para a lista de afazeres");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                DialogControler controller = fxmlLoader.getController();
                TodoItem newItem = controller.processResults();
                //select the new item after created
                todoListView.getSelectionModel().select(newItem);
            }

    }

    @FXML
    public void handleClickListView () {
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
      //  System.out.println("The selected item is " + item);
        itemsDetailsTextArea.setText(item.getDetailedDescription());
        deadLineLabel.setText(item.getDeadLine().toString());
    }

    public void deleteItem(TodoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo item");
        alert.setHeaderText("Delete item " + item.getShortDescription());
        alert.setContentText("Are you sure? Press OKAY to confirm or CANCEL to return without deleting the item.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }

}
