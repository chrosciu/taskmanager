package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.events.StoryApprovedEvent;
import com.smalaca.taskamanager.events.TaskApprovedEvent;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;

public class ToDoItemApprovedStateVisitor implements ToDoItemVisitor {
    private final EventsRegistry eventsRegistry;
    private final StoryService storyService;

    public ToDoItemApprovedStateVisitor(EventsRegistry eventsRegistry, StoryService storyService) {
        this.eventsRegistry = eventsRegistry;
        this.storyService = storyService;
    }

    @Override
    public void visit(Story story) {
        StoryApprovedEvent event = new StoryApprovedEvent();
        event.setStoryId(story.getId());
        eventsRegistry.publish(event);
    }

    @Override
    public void visit(Task task) {
        if (task.isSubtask()) {
            TaskApprovedEvent event = new TaskApprovedEvent();
            event.setTaskId(task.getId());
            eventsRegistry.publish(event);
        } else {
            storyService.attachPartialApprovalFor(task.getStory().getId(), task.getId());
        }
    }
}
