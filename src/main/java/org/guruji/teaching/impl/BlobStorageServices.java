package org.guruji.teaching.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.guruji.teaching.api.StorageOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BlobStorageServices implements StorageOperations {
    private static final String CONTAINER = "container";
    private static final String BLOB = "blob";
    private static final String FILE_PATH = "file path";
    private final Scanner SCANNER = new Scanner(System.in);
    private Map<String, BlobContainerClient> containerClientMap = new HashMap<>();
    private BlobServiceClient blobServiceClient = null;

    public BlobStorageServices(String connectionString) {
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    @Override
    public void upload() {
        BlobClient blobClient = getBlobClient();
        String filePath = readData(FILE_PATH);
        blobClient.uploadFromFile(filePath);

    }

    @Override
    public void download() {

        BlobClient blobClient = getBlobClient();
        String filePath = readData(FILE_PATH);
        blobClient.downloadToFile(filePath);

    }

    @Override
    public boolean delete() {
        BlobClient blobClient = getBlobClient();
        return blobClient.deleteIfExists();
    }

    private String readData(String input) {
        System.out.println("Enter the " + input + " name:");
        return SCANNER.next();
    }

    private BlobClient getBlobClient() {
        String containerName = readData(CONTAINER);
        BlobContainerClient blobContainerClient = null;
        if (containerClientMap.containsKey(containerName)) {
            blobContainerClient = containerClientMap.get(containerName);

        } else {
            blobContainerClient = blobServiceClient.createBlobContainerIfNotExists(containerName);
            containerClientMap.put(containerName, blobContainerClient);
        }
        String blobName = readData(BLOB);
        return blobContainerClient.getBlobClient(blobName);
    }
}
