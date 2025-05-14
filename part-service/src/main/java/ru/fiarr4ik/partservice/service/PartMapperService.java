package ru.fiarr4ik.partservice.service;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fiarr4ik.partservice.dto.PartDto;
import ru.fiarr4ik.partservice.entity.Part;

@Service
public class PartMapperService {

    private final ModelMapper modelMapper;

    @Autowired
    public PartMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<PartDto, Part>() {
            @Override
            protected void configure() {
                skip(destination.getPartId());
            }
        });
    }

    public Part toModel(PartDto partDto) {
        return modelMapper.map(partDto, Part.class);
    }

    public PartDto toDto(Part part) {
        return modelMapper.map(part, PartDto.class);
    }
}
