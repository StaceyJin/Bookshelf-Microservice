package com.jin.bookshelf.core.portal.businesslayer;

import com.jin.api.core.portal.Portal;

import java.util.List;

public interface PortalService {

    public List<Portal> getBookById(int bookId);

    public Portal createPortal(Portal model);

    public void deletePortals(int bookId);
}
