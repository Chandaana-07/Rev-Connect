package com.revconnect.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.dao.CreatorBusinessDAO;
import com.revconnect.db.DBConnection;
import com.revconnect.model.CreatorBusinessProfile;

public class CreatorBusinessDAOImpl implements CreatorBusinessDAO {

    // ---------------- REGISTER PROFILE ----------------
    @Override
    public boolean registerProfile(CreatorBusinessProfile p) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "INSERT INTO creator_business_profile " +
                "(user_id, account_type, display_name, category, bio, address, " +
                "contact_info, website, social_links, business_hours, external_links) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(sql);

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
        } finally {
            close(null, ps, con);
        }
        return false;
    }

    // ---------------- UPDATE PROFILE ----------------
    @Override
    public boolean updateProfile(CreatorBusinessProfile p) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "UPDATE creator_business_profile SET " +
                "display_name=?, category=?, bio=?, address=?, contact_info=?, " +
                "website=?, social_links=?, business_hours=?, external_links=? " +
                "WHERE user_id=?";

            ps = con.prepareStatement(sql);

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
        } finally {
            close(null, ps, con);
        }
        return false;
    }

    // ---------------- GET PROFILE ----------------
    @Override
    public CreatorBusinessProfile getProfile(int userId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String sql =
                "SELECT user_id, account_type, display_name, category, bio, address, " +
                "contact_info, website, social_links, business_hours, external_links " +
                "FROM creator_business_profile WHERE user_id=?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            rs = ps.executeQuery();

            if (rs.next()) {
                CreatorBusinessProfile p = new CreatorBusinessProfile();
                p.setUserId(rs.getInt("USER_ID"));
                p.setAccountType(rs.getString("ACCOUNT_TYPE"));
                p.setDisplayName(rs.getString("DISPLAY_NAME"));
                p.setCategory(rs.getString("CATEGORY"));
                p.setBio(rs.getString("BIO"));
                p.setAddress(rs.getString("ADDRESS"));
                p.setContactInfo(rs.getString("CONTACT_INFO"));
                p.setWebsite(rs.getString("WEBSITE"));
                p.setSocialLinks(rs.getString("SOCIAL_LINKS"));
                p.setBusinessHours(rs.getString("BUSINESS_HOURS"));
                p.setExternalLinks(rs.getString("EXTERNAL_LINKS"));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return null;
    }

    // ---------------- UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
