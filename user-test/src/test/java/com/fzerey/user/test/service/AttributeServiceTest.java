package com.fzerey.user.test.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fzerey.user.domain.model.Attribute;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.service.attribute.AttributeServiceImpl;
import com.fzerey.user.service.attribute.dtos.CreateAttributeDto;
import com.fzerey.user.shared.exceptions.attribute.AttributeAlreadyExistsException;

class AttributeServiceTest {

    @Mock
    private AttributeRepository attributeRepository;

    @InjectMocks
    private AttributeServiceImpl attributeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAttribute_whenAttributeNotExists_shouldCreateAttribute() {
        CreateAttributeDto createAttributeDto = new CreateAttributeDto("attributeKey");
        when(attributeRepository.findByKey("attributeKey")).thenReturn(Optional.empty());

        attributeService.createAttribute(createAttributeDto);

        verify(attributeRepository).save(any(Attribute.class));
    }

    @Test
    void createAttribute_whenAttributeExists_shouldThrowException() {
        CreateAttributeDto createAttributeDto = new CreateAttributeDto("existingKey");
        Attribute existingAttribute = new Attribute();
        existingAttribute.setKey("existingKey");
        when(attributeRepository.findByKey("existingKey")).thenReturn(Optional.of(existingAttribute));

        assertThrows(AttributeAlreadyExistsException.class,
                () -> attributeService.createAttribute(createAttributeDto));
    }

}
