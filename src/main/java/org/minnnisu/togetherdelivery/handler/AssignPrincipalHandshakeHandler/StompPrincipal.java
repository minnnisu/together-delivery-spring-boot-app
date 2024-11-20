package org.minnnisu.togetherdelivery.handler.AssignPrincipalHandshakeHandler;

import lombok.Getter;

import java.security.Principal;
import java.util.UUID;

@Getter
public class StompPrincipal implements Principal {
    final String name;

    public StompPrincipal() {
        this.name = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }
}
