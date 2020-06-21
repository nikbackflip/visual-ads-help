package com.backflip.vadsh.service;

public interface FileStorage {

    String save(String content);

    byte[] getContent(String contentId);

    void clearStorage();
}
