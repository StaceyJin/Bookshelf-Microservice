package com.jin.bookshelf.core.portal.presentationlayer.controllers;

import com.jin.api.core.portal.Portal;
import com.jin.api.core.portal.PortalServiceAPI;
import com.jin.bookshelf.core.portal.businesslayer.PortalService;
import com.jin.utils.exceptions.InvalidInputException;
import com.jin.utils.exceptions.WrongGenreException;
import com.jin.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PortalRESTController implements PortalServiceAPI {

    private final static Logger LOG = LoggerFactory.getLogger(PortalRESTController.class);

    private final PortalService portalService;

    @Autowired
    public PortalRESTController(PortalService portalService) {
        this.portalService = portalService;
    }

    @Override
    public List<Portal> getPortal(int bookId){

        if (bookId < 1) throw new InvalidInputException("Invalid bookId: " + bookId);
        /*if (bookId == 213){
            LOG.debug("No portal found for bookId: {}", + bookId);
            return new ArrayList<>();
        }*/
        if (bookId == 301) throw new WrongGenreException("Genre not recognized for bookId: " + bookId);

/*        List<Portal> listPortal = new ArrayList<>();
        listPortal.add(new Portal(bookId, 1,  "https://play.google.com/books", "myBookAccount", serviceUtil.getServiceAddress()));*/

        List<Portal> listPortal = portalService.getBookById(bookId);
        LOG.debug("/portal found response size: {}", listPortal.size());

        return listPortal;
    }

    @Override
    public Portal createPortal(Portal model){

        Portal portal = portalService.createPortal(model);

        LOG.debug("REST Controller createPortal: created an entity: {}/{}", portal.getBookId(), portal.getPortalId());
        return portal;
    }

    @Override
    public void deletePortals(int bookId){
        LOG.debug("REST Controller deletePortals: trying to delete portals for the book with bookId: {}", bookId);

        portalService.deletePortals(bookId);
    }
}
