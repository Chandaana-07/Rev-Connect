package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.dao.BusinessProductDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.BusinessProduct;

public class BusinessProductDAOImpl implements BusinessProductDAO {

    @Override
    public boolean addProduct(BusinessProduct p) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO business_products " +
                         "(user_id, name, description, price, link) " +
                         "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getPrice());
            ps.setString(5, p.getLink());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BusinessProduct> getProductsByUser(int userId) {

        List<BusinessProduct> list = new ArrayList<BusinessProduct>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM business_products WHERE user_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BusinessProduct p = new BusinessProduct();
                p.setProductId(rs.getInt(1));
                p.setUserId(rs.getInt(2));
                p.setName(rs.getString(3));
                p.setDescription(rs.getString(4));
                p.setPrice(rs.getString(5));
                p.setLink(rs.getString(6));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
