package com.application.catalogue.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Image {

    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:postgresql://ep-dark-thunder-a1utui2y.ap-southeast-1.aws.neon.tech/neondb";
        String user = "neondb_owner";
        String password = "ugMywNxJ24Oj";

        // Path to the image file
        String imagePath = "src/main/java/com/application/catalogue/Image/f5.jpg";

        // Insert the image into the database
        uploadImage(url, user, password, imagePath, "E345"); // Specify the product article ID
    }

    public static void uploadImage(String url, String user, String password, String imagePath, String article) {
        // SQL statement to create a large object and get its OID
        String createLargeObjectSQL = "SELECT lo_create(0)";

        // SQL statement to insert the image data
        String insertLargeObjectSQL = "INSERT INTO pg_largeobject (loid, pageno, data) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             FileInputStream fis = new FileInputStream(new File(imagePath))) {

            conn.setAutoCommit(false); // Start transaction

            // Create a new large object and get its OID
            Long oid;
            try (Statement stmt = conn.createStatement()) {
                var rs = stmt.executeQuery(createLargeObjectSQL);
                rs.next();
                oid = rs.getLong(1);
            }

            // Read the image in chunks and store it in the database
            byte[] buffer = new byte[2048];
            int pageNum = 1;
            int bytesRead;

            try (PreparedStatement pstmt = conn.prepareStatement(insertLargeObjectSQL)) {
                while ((bytesRead = fis.read(buffer)) != -1) {
                    pstmt.setLong(1, oid);
                    pstmt.setInt(2, pageNum++);
                    pstmt.setBytes(3, buffer);
                    pstmt.executeUpdate();
                }
            }

            // Update the product table with the OID reference
            String updateSQL = "UPDATE product SET image = ? WHERE article = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setLong(1, oid);
                pstmt.setString(2, article); // Specify the product article ID
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit transaction
            System.out.println("Image uploaded successfully with OID: " + oid);

        } catch (SQLException e) {
            System.err.println("Database error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading the image file:");
            e.printStackTrace();
        }
    }
}
