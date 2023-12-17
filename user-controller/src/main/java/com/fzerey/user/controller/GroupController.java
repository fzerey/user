package com.fzerey.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fzerey.user.service.group.GroupService;
import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/1.0/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    ResponseEntity<?> createGroup(@RequestBody CreateGroupDto createGroupDto) {
        groupService.createGroup(createGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<?> listGroups() {
        var groups = groupService.getGroups();
        return new ResponseEntity<List<GetGroupDto>>(groups, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getGroup(@PathParam("id") Long id) {
        var groups = groupService.getGroup(id);
        return new ResponseEntity<GetGroupDto>(groups, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    ResponseEntity<?> updateGroup(@PathParam("id") Long id, @RequestBody UpdateGroupDto updateGroupDto) {
        updateGroupDto.setId(id);
        groupService.updateGroup(updateGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
