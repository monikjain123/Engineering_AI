
package com.monik.jain.engineeringai.models;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class _highlightResult {

    @Expose
    private Author author;
    @Expose
    private Title title;
    @Expose
    private Url url;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

}
