package com.tuflex.admin.app.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tuflex.admin.app.user.payload.request.HotelAddRequest;
import com.tuflex.admin.app.user.payload.request.RoomAddRequest;
import com.tuflex.admin.app.user.service.HotelService;
import com.tuflex.admin.app.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hotel")
public class HotelRestController {
    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<?> add(Authentication authentication, @Valid @ModelAttribute HotelAddRequest req)
            throws Exception {
        hotelService.add(req);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/add-room", method = RequestMethod.POST)
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<?> addRoom(Authentication authentication, @Valid @ModelAttribute RoomAddRequest req)
            throws Exception {
        hotelService.addRoom(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("delete")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public ResponseEntity<?> withdrawal(Authentication authentication, @RequestParam Long pid) {
        hotelService.delete(pid);
        return ResponseEntity.ok().build();
    }
}
