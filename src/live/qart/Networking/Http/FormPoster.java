package live.qart.Networking.Http;

import java.io.*;
import java.net.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class FormPoster {
    private URL url;
    // from Chapter 5, Example 5-8
    private QueryString query = new QueryString();

    public FormPoster (URL url) {
        if (!url.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException(
                    "Posting only works for http URLs");
        }
        this.url = url;
    }


    public void add(String name, String value) {
        query.add(name, value);
    }
    public URL getURL() {
        return this.url;
    }


    public InputStream post() throws IOException {
// open the connection and prepare it to POST
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);
        try (OutputStreamWriter out
                     = new OutputStreamWriter(uc.getOutputStream(), "UTF-8")) {
// The POST line, the Content-type header,
// and the Content-length headers are sent by the URLConnection.
// We just need to send the data
            out.write(query.toString());
            out.write("\r\n");
            out.flush();
        }
// Return the response
        return uc.getInputStream();
    }

    public static void main(String[] args) {
        URL url;
        if (args.length > 0) {
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException ex) {
                System.err.println("Usage: java FormPoster url");
                return;
            }
        } else {
            try {
                url = new URL(
                        "http://www.cafeaulait.org/books/jnp4/postquery.phtml");
            } catch (MalformedURLException ex) { // shouldn't happen
                System.err.println(ex);
                return;
            }
        }
        FormPoster poster = new FormPoster(url);
        poster.add("name", "Elliotte Rusty Harold");
        poster.add("email", "elharo@ibiblio.org");
        try (InputStream in = poster.post()) {

            // Read the response
            Reader r = new InputStreamReader(in);
            int c;
            while((c = r.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}


class QueryString {
    private StringBuilder query = new StringBuilder();

    public QueryString() {
    }

    public synchronized void add(String name, String value) {
        query.append('&');
        encode(name, value);
    }

    private synchronized void encode(String name, String value) {
        try {
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append('=');
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }

    public synchronized String getQuery() {
        return query.toString();
    }

    @Override
    public String toString() {
        return getQuery();
    }
}