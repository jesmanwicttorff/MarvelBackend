package com.marvell.service.mapper;

import com.marvell.domain.Distribuidor;
import com.marvell.domain.Marvell;
import com.marvell.service.dto.DistribuidorDTO;
import com.marvell.service.dto.MarvellDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Distribuidor} and its DTO {@link DistribuidorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistribuidorMapper extends EntityMapper<DistribuidorDTO, Distribuidor> {
    @Mapping(target = "distribuidoid", source = "distribuidoid", qualifiedByName = "marvellId")
    DistribuidorDTO toDto(Distribuidor s);

    @Named("marvellId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MarvellDTO toDtoMarvellId(Marvell marvell);
}
