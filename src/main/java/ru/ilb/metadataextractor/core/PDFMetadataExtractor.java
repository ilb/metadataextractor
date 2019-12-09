package ru.ilb.metadataextractor.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import ru.ilb.metadataextractor.entities.MetadataExtractor;
import ru.ilb.metadataextractor.exceptions.CreateObjectError;
import ru.ilb.metadataextractor.exceptions.GetPropertyError;

public class PDFMetadataExtractor implements MetadataExtractor {

    private PDDocumentInformation pdfInfo;
    private byte[] document;

    public PDFMetadataExtractor(byte[] document) {
        this.document = document;

        try (PDDocument pdfDocument = PDDocument.load(document);) {
            pdfInfo = pdfDocument.getDocumentInformation();
        } catch (Exception e) {
            throw new CreateObjectError("Error parse XMP PDF" + e);
        }
    }

    public byte[] insertMetadata(Map<String, String> metadata) {

        try {
            PDDocument pdfDocument = PDDocument.load(document);
            PDDocumentInformation info = new PDDocumentInformation();

            metadata.forEach((key, value) -> info.setCustomMetadataValue(key, value));

            pdfDocument.setDocumentInformation(info);

            byte[] docWithMetadata;

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                pdfDocument.save(out);
                docWithMetadata = out.toByteArray();
            }

            return docWithMetadata;
        } catch (IOException ex) {
            throw new CreateObjectError("Bad PDF writedocument: " + ex);
        }
    }

    @Override
    public String getProperty(String propName) {
        String result = null;
        try {
            result = pdfInfo.getCustomMetadataValue(propName);
            return result;
        } catch (Exception ex) {
            throw new GetPropertyError("Error parsing PDF property: " + ex);
        }
    }

    @Override
    public Map<String, String> asMap() {
        try {
            Map<String, String> metadata = new HashMap<>();

            Set<String> keys = pdfInfo.getMetadataKeys();
            keys.forEach(key -> metadata.put(key, pdfInfo.getCustomMetadataValue(key)));

            return metadata;
        } catch (Exception ex) {
            throw new GetPropertyError("Error parsing PDF MAP property: " + ex);
        }
    }

}
