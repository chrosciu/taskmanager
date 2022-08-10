package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public class NoOpToDoItemVisitor implements ToDoItemVisitor {

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
    }
}
