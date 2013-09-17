/*
 * Copyright 2013- Yan Bonnel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mycompany.services;

import com.mycompany.model.Cidre;
import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;

import java.util.Collection;

public class JsonpRoute extends Route<Void, Collection<Cidre>> implements FreeRoute {

    private CidreRessource cidreRessource;

    public JsonpRoute(String routePath, CidreRessource cidreRessource) {
        super(routePath, Void.class);
        this.cidreRessource = cidreRessource;
    }

    @Override
    public Response<Collection<Cidre>> handle(Void param, RouteParameters routeParams) throws HttpErrorException {
        return new Response<>(cidreRessource.getAll()) ;
    }
}
