package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.service.StoryService;

public class ToDoItemInProgressState implements ToDoItemState {

    private final StoryService storyService;

    public ToDoItemInProgressState(StoryService storyService) {
        this.storyService = storyService;
    }

    @Override
    public void process(ToDoItem toDoItem) {
        if (toDoItem instanceof Task) {
            Task task = (Task) toDoItem;
            storyService.updateProgressOf(task.getStory(), task);
        }
    }
}
