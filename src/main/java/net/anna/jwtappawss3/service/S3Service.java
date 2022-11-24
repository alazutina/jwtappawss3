package net.anna.jwtappawss3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 s3client;

    public void createBucket() {
        String bucketName = "annalazutina";

        if (s3client.doesBucketExistV2(bucketName)) {
            log.info("Bucket {} already exists, use a different name", bucketName);
            return;
        }

        s3client.createBucket(bucketName);
    }

    public void listBuckets(){
        List<Bucket> buckets = s3client.listBuckets();
        log.info("buckets: {}", buckets);
    }

    @SneakyThrows
    public void uploadFile() {
        String bucketName = "annalazutina";
        ClassLoader loader = S3Service.class.getClassLoader();
        File file = new File(loader.getResource("11.txt").getFile());
        s3client.putObject(
                bucketName,
                "11.txt",
                file);
    }

    @SneakyThrows
    public void uploadFile(String fileName) {
        String bucketName = "annalazutina";
        ClassLoader loader = S3Service.class.getClassLoader();
        File file = new File(loader.getResource(fileName).getFile());
        s3client.putObject(
                bucketName,
                fileName,
                file);
    }

    public void listFiles() {
        String bucketName = "annalazutina";

        ObjectListing objects = s3client.listObjects(bucketName);
        for(S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
            log.info("File name: {}", objectSummary.getKey());
        }
    }

    @SneakyThrows
    public void downloadFile() {
        String bucketName = "annalazutina";

        S3Object s3object = s3client.getObject(bucketName, "11.txt");
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        File file = new File("<PUT_DESIRED_PATH_HERE>");

        FileCopyUtils.copy(inputStream, new FileOutputStream(file));
    }

    public void deleteFile() {
        String bucketName = "annalazutina";
        s3client.deleteObject(bucketName, "11.txt");
    }

    public void deleteFile(String fileName) {
        String bucketName = "annalazutina";
        s3client.deleteObject(bucketName, fileName);
    }
}

