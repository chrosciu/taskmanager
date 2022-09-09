package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public class ToDoItemNoOpState implements ToDoItemState {

    @Override
    public void process(ToDoItem toDoItem) {}
}
