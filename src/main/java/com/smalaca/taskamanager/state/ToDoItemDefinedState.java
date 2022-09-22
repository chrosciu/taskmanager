package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.visitor.ToDoItemDefinedStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemDefinedState implements ToDoItemState {
    private final ToDoItemDefinedStateVisitor toDoItemVisitor;

    public ToDoItemDefinedState(ProjectBacklogService projectBacklogService, CommunicationService communicationService,
        SprintBacklogService sprintBacklogService, EventsRegistry eventsRegistry) {
        this.toDoItemVisitor = new ToDoItemDefinedStateVisitor(projectBacklogService, communicationService,
            sprintBacklogService, eventsRegistry);
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
