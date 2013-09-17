package com.mycompany.services;


import com.mycompany.model.Cidre;
import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.resource.RestResource;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CidreRessource extends RestResource<Cidre> {

    public CidreRessource(String route) {
        super(route, Cidre.class);
    }

    @Override
    public Cidre getById(String id) throws HttpErrorException {
        return MongoService.getDatastore().get(Cidre.class, new ObjectId(id)).prepareForJson();
    }

    @Override
    public Collection<Cidre> getAll() throws HttpErrorException {
        List<Cidre> cidres = new ArrayList<>();
        for (Cidre cidre : MongoService.getDatastore().find(Cidre.class).asList()) {
            cidres.add(cidre.prepareForJson());
        }
        return cidres;
    }

    @Override
    public void update(String id, Cidre resource) throws HttpErrorException {
        resource.setId(id);
        MongoService.getDatastore().save(resource.prepareForDb());
    }

    @Override
    public Cidre create(Cidre resource) throws HttpErrorException {
        MongoService.getDatastore().save(resource.prepareForDb());
        return resource;
    }

    @Override
    public void delete(String id) throws HttpErrorException {
        MongoService.getDatastore().delete(Cidre.class, new ObjectId(id));
    }
}
