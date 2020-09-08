package com.example.restdocs.crud;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class CrudInput {

    @NotNull
    private long id;

    @NotBlank
    private String title;

    private String body;

    private List<URI> tagUris;

    @JsonCreator
    public CrudInput(@JsonProperty("id") long id, @JsonProperty("title") String title,
                     @JsonProperty("body") String body, @JsonProperty("tags") List<URI> tagUris) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tagUris = tagUris == null ? Collections.<URI>emptyList() : tagUris;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTagUris(List<URI> tagUris) {
        this.tagUris = tagUris;
    }

    @JsonProperty("tags")
    public List<URI> getTagUris() {
        return this.tagUris;
    }

}