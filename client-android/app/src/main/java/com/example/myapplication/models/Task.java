package com.example.myapplication.models;

import java.util.List;

public class Task {
    private int id;
    private String title;
    private String description;
    private String status;
    private int ownerId;
    private Integer delegatedTo;
    private List<Integer> collaborators;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Integer getDelegatedTo() {
        return delegatedTo;
    }

    public List<Integer> getCollaborators() {
        return collaborators;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDelegatedTo(Integer delegatedTo) {
        this.delegatedTo = delegatedTo;
    }

    public void setCollaborators(List<Integer> collaborators) {
        this.collaborators = collaborators;
    }
}
