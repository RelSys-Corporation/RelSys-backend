package com.domain.crm.person.resources;

import com.domain.crm.person.Person;
import com.domain.crm.person.dtos.DigitalPersonInputDto;
import com.domain.crm.person.dtos.LegalPersonInputDto;
import com.domain.crm.person.dtos.PersonOutputDto;
import com.domain.crm.person.dtos.PhysicalPersonInputDto;
import com.domain.crm.person.mappers.PersonDtoMapper;
import com.domain.crm.person.services.PersonService;
import com.domain.crm.person.subtype.LegalPerson;
import com.domain.crm.person.subtype.PhysicalPerson;
import com.domain.system.message.Message;
import com.infrastructure.annotations.RequiredField;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    @Inject
    PersonService personService;

    @POST
    @Transactional
    @Path("/digital")
    @RolesAllowed("CREATE-PERSON")
    public Response createDigitalPerson(
            @RequiredField(entity = Message.Entity.PERSON, property = Message.Property.DATA)
            @Valid
            DigitalPersonInputDto dto
    ) {
        Person person = personService.createDigitalPerson(
                dto.name(),
                dto.email()
        );

        PersonOutputDto personOutputDto = PersonDtoMapper.toDto(person);

        URI uri = URI.create("/person/" + personOutputDto.id());

        return Response.created(uri)
                .entity(personOutputDto)
                .build();
    }

    @POST
    @Transactional
    @Path("/legal")
    @RolesAllowed("CREATE-PERSON")
    public Response createLegalPerson(
            @RequiredField(entity = Message.Entity.PERSON, property = Message.Property.DATA)
            @Valid
            LegalPersonInputDto dto
    ) {
        LegalPerson person = personService.createLegalPerson(
                dto.name(),
                dto.email(),
                dto.cnpj()
        );

        PersonOutputDto personOutputDto = PersonDtoMapper.toDto(person);

        URI uri = URI.create("/person/" + personOutputDto.id());

        return Response.created(uri)
                .entity(personOutputDto)
                .build();
    }

    @POST
    @Transactional
    @Path("/physical")
    @RolesAllowed("CREATE-PERSON")
    public Response createPhysicalPerson(
            @RequiredField(entity = Message.Entity.PERSON, property = Message.Property.DATA)
            @Valid
            PhysicalPersonInputDto dto
    ) {
        PhysicalPerson person = personService.createPhysicalPerson(
                dto.name(),
                dto.email(),
                dto.cpf()
        );

        PersonOutputDto personOutputDto = PersonDtoMapper.toDto(person);

        URI uri = URI.create("/person/" + personOutputDto.id());

        return Response.created(uri)
                .entity(personOutputDto)
                .build();
    }

    @PUT
    @Transactional
    @Path("/{id}/roles")
    @RolesAllowed("UPDATE-USER_ROLE")
    public Response updateRoles(@PathParam("id") Long id) {
        return null;
    }
}
