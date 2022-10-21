package com.jin.bookshelf.core.accounting.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountingRepository extends CrudRepository<AccountingEntity, Integer> {

    @Transactional(readOnly = true)
    List<AccountingEntity> findByBookId(int bookId);
}
