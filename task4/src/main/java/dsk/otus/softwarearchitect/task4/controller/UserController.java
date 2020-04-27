package dsk.otus.softwarearchitect.task4.controller;

import dsk.otus.softwarearchitect.task4.entity.UserEntity;
import dsk.otus.softwarearchitect.task4.entity.VersionEntity;
import dsk.otus.softwarearchitect.task4.repository.UserRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
//@Timed
//@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

//    Counter counter;
    private final MeterRegistry registry;

    // example https://github.com/hellosatish/monitoring/blob/master/person-application/src/main/java/com/satish/monitoring/web/rest/PersonResource.java

    public UserController(MeterRegistry registry) {
        this.registry = registry;
        // registers a gauge to observe the size of the population
//        registry.collectionSize("population", people);
//        counter = Counter
//                .builder("instance")
//                .description("Application Request Count")
//                .tags("method", "endpoint", "http_status")
//                .register(registry);
        // register a counter of questionable usefulness
//        counter = registry.counter("app_request_count", "method", "endpoint", "http_status");

        // register a timer -- though for request timing it is easier to use @Timed
//        findPersonTimer = registry.timer("http_requests", "method", "GET");
    }

    @PostMapping(path = "/users", produces = "application/json")
    @Counted
//    @Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
    public @ResponseBody
    UserEntity createUser(@RequestBody UserEntity user) {
        user.setId(java.util.UUID.randomUUID().toString());
        user.setNew(true);
        userRepository.save(user);

        return user;
    }

    @GetMapping(path = "/users/{id}", produces = "application/json")
    @Counted
    public @ResponseBody UserEntity getUser(@PathVariable("id") String id) throws Exception {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isPresent())
            return result.get();
        throw new Exception("Пользователь не найден");
    }
//    @GetMapping(value = "/users/{id}", produces = "application/json")
////    @Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
//    public ResponseEntity<UserEntity> getUser(@PathVariable("id") String id) {
//        Optional<UserEntity> result = userRepository.findById(id);
//        if (result.isPresent())
//            return ResponseEntity.ok(result.get());
//        return new ResponseEntity<UserEntity>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping(value = "/users", produces = "application/json")
////    @Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
//    public ResponseEntity<List<UserEntity>> getUsers() {
//        Iterable<UserEntity> result = userRepository.findAll();
//        List<UserEntity> users = new ArrayList();
//        for (UserEntity user : result) {
//            users.add(user);
//        }
//        return ResponseEntity.ok(users);
////        return new ResponseEntity<UserEntity>(HttpStatus.NOT_FOUND);
//    }
    @GetMapping(value = "/users", produces = "application/json")
//    @Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
    @Counted
    public List<UserEntity> getUsers() {
        Iterable<UserEntity> result = userRepository.findAll();
        List<UserEntity> users = new ArrayList();
        for (UserEntity user : result) {
            users.add(user);
        }
        return users;
//        return new ResponseEntity<UserEntity>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(path="/users/{id}", consumes = "application/json", produces = "application/json")
//    @Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
    @Counted
    public @ResponseBody UserEntity updateUser(@PathVariable("id") String id, @RequestBody UserEntity user) throws Exception {
        user.setId(id);
        user.setNew(false);
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path="/users/{id}")
//    @Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
    @Counted
    public void deleteUser(@PathVariable("id") String id) throws Exception {
        userRepository.deleteById(id);
    }

    @GetMapping(path = "/version", produces = "application/json")
    @Counted
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("0.1");
        return version;
    }
}
