package com.fzerey.user.service.attribute;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.Attribute;
import com.fzerey.user.infrastructure.repository.AttributeRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AttributeInitializationServiceImpl implements AttributeInitializationService {

    private AttributeRepository attributeRepository;
    private Environment environment;

    public AttributeInitializationServiceImpl(AttributeRepository attributeRepository, Environment environment) {
        this.attributeRepository = attributeRepository;
        this.environment = environment;
    }

    @PostConstruct
    public void initialize() {
        String attributes = environment.getProperty("com.fzerey.user.initial.attributes");
        if (attributes != null) {
            String[] keys = attributes.split(",");
            for (String key : keys) {
                attributeRepository.findByKey(key).ifPresentOrElse(a -> {
                }, () -> {
                    Attribute attribute = new Attribute();
                    attribute.setKey(key);
                    attributeRepository.save(attribute);
                });
            }
        }
    }

}
