package com.myprac.advanced.android.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "retrophototable")
public class RetroPhoto {

    @ColumnInfo
    @PrimaryKey
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "album_id")
    @SerializedName("albumId")
    private Integer albumId;

    @ColumnInfo
    @SerializedName("title")
    private String title;

    @ColumnInfo
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "thumbnail_url")
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    public RetroPhoto(Integer albumId, Integer id, String title, String url, String thumbnailUrl) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
