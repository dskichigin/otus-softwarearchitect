package dsk.otus.softwarearchitect.task3.controller;

import dsk.otus.softwarearchitect.task3.entity.UserEntity;
import dsk.otus.softwarearchitect.task3.entity.VersionEntity;
import dsk.otus.softwarearchitect.task3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/users", produces = "application/json")
    public @ResponseBody UserEntity createUser(@RequestBody UserEntity user) {
        user.setId(java.util.UUID.randomUUID().toString());
        user.setNew(true);
        userRepository.save(user);

        return user;
    }

    @GetMapping(path = "/users/{id}", produces = "application/json")
    public @ResponseBody UserEntity getUser(@PathVariable("id") String id) throws Exception {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isPresent())
            return result.get();
        throw new Exception("Пользователь не найден");
    }

    @PutMapping(path="/users/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody UserEntity updateUser(@PathVariable("id") String id, @RequestBody UserEntity user) throws Exception {
        user.setId(id);
        user.setNew(false);
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path="/users/{id}")
    public void deleteUser(@PathVariable("id") String id) throws Exception {
        userRepository.deleteById(id);
    }

    @GetMapping(path = "/version", produces = "application/json")
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("0.1");
        return version;
    }
}
