package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemNoOpState implements ToDoItemState {
    private final ToDoItemVisitor toDoItemVisitor = new ToDoItemVisitor() {};

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
