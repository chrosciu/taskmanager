package com.smalaca.taskamanager.processor;

import com.smalaca.taskamanager.events.StoryApprovedEvent;
import com.smalaca.taskamanager.events.StoryDoneEvent;
import com.smalaca.taskamanager.events.TaskApprovedEvent;
import com.smalaca.taskamanager.events.ToDoItemReleasedEvent;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
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
import org.springframework.stereotype.Component;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.DONE;

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
        switch (toDoItem.getStatus()) {
            case DEFINED:
                new ToDoItemDefinedState(projectBacklogService, communicationService, sprintBacklogService,
                    eventsRegistry).process(toDoItem);
                break;

            case IN_PROGRESS:
                new ToDoItemInProgressState(storyService).process(toDoItem);
                break;

            case DONE:
                new ToDoItemDoneState(eventsRegistry, storyService).process(toDoItem);
                break;

            case APPROVED:
                new ToDoItemApprovedState(eventsRegistry, storyService).process(toDoItem);
                break;

            case RELEASED:
                new ToDoItemReleasedState(eventsRegistry).process(toDoItem);
                break;

            default:
                break;
        }
    }
}
