package br.com.jujubaprojects.parkingapi.Web.dto.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import br.com.jujubaprojects.parkingapi.Web.dto.PageableDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toDto(@SuppressWarnings("rawtypes") Page page) {
        return new ModelMapper().map(page, PageableDto.class);
    }
    
}
