package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemVisitorState implements ToDoItemState {
    private final ToDoItemVisitor toDoItemVisitor;

    public ToDoItemVisitorState(ToDoItemVisitor toDoItemVisitor) {
        this.toDoItemVisitor = toDoItemVisitor;
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
