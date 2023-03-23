package edu.miu.badge.controllers;

import edu.miu.badge.dto.RequestMembershipDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.services.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/memberships")
public class MembershipController {
    @Autowired
    private MembershipService membershipService;
    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(membershipService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{membershipId}")
    public ResponseEntity<?> getMembershipById(@PathVariable int membershipId) throws ResourceNotFoundException{
        return new ResponseEntity<>(membershipService.getMembershipById(membershipId), HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody RequestMembershipDTO membershipDTO) throws ResourceNotFoundException{
        return new ResponseEntity<>(membershipService.create(membershipDTO), HttpStatus.OK);
    }
    @PutMapping("/{membershipId}")
    public ResponseEntity<?> update(@PathVariable int membershipId, @RequestBody RequestMembershipDTO membershipDTO) throws ResourceNotFoundException{
        return new ResponseEntity<>(membershipService.update(membershipId, membershipDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<?> delete(@PathVariable int membershipId) throws ResourceNotFoundException{
        return new ResponseEntity<>(membershipService.delete(membershipId), HttpStatus.OK);
    }

}
