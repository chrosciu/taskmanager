package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public interface ToDoItemState {

    void process(ToDoItem toDoItem);
}
