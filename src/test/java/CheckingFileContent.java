
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.jupiter.api.Test;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class CheckingFileContent {
    String txtFile = "./src/test/resources/text.txt",
        pdfFile = "./src/test/resources/text.pdf",
        xlsFile = "./src/test/resources/text.xls",
        xlsxFile = "./src/test/resources/text.xlsx",
        docFile = "./src/test/resources/text.doc",
        docxFile = "./src/test/resources/text.docx",
        zipFilePath = "./src/test/resources/text.zip",
        unzipPath = "./src/test/resources/unzip/",
        unzipTxtPath = "./src/test/resources/unzip/text.txt",
        zipPassword = "qa-guru",
        expectedData = "means using automated testing tools to run tests";

    @Test
    void txtCheckingFileContent() throws IOException {
        String actualData = Files.readTextFromPath(txtFile);
        assertThat(actualData, containsString(expectedData));
    }

    @Test
    void pdfCheckingFileContent() throws IOException {
        PDF pdf = new PDF(new File(pdfFile));
        assertThat(pdf, PDF.containsText(expectedData));
    }

    @Test
    void xlsCheckingFileContent() {
        XLS spreadsheet = new XLS(new File(xlsFile));
        assertThat(spreadsheet, XLS.containsText(expectedData));
    }

    @Test
    void xlsxCheckingFileContent() {
        String actualDate = Files.readXlsxFromPath(xlsxFile);
        assertThat(actualDate,containsString(expectedData));
    }

    @Test
    void zipCheckingFileContent() throws IOException, ZipException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        if (zipFile.isEncrypted()) {
            zipFile.setPassword(zipPassword);
        }
        zipFile.extractAll(unzipPath);
        String actualDate = Files.readTextFromPath(unzipTxtPath);
        assertThat(actualDate, containsString(expectedData));
    }

    @Test
    void docCheckingFileContent() throws IOException {
        FileInputStream fis = new FileInputStream(docFile);
        HWPFDocument doc = new HWPFDocument(fis);
        WordExtractor we = new WordExtractor(doc);
        assertThat(we.getText(), containsString(expectedData));
        fis.close();
    }

    @Test
    void docxCheckingFileContent() throws IOException {
        FileInputStream fis1 = new FileInputStream(docxFile);
        XWPFDocument doc1 = new XWPFDocument(fis1);
        XWPFWordExtractor we1 = new XWPFWordExtractor(doc1);
        assertThat(we1.getText(), containsString(expectedData));
    }

}