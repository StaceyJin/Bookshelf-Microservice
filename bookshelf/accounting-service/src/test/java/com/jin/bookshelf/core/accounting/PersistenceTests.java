package com.jin.bookshelf.core.accounting;

import com.jin.bookshelf.core.accounting.datalayer.AccountingEntity;
import com.jin.bookshelf.core.accounting.datalayer.AccountingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
public class PersistenceTests {

    @Autowired
    private AccountingRepository repository;

    private AccountingEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        AccountingEntity entity = new AccountingEntity(1, 2, 9.99, 5.99, 4.00, "m");
        savedEntity = repository.save(entity);

        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void createAccountingEntity() {

        AccountingEntity newEntity = new AccountingEntity(1, 3, 9.99, 4.99, 5.00, "m2");
        repository.save(newEntity);

        AccountingEntity foundEntity = repository.findById(newEntity.getId()).get();
        //assertEqualsReview(newEntity, foundEntity);

        assertEquals(2, repository.count());
    }

    @Test
    public void updateAccountingEntity() {
        savedEntity.setListedPrice(10.99);
        repository.save(savedEntity);

        AccountingEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals(10.99, foundEntity.getListedPrice());
    }

    @Test
    public void deleteAccountingEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByBookId() {
        List<AccountingEntity> entityList = repository.findByBookId(savedEntity.getBookId());

        assertThat(entityList, hasSize(1));

        assertThat(entityList.get(0), samePropertyValuesAs(savedEntity));
    }

}
