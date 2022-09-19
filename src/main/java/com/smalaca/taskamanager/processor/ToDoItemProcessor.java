package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.state.ToDoItemState;
import com.smalaca.taskamanager.state.ToDoItemVisitorState;
import com.smalaca.taskamanager.visitor.ToDoItemApprovedStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemDefinedStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemDoneStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemInProgressStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemReleasedStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;
import org.springframework.stereotype.Component;

@Component
public class ToDoItemProcessor {
    private final ToDoItemState toDoItemDefinedState;
    private final ToDoItemState toDoItemInProgressState;
    private final ToDoItemState toDoItemDoneState;
    private final ToDoItemState toDoItemApprovedState;
    private final ToDoItemState toDoItemReleasedState;
    private final ToDoItemState toDoItemNoOpState;

    public ToDoItemProcessor(
            StoryService storyService, EventsRegistry eventsRegistry, ProjectBacklogService projectBacklogService,
            CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        toDoItemDefinedState = new ToDoItemVisitorState(
            new ToDoItemDefinedStateVisitor(
                projectBacklogService, communicationService, sprintBacklogService, eventsRegistry
            )
        );
        toDoItemInProgressState = new ToDoItemVisitorState(new ToDoItemInProgressStateVisitor(storyService));
        toDoItemDoneState = new ToDoItemVisitorState(new ToDoItemDoneStateVisitor(eventsRegistry, storyService));
        toDoItemApprovedState = new ToDoItemVisitorState(new ToDoItemApprovedStateVisitor(eventsRegistry, storyService));
        toDoItemReleasedState = new ToDoItemVisitorState(new ToDoItemReleasedStateVisitor(eventsRegistry));
        toDoItemNoOpState = new ToDoItemVisitorState(new ToDoItemVisitor() {});
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
