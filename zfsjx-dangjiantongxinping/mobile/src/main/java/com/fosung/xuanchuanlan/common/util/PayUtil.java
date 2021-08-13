package com.fosung.xuanchuanlan.common.util;


import com.fosung.frameutils.util.Base64Util;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Company：fosung
 * Author： shanghome on 2018-05-02 15:51.
 * RealName：商学壮
 * Email：shanghome@163.com
<<<<<<< Updated upstream
 * Description：
 */


public class PayUtil {
    private static String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDlqduqj3nniOo2rBhkHdkYFBunqx27jlPiBDnT36ZgK0HG7YZHeUZizTvzTDTygH6G4iCj6ROTxFwvd+89WPuA9gSrZHcc3drzG3iyDvcJJlhGSvl1s6KcpVLiSo35MPwpoYfR/x7TZY+Y391M2VklIHreLgvsDb9+w92HSHhxZQIDAQAB";//测试
   // private static String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3WKF8O69Sphe0Uwk9PdxhS+cEOXUWIYuSCTiAI1TTT+7qv8uhW0by49IqVmApHcgYxd955g6PjIoz9sHZrbZlve+S4aIyrmzl6oWUQnvHTU0Q+H3QFY6PIqVJ/LB2XfevzATMf8uopArp+ttl18tijWNMw01X0+dG19f+i3+sgQIDAQAB";

    private static String http = "http://fhts.test.bank.ecitic.com/MsmbV3_SIT/rest/jtSkip/001?dftype=1&chl=DTZX&jiaodf=";//测试
  //  private static String http = "https://wap.bank.ecitic.com/MsmbV3/rest/jtSkip/001?dftype=1&chl=DTZX&jiaodf=";
    public static String getUrl(String data) {
        try {
            return http + encryptBASE64(encrypt(data.getBytes(), getPublicKey(public_key), 2048, 11, "RSA/ECB/PKCS1Padding"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    private static String encryptBASE64(byte[] key) throws Exception {
        return new String(Base64Util.encode(key));
    }

    private static PublicKey getPublicKey(String keyStr) throws Exception {
        PublicKey publicKey = generatePublicKey(Base64Util.decode(keyStr), "RSA");
        return publicKey;
    }

    private static PublicKey generatePublicKey(byte[] bytes, String keypairAlgorithm) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keypairAlgorithm);
        PublicKey publickey = keyFactory.generatePublic(keySpec);
        return publickey;
    }

    private static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int encryptBlockSize = keyByteSize - reserveSize;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new Exception("ENCRYPT ERROR:", e);
        } finally {
            try {
                if (outbuf != null) {
                    outbuf.close();
                }
            } catch (Exception e) {
                outbuf = null;
                throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);
            }
        }
    }

}
