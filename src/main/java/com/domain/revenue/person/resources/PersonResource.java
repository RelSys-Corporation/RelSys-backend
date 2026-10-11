package com.domain.revenue.person.resources;

import com.domain.revenue.person.subtype.physicalPerson.PhysicalPerson;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    @POST
    @Transactional
    @RolesAllowed("CREATE-PERSON")
    public Response createPerson() {
        /*
         * precisa trazer se é F, J ou D (digital, sem cpf/cnpj e outros dados)
         * preicsa trazer as roles ['CLIENT', 'SUPPLIER']
         */
        return null;
    }

    @PUT
    @Transactional
    @Path("/{id}/roles")
    @RolesAllowed("UPDATE-USER_ROLE")
    public Response updateRoles(@PathParam("id") Long id) {
        return null;
    }
}
