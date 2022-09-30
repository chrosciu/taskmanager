package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.state.ToDoItemApprovedState;
import com.smalaca.taskamanager.state.ToDoItemDefinedState;
import com.smalaca.taskamanager.state.ToDoItemDoneState;
import com.smalaca.taskamanager.state.ToDoItemInProgressState;
import com.smalaca.taskamanager.state.ToDoItemReleasedState;
import com.smalaca.taskamanager.state.ToDoItemState;
import org.springframework.stereotype.Component;

@Component
public class ToDoItemProcessor {
    private final StoryService storyService;
    private final EventsRegistry eventsRegistry;
    private final ProjectBacklogService projectBacklogService;
    private final CommunicationService communicationService;
    private final SprintBacklogService sprintBacklogService;

    public ToDoItemProcessor(
            StoryService storyService, EventsRegistry eventsRegistry, ProjectBacklogService projectBacklogService,
            CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        this.storyService = storyService;
        this.eventsRegistry = eventsRegistry;
        this.projectBacklogService = projectBacklogService;
        this.communicationService = communicationService;
        this.sprintBacklogService = sprintBacklogService;
    }

    public void processFor(ToDoItem toDoItem) {
        ToDoItemState toDoItemState = getStateForToDoItem(toDoItem);
        toDoItemState.process(toDoItem);
    }

    private ToDoItemState getStateForToDoItem(ToDoItem toDoItem) {
        switch (toDoItem.getStatus()) {
            case DEFINED:
                return new ToDoItemDefinedState(projectBacklogService, communicationService,
                    sprintBacklogService, eventsRegistry);
            case IN_PROGRESS:
                return new ToDoItemInProgressState(storyService);
            case DONE:
                return new ToDoItemDoneState(eventsRegistry, storyService);
            case APPROVED:
                return new ToDoItemApprovedState(eventsRegistry, storyService);
            case RELEASED:
                return new ToDoItemReleasedState(eventsRegistry);
            default:
                return new ToDoItemState() {};
        }
    }
}
