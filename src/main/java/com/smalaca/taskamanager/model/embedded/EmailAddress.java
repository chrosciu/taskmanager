package com.smalaca.taskamanager.model.embedded;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class EmailAddress {
    private String emailAddress;

    @Deprecated
    public EmailAddress() {
    }

    @Deprecated
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
