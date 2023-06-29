package com.smalaca.taskamanager.model.embedded;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Codename {
    private final String shortName;
    private final String fullName;

    @Deprecated
    private Codename() {
        this(null, null);
    }
}
