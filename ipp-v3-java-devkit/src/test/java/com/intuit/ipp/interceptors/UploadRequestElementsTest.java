package com.intuit.ipp.interceptors;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UploadRequestElementsTest {
    private String boundaryForEntity;
    private String boundaryForContent;
    private InputStream docContent;
    private String boundaryId;
    private String elementsId;
    private UploadRequestElements uploadRequestElements;

    @Test
    public void testSetersAndGetters() {
        //test values for testing setter
        boundaryForEntity = "entityBoundary";
        boundaryForContent = "contentBoundary";
        docContent = new ByteArrayInputStream("content for testing input stream".getBytes());
        boundaryId = "boundaryId";
        elementsId = "elementsId";
        uploadRequestElements = new UploadRequestElements();

        //set test values
        uploadRequestElements.setElementsId(elementsId);
        uploadRequestElements.setBoundaryForEntity(boundaryForEntity);
        uploadRequestElements.setBoundaryForContent(boundaryForContent);
        uploadRequestElements.setBoundaryId(boundaryId);
        uploadRequestElements.setElementsId(elementsId);
        uploadRequestElements.setDocContent(docContent);

        //get test values
        Assert.assertEquals(uploadRequestElements.getElementsId(), elementsId);
        Assert.assertEquals(uploadRequestElements.getBoundaryForEntity(), boundaryForEntity);
        Assert.assertEquals(uploadRequestElements.getBoundaryForContent(), boundaryForContent);
        Assert.assertEquals(uploadRequestElements.getBoundaryId(), boundaryId);
        Assert.assertEquals(uploadRequestElements.getElementsId(), elementsId);
        Assert.assertEquals(uploadRequestElements.getDocContent(), docContent);
    }
}
