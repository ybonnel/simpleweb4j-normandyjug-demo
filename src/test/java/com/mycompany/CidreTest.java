package com.mycompany;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.model.Cidre;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CidreTest extends TestWithMongo {

    private static String QUERY_PARAM = "?login=admin&password=admin";

    @Test
    public void should_return_no_cidre() {
        List<Cidre> cidres = new Gson().fromJson(HttpRequest.get(defaultUrl() + "/cidre" + QUERY_PARAM).body(), new TypeToken<List<Cidre>>(){}.getType());
        assertTrue(cidres.isEmpty());
    }

    private String insertCidre() {
        Cidre cidre = new Cidre();
        cidre.setName("name");
        cidre.setPrice(6.4);
        Gson gson = new Gson();
        HttpRequest send = HttpRequest.post(defaultUrl() + "/cidre" + QUERY_PARAM).send(gson.toJson(cidre));
        assertEquals(send.body(), 201, send.code());

        List<Cidre> cidres = gson.fromJson(HttpRequest.get(defaultUrl() + "/cidre" + QUERY_PARAM).body(), new TypeToken<List<Cidre>>(){}.getType());
        assertEquals(1, cidres.size());
        assertEquals("name", cidres.get(0).getName());
        assertEquals(6.4, cidres.get(0).getPrice(), 0.001);
        return cidres.get(0).getId();
    }

    @Test
    public void should_create_and_get_by_id() {
        String id = insertCidre();

        Cidre newCidre = new Gson().fromJson(HttpRequest.get(defaultUrl() + "/cidre/" + id + QUERY_PARAM).body(), Cidre.class);
        assertNotNull(newCidre);
        assertEquals(id, newCidre.getId());
        assertEquals("name", newCidre.getName());
    }

    @Test
    public void should_update() {
        String id = insertCidre();
        Gson gson = new Gson();

        Cidre newCidre = new Cidre();
        newCidre.setName("newName");

        assertEquals(204, HttpRequest.put(defaultUrl() + "/cidre/" + id + QUERY_PARAM).send(gson.toJson(newCidre)).code());

        List<Cidre> cidres = gson.fromJson(HttpRequest.get(defaultUrl() + "/cidre" + QUERY_PARAM).body(), new TypeToken<List<Cidre>>(){}.getType());
        assertEquals(1, cidres.size());
        assertEquals("newName", cidres.get(0).getName());
    }

    @Test
    public void should_delete() {
        String id = insertCidre();
        Gson gson = new Gson();

        assertEquals(204, HttpRequest.delete(defaultUrl() + "/cidre/" + id + QUERY_PARAM).code());
        List<Cidre> cidres = gson.fromJson(HttpRequest.get(defaultUrl() + "/cidre" + QUERY_PARAM).body(), new TypeToken<List<Cidre>>(){}.getType());
        assertTrue(cidres.isEmpty());
    }
}
