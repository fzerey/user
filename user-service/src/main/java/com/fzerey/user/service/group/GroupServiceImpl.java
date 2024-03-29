package com.fzerey.user.service.group;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.Group;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.ListGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;
import com.fzerey.user.shared.exceptions.group.GroupAlreadyExistsException;
import com.fzerey.user.shared.exceptions.group.GroupNotFoundException;
import com.fzerey.user.shared.requests.model.PagedResponse;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createGroup(CreateGroupDto groupDto) {
        repository.findByName(groupDto.getName()).ifPresent(g -> {
            throw new GroupAlreadyExistsException();
        });
        var group = new Group(groupDto.getName());
        repository.save(group);
    }

    @Override
    public void updateGroup(UpdateGroupDto groupDto) {
        var group = repository.findById(groupDto.getId()).orElseThrow(GroupNotFoundException::new);
        group.setName(groupDto.getName());
        repository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        var group = repository.findById(id).orElseThrow(GroupNotFoundException::new);
        repository.delete(group);
    }

    @Override
    public GetGroupDto getGroup(Long id) {
        var group = repository.findById(id).orElseThrow(GroupNotFoundException::new);
        return new GetGroupDto(group.getId(), group.getName());
    }

    @Override
    public PagedResponse<GetGroupDto> getGroups(ListGroupDto listGroupDto) {
        Sort sort = Sort.by(listGroupDto.getSortBy());
        if (listGroupDto.getSortDirection().equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(listGroupDto.getPage() - 1, listGroupDto.getSize(), sort);
        var groups = listGroupDto.getQuery() != null ? repository.findByNameLike(listGroupDto.getQuery(), pageable)
                : repository.findAll(pageable);
        PagedResponse<GetGroupDto> response = new PagedResponse<>();
        response.fromPage(groups.map(this::convertToDto));
        return response;
    }

    private GetGroupDto convertToDto(Group group) {
        return new GetGroupDto(group.getId(), group.getName());
    }
}
