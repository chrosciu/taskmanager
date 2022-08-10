package com.smalaca.taskamanager.visitor;

import com.smalaca.taskamanager.model.entities.Epic;
import com.smalaca.taskamanager.model.entities.Story;
import com.smalaca.taskamanager.model.entities.Task;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.processor.ToDoItemProcessor;

public interface ToDoItemVisitor {

    void visit(ToDoItem toDoItem);

    void visit(Epic epic);

    void visit(Story story);

    void visit(Task task);

    static void visit(ToDoItemVisitor toDoItemVisitor, ToDoItem toDoItem) {
        if (toDoItem instanceof Task) {
            toDoItemVisitor.visit((Task)toDoItem);
        } else if (toDoItem instanceof Epic) {
            toDoItemVisitor.visit((Epic)toDoItem);
        } else if (toDoItem instanceof Story) {
            toDoItemVisitor.visit((Story)toDoItem);
        } else {
            toDoItemVisitor.visit(toDoItem);
        }
    }
}
