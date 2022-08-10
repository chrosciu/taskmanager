package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.events.ToDoItemReleasedEvent;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;

public class ReleasedToDoItemVisitor implements ToDoItemVisitor {

    private final EventsRegistry eventsRegistry;

    public ReleasedToDoItemVisitor(EventsRegistry eventsRegistry) {
        this.eventsRegistry = eventsRegistry;
    }

    @Override
    public void visit(ToDoItem toDoItem) {
        ToDoItemReleasedEvent event = new ToDoItemReleasedEvent();
        event.setToDoItemId(toDoItem.getId());
        eventsRegistry.publish(event);
    }

    @Override
    public void visit(Epic epic) {
        visit((ToDoItem)epic);
    }

    @Override
    public void visit(Story story) {
        visit((ToDoItem)story);
    }

    @Override
    public void visit(Task task) {
        visit((ToDoItem)task);
    }
}
