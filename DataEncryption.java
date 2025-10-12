package Bank_Management.myBank.util;

public class DataEncryption {




    // !!! WARNING: This is a simple, insecure encryption for educational purposes ONLY.
// !!! DO NOT use this in a real-world application. Use a library like jBCrypt.

        private static final int SHIFT_KEY = 3;

        public static String encrypt(String plainText) {
            StringBuilder encryptedText = new StringBuilder();
            for (char ch : plainText.toCharArray()) {
                encryptedText.append((char)(ch + SHIFT_KEY));
            }
            return encryptedText.toString();
        }

        public static String decrypt(String encryptedText) {
            StringBuilder plainText = new StringBuilder();
            for (char ch : encryptedText.toCharArray()) {
                plainText.append((char)(ch - SHIFT_KEY));
            }
            return plainText.toString();
        }
    }

