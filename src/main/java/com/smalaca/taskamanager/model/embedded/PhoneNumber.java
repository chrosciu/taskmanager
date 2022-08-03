package com.smalaca.taskamanager.model.embedded;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class PhoneNumber {
    @Column(name = "phone_prefix")
    private String prefix;

    @Column(name = "phone_number")
    private String number;

    @Deprecated
    public PhoneNumber() {}

    @Deprecated
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Deprecated
    public void setNumber(String number) {
        this.number = number;
    }

}
