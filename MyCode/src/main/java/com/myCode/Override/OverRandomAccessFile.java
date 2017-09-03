package com.myCode.Override;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class OverRandomAccessFile extends RandomAccessFile {
	public OverRandomAccessFile(File file, String mode) throws FileNotFoundException {
		super(file, mode);
	}

	public  String newReadLine() throws IOException {
        StringBuffer input = new StringBuffer();
//        List<Character> bb=new ArrayList<>();
        List<Byte> bb=new ArrayList<>();
        int c = -1;
        boolean eol = false;

        while (!eol) {
            switch (c = read()) {
            case -1:
            case '\n':
                eol = true;
                break;
            case '\r':
                eol = true;
                long cur = getFilePointer();
                if ((read()) != '\n') {
                    seek(cur);
                }
                break;
            default:
                input.append((char)c);
                bb.add((byte) c);
                break;
            }
        }

        if ((c == -1) && (input.length() == 0)) {
            return null;
        }
        Byte[] b=(Byte[])bb.toArray();
        return new String(new String(input).getBytes(),"UTF-8");
//        return input.toString();
    }
}
