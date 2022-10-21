package com.jin.bookshelf.core.accounting.businesslayer;

import com.jin.api.core.accounting.Accounting;
import com.jin.bookshelf.core.accounting.datalayer.AccountingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel="spring")
public interface AccountingMapper {

    @Mapping(
            target = "serviceAddress", ignore = true)

    Accounting entityToModel(AccountingEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    AccountingEntity modelToEntity(Accounting model);

    List<Accounting> entityListToModelList(List<AccountingEntity> entity);
    List<AccountingEntity> modelListToEntityList(List<Accounting> model);
}
