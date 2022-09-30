package com.smalaca.taskamanager.service;

import com.smalaca.taskamanager.client.ChatClient;
import com.smalaca.taskamanager.client.MailClient;
import com.smalaca.taskamanager.client.SmsCommunicatorClient;
import com.smalaca.taskamanager.devnull.DevNullDirectory;
import com.smalaca.taskamanager.infrastructure.enums.CommunicatorType;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.strategy.CommunicationStrategy;
import com.smalaca.taskamanager.strategy.DirectCommunicationStrategy;
import com.smalaca.taskamanager.strategy.MailCommunicationStrategy;
import com.smalaca.taskamanager.strategy.NullTypeCommunicationStrategy;
import com.smalaca.taskamanager.strategy.SmsCommunicationStrategy;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    private CommunicationStrategy communicationStrategy;
    private final MailCommunicationStrategy mailCommunicationStrategy;
    private final SmsCommunicationStrategy smsCommunicationStrategy;
    private final DirectCommunicationStrategy directCommunicationStrategy;
    private final NullTypeCommunicationStrategy nullTypeCommunicationStrategy;

    public CommunicationServiceImpl(
            ProjectBacklogService projectBacklogService, DevNullDirectory devNullDirectory, ChatClient chat,
            SmsCommunicatorClient smsCommunicator, MailClient mailClient) {
        this.mailCommunicationStrategy = new MailCommunicationStrategy(mailClient);
        this.smsCommunicationStrategy = new SmsCommunicationStrategy(smsCommunicator, projectBacklogService);
        this.directCommunicationStrategy = new DirectCommunicationStrategy(chat, projectBacklogService);
        this.nullTypeCommunicationStrategy = new NullTypeCommunicationStrategy(devNullDirectory);
    }

    @Override
    @Deprecated
    public void setType(CommunicatorType type) {
        this.communicationStrategy = selectCommunicationStrategy(type);
    }

    @Override
    public void setCommunicationStrategy(CommunicationStrategy communicationStrategy) {
        this.communicationStrategy = communicationStrategy;
    }

    private CommunicationStrategy selectCommunicationStrategy(CommunicatorType type) {
        switch (type) {
            case MAIL:
                return mailCommunicationStrategy;
            case SMS:
                return smsCommunicationStrategy;
            case DIRECT:
                return directCommunicationStrategy;
            case NULL_TYPE:
                return nullTypeCommunicationStrategy;
            default:
                throw new IllegalStateException("Should never happen");
        }
    }

    public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
        communicationStrategy.notify(toDoItem, productOwner);
    }

    public void notify(ToDoItem toDoItem, Owner owner) {
        communicationStrategy.notify(toDoItem, owner);
    }

    public void notify(ToDoItem toDoItem, Watcher watcher) {
        communicationStrategy.notify(toDoItem, watcher);
    }

    public void notify(ToDoItem toDoItem, User user) {
        communicationStrategy.notify(toDoItem, user);
    }

    public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
        communicationStrategy.notify(toDoItem, stakeholder);
    }

    public void notifyTeamsAbout(ToDoItem toDoItem, Project project) {
        for (Team team : project.getTeams()) {
            notify(toDoItem, team);
        }
    }

    public void notify(ToDoItem toDoItem, Team team) {
        for (User user : team.getMembers()) {
            notify(toDoItem, user);
        }
    }
}
