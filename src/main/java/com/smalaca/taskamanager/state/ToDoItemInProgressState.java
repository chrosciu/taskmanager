package com.smalaca.taskamanager.state;

import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.service.StoryService;
import com.smalaca.taskamanager.visitor.ToDoItemInProgressStateVisitor;
import com.smalaca.taskamanager.visitor.ToDoItemVisitor;

public class ToDoItemInProgressState implements ToDoItemState {
    private final ToDoItemInProgressStateVisitor toDoItemVisitor;

    public ToDoItemInProgressState(StoryService storyService) {
        this.toDoItemVisitor = new ToDoItemInProgressStateVisitor(storyService);
    }

    @Override
    public void process(ToDoItem toDoItem) {
        ToDoItemVisitor.visit(toDoItemVisitor, toDoItem);
    }
}
