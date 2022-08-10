package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.CommunicationService;
import com.smalaca.taskamanager.service.ProjectBacklogService;
import com.smalaca.taskamanager.service.SprintBacklogService;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.visitor.ApprovedToDoItemVisitor;
import com.smalaca.taskamanager.visitor.DefinedToDoItemVisitor;
import com.smalaca.taskamanager.visitor.DoneToDoItemVisitor;
import com.smalaca.taskamanager.visitor.InProgressToDoItemVisitor;
import com.smalaca.taskamanager.visitor.ReleasedToDoItemVisitor;
import org.springframework.stereotype.Component;

@Component
public class ToDoItemProcessor {

    private final DefinedToDoItemVisitor definedToDoItemVisitor;
    private final ReleasedToDoItemVisitor releasedToDoItemVisitor;
    private final InProgressToDoItemVisitor inProgressToDoItemVisitor;
    private final DoneToDoItemVisitor doneToDoItemVisitor;
    private final ApprovedToDoItemVisitor approvedToDoItemVisitor;

    public ToDoItemProcessor(
            StoryService storyService, EventsRegistry eventsRegistry, ProjectBacklogService projectBacklogService,
            CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        definedToDoItemVisitor = new DefinedToDoItemVisitor(projectBacklogService, eventsRegistry, communicationService,
            sprintBacklogService);
        releasedToDoItemVisitor = new ReleasedToDoItemVisitor(eventsRegistry);
        inProgressToDoItemVisitor = new InProgressToDoItemVisitor(storyService);
        doneToDoItemVisitor = new DoneToDoItemVisitor(eventsRegistry, storyService);
        approvedToDoItemVisitor = new ApprovedToDoItemVisitor(eventsRegistry, storyService);
    }

    public void processFor(ToDoItem toDoItem) {
        switch (toDoItem.getStatus()) {
            case DEFINED:
                processDefined(toDoItem);
                break;

            case IN_PROGRESS:
                processInProgress(toDoItem);
                break;

            case DONE:
                processDone(toDoItem);
                break;

            case APPROVED:
                processApproved(toDoItem);
                break;

            case RELEASED:
                processReleased(toDoItem);
                break;

            default:
                break;
        }
    }

    private void processDefined(ToDoItem toDoItem) {
        toDoItem.accept(definedToDoItemVisitor);
    }

    private void processInProgress(ToDoItem toDoItem) {
        toDoItem.accept(inProgressToDoItemVisitor);
    }

    private void processDone(ToDoItem toDoItem) {
        toDoItem.accept(doneToDoItemVisitor);
    }

    private void processApproved(ToDoItem toDoItem) {
        toDoItem.accept(approvedToDoItemVisitor);
    }

    private void processReleased(ToDoItem toDoItem) {
        toDoItem.accept(releasedToDoItemVisitor);
    }
}
