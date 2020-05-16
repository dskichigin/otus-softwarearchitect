package dsk.otus.softwarearchitect.task5.controller;

import dsk.otus.softwarearchitect.task5.common.Common;
import dsk.otus.softwarearchitect.task5.core.SessionManager;
import dsk.otus.softwarearchitect.task5.entity.UserEntity;
import dsk.otus.softwarearchitect.task5.entity.VersionEntity;
import dsk.otus.softwarearchitect.task5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionManager sessionManager;

    public UserController() { }

    @PostMapping(path = "/users", produces = "application/json")
    public @ResponseBody
    ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        user.setId(java.util.UUID.randomUUID().toString());
        user.setNew(true);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/users/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<UserEntity> getUser(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        String sessionId = Common.getSessionId(request);
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!sessionManager.existSession(sessionId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isPresent()) {
            if (result.get().getId().equals(sessionManager.getSession(sessionId).getUser().getId()))
                return ResponseEntity.ok(result.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        throw new Exception("Пользователь не найден");
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<UserEntity>> getUsers(HttpServletRequest request) {
        String sessionId = Common.getSessionId(request);
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!sessionManager.existSession(sessionId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Iterable<UserEntity> result = userRepository.findAll();
        List<UserEntity> users = new ArrayList();
        for (UserEntity user : result) {
            users.add(user);
        }
        return ResponseEntity.ok(users);
//        return new ResponseEntity<UserEntity>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(path="/users/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<UserEntity> updateUser(@PathVariable("id") String id, @RequestBody UserEntity user, HttpServletRequest request) {
        String sessionId = Common.getSessionId(request);
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        if (!sessionManager.existSession(sessionId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        if (!id.equals(sessionManager.getSession(sessionId).getUser().getId())) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        UserEntity user_old = null;
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isPresent()) {
            user_old = result.get();
        }
        if (user_old != null) {
            user.setId(id);
            user.setNew(false);
            if (user.getPassword() == null)
                user.setPassword(user_old.getPassword());
            else
                if (user.getPassword().equals(""))
                    user.setPassword(user_old.getPassword());

            userRepository.save(user);
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path="/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        String sessionId = Common.getSessionId(request);
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        if (!sessionManager.existSession(sessionId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        if (!id.equals(sessionManager.getSession(sessionId).getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/version", produces = "application/json")
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("2.0");
        return version;
    }
}
