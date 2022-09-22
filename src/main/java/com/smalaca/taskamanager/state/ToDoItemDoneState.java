package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.visitor.ToDoItemDoneStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemDoneState implements ToDoItemState {
    private final ToDoItemDoneStateVisitor toDoItemVisitor;

    public ToDoItemDoneState(EventsRegistry eventsRegistry, StoryService storyService) {
        this.toDoItemVisitor = new ToDoItemDoneStateVisitor(eventsRegistry, storyService);
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
