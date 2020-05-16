package dsk.otus.softwarearchitect.task5.core;

import dsk.otus.softwarearchitect.task5.entity.SessionEntity;
import dsk.otus.softwarearchitect.task5.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionManager {
    private List<SessionEntity> sessions = new ArrayList<>();

    public String createSession(UserEntity user) {
        String session_id = java.util.UUID.randomUUID().toString();
        sessions.add(new SessionEntity(session_id, user));
        return session_id;
    }
    public boolean existSession(String session_id) {
        for (SessionEntity ses : sessions)
            if (ses.getSessionId().equals(session_id)) return true;
        return false;
    }
    public SessionEntity getSession(String session_id) {
        for (SessionEntity ses : sessions)
            if (ses.getSessionId().equals(session_id)) return ses;
        return null;
    }
    public void removeSession(String session_id) {
        if (session_id == null) return;
        SessionEntity session = null;
        for (SessionEntity ses : sessions)
            if (ses.getSessionId().equals(session_id)) {
                session = ses;
                break;
            }

        sessions.remove(session);
    }
}
