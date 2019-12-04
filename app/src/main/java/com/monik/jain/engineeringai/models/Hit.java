
package com.monik.jain.engineeringai.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Hit {

    @Expose
    private com.monik.jain.engineeringai.models._highlightResult _highlightResult;
    @Expose
    private List<String> _tags;
    @Expose
    private String author;
    @SerializedName("comment_text")
    private Object commentText;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("created_at_i")
    private Long createdAtI;
    @SerializedName("num_comments")
    private Long numComments;
    @Expose
    private String objectID;
    @SerializedName("parent_id")
    private Object parentId;
    @Expose
    private Long points;
    @SerializedName("story_id")
    private Object storyId;
    @SerializedName("story_text")
    private Object storyText;
    @SerializedName("story_title")
    private Object storyTitle;
    @SerializedName("story_url")
    private Object storyUrl;
    @Expose
    private String title;
    @Expose
    private String url;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    private boolean isEnabled;

    public com.monik.jain.engineeringai.models._highlightResult get_highlightResult() {
        return _highlightResult;
    }

    public void set_highlightResult(com.monik.jain.engineeringai.models._highlightResult _highlightResult) {
        this._highlightResult = _highlightResult;
    }

    public List<String> get_tags() {
        return _tags;
    }

    public void set_tags(List<String> _tags) {
        this._tags = _tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Object getCommentText() {
        return commentText;
    }

    public void setCommentText(Object commentText) {
        this.commentText = commentText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedAtI() {
        return createdAtI;
    }

    public void setCreatedAtI(Long createdAtI) {
        this.createdAtI = createdAtI;
    }

    public Long getNumComments() {
        return numComments;
    }

    public void setNumComments(Long numComments) {
        this.numComments = numComments;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Object getStoryId() {
        return storyId;
    }

    public void setStoryId(Object storyId) {
        this.storyId = storyId;
    }

    public Object getStoryText() {
        return storyText;
    }

    public void setStoryText(Object storyText) {
        this.storyText = storyText;
    }

    public Object getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(Object storyTitle) {
        this.storyTitle = storyTitle;
    }

    public Object getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(Object storyUrl) {
        this.storyUrl = storyUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
