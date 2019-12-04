
package com.monik.jain.engineeringai.models;

import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Posts {

    @Expose
    private Boolean exhaustiveNbHits;
    @Expose
    private List<Hit> hits;
    @Expose
    private Long hitsPerPage;
    @Expose
    private Long nbHits;
    @Expose
    private Long nbPages;
    @Expose
    private Long page;
    @Expose
    private String params;
    @Expose
    private Long processingTimeMS;
    @Expose
    private String query;

    public Boolean getExhaustiveNbHits() {
        return exhaustiveNbHits;
    }

    public void setExhaustiveNbHits(Boolean exhaustiveNbHits) {
        this.exhaustiveNbHits = exhaustiveNbHits;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public Long getHitsPerPage() {
        return hitsPerPage;
    }

    public void setHitsPerPage(Long hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public Long getNbHits() {
        return nbHits;
    }

    public void setNbHits(Long nbHits) {
        this.nbHits = nbHits;
    }

    public Long getNbPages() {
        return nbPages;
    }

    public void setNbPages(Long nbPages) {
        this.nbPages = nbPages;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getProcessingTimeMS() {
        return processingTimeMS;
    }

    public void setProcessingTimeMS(Long processingTimeMS) {
        this.processingTimeMS = processingTimeMS;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
