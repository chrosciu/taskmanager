package com.smalaca.taskamanager.model.embedded;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;

@Embeddable
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class UserName {
    private final String firstName;
    private final String lastName;

    @Deprecated
    private UserName() {
        this(null, null);
    }
}
