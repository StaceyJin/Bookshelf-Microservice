package com.jin.bookshelf.core.accounting.businesslayer;

import com.jin.api.core.accounting.Accounting;
import com.jin.bookshelf.core.accounting.datalayer.AccountingEntity;
import com.jin.bookshelf.core.accounting.datalayer.AccountingRepository;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountingServiceImpl implements AccountingService{

    private final static Logger LOG = LoggerFactory.getLogger(AccountingServiceImpl.class);

    private final AccountingRepository repository;

    private final AccountingMapper mapper;

    private final ServiceUtil serviceUtil;

    public AccountingServiceImpl(AccountingRepository repository, AccountingMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Accounting> getBookById(int bookId){

        List<AccountingEntity> entityList = repository.findByBookId(bookId);
        List<Accounting> list = mapper.entityListToModelList(entityList);
        list.forEach(e-> e.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("Accounting getBookById: response size: {}", list.size());
        return list;
    }

    @Override
    public Accounting createAccounting(Accounting model){

        AccountingEntity entity = mapper.modelToEntity(model);
        AccountingEntity newEntity = repository.save(entity);

        LOG.debug("AccountingService createAccounting: create an accounting entity: {}/{}", model.getBookId(), model.getAccountId());
        return mapper.entityToModel(newEntity);
    }

    @Override
    public void deleteAccountDetails(int bookId){

        LOG.debug("AccountingService deleteAccountDetails: tries to delete accounting for the book with bookId: {}", bookId);
        repository.deleteAll(repository.findByBookId(bookId));
    }
}
