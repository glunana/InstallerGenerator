package com.example.installer.service.installer;

import com.example.installer.File;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ProjectFileIterator implements Iterator<File> {

    private final List<File> files;
    private int position = 0;

    public ProjectFileIterator(List<File> files) {
        this.files = files;
    }

    @Override
    public boolean hasNext() {
        return position < files.size();
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return files.get(position++);
    }
}
