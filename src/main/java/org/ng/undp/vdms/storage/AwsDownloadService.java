package org.ng.undp.vdms.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by emmanuel on 4/27/17.
 */
@Service
public class AwsDownloadService {

    @Autowired
    private AWSCredentials credentials;

    private TransferManager transferManager;

    /**
     * Instantiate an instance of the FileUpload Service Providing the required
     * amazon Credentials
     *
     * @throws IOException
     */
    public AwsDownloadService() throws IOException {
        this.transferManager = new TransferManager(credentials);
    }

    /**
     * This method is used to upload a single file to an amazon bucket
     *
     * @param bucketName - The name of s3 bucket to download from
     * @param fileName - unique fileName representing the file key in s3 bucket
     * @param file - The file to download this object to
     * @return Download object
     */
    public Download downloadFromS3(String bucketName, String fileName, File file) {
        System.out.println("Downloading file from bucket name: " + bucketName);
        return transferManager.download(bucketName, fileName, file);
    }


    /**
     * Returns an ObjectListing object
     * which contains a list of ObjectSummaries
     * @param bucketName
     * @return
     */
    public ObjectListing listObjects(String bucketName) {
        TransferManager tx = new TransferManager(this.credentials);
        ObjectListing objectListing = tx.getAmazonS3Client().listObjects(bucketName);
        System.out.println("Listing Object listing...");
        if (objectListing.getObjectSummaries().isEmpty()) {
            System.out.println(" Object listing is empty");
        }

        for (S3ObjectSummary b : objectListing.getObjectSummaries()) {
            System.out.printf("%nBucket name: %s, Encoding key: %s, Object Size: %s, Date Modified: %s, E Tag: %s", b.getBucketName(), b.getKey(), b.getSize(), b.getLastModified(), b.getETag());
        }
        return objectListing;
    }

    /**
     * Aborts any Multipart uploads that were initiated before the specified
     * date.
     *
     * @param bucketName
     * @param date
     */
    public void abortFileDownload(String bucketName, Date date) {
        transferManager.abortMultipartUploads(bucketName, date);
    }


}
