package dsk.otus.softwarearchitect.task5.entity;

public class SessionEntity {

    private String sessionId;
    private UserEntity user;

    public SessionEntity(String sessionId, UserEntity user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
