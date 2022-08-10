package com.smalaca.taskamanager.visitor;

import static com.smalaca.taskamanager.model.enums.ToDoItemStatus.DONE;

import com.smalaca.taskamanager.events.StoryDoneEvent;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;

public class DoneToDoItemVisitor implements ToDoItemVisitor {

    private final EventsRegistry eventsRegistry;
    private final StoryService storyService;

    public DoneToDoItemVisitor(EventsRegistry eventsRegistry, StoryService storyService) {
        this.eventsRegistry = eventsRegistry;
        this.storyService = storyService;
    }

    @Override
    public void visit(ToDoItem toDoItem) {

    }

    @Override
    public void visit(Epic epic) {

    }

    @Override
    public void visit(Story story) {
        StoryDoneEvent event = new StoryDoneEvent();
        event.setStoryId(story.getId());
        eventsRegistry.publish(event);
    }

    @Override
    public void visit(Task task) {
        Story story = task.getStory();
        storyService.updateProgressOf(task.getStory(), task);
        if (DONE.equals(story.getStatus())) {
            StoryDoneEvent event = new StoryDoneEvent();
            event.setStoryId(story.getId());
            eventsRegistry.publish(event);
        }
    }
}
