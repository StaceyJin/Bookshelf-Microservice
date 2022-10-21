package com.jin.bookshelf.core.portal.businesslayer;

import com.jin.api.core.portal.Portal;
import com.jin.bookshelf.core.portal.datalayer.PortalEntity;
import com.jin.bookshelf.core.portal.datalayer.PortalRepository;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortalServiceImpl implements PortalService{

    private final static Logger LOG = LoggerFactory.getLogger(PortalServiceImpl.class);

    private final PortalRepository repository;

    private final PortalMapper mapper;

    private final ServiceUtil serviceUtil;

    public PortalServiceImpl(PortalRepository repository, PortalMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Portal> getBookById(int bookId){

        List<PortalEntity> entityList = repository.findByBookId(bookId);
        List<Portal> list = mapper.entityListToModelList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("Portals getBookById: response size: {}", list.size());
        return list;
    }

    @Override
    public Portal createPortal(Portal model){

        PortalEntity entity = mapper.modelToEntity(model);
        PortalEntity newEntity = repository.save(entity);

        LOG.debug("PortalService createPortal: create a portal entity: {}/{}", model.getBookId(), model.getPortalId());
        return mapper.entityToModel(newEntity);
    }

    @Override
    public void deletePortals(int bookId){

        LOG.debug("PortalService deletePortals: tries to delete portal for the book with bookId: {}", bookId);
        repository.deleteAll(repository.findByBookId(bookId));
    }
}
