package uk.gov.metoffice.swfhim;

/**
 * POJO for sending the UnzippingHandler instructions
 * -- the S3 bucket and file key of the zip file
 * -- the S3 bucket onto which unzipped files should be placed
 */
public class UnzipInstruction {

    private String inputBucket;
    private String inputZipFile;
    private String outputBucket;

    public String getInputBucket() {
        return inputBucket;
    }

    public void setInputBucket(String inputBucket) {
        this.inputBucket = inputBucket;
    }

    public String getInputZipFile() {
        return inputZipFile;
    }

    public void setInputZipFile(String inputZipFile) {
        this.inputZipFile = inputZipFile;
    }

    public String getOutputBucket() {
        return outputBucket;
    }

    public void setOutputBucket(String outputBucket) {
        this.outputBucket = outputBucket;
    }
}
