package com.files.entities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileModel {
    private final String _name;
    private final long _size;
    private final String _createdDate;
    private final boolean _isFile;

    public FileModel(File file) throws IOException {
        _name = file.getName();
        _size = file.length();
        _isFile = file.isFile();

        BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        Date lastModified = Date.from(attributes.lastModifiedTime().toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        _createdDate = dateFormat.format(lastModified);
    }

    public String getName() {
        return _name;
    }

    public long getSize() {
        return _size;
    }

    public String getCreatedDate() {
        return _createdDate;
    }

    public boolean getIsFile() {
        return _isFile;
    }
}