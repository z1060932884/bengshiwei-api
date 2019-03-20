package com.bengshiwei.zzj.app.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.google.gson.Gson;
import org.jsoup.Connection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.Collection;

@Provider
public class WriterInterceptor implements javax.ws.rs.ext.WriterInterceptor, ReaderInterceptor {

//    byte[] privateKey = Base64.decode(privateKeyStr);
//    byte[] publicKey = Base64.decode(pulicKeyStr);



    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {


// 私钥base64-->MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg2uPgTN+MNblSb8hDlbHrTp5+cXezlErvztt1f9YINgegCgYIKoEcz1UBgi2hRANCAAQnjTvYcLJREJtze99F3OVgIPbTEOylh7x8q99dYBq+dK7wwxEjGf+5Qb8iLWgGRQuP8N4Tnq4cx27L1xLIgkGl
        Gson gson = new Gson();
        //加密返回

        String data = gson.toJson(context.getEntity());
        System.out.println("WriterInterceptor------>"+gson.toJson(context.getEntity()));
        OutputStream outputStream = context.getOutputStream();
        outputStream.write(sm2EncUtils(data).getBytes());
        context.setOutputStream(outputStream);
        outputStream.flush();
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {

        System.out.println("ReaderInterceptor---->"+new String());
        return context.proceed();
    }

    public static String sm2EncUtils(String data) {
        String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK3nXZSpYv0JRNFDT3XG0jURw0itaylhma/w8k9LSbtpNqtUG1lvi8mke9UCY+xkn5vXY9+Bk565s4QcPYwQdbb8cT7CXPWgW0cQCYkAmIuyekoh55bVtXTa9zW0nqpxXJs82qNcTpYs7e0mdLuxZIn0YeJonWJJChOOaGaRkBubAgMBAAECgYAqOQqXlajbFu0GgflA900CZZWsh66FFZVjCnVKm1UDk8AaSQl65YJjKvSF+1aXhrbZ96ngEm3tE9lqMhEfeL+bjsmZnc6uxYwANzv+wh54Uf5AVso9uXbMXlMe1MNaR7oZkhI+du6VdLSu0n1WlRrmcg+zliDkLrKfYclzcahIwQJBAPBzNS1LkmtPfi0vRi/MZfF3ChMvEVeCGyCWvb0lJ5bPnkezKDCxWBcfWRozt+qgz6Q/fgv+LXm1+6CqMgCFkX8CQQC5JnH5U5kyTG6cH8uplg0I2sxljp5nFs/tnF0RkTVzYp9R5CiFXwGKH91st8t95FzKkLuILoy5VrLScYo8a4vlAkBJuhmhFN4Fd29p7Wfo+hR8EJMPRMxdd7BXssDlAUJ9VJXkyENXgtlO5bbNePQ4xixE4Y8FoF9TRYCtR+JjFJGDAkAvsCdLAK1Et0sGC2p5k5xn23Mp9UH3a3jCyrNuAuixf4VpokqNj5rl6K8vgWd4VYlQ41ZqDRNR6XLFoVjplwnBAkAN2gcitZZBvS9XE7woaFohUL8QR3MHuAlbmhwTxRCt+01vDvpUlMj1MyvkG3h/uL/uR2aVTV+Cpu72I04Trag1";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt512UqWL9CUTRQ091xtI1EcNIrWspYZmv8PJPS0m7aTarVBtZb4vJpHvVAmPsZJ+b12PfgZOeubOEHD2MEHW2/HE+wlz1oFtHEAmJAJiLsnpKIeeW1bV02vc1tJ6qcVybPNqjXE6WLO3tJnS7sWSJ9GHiaJ1iSQoTjmhmkZAbmwIDAQAB";
        RSA rsa = new RSA(privateKeyStr,publicKeyStr);
        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes(data,CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        System.out.println("加密--->" + HexUtil.encodeHexStr(encrypt2));
        System.out.println("解密--->" + StrUtil.str(decrypt2,CharsetUtil.CHARSET_UTF_8));
        return HexUtil.encodeHexStr(encrypt2);
    }


}
