package com.smalaca.taskamanager.strategy;

import com.smalaca.taskamanager.devnull.DevNullDirectory;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;

public class NullTypeCommunicationStrategy implements CommunicationStrategy {

    private final DevNullDirectory devNullDirectory;

    public NullTypeCommunicationStrategy(DevNullDirectory devNullDirectory) {
        this.devNullDirectory = devNullDirectory;
    }

    @Override
    public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
        notifyAbout();
    }

    @Override
    public void notify(ToDoItem toDoItem, Owner owner) {
        notifyAbout();
    }

    @Override
    public void notify(ToDoItem toDoItem, Watcher watcher) {
        notifyAbout();
    }

    @Override
    public void notify(ToDoItem toDoItem, User user) {
        notifyAbout();
    }

    @Override
    public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
        notifyAbout();
    }

    private void notifyAbout() {
        devNullDirectory.forget();
    }
}
