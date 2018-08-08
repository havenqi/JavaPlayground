package live.qart.Networking.Http;

import java.io.*;
import java.net.*;
import java.util.Date;

/**
 * Created by QArt on 2018/8/7.
 */
public class URLConnectionFacility {

    public static void setupEnv() {
//            System.setProperty("proxySet", "true")
            System.setProperty("https.proxyHost", "127.0.0.1");
            System.setProperty("https.proxyPort", "8888");
            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "8888");
            System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\java\\jdk1.8.0_162\\bin\\FiddlerKeystore");
            System.setProperty("javax.net.ssl.trustStorePassword", "fiddler");
    }

    public static void myLocalHost() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("LocalAddr=" + ip);
    }

    public static void getWebContent(String parm) {

            try {
                // Open the URLConnection for reading
                URL u = new URL(parm);
                URLConnection uc = u.openConnection();
                try (InputStream raw = uc.getInputStream()) { // autoclose
                    InputStream buffer = new BufferedInputStream(raw); // chain the InputStream to a Reader
                    Reader reader = new InputStreamReader(buffer);
                    int c;
                    while ((c = reader.read()) != -1) { System.out.print((char) c);
                    } }
            } catch (MalformedURLException ex) { System.err.println(parm + " is not a parseable URL");
            } catch (IOException ex) { System.err.println(ex);
            }

    }

    public static void encodingAwareSourceViewer(String parm) {

            try {
                // set default encoding
                String encoding = "ISO-8859-1";
                URL u = new URL(parm);
                URLConnection uc = u.openConnection();
                String contentType = uc.getContentType();
                int encodingStart = contentType.indexOf("charset=");
                if (encodingStart != -1) {
                    encoding = contentType.substring(encodingStart + 8);
                }
                InputStream in = new BufferedInputStream(uc.getInputStream());
                Reader r = new InputStreamReader(in, encoding);
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
                r.close();
            } catch (MalformedURLException ex) {
                System.err.println(parm + " is not a parseable URL");
            } catch (UnsupportedEncodingException ex) {
                System.err.println(
                        "Server sent an encoding Java does not support: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println(ex);
            }

    }

    public static void saveBinaryFile(URL u) throws IOException {
        URLConnection uc = u.openConnection();
        System.out.println("The response time(raw):" + uc.getDate());
        System.out.println("The response time:" + new Date(uc.getDate()));
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();
        if (contentType.startsWith("text/") || contentLength == -1 ) {
            throw new IOException("This is not a binary file.");
        }
        try (InputStream raw = uc.getInputStream()) {
            InputStream in = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];
            int offset = 0;
            while (offset < contentLength) {
                int bytesRead = in.read(data, offset, data.length - offset);
                if (bytesRead == -1) break;
                offset += bytesRead;
                System.out.println("offset=" + offset);
            }
            if (offset != contentLength) {
                throw new IOException("Only read " + offset
                        + " bytes; Expected " + contentLength + " bytes");
            }
            String filename = u.getFile();
            filename = filename.substring(filename.lastIndexOf('/') + 1);
            try (FileOutputStream fout = new FileOutputStream(filename)) {
                fout.write(data);
                fout.flush();
            }
        }
    }

    public static void headerView(String parm) {
        try {
            URL u = new URL(parm);
            URLConnection uc = u.openConnection();
            System.out.println("Content-type: " + uc.getContentType());
            if (uc.getContentEncoding() != null) {
                System.out.println("Content-encoding: "
                        + uc.getContentEncoding());
            }
            if (uc.getDate() != 0) {
                System.out.println("Date: " + new Date(uc.getDate()));
            }
            if (uc.getLastModified() != 0) {
                System.out.println("Last modified: "
                        + new Date(uc.getLastModified()));
            }
            if (uc.getExpiration() != 0) {
                System.out.println("Expiration date: "
                        + new Date(uc.getExpiration()));
            }
            if (uc.getContentLength() != -1) {
                System.out.println("Content-length: " + uc.getContentLength());
            }
        } catch (MalformedURLException ex) {
            System.err.println(parm + " is not a URL I understand");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println();
    }


    public static void main (String[] args) {

        setupEnv();

        String[] parm = new String[15];
        parm[0]= "http://10.211.17.67:4000/p2_Merchants.html";
        parm[1]= "http://paytesta.8f8.com/document-management/FileDownload?filePath=/bfbdata/doc/%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97/%E8%AF%81%E4%B9%A6&fileName=8f8server.cer";
        parm[2]= "http://www.oreilly.com/favicon.ico";
        
//        myLocalHost();

//        getWebContent(parm);

//        encodingAwareSourceViewer(parm[0]);

        // Save binary file
//        try {
//            URL root = new URL(parm[1]);
//            saveBinaryFile(root);
//        } catch (MalformedURLException ex) {
//            System.err.println(parm[1] + " is not URL I understand.");
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }

        headerView(parm[2]);


    }

}
