package dsk.otus.softwarearchitect.task5.app.contoller;

import dsk.otus.softwarearchitect.task5.app.entity.VersionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {

    public AppController() { }

    @GetMapping(path = "/app/info", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List> info(HttpServletRequest request) {
        List<String> list = new ArrayList<>();
        if (request.getHeader("X-UserId") != null) {
            list.add("X-UserId: "+request.getHeader("X-UserId"));
            if (request.getHeader("X-User") != null) list.add("X-User: "+request.getHeader("X-User"));
            if (request.getHeader("X-Email") != null) list.add("X-Email: "+request.getHeader("X-Email"));
            if (request.getHeader("X-First-Name") != null) list.add("X-First-Name: "+request.getHeader("X-First-Name"));
            if (request.getHeader("X-Last-Name") != null) list.add("X-Last-Name: "+request.getHeader("X-Last-Name"));
            if (request.getHeader("X-Phone") != null) list.add("X-Phone: "+request.getHeader("X-Phone"));
        } else {
            list.add("Not authenticated");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/version", produces = "application/json")
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }

}
