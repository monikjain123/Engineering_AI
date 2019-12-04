
package com.monik.jain.engineeringai.models;

import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Author {

    @Expose
    private String matchLevel;
    @Expose
    private List<Object> matchedWords;
    @Expose
    private String value;

    public String getMatchLevel() {
        return matchLevel;
    }

    public void setMatchLevel(String matchLevel) {
        this.matchLevel = matchLevel;
    }

    public List<Object> getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(List<Object> matchedWords) {
        this.matchedWords = matchedWords;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
