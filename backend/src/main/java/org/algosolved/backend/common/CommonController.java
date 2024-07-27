package org.algosolved.backend.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/health/ping")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("pong");
    }
}
