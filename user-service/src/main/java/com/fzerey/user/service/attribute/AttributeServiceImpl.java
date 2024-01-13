package com.fzerey.user.service.attribute;

import org.springframework.stereotype.Service;

import com.fzerey.user.domain.model.Attribute;
import com.fzerey.user.infrastructure.repository.AttributeRepository;
import com.fzerey.user.service.attribute.dtos.CreateAttributeDto;
import com.fzerey.user.shared.exceptions.attribute.AttributeAlreadyExistsException;

@Service
public class AttributeServiceImpl implements AttributeService {

    private AttributeRepository attributeRepository;

    public AttributeServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Override
    public void createAttribute(CreateAttributeDto createAttributeDto) {
        var existingAttribute = attributeRepository.findByKey(createAttributeDto.getKey());
        if (existingAttribute.isPresent()) {
            throw new AttributeAlreadyExistsException();
        }
        var attribute = new Attribute();
        attribute.setKey(createAttributeDto.getKey());
        attributeRepository.save(attribute);

    }

}
