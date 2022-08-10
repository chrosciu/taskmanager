package com.smalaca.taskamanager.strategy;

import com.smalaca.taskamanager.client.SmsCommunicatorClient;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.service.ProjectBacklogService;

public class SmsCommunicationStrategy implements CommunicationStrategy {

    private final SmsCommunicatorClient smsCommunicator;
    private final ProjectBacklogService projectBacklogService;

    public SmsCommunicationStrategy(SmsCommunicatorClient smsCommunicator, ProjectBacklogService projectBacklogService) {
        this.smsCommunicator = smsCommunicator;
        this.projectBacklogService = projectBacklogService;
    }

    @Override
    public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
        notifyAbout(toDoItem, productOwner.getPhoneNumber());
    }

    @Override
    public void notify(ToDoItem toDoItem, Owner owner) {
        notifyAbout(toDoItem, owner.getPhoneNumber());
    }

    @Override
    public void notify(ToDoItem toDoItem, Watcher watcher) {
        notifyAbout(toDoItem, watcher.getPhoneNumber());
    }

    @Override
    public void notify(ToDoItem toDoItem, User user) {
        notifyAbout(toDoItem, user.getPhoneNumber());
    }

    @Override
    public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
        notifyAbout(toDoItem, stakeholder.getPhoneNumber());
    }

    private void notifyAbout(ToDoItem toDoItem, PhoneNumber phoneNumber) {
        smsCommunicator.textTo(phoneNumber, projectBacklogService.linkFor(toDoItem.getId()));
    }
}
