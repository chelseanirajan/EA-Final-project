package edu.miu.badge.controllers;

import edu.miu.badge.domains.HttpResponse;
import edu.miu.badge.dto.ResponseBadgeDTO;
import edu.miu.badge.dto.RequestMemberDTO;
import edu.miu.badge.dto.ResponseMemberDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.services.MemberService;
import edu.miu.badge.services.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/members", produces = "application/json")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MembershipService membershipService;

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody RequestMemberDTO requestMemberDTO) throws ResourceNotFoundException{
        return new ResponseEntity<ResponseMemberDTO>(memberService.insertNewMember(requestMemberDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable int id) throws ResourceNotFoundException{
        return new ResponseEntity<ResponseMemberDTO>(memberService.getMemberById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemberById(@PathVariable int id) throws ResourceNotFoundException{
        return new ResponseEntity<String>(memberService.deleteMemberById(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/badges")                                             // get all badges of a member
    public ResponseEntity<?> getMemberBadges(@PathVariable int id) throws ResourceNotFoundException{
        return new ResponseEntity<List<ResponseBadgeDTO>>(memberService.getMemberBadges(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")                                           // get all transactions of a member
    public ResponseEntity<?> getMemberTransactions(@PathVariable int id) throws ResourceNotFoundException{
        return new ResponseEntity<List<ResponseTransactionDTO>>(memberService.getMemberTransactions(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/memberships")
    public ResponseEntity<?> getMembershipsByMemberId(@PathVariable int id, @RequestParam(name = "planType", required = false) String planType) {
        return new ResponseEntity<>(membershipService.getMembershipsByMemberId(id, planType), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllMembers(){
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @PutMapping("/{member_id}")
    public ResponseEntity<?> updateMember(@PathVariable("member_id") int id, @RequestBody RequestMemberDTO requestMemberDTO) throws ResourceNotFoundException{
        return new ResponseEntity<>(memberService.updateMember(id, requestMemberDTO), HttpStatus.OK);
    }
    @GetMapping("/{memberid}/plans")
    public ResponseEntity<?> getPlansForMember(@PathVariable int memberid){
        return new ResponseEntity<>(membershipService.getAllPlansForMember(memberid), HttpStatus.OK);
    }


}
