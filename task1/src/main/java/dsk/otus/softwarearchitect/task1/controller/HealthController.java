package dsk.otus.softwarearchitect.task1.controller;

import dsk.otus.softwarearchitect.task1.HealthStatusEntity;
import dsk.otus.softwarearchitect.task1.VersionEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public HealthStatusEntity getHealth() {
        HealthStatusEntity healthStatus = new HealthStatusEntity();
        healthStatus.setStatus("OK");
        return healthStatus;
    }
    @GetMapping("/version")
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("0.1");
        return version;
    }
}
