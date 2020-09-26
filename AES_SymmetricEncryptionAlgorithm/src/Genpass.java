import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Genpass {

    public static void main(String args[]) throws UnsupportedEncodingException {

        final String ALGORITHM       = "AES";
        final String myEncryptionKey = "ThisIsFoundation";
        final String UNICODE_FORMAT  = "UTF8";

        byte[] keyAsBytes;
        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        Key key = new SecretKeySpec(keyAsBytes, ALGORITHM);


        try{
            Scanner input = new Scanner(System.in);
            System.out.println("Enter User ID: ");
            String userName = input.nextLine();
            System.out.println("Enter Password: ");
            String password = input.nextLine();

            Cipher c = Cipher.getInstance(ALGORITHM);

            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(password.getBytes(UNICODE_FORMAT));
            String encryptedValue = new BASE64Encoder().encode(encValue);


            FileWriter fileWrite=new FileWriter("password.txt",true);
            fileWrite.write(userName + "\t" + encryptedValue + "\n");
            fileWrite.close();

            FileOutputStream fileOut = new FileOutputStream("key.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(key);
            objectOut.close();


        }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | NullPointerException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }

    }
}
