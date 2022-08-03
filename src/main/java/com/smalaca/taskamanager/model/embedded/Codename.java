package com.smalaca.taskamanager.model.embedded;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Codename {
    private final String shortName;
    private final String fullName;

    private Codename() {
        this(null, null);
    }

    public Codename(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Codename codename = (Codename) o;

        return new EqualsBuilder()
                .append(shortName, codename.shortName)
                .append(fullName, codename.fullName)
                .isEquals();
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(shortName)
                .append(fullName)
                .toHashCode();
    }
}
