package seedu.address.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.Test;

//@@author qiu-siqi
public class CipherEngineTest {

    private static final String VALID_BOOK_SHELF = "./src/test/data/XmlUtilTest/validBookShelf.xml";
    private static final String TEMP_FILE = "tempfile.xml";

    private static final String TEST_KEY_1 = "";
    private static final String TEST_KEY_2 = "Qg5gk20g%1~";

    private static final String PASSWORD_1 = "";
    private static final String PASSWORD_2 = "thisismypassword";
    private static final String PASSWORD_3 = "1RS#(`D #Q HT%";

    @Test
    public void encryptDecryptFile() throws Exception {
        File validBookShelf = new File(VALID_BOOK_SHELF);
        File copy = new File(TEMP_FILE);

        try {
            Files.copy(validBookShelf.toPath(), copy.toPath());

            CipherEngine.encryptFile(TEMP_FILE, TEST_KEY_1);
            assertDifferentContent(validBookShelf, copy);
            CipherEngine.decryptFile(TEMP_FILE, TEST_KEY_1);
            assertSameContent(validBookShelf, copy);

            CipherEngine.encryptFile(TEMP_FILE, TEST_KEY_2);
            assertDifferentContent(validBookShelf, copy);
            CipherEngine.decryptFile(TEMP_FILE, TEST_KEY_2);
            assertSameContent(validBookShelf, copy);
        } finally {
            copy.delete();
        }
    }

    @Test
    public void encryptDecryptKey() throws Exception {
        String hashed = CipherEngine.hashPassword(PASSWORD_1);
        assertNotEquals(hashed, PASSWORD_1);
        assertTrue(CipherEngine.isValidPasswordHash(hashed));
        assertTrue(CipherEngine.checkPassword(PASSWORD_1, hashed));

        hashed = CipherEngine.hashPassword(PASSWORD_2);
        assertNotEquals(hashed, PASSWORD_2);
        assertTrue(CipherEngine.isValidPasswordHash(hashed));
        assertTrue(CipherEngine.checkPassword(PASSWORD_2, hashed));

        hashed = CipherEngine.hashPassword(PASSWORD_3);
        assertNotEquals(hashed, PASSWORD_3);
        assertTrue(CipherEngine.isValidPasswordHash(hashed));
        assertTrue(CipherEngine.checkPassword(PASSWORD_3, hashed));
    }

    private boolean isSameInContent(File one, File two) throws IOException {
        return Arrays.equals(Files.readAllBytes(one.toPath()), (Files.readAllBytes(two.toPath())));
    }

    private void assertSameContent(File one, File two) throws IOException {
        assertTrue(isSameInContent(one, two));
    }

    private void assertDifferentContent(File one, File two) throws IOException {
        assertFalse(isSameInContent(one, two));
    }
}
