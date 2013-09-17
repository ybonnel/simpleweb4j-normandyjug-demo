package com.mycompany.model;


import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;
import org.bson.types.ObjectId;

@Entity("cidres")
public class Cidre {

    @Id
    private ObjectId internalId;

    @Transient
    private String id;

    private String name;

    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Cidre prepareForJson() {
        setId(internalId.toString());
        internalId = null;
        return this;
    }

    public Cidre prepareForDb() {
        if (id != null) {
            internalId = new ObjectId(id);
        }
        return this;
    }
}
