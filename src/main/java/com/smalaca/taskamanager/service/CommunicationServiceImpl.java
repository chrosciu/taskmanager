package com.smalaca.taskamanager.service;

import com.smalaca.taskamanager.client.ChatClient;
import com.smalaca.taskamanager.client.MailClient;
import com.smalaca.taskamanager.client.SmsCommunicatorClient;
import com.smalaca.taskamanager.devnull.DevNullDirectory;
import com.smalaca.taskamanager.infrastructure.enums.CommunicatorType;
import com.smalaca.taskamanager.model.embedded.Owner;
import com.smalaca.taskamanager.model.embedded.PhoneNumber;
import com.smalaca.taskamanager.model.embedded.Stakeholder;
import com.smalaca.taskamanager.model.embedded.Watcher;
import com.smalaca.taskamanager.model.entities.ProductOwner;
import com.smalaca.taskamanager.model.entities.Project;
import com.smalaca.taskamanager.model.entities.Team;
import com.smalaca.taskamanager.model.entities.User;
import com.smalaca.taskamanager.model.interfaces.ToDoItem;
import com.smalaca.taskamanager.model.other.ChatRoom;
import com.smalaca.taskamanager.strategy.CommunicationStrategy;
import com.smalaca.taskamanager.strategy.MailCommunicationStrategy;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    private static final String SEPARATOR = ".";

    private final ProjectBacklogService projectBacklogService;
    private final DevNullDirectory devNullDirectory;
    private final ChatClient chat;
    private final SmsCommunicatorClient smsCommunicator;
    private final MailClient mailClient;
    private CommunicatorType type;
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

    public void setType(CommunicatorType type) {
        this.type = type;
        this.communicationStrategy = selectCommunicationStrategy(type);
    }

    private CommunicationStrategy selectCommunicationStrategy(CommunicatorType type) {
        switch (type) {
            case MAIL:
                return new MailCommunicationStrategy(mailClient);
            default:
                return new LegacyCommunicationStrategy();
        }
    }

    private class LegacyCommunicationStrategy implements CommunicationStrategy {

        @SuppressWarnings("MissingSwitchDefault")
        @Override
        public void notify(ToDoItem toDoItem, ProductOwner productOwner) {
            switch (type) {
                case SMS:
                    notifyAbout(toDoItem, productOwner.getPhoneNumber());
                    break;
                case DIRECT:
                    notifyAbout(toDoItem, productOwner.getFirstName() + SEPARATOR + productOwner.getLastName());
                    break;
                case NULL_TYPE:
                    notifyAbout();
                    break;
            }
        }

        @SuppressWarnings("MissingSwitchDefault")
        @Override
        public void notify(ToDoItem toDoItem, Owner owner) {
            switch (type) {
                case SMS:
                    notifyAbout(toDoItem, owner.getPhoneNumber());
                    break;
                case DIRECT:
                    notifyAbout(toDoItem, owner.getFirstName() + SEPARATOR + owner.getLastName());
                    break;
                case NULL_TYPE:
                    notifyAbout();
                    break;
            }
        }

        @SuppressWarnings("MissingSwitchDefault")
        @Override
        public void notify(ToDoItem toDoItem, Watcher watcher) {
            switch (type) {
                case SMS:
                    notifyAbout(toDoItem, watcher.getPhoneNumber());
                    break;
                case DIRECT:
                    notifyAbout(toDoItem, watcher.getFirstName() + SEPARATOR + watcher.getLastName());
                    break;
                case NULL_TYPE:
                    notifyAbout();
                    break;
            }
        }

        @SuppressWarnings("MissingSwitchDefault")
        @Override
        public void notify(ToDoItem toDoItem, User user) {
            switch (type) {
                case SMS:
                    notifyAbout(toDoItem, user.getPhoneNumber());
                    break;
                case DIRECT:
                    notifyAbout(toDoItem, user.getLogin());
                    break;
                case NULL_TYPE:
                    notifyAbout();
                    break;
            }
        }

        @SuppressWarnings("MissingSwitchDefault")
        @Override
        public void notify(ToDoItem toDoItem, Stakeholder stakeholder) {
            switch (type) {
                case DIRECT:
                    notifyAbout(toDoItem, stakeholder.getFirstName() + SEPARATOR + stakeholder.getLastName());
                    break;
                case SMS:
                    notifyAbout(toDoItem, stakeholder.getPhoneNumber());
                    break;
                case NULL_TYPE:
                    notifyAbout();
                    break;
            }
        }

        private void notifyAbout() {
            devNullDirectory.forget();
        }

        private void notifyAbout(ToDoItem toDoItem, String userName) {
            ChatRoom chatRoom = chat.connectWith(userName);
            chatRoom.send(projectBacklogService.linkFor(toDoItem.getId()));
        }

        private void notifyAbout(ToDoItem toDoItem, PhoneNumber phoneNumber) {
            smsCommunicator.textTo(phoneNumber, projectBacklogService.linkFor(toDoItem.getId()));
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
