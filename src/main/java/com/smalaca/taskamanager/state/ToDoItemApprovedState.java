package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.events.StoryApprovedEvent;
import com.smalaca.taskamanager.events.TaskApprovedEvent;
import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.registry.EventsRegistry;
import com.smalaca.taskamanager.service.StoryService;

public class ToDoItemApprovedState implements ToDoItemState {

    private final EventsRegistry eventsRegistry;
    private final StoryService storyService;
    private final ToDoItemApprovedStateVisitor toDoItemVisitor;

    public ToDoItemApprovedState(EventsRegistry eventsRegistry, StoryService storyService) {
        this.eventsRegistry = eventsRegistry;
        this.storyService = storyService;
        toDoItemVisitor = new ToDoItemApprovedStateVisitor();
    }

    private interface ToDoItemVisitor {
        default void visit(ToDoItem toDoItem) {}

        default void visit(Epic epic) {}

        default void visit(Story story) {}

        default void visit(Task task) {}

        static void visit(ToDoItemVisitor toDoItemVisitor, ToDoItem toDoItem) {
            if (toDoItem instanceof Task) {
                toDoItemVisitor.visit((Task) toDoItem);
            } else if (toDoItem instanceof Epic) {
                toDoItemVisitor.visit((Epic) toDoItem);
            } else if (toDoItem instanceof Story) {
                toDoItemVisitor.visit((Story) toDoItem);
            } else {
                toDoItemVisitor.visit(toDoItem);
            }
        }
    }

    private class ToDoItemApprovedStateVisitor implements ToDoItemVisitor {

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

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
