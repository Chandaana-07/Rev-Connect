package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.CreatorBusinessDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.CreatorBusinessProfile;

public class CreatorBusinessDAOImpl implements CreatorBusinessDAO {

    @Override
    public boolean registerProfile(CreatorBusinessProfile p) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO creator_business_profile VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getAccountType());
            ps.setString(3, p.getDisplayName());
            ps.setString(4, p.getCategory());
            ps.setString(5, p.getBio());
            ps.setString(6, p.getAddress());
            ps.setString(7, p.getContactInfo());
            ps.setString(8, p.getWebsite());
            ps.setString(9, p.getSocialLinks());
            ps.setString(10, p.getBusinessHours());
            ps.setString(11, p.getExternalLinks());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProfile(CreatorBusinessProfile p) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE creator_business_profile SET " +
                    "display_name=?, category=?, bio=?, address=?, contact_info=?, " +
                    "website=?, social_links=?, business_hours=?, external_links=? " +
                    "WHERE user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getDisplayName());
            ps.setString(2, p.getCategory());
            ps.setString(3, p.getBio());
            ps.setString(4, p.getAddress());
            ps.setString(5, p.getContactInfo());
            ps.setString(6, p.getWebsite());
            ps.setString(7, p.getSocialLinks());
            ps.setString(8, p.getBusinessHours());
            ps.setString(9, p.getExternalLinks());
            ps.setInt(10, p.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CreatorBusinessProfile getProfile(int userId) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM creator_business_profile WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CreatorBusinessProfile p = new CreatorBusinessProfile();
                p.setUserId(rs.getInt(1));
                p.setAccountType(rs.getString(2));
                p.setDisplayName(rs.getString(3));
                p.setCategory(rs.getString(4));
                p.setBio(rs.getString(5));
                p.setAddress(rs.getString(6));
                p.setContactInfo(rs.getString(7));
                p.setWebsite(rs.getString(8));
                p.setSocialLinks(rs.getString(9));
                p.setBusinessHours(rs.getString(10));
                p.setExternalLinks(rs.getString(11));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
