package org.launchcode.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity //Store this class in database (persistent class); all fields in class will be stored in database as columns
public class Category {

    @Id //primary key
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }

    @NotNull
    @Size(min= 3, max = 15)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category() { }

    public Category(String name) {
        this.name = name;
    }

    public void setCategory(String name) { this.name = name; }

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Cheese> cheeses = new ArrayList<>();
}
