package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.visitor.ToDoItemApprovedStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemApprovedState implements ToDoItemState {

    private final ToDoItemApprovedStateVisitor toDoItemApprovedStateVisitor;

    public ToDoItemApprovedState(EventsRegistry eventsRegistry, StoryService storyService) {
        toDoItemApprovedStateVisitor = new ToDoItemApprovedStateVisitor(eventsRegistry, storyService);
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemApprovedStateVisitor, toDoItem);
    }
}
