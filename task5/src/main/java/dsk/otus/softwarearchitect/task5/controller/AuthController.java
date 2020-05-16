package dsk.otus.softwarearchitect.task5.controller;

import dsk.otus.softwarearchitect.task5.common.Common;
import dsk.otus.softwarearchitect.task5.core.SessionManager;
import dsk.otus.softwarearchitect.task5.entity.*;
import dsk.otus.softwarearchitect.task5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserController userController;
    @Autowired
    SessionManager sessionManager;

    @GetMapping(path = "/auth/signin", produces = "application/json")
    public @ResponseBody
    ResponseEntity<MessageEntity> signin() {
        return ResponseEntity.ok().body(new MessageEntity("Please go to login and provide Login/Password"));
    }

    @GetMapping(path = "/auth/auth", produces = "application/json")
    public @ResponseBody
    ResponseEntity auth(HttpServletRequest request, HttpServletResponse response) {

        String sessionId = Common.getSessionId(request);
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!sessionManager.existSession(sessionId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        SessionEntity session = sessionManager.getSession(sessionId);
        UserEntity user = session.getUser();
        response.setHeader("X-UserId", user.getId());
        response.setHeader("X-User", user.getUsername());
        response.setHeader("X-Email", user.getEmail());
        response.setHeader("X-First-Name", user.getFirstname());
        response.setHeader("X-Last-Name", user.getLastname());
        response.setHeader("X-Phone", user.getPhone());

        return ResponseEntity.ok().build();
    }

    /**
     * регистрация пользователя
     * @param user
     * @return
     */
    @PostMapping(path = "/auth/register", produces = "application/json")
    public @ResponseBody
    ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user) {
        return userController.createUser(user);
    }

    @PostMapping(path = "/auth/login", produces = "application/json")
    public @ResponseBody
    ResponseEntity<StatusEntity> login(@RequestBody LoginEntity login, HttpServletResponse response) throws Exception {
        if (login == null) throw new Exception("Данные для аутентификации не указаны");
        if (login.getLogin() == null) throw new Exception("Не указан логин");
        if (login.getPassword() == null) throw new Exception("Не указан пароль");

        List<UserEntity> users = userRepository.findByUserName(login.getLogin());
        for (UserEntity user : users) {
            if (user.getPassword() == null) continue;
            if (user.getPassword().equals(login.getPassword())) {
                String session_id = sessionManager.createSession(user);

                Cookie cookie = new Cookie("session_id",session_id);

                // expires in 7 days
//                cookie.setMaxAge(7 * 24 * 60 * 60);

                // optional properties
//                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);

                return ResponseEntity.ok(new StatusEntity("ok"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(path = "/auth/logout", produces = "application/json")
    public @ResponseBody
    ResponseEntity<StatusEntity> logout(HttpServletRequest request) {
        String sessionId = Common.getSessionId(request);
        if (sessionId == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!sessionManager.existSession(sessionId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        sessionManager.removeSession(Common.getSessionId(request));
        return ResponseEntity.ok(new StatusEntity("ok"));
    }
}
