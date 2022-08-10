package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.service.StoryService;

public class InProgressToDoItemVisitor implements ToDoItemVisitor {

    private final StoryService storyService;

    public InProgressToDoItemVisitor(StoryService storyService) {
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
    }

    @Override
    public void visit(Task task) {
        storyService.updateProgressOf(task.getStory(), task);
    }
}
