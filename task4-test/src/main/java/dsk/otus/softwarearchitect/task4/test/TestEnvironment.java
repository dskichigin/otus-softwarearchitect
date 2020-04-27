package dsk.otus.softwarearchitect.task4.test;

import dsk.otus.softwarearchitect.task4.test.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestEnvironment {
    private List<String> createUserIDs = Collections.synchronizedList(new ArrayList<String>());
    private List<UserEntity> readUsers = Collections.synchronizedList(new ArrayList<UserEntity>());
    private List<UserEntity> updateUsers = Collections.synchronizedList(new ArrayList<UserEntity>());

    public synchronized void addCreateUser(String id) {
        createUserIDs.add(id);
    }
    public synchronized String getCreateUserID() {
        if (createUserIDs.size() > 0) {
            String id = createUserIDs.get(0);
            createUserIDs.remove(id);
            return id;
        }
        return null;
    }
    public synchronized void addReadUser(UserEntity user) {
        readUsers.add(user);
    }
    public synchronized UserEntity getReadUser() {
        if (readUsers.size() > 0) {
            UserEntity user = readUsers.get(0);
            readUsers.remove(user);
            return user;
        }
        return null;
    }
    public synchronized void addUpdateUser(UserEntity user) {
        updateUsers.add(user);
    }
    public synchronized UserEntity getUpdateUser() {
        if (updateUsers.size() > 0) {
            UserEntity user = updateUsers.get(0);
            updateUsers.remove(user);
            return user;
        }
        return null;
    }
}
