package com.marvell.service.mapper;

import com.marvell.domain.Marvell;
import com.marvell.service.dto.MarvellDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Marvell} and its DTO {@link MarvellDTO}.
 */
@Mapper(componentModel = "spring")
public interface MarvellMapper extends EntityMapper<MarvellDTO, Marvell> {}
