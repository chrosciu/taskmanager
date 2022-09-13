package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.events.ToDoItemReleasedEvent;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;

public class ToDoItemReleasedState implements ToDoItemState {

    private final EventsRegistry eventsRegistry;

    public ToDoItemReleasedState(EventsRegistry eventsRegistry) {
        this.eventsRegistry = eventsRegistry;
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemReleasedEvent event = new ToDoItemReleasedEvent();
        event.setToDoItemId(toDoItem.getId());
        eventsRegistry.publish(event);
    }
}
