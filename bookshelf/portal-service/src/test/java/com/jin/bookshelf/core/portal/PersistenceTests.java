package com.jin.bookshelf.core.portal;

import com.jin.bookshelf.core.portal.datalayer.PortalEntity;
import com.jin.bookshelf.core.portal.datalayer.PortalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
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
    private PortalRepository repository;

    private PortalEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        PortalEntity entity = new PortalEntity(1, 2, "l","un");
        savedEntity = repository.save(entity);

        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void createPortalEntity() {

        PortalEntity newEntity = new PortalEntity(1, 3, "l2", "un2");
        repository.save(newEntity);

        PortalEntity foundEntity = repository.findById(newEntity.getId()).get();
        //assertEqualsReview(newEntity, foundEntity);

        assertEquals(2, repository.count());
    }

    @Test
    public void updatePortalEntity() {
        savedEntity.setLink("books.com");
        repository.save(savedEntity);

        PortalEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("books.com", foundEntity.getLink());
    }

    @Test
    public void deletePortalEntity() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByProductId() {
        List<PortalEntity> entityList = repository.findByBookId(savedEntity.getBookId());

        assertThat(entityList, hasSize(1));

        assertThat(entityList.get(0), samePropertyValuesAs(savedEntity));
    }
}
