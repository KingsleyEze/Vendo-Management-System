package org.ng.undp.vdms.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by macbook on 1/19/17.
 */
public interface AwsS3Service {

    public void uploadFile(String uploadFile, String fileName);

    public ResponseEntity<byte[]> download(String key) throws IOException;

    public List<S3ObjectSummary> list();
    public PutObjectResult upload(InputStream inputStream, String uploadKey) ;
    public void uploadFile(String fileName, InputStream input, ObjectMetadata objectMetadata);


    public Resource loadAsResource(String filename, String awsBasePath, String nameCardBucket);


    public Path load(String filename, String awsBasePath, String nameCardBucket) ;






    public void delete(String filename) ;



}