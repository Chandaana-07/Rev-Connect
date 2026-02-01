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

    // ---------------- ADD PRODUCT ----------------
    @Override
    public boolean addProduct(BusinessProduct p) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO business_products " +
                "(user_id, name, description, price, link) " +
                "VALUES (?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getPrice());
            ps.setString(5, p.getLink());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, con);
        }
        return false;
    }

    // ---------------- GET PRODUCTS BY USER ----------------
    @Override
    public List<BusinessProduct> getProductsByUser(int userId) {

        List<BusinessProduct> list = new ArrayList<BusinessProduct>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT PRODUCT_ID, USER_ID, NAME, DESCRIPTION, PRICE, LINK " +
                "FROM business_products " +
                "WHERE user_id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                BusinessProduct p = new BusinessProduct();
                p.setProductId(rs.getInt("PRODUCT_ID"));
                p.setUserId(rs.getInt("USER_ID"));
                p.setName(rs.getString("NAME"));
                p.setDescription(rs.getString("DESCRIPTION"));
                p.setPrice(rs.getString("PRICE"));
                p.setLink(rs.getString("LINK"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }

        return list;
    }

    // ---------------- UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
