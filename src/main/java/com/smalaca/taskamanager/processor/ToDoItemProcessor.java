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
    private final ToDoItemDefinedState toDoItemDefinedState;
    private final ToDoItemInProgressState toDoItemInProgressState;
    private final ToDoItemDoneState toDoItemDoneState;
    private final ToDoItemApprovedState toDoItemApprovedState;
    private final ToDoItemReleasedState toDoItemReleasedState;
    private final ToDoItemState toDoItemNoOpState;

    public ToDoItemProcessor(
            StoryService storyService, EventsRegistry eventsRegistry, ProjectBacklogService projectBacklogService,
            CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        toDoItemDefinedState = new ToDoItemDefinedState(projectBacklogService, communicationService,
            sprintBacklogService, eventsRegistry);
        toDoItemInProgressState = new ToDoItemInProgressState(storyService);
        toDoItemDoneState = new ToDoItemDoneState(eventsRegistry, storyService);
        toDoItemApprovedState = new ToDoItemApprovedState(eventsRegistry, storyService);
        toDoItemReleasedState = new ToDoItemReleasedState(eventsRegistry);
        toDoItemNoOpState = new ToDoItemState() {};
    }

    public void processFor(ToDoItem toDoItem) {
        ToDoItemState toDoItemState = getStateForToDoItem(toDoItem);
        toDoItemState.process(toDoItem);
    }

    private ToDoItemState getStateForToDoItem(ToDoItem toDoItem) {
        switch (toDoItem.getStatus()) {
            case DEFINED:
                return toDoItemDefinedState;
            case IN_PROGRESS:
                return toDoItemInProgressState;
            case DONE:
                return toDoItemDoneState;
            case APPROVED:
                return toDoItemApprovedState;
            case RELEASED:
                return toDoItemReleasedState;
            default:
                return toDoItemNoOpState;
        }
    }
}
