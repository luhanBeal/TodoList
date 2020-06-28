package com.luhanbeal.todoList.datamodel;

import java.time.LocalDate;

public class TodoItem {

    private String shortDescription;
    private String detailedDescription;
    private LocalDate deadLine;

    public TodoItem(String shortDescription, String detailedDescription, LocalDate deadLine) {
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.deadLine = deadLine;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    //override to string to get the short description !!!

    @Override
    public String toString() {
        return shortDescription;
    }
}
