package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public interface ToDoItemVisitor {

    void visit(ToDoItem toDoItem);

    void visit(Epic epic);

    void visit(Story story);

    void visit(Task task);
}
