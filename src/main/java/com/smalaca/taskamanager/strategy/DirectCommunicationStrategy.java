package com.smalaca.taskamanager.strategy;

import com.smalaca.taskamanager.client.ChatClient;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.model.other.ChatRoom;
import com.smalaca.taskamanager.service.ProjectBacklogService;

public class DirectCommunicationStrategy implements CommunicationStrategy {
    private static final String SEPARATOR = ".";

    private final ChatClient chat;
    private final ProjectBacklogService projectBacklogService;

    public DirectCommunicationStrategy(ChatClient chat, ProjectBacklogService projectBacklogService) {
        this.chat = chat;
        this.projectBacklogService = projectBacklogService;
    }


    @Override
    public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
        notifyAbout(toDoItem, productOwner.getFirstName() + SEPARATOR + productOwner.getLastName());
    }

    @Override
    public void notify(ToDoItem toDoItem, Owner owner) {
        notifyAbout(toDoItem, owner.getFirstName() + SEPARATOR + owner.getLastName());
    }

    @Override
    public void notify(ToDoItem toDoItem, Watcher watcher) {
        notifyAbout(toDoItem, watcher.getFirstName() + SEPARATOR + watcher.getLastName());
    }

    @Override
    public void notify(ToDoItem toDoItem, User user) {
        notifyAbout(toDoItem, user.getLogin());
    }

    @Override
    public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
        notifyAbout(toDoItem, stakeholder.getFirstName() + SEPARATOR + stakeholder.getLastName());
    }

    private void notifyAbout(ToDoItem toDoItem, String userName) {
        ChatRoom chatRoom = chat.connectWith(userName);
        chatRoom.send(projectBacklogService.linkFor(toDoItem.getId()));
    }
}
