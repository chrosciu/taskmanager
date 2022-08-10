package com.smalaca.taskamanager.strategy;

import com.smalaca.taskamanager.client.MailClient;
import com.smalaca.taskamanager.model.embedded.EmailAddress;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.model.other.Mail;
import com.smalaca.taskamanager.session.SessionHolder;

public class MailCommunicationStrategy implements CommunicationStrategy {
    private final MailClient mailClient;

    public MailCommunicationStrategy(MailClient mailClient) {
        this.mailClient = mailClient;
    }

    @Override
    public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
        notifyAbout(toDoItem, productOwner.getEmailAddress());
    }

    @Override
    public void notify(ToDoItem toDoItem, Owner owner) {
        notifyAbout(toDoItem, owner.getEmailAddress());
    }

    @Override
    public void notify(ToDoItem toDoItem, Watcher watcher) {
        notifyAbout(toDoItem, watcher.getEmailAddress());
    }

    @Override
    public void notify(ToDoItem toDoItem, User user) {
        notifyAbout(toDoItem, user.getEmailAddress());
    }

    @Override
    public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
        notifyAbout(toDoItem, stakeholder.getEmailAddress());
    }

    private void notifyAbout(ToDoItem toDoItem, EmailAddress emailAddress) {
        User loggedUser = SessionHolder.instance().logged();
        Mail mail = new Mail();
        mail.setFrom(loggedUser.getEmailAddress());
        mail.setTo(emailAddress);
        mail.setTopic("NOTIFICATION ABOUT: " + toDoItem.getId());
        mail.setContent(String.valueOf(toDoItem.getId()));

        mailClient.send(mail);
    }
}
