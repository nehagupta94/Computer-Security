import sun.misc.BASE64Decoder;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Verifypass {

    public static void main(String[] args){
        try {

            final String ALGORITHM       = "AES";

            FileInputStream fileIn = new FileInputStream("key.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            objectIn.close();

            Key key = (Key) obj;

            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            Map<String,String> map = new HashMap<>();
            BufferedReader br = new BufferedReader(new FileReader("password.txt"));
            String line = br.readLine();
            while(line != null){
                map.put(line.split("\t")[0],line.split("\t")[1]);
                line = br.readLine();
            }

            //System.out.println(map);

            Scanner input = new Scanner(System.in);
            System.out.println("Enter User ID: ");
            String userName = input.nextLine();
            System.out.println("Enter Password: ");
            String password = input.nextLine();

            if(map.containsKey(userName)){
                String encryptedValue = map.get(userName);

                byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
                byte[] decValue = c.doFinal(decordedValue);
                String decryptedValue = new String(decValue);

                if(decryptedValue.equals(password)){
                    System.out.println("password is correct");
                }else{
                    System.out.println("the password is incorrect");
                }
            }else{
                System.err.println("ID does not exist");
            }

        }catch (IOException | IllegalBlockSizeException | BadPaddingException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
