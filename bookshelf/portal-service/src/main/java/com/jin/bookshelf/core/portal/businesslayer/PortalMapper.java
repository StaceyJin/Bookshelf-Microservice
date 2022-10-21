package com.jin.bookshelf.core.portal.businesslayer;

import com.jin.api.core.portal.Portal;
import com.jin.bookshelf.core.portal.datalayer.PortalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PortalMapper {

    @Mapping(target = "serviceAddress", ignore = true)
    Portal entityToModel(PortalEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    PortalEntity modelToEntity(Portal model);

    List<Portal> entityListToModelList(List<PortalEntity> entity);
    List<PortalEntity> modelListToEntityList(List<Portal> model);
}
