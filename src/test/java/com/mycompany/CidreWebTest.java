package com.mycompany;


import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

public class CidreWebTest extends TestWithMongo {

    @Before
    public void setup() throws IOException {
        goTo("/#/login");
        await().atMost(3, TimeUnit.SECONDS).until("#login").isPresent();
        fill("#login").with("admin");
        fill("#password").with("admin");
        click("#submit");
        await().atMost(3, TimeUnit.SECONDS).until("#login").isNotPresent();

    }

    @Test
    public void should_not_have_cidre() {
        assertThat(find("#cidres")).isNotEmpty();
        assertThat(find("tbody tr")).isEmpty();
    }

    private void insertCidre(String nameOfCidre) {
        click("#addCidre");
        await().atMost(3, TimeUnit.SECONDS).until("#cidres").isNotPresent();
        fill("#name").with(nameOfCidre);
        fill("#price").with("6.4");
        click("#submit");
        await().atMost(3, TimeUnit.SECONDS).until("#cidres").isPresent();
    }

    @Test
    public void can_insert_cidre() {
        insertCidre("name");
        FluentList<FluentWebElement> trInTbody = find("tbody tr");
        assertThat(trInTbody).hasSize(1);
        FluentWebElement oneCidre = trInTbody.get(0);
        assertThat(oneCidre.findFirst("td").getText()).isEqualTo("name");
    }

    @Test
    public void can_update_cidre() {
        insertCidre("name");
        click("a.icon-edit");
        await().atMost(3, TimeUnit.SECONDS).until("#cidres").isNotPresent();
        clear("#name");
        fill("#name").with("newName");
        click("#submit");
        await().atMost(3, TimeUnit.SECONDS).until("#cidres").isPresent();
        FluentList<FluentWebElement> trInTbody = find("tbody tr");
        assertThat(trInTbody).hasSize(1);
        FluentWebElement oneCidre = trInTbody.get(0);
        assertThat(oneCidre.findFirst("td").getText()).isEqualTo("newName");
    }

    @Test
    public void can_delete_cidre() {
        insertCidre("name");
        click("a.icon-remove");
        click("#remove");
        assertThat(find("tbody tr")).isEmpty();
    }
}
