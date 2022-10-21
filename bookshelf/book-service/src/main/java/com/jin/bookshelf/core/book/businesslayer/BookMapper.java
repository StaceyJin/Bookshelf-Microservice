package com.jin.bookshelf.core.book.businesslayer;

import com.jin.api.core.book.Book;
import com.jin.bookshelf.core.book.datalayer.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "serviceAddress", ignore = true)
    Book entityToModel(BookEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    BookEntity modelToEntity(Book model);
}
