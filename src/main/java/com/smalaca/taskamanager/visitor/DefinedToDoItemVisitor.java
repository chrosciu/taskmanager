package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.events.EpicReadyToPrioritize;
import com.smalaca.taskamanager.exception.UnsupportedToDoItemType;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;

public class DefinedToDoItemVisitor implements ToDoItemVisitor {
    private final ProjectBacklogService projectBacklogService;
    private final EventsRegistry eventsRegistry;
    private final CommunicationService communicationService;
    private final SprintBacklogService sprintBacklogService;

    public DefinedToDoItemVisitor(ProjectBacklogService projectBacklogService, EventsRegistry eventsRegistry,
        CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        this.projectBacklogService = projectBacklogService;
        this.eventsRegistry = eventsRegistry;
        this.communicationService = communicationService;
        this.sprintBacklogService = sprintBacklogService;
    }

    @Override
    public void visit(ToDoItem toDoItem) {
        throw new UnsupportedToDoItemType();
    }

    @Override
    public void visit(Epic epic) {
        projectBacklogService.putOnTop(epic);
        EpicReadyToPrioritize event = new EpicReadyToPrioritize();
        event.setEpicId(epic.getId());
        eventsRegistry.publish(event);
        communicationService.notify(epic, epic.getProject().getProductOwner());
    }

    @Override
    public void visit(Story story) {
        if (story.getTasks().isEmpty()) {
            projectBacklogService.moveToReadyForDevelopment(story, story.getProject());
        } else {
            if (!story.isAssigned()) {
                communicationService.notifyTeamsAbout(story, story.getProject());
            }
        }
    }

    @Override
    public void visit(Task task) {
        sprintBacklogService.moveToReadyForDevelopment(task, task.getCurrentSprint());
    }
}
