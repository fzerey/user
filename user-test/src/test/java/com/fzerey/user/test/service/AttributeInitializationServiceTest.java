package com.fzerey.user.test.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.fzerey.user.domain.model.Attribute;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.service.attribute.AttributeInitializationServiceImpl;

class AttributeInitializationServiceImplTest {

    @Mock
    private AttributeRepository attributeRepository;

    @Mock
    private Environment environment;

    private AttributeInitializationServiceImpl attributeInitializationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        attributeInitializationService = new AttributeInitializationServiceImpl(attributeRepository, environment);
    }

    @Test
    void initialize_ShouldCreateNewAttributes() {

        String attributeKeys = "attr1,attr2,attr3";
        when(environment.getProperty("com.fzerey.user.initial.attributes")).thenReturn(attributeKeys);

        when(attributeRepository.findByKey(anyString())).thenReturn(Optional.empty());

        attributeInitializationService.initialize();

        verify(attributeRepository, times(3)).save(any(Attribute.class));
    }

    @Test
    void initialize_ShouldNotCreateExistingAttributes() {

        String attributeKeys = "existingAttr";
        when(environment.getProperty("com.fzerey.user.initial.attributes")).thenReturn(attributeKeys);

        when(attributeRepository.findByKey("existingAttr")).thenReturn(Optional.of(new Attribute()));

        attributeInitializationService.initialize();

        verify(attributeRepository, never()).save(any(Attribute.class));
    }

    @Test
    void initialize_WhenNoAttributesConfigured_ShouldNotCreateAttributes() {

        when(environment.getProperty("com.fzerey.user.initial.attributes")).thenReturn(null);

        attributeInitializationService.initialize();

        verify(attributeRepository, never()).save(any(Attribute.class));
    }
}
