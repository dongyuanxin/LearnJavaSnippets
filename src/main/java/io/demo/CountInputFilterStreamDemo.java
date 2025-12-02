package io.demo;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;

public class CountInputFilterStreamDemo {
    public static void main(String[] args) throws java.io.IOException {
        byte[] data = "hello world!".getBytes();
        try (CountInputFilterStream input = new CountInputFilterStream(new ByteArrayInputStream(data))) {
            int n;
            while ((n = input.read()) != -1) {
                System.out.println((char)n);
            }
            System.out.println("Total bytes read: " + input.getBytesRead());
        }


    }
}

class CountInputFilterStream extends FilterInputStream {
    private int count;

    public CountInputFilterStream(java.io.InputStream in) {
        super(in);
    }

    public int getBytesRead() {
        return this.count;
    }

    @Override
    public int read() throws java.io.IOException {
        int result = super.read();
        if (result != -1) {
            this.count++;
        }
        return result;
    }

    @Override
    public int read(byte[] b, int off, int len) throws java.io.IOException {
        int n = in.read(b, off, len);
        if (n != -1) {
            this.count += n;
        }
        return n;
    }
}