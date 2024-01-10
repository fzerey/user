package com.fzerey.user.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fzerey.user.domain.model.Group;
import com.fzerey.user.infrastructure.repository.GroupRepository;
import com.fzerey.user.service.group.GroupServiceImpl;
import com.fzerey.user.service.group.dtos.CreateGroupDto;
import com.fzerey.user.service.group.dtos.GetGroupDto;
import com.fzerey.user.service.group.dtos.ListGroupDto;
import com.fzerey.user.service.group.dtos.UpdateGroupDto;
import com.fzerey.user.shared.exceptions.group.GroupAlreadyExistsException;
import com.fzerey.user.shared.exceptions.group.GroupNotFoundException;
import com.fzerey.user.shared.requests.model.PagedResponse;

class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    private Page<Group> createMockedPage() {
        List<Group> list = Arrays.asList(new Group(), new Group());
        return new PageImpl<>(list);
    }

    @Test
    void createGroup_whenGroupExists_shouldThrowException() {
        CreateGroupDto groupDto = new CreateGroupDto("ExistingGroup");
        when(groupRepository.findByName("ExistingGroup")).thenReturn(Optional.of(new Group()));

        assertThrows(GroupAlreadyExistsException.class, () -> groupService.createGroup(groupDto));
    }

    @Test
    void updateGroup_whenGroupExists_shouldUpdateGroup() {
        UpdateGroupDto groupDto = new UpdateGroupDto("UpdatedGroup");
        groupDto.setId(1L);
        Group existingGroup = new Group();
        existingGroup.setId(1L);
        existingGroup.setName("OldGroup");
        when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));

        groupService.updateGroup(groupDto);

        assertEquals("UpdatedGroup", existingGroup.getName());
        verify(groupRepository).save(existingGroup);
    }

    @Test
    void updateGroup_whenGroupNotExists_shouldThrowException() {
        UpdateGroupDto groupDto = new UpdateGroupDto("Group");
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.updateGroup(groupDto));
    }

    @Test
    void deleteGroup_whenGroupExists_shouldDeleteGroup() {
        Group existingGroup = new Group();
        existingGroup.setId(1L);
        when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));

        groupService.deleteGroup(1L);

        verify(groupRepository).delete(existingGroup);
    }

    @Test
    void deleteGroup_whenGroupNotExists_shouldThrowException() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.deleteGroup(1L));
    }

    @Test
    void getGroup_whenGroupExists_shouldReturnGroup() {
        Group existingGroup = new Group();
        existingGroup.setId(1L);
        existingGroup.setName("Group");
        when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));

        GetGroupDto result = groupService.getGroup(1L);

        assertNotNull(result);
        assertEquals("Group", result.getName());
    }

    @Test
    void getGroup_whenGroupNotExists_shouldThrowException() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupService.getGroup(1L));
    }

    @Test
    void getGroups_withValidParameters_shouldReturnPagedResponse() {
        ListGroupDto listGroupDto = new ListGroupDto();
        listGroupDto.setPage(1);
        listGroupDto.setSize(10);
        listGroupDto.setSortBy("name");
        listGroupDto.setSortDirection("asc");
        Page<Group> mockedPage = createMockedPage();
        when(groupRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);

        PagedResponse<GetGroupDto> result = groupService.getGroups(listGroupDto);

        assertNotNull(result);
        assertEquals(mockedPage.getTotalElements(), result.getTotalElements());
        verify(groupRepository).findAll(any(Pageable.class));
    }
}
