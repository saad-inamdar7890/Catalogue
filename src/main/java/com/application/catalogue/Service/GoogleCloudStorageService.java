package com.application.catalogue.Service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GoogleCloudStorageService {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public String uploadFile(MultipartFile file, String bucketName) throws IOException {
        String fileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    public void deleteFile(String filePath) {
        // Extract the bucket name and file name from the file path
        String bucketName = filePath.split("/")[2];
        String fileName = filePath.substring(filePath.indexOf(bucketName) + bucketName.length() + 1);

        // Create a BlobId for the file to be deleted
        BlobId blobId = BlobId.of(bucketName, fileName);

        // Delete the file from the bucket
        storage.delete(blobId);
    }
}