package com.fzerey.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fzerey.user.controller.model.ListGroupModel;
import com.fzerey.user.service.group.GroupService;
import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;
import com.fzerey.user.shared.requests.model.PagedResponse;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/1.0/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    ResponseEntity<Void> createGroup(@RequestBody CreateGroupDto createGroupDto) {
        groupService.createGroup(createGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<PagedResponse<GetGroupDto>> listGroups(@ModelAttribute ListGroupModel model) {
        var groups = groupService.getGroups(model.toDto());
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateGroup(@PathParam("id") Long id, @RequestBody UpdateGroupDto updateGroupDto) {
        updateGroupDto.setId(id);
        groupService.updateGroup(updateGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
