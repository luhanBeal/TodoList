package com.luhanbeal.todoList;

import datamodel.TodoItem;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<TodoItem> todoItems;

    public void initialize() {
        TodoItem item1 = new TodoItem("Mail birthday to my father", "His birthday is not going to happen due to corona virus",
                LocalDate.of(2020, Month.NOVEMBER, 8));
        TodoItem item2 = new TodoItem("Go to beach", "my girl let me stay with her a few days at the beach",
                LocalDate.of(2020, Month.MAY, 1));

        todoItems = new ArrayList<TodoItem>();
        todoItems.add(item1);
        todoItems.add(item2);
    }
}
