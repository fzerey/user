package com.fzerey.user.service.group;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.Group;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;
import com.fzerey.user.shared.exceptions.group.GroupNotFoundException;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createGroup(CreateGroupDto groupDto) {
        var existingGroup = repository.findByName(groupDto.getName());
        if(existingGroup.isPresent()) {
            throw new RuntimeException("Group already exists");
        }
        var group = new Group(groupDto.getName());
        repository.save(group);
    }

    @Override
    public void updateGroup(UpdateGroupDto groupDto) {
        var group = repository.findById(groupDto.getId()).orElseThrow(() -> new GroupNotFoundException());
        group.setName(groupDto.getName());
        repository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        var group = repository.findById(id).orElseThrow(() -> new GroupNotFoundException());
        repository.delete(group);
    }

    @Override
    public GetGroupDto getGroup(Long id) {
        var group = repository.findById(id).orElseThrow(() -> new GroupNotFoundException());
        return new GetGroupDto(group.getId(), group.getName());
    }

    @Override
    public List<GetGroupDto> getGroups() {
        List<Group> groups = repository.findAll();
        return groups.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private GetGroupDto convertToDto(Group group) {
        GetGroupDto dto = new GetGroupDto(group.getId(), group.getName());
        return dto;
    }
}
