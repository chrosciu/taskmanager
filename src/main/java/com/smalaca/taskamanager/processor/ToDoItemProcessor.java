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
import com.smalaca.taskamanager.visitor.NoOpToDoItemVisitor;
import com.smalaca.taskamanager.visitor.ReleasedToDoItemVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;
import org.springframework.stereotype.Component;

@Component
public class ToDoItemProcessor {

    private final DefinedToDoItemVisitor definedToDoItemVisitor;
    private final ReleasedToDoItemVisitor releasedToDoItemVisitor;
    private final InProgressToDoItemVisitor inProgressToDoItemVisitor;
    private final DoneToDoItemVisitor doneToDoItemVisitor;
    private final ApprovedToDoItemVisitor approvedToDoItemVisitor;
    private final NoOpToDoItemVisitor noOpToDoItemVisitor;

    public ToDoItemProcessor(
            StoryService storyService, EventsRegistry eventsRegistry, ProjectBacklogService projectBacklogService,
            CommunicationService communicationService, SprintBacklogService sprintBacklogService) {
        definedToDoItemVisitor = new DefinedToDoItemVisitor(projectBacklogService, eventsRegistry, communicationService,
            sprintBacklogService);
        releasedToDoItemVisitor = new ReleasedToDoItemVisitor(eventsRegistry);
        inProgressToDoItemVisitor = new InProgressToDoItemVisitor(storyService);
        doneToDoItemVisitor = new DoneToDoItemVisitor(eventsRegistry, storyService);
        approvedToDoItemVisitor = new ApprovedToDoItemVisitor(eventsRegistry, storyService);
        noOpToDoItemVisitor = new NoOpToDoItemVisitor();
    }

    public void processFor(ToDoItem toDoItem) {
        ToDoItemVisitor visitor = selectVisitor(toDoItem);
        toDoItem.accept(visitor);
    }

    private ToDoItemVisitor selectVisitor(ToDoItem toDoItem) {
        switch (toDoItem.getStatus()) {
            case DEFINED:
                return definedToDoItemVisitor;
            case IN_PROGRESS:
                return inProgressToDoItemVisitor;
            case DONE:
                return doneToDoItemVisitor;
            case APPROVED:
                return approvedToDoItemVisitor;
            case RELEASED:
                return releasedToDoItemVisitor;
            default:
                return noOpToDoItemVisitor;
        }
    }
}
