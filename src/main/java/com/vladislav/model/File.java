package com.vladislav.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;
    @Column(name = "file_name")
    @Expose
    private String fileName;
    @Column(name = "file_path")
    @Expose
    private String filePath;
    @OneToMany(mappedBy = "file")
    private List<Event> eventList;

    public File() {
    }


    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
