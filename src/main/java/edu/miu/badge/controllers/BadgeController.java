package edu.miu.badge.controllers;


import edu.miu.badge.dto.RequestBadgeDTO;
import edu.miu.badge.dto.ResponseBadgeDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.services.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    @Autowired
    private BadgeService badgeService;

    @GetMapping
    public ResponseEntity<?> getBadges() {                                             // get all badges
        return new ResponseEntity<List<ResponseBadgeDTO>>(badgeService.getAllBadges(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBadge(@PathVariable int id) throws ResourceNotFoundException{                          // get a badge by id
        return new ResponseEntity<ResponseBadgeDTO>(badgeService.getBadge(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addBadge(@RequestBody RequestBadgeDTO badge) throws ResourceNotFoundException{                                    // add a badge
        return new ResponseEntity<ResponseBadgeDTO>(badgeService.createBadge(badge), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> inactiveBadge(@PathVariable int id) throws ResourceNotFoundException{                        // inactive a badge
        return new ResponseEntity<String>(badgeService.inactiveBadge(id), HttpStatus.OK);
    }
}
