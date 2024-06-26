package hust.server.app.endpoint.rest;

import hust.server.app.service.ResponseFactory;
import hust.server.domain.products.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping("/admin/v1/branches")
    public ResponseEntity<?> getBranches(@RequestParam String userId){
        return ResponseFactory.response(branchService.getBranches(userId));
    }
}
