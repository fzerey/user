package com.fzerey.user.service.group;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.Group;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createGroup(CreateGroupDto groupDto) {
        var group = new Group(groupDto.getName());
        repository.save(group);
    }

    @Override
    public void updateGroup(UpdateGroupDto groupDto) {
        var group = repository.findById(groupDto.getId()).get();
        group.setName(groupDto.getName());
        repository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        var group = repository.findById(id).get();
        repository.delete(group);
    }

    @Override
    public GetGroupDto getGroup(Long id) {
        var group = repository.findById(id).get();
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
