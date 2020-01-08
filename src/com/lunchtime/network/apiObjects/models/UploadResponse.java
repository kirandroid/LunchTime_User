/**
 * @Author Kiran Pradhananga
 * Picture Upload Response model
 * */

package com.lunchtime.network.apiObjects.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("public_id")
    @Expose
    private String publicId;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("resource_type")
    @Expose
    private String resourceType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("bytes")
    @Expose
    private Integer bytes;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("placeholder")
    @Expose
    private Boolean placeholder;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("secure_url")
    @Expose
    private String secureUrl;
    @SerializedName("overwritten")
    @Expose
    private Boolean overwritten;
    @SerializedName("original_filename")
    @Expose
    private String originalFilename;
    @SerializedName("eager")
    @Expose
    private List<Eager> eager = null;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Boolean getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(Boolean placeholder) {
        this.placeholder = placeholder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecureUrl() {
        return secureUrl;
    }

    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }

    public Boolean getOverwritten() {
        return overwritten;
    }

    public void setOverwritten(Boolean overwritten) {
        this.overwritten = overwritten;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public List<Eager> getEager() {
        return eager;
    }

    public void setEager(List<Eager> eager) {
        this.eager = eager;
    }

}