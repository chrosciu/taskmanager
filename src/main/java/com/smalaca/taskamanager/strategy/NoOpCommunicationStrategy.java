package com.smalaca.taskamanager.strategy;

import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public class NoOpCommunicationStrategy implements CommunicationStrategy {

    @Override
    public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
    }

    @Override
    public void notify(ToDoItem toDoItem, Owner owner) {
    }

    @Override
    public void notify(ToDoItem toDoItem, Watcher watcher) {
    }

    @Override
    public void notify(ToDoItem toDoItem, User user) {
    }

    @Override
    public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
    }
}
