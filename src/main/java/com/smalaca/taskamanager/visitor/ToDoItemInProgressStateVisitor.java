package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.service.StoryService;

public class ToDoItemInProgressStateVisitor implements ToDoItemVisitor {
    private final StoryService storyService;

    public ToDoItemInProgressStateVisitor(StoryService storyService) {
        this.storyService = storyService;
    }

    @Override
    public void visit(Task task) {
        storyService.updateProgressOf(task.getStory(), task);
    }
}
