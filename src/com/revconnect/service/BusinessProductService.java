package com.revconnect.service;

import java.util.List;

import com.revconnect.dao.BusinessProductDAO;
import com.revconnect.dao.impl.BusinessProductDAOImpl;
import com.revconnect.model.BusinessProduct;

public class BusinessProductService {

    private BusinessProductDAO dao = new BusinessProductDAOImpl();

    public boolean addProduct(BusinessProduct p) {
        return dao.addProduct(p);
    }

    public List<BusinessProduct> getProducts(int userId) {
        return dao.getProductsByUser(userId);
    }
}
