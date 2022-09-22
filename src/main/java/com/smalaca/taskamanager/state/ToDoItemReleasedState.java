package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.visitor.ToDoItemReleasedStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemReleasedState implements ToDoItemState {
    private final ToDoItemReleasedStateVisitor toDoItemVisitor;

    public ToDoItemReleasedState(EventsRegistry eventsRegistry) {
        this.toDoItemVisitor = new ToDoItemReleasedStateVisitor(eventsRegistry);
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
