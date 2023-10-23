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
import com.smalaca.taskamanager.strategy.NoOpCommunicationStrategy;
import com.smalaca.taskamanager.strategy.NullTypeCommunicationStrategy;
import com.smalaca.taskamanager.strategy.SmsCommunicationStrategy;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    private final ProjectBacklogService projectBacklogService;
    private final DevNullDirectory devNullDirectory;
    private final ChatClient chat;
    private final SmsCommunicatorClient smsCommunicator;
    private final MailClient mailClient;
    private CommunicationStrategy communicationStrategy;

    public CommunicationServiceImpl(
            ProjectBacklogService projectBacklogService, DevNullDirectory devNullDirectory, ChatClient chat,
            SmsCommunicatorClient smsCommunicator, MailClient mailClient) {
        this.projectBacklogService = projectBacklogService;
        this.devNullDirectory = devNullDirectory;
        this.chat = chat;
        this.smsCommunicator = smsCommunicator;
        this.mailClient = mailClient;
    }

    @Deprecated
    public void setType(CommunicatorType type) {
        this.communicationStrategy = selectCommunicationStrategy(type);
    }

    private CommunicationStrategy selectCommunicationStrategy(CommunicatorType type) {
        switch (type) {
            case MAIL:
                return new MailCommunicationStrategy(mailClient);
            case SMS:
                return new SmsCommunicationStrategy(smsCommunicator, projectBacklogService);
            case DIRECT:
                return new DirectCommunicationStrategy(chat, projectBacklogService);
            case NULL_TYPE:
                return new NullTypeCommunicationStrategy(devNullDirectory);
        }
        return new NoOpCommunicationStrategy();
    }

    public void setCommunicationStrategy(CommunicationStrategy communicationStrategy) {
        this.communicationStrategy = communicationStrategy;
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
