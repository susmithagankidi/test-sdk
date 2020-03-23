package uk.gov.metoffice.swfhim;

import com.amazonaws.services.lambda.runtime.Context;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Simple handler that unzips files when given an input bucket, output bucket, and zip-file name
 *  -- to demonstrate that
 */
public class UnzippingHandler {

    private static final S3Client s3Client = S3Client.create();

    public UnzipResult handleRequest(UnzipInstruction unzipInstruction, Context context) {

        String inputBucket = unzipInstruction.getInputBucket();
        String inputZipFile = unzipInstruction.getInputZipFile();
        String outputBucket = unzipInstruction.getOutputBucket();

        UnzipResult unzipResult = new UnzipResult();

        try (ZipInputStream zipInputStream = new ZipInputStream(inputStreamFromS3(inputBucket, inputZipFile))) {

            for (ZipEntry zipEntry = zipInputStream.getNextEntry(); zipEntry != null; zipEntry = zipInputStream.getNextEntry()) {

                long entrySize = zipEntry.getSize();
                String fileKey = zipEntry.getName();

                if (fileKey.endsWith(".xml") || fileKey.endsWith(".txt")) {
                    writeUnzipped(outputBucket, fileKey, zipInputStream, entrySize);
                    unzipResult.add(fileKey);
                    System.out.println("Unzipped " + fileKey + " with size " + entrySize);
                }

            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return unzipResult;
    }

    private InputStream inputStreamFromS3(String inputBucket, String inputZipFile) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(inputBucket)
                .key(inputZipFile)
                .build();

        return s3Client.getObject(getObjectRequest,
                ResponseTransformer.toInputStream());
    }

    private void writeUnzipped(String outputBucket, String fileKey, ZipInputStream zipInputStream, long entrySize) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(outputBucket)
                .key(fileKey)
                .build();
        s3Client.putObject(putObjectRequest,
                RequestBody.fromContentProvider(() -> zipInputStream, entrySize, "text/xml"));
    }


}
