package com.jin.bookshelf.core.portal.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PortalRepository extends CrudRepository<PortalEntity, Integer> {

    @Transactional(readOnly = true)
    List<PortalEntity> findByBookId(int bookId);
}
