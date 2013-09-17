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

import com.mycompany.model.User;
import fr.ybonnel.simpleweb4j.exception.HttpErrorException;
import fr.ybonnel.simpleweb4j.handlers.Response;
import fr.ybonnel.simpleweb4j.handlers.Route;
import fr.ybonnel.simpleweb4j.handlers.RouteParameters;
import fr.ybonnel.simpleweb4j.handlers.filter.AbstractFilter;

public class UserService {

    private String goodLogin;
    private String goodPassword;

    public UserService(String goodLogin, String goodPassword) {
        this.goodLogin = goodLogin;
        this.goodPassword = goodPassword;
    }

    protected boolean isCorrectUser(User user) {
        return goodLogin.equals(user.getLogin())
                && goodPassword.equals(user.getPassword());
    }

    public AbstractFilter getSecurityFilter() {
        return new AbstractFilter() {
            @Override
            public void handle(Route route, RouteParameters routeParams) throws HttpErrorException {
                if (!(route instanceof FreeRoute) &&!isCorrectUser(
                        new User(
                                routeParams.getParam("login"),
                                routeParams.getParam("password")
                        )
                )) {
                    throw new HttpErrorException(401);
                }
            }
        };
    }

    public class LoginRoute extends Route<User, Void> implements FreeRoute {

        public LoginRoute() {
            super("/login", User.class);
        }

        @Override
        public Response<Void> handle(User param, RouteParameters routeParams) throws HttpErrorException {
            if (!isCorrectUser(param)) {
                throw new HttpErrorException(401);
            }
            return new Response<>(null);
        }
    }

    public Route<User, Void> getLoginRoute() {
        return new LoginRoute();
    }
}
