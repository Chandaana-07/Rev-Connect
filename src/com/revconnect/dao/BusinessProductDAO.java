package com.revconnect.dao;

import java.util.List;
import com.revconnect.model.BusinessProduct;

public interface BusinessProductDAO {

    boolean addProduct(BusinessProduct p);

    List<BusinessProduct> getProductsByUser(int userId);
}
