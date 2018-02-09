package ch.aaap.harvestclient.domain;

import java.time.Instant;

import ch.aaap.harvestclient.domain.reference.dto.ClientReferenceDto;

public class ClientContact {

    private Long id;

    private ClientReferenceDto clientReferenceDto;
    private String title;
    private String firstName;
    private String lastName;
    private String email;

    private String phoneOffice;
    private String phoneMobile;
    private String fax;

    private Instant createdAt;
    private Instant updatedAt;
}
