package com.marvell.service.mapper;

import com.marvell.domain.Marvell;
import com.marvell.domain.Zapatos;
import com.marvell.service.dto.MarvellDTO;
import com.marvell.service.dto.ZapatosDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZapatosMapper extends EntityMapper<ZapatosDTO, Zapatos> {}
