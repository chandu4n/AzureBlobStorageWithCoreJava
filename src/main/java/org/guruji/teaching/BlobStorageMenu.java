package org.guruji.teaching;

import org.guruji.teaching.api.StorageOperations;
import org.guruji.teaching.impl.BlobStorageServices;

import java.util.Scanner;

public class BlobStorageMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the azure blob storage connection string:");
        String connectionString = scanner.next();
        StorageOperations storageOperations = new BlobStorageServices(connectionString);
        boolean next = true;
        while (next) {
            System.out.println("Menu");
            System.out.println("1. Upload the file");
            System.out.println("2. Download the file");
            System.out.println("3. Delete the file");
            System.out.println("4. Exit");
            System.out.println();
            System.out.println("Enter you choice:");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    storageOperations.upload();
                    break;
                case 2:
                    storageOperations.download();
                    break;
                case 3:
                    boolean isDeleted = storageOperations.delete();
                    if (isDeleted) {
                        System.out.println("Deleted successfully");
                    } else {
                        System.err.println("Item not found with the given details");
                    }
                    break;
                case 4:
                    next = false;
                    break;
                default:
                    System.err.println("Invalid choice");
            }

        }
    }
}
