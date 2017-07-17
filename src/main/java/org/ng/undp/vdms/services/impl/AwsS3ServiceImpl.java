package org.ng.undp.vdms.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.poi.util.IOUtils;
import org.ng.undp.vdms.services.AwsS3Service;
import org.ng.undp.vdms.storage.StorageFileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//import com.amazonaws.util.IOUtils;

/**
 * Created by macbook on 1/19/17.
 */
@Service
public class AwsS3ServiceImpl implements AwsS3Service {

    private  Path rootLocation;

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws_namecard_bucket}")
    private String nameCardBucket;


    @Value("${aws_basepath}")
    private  String awsBasePath;











    /*
     * upload file to folder and set it to public
     */
    public void uploadFile(String uploadFile, String filename) {

        String fileNameInS3 = filename;

        s3client.putObject(
                new PutObjectRequest(nameCardBucket,
                        fileNameInS3, new File(uploadFile))
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void uploadFile(String fileName,InputStream input, ObjectMetadata objectMetadata) {

        String fileNameInS3 = fileName;



       s3client.putObject(new PutObjectRequest(nameCardBucket, fileName, input , objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));


       /*
       PutObjectRequest(String bucketName,
                        String key,
                        InputStream input,
                        ObjectMetadata metadata)

       * */


    }


    public PutObjectResult upload(InputStream inputStream, String uploadKey) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(nameCardBucket, uploadKey, inputStream, new ObjectMetadata());

        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putObjectResult = s3client.putObject(putObjectRequest);

        IOUtils.closeQuietly(inputStream);

        return putObjectResult;
    }


/*
    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(nameCardBucket, key);

        S3Object s3Object = s3client.getObject(getObjectRequest);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }*/

    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(nameCardBucket, key);

        S3Object s3Object = s3client.getObject(getObjectRequest);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("inline", fileName);


        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }


    public List<S3ObjectSummary> list() {
        ObjectListing objectListing = s3client.listObjects(new ListObjectsRequest().withBucketName(nameCardBucket));

        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

        return s3ObjectSummaries;
    }



    @Override
    public Path load(String filename,String awsBasePath, String nameCardBucket) {
        //return Paths.get(filename);

        String fl =  awsBasePath + nameCardBucket;
        this.rootLocation = Paths.get(fl);


        System.out.println(this.rootLocation);
        System.out.println(rootLocation);
        return this.rootLocation.resolve(filename);

    }  public Resource loadAsResource(String filename,String awsBasePath, String nameCardBucket) {

        try {
            Path file = load(filename, awsBasePath, nameCardBucket);
            System.out.println("The file path is " + file);
            Resource resource = new UrlResource(file.toString());
            //if (resource.exists() || resource.isReadable()) {
                return resource;
            //} else {
              //  throw new StorageFileNotFoundException("Could not read file: " + filename);

           // }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public void delete(String filename){

      s3client.deleteObject(nameCardBucket,filename);
    }
}