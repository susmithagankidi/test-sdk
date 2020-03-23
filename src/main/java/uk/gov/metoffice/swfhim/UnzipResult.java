package uk.gov.metoffice.swfhim;

import java.util.ArrayList;
import java.util.List;

/**
 * Little POJO so that the UnzippingHandler can return a list of files unzipped
 */
public class UnzipResult {

    private List<String> fileKeys = new ArrayList<>();

    public void add(String thisFileKey) {
        fileKeys.add(thisFileKey);
    }

    public List<String> getFileKeys() {
        return fileKeys;
    }

}
