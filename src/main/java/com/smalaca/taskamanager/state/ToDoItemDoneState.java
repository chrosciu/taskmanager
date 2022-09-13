package com.smalaca.taskamanager.state;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.DONE;

import com.smalaca.taskamanager.events.StoryDoneEvent;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;

public class ToDoItemDoneState implements ToDoItemState {

    private final EventsRegistry eventsRegistry;
    private final StoryService storyService;

    public ToDoItemDoneState(EventsRegistry eventsRegistry, StoryService storyService) {
        this.eventsRegistry = eventsRegistry;
        this.storyService = storyService;
    }

    @Override
    public void process(ToDoItem toDoItem) {
        if (toDoItem instanceof Task) {
            Task task = (Task) toDoItem;
            Story story = task.getStory();
            storyService.updateProgressOf(task.getStory(), task);
            if (DONE.equals(story.getStatus())) {
                StoryDoneEvent event = new StoryDoneEvent();
                event.setStoryId(story.getId());
                eventsRegistry.publish(event);
            }
        } else if (toDoItem instanceof Story) {
            Story story = (Story) toDoItem;
            StoryDoneEvent event = new StoryDoneEvent();
            event.setStoryId(story.getId());
            eventsRegistry.publish(event);
        }
    }
}
