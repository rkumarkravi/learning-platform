package com.rk.olms.dtos.responses;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PartialContentResourceResDto extends FileSystemResource {

    private final long start;
    private final long length;

    public PartialContentResourceResDto(File file, long start, long length) {
        super(file);
        this.start = start;
        this.length = length;
    }

    @Override
    public long contentLength() throws IOException {
        return length;
    }

    @Override
    public String getFilename() {
        return Objects.requireNonNull(super.getFilename());
    }

    @Override
    public Resource createRelative(String relativePath) {
        return null; // Not needed for streaming
    }
}