package com.example.victimapp;

import android.util.Base64;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class FlagContainer implements Serializable {

    @Serial
    private static final long serialVersionUID = 8734541966102341989L;
    private String[] parts;
    private ArrayList<Integer> perm;

    public FlagContainer(String[] parts, ArrayList<Integer> perm) {
        this.parts = parts;
        this.perm = perm;
    }

    //CHANGED TO PUBLIC (CROSS PACKAGE)
    public String getFlag() {
        int n = parts.length;
        int i;
        String b64 = new String();
        for (i=0; i<n; i++) {
            b64 += parts[perm.get(i)];
        }

        byte[] flagBytes = Base64.decode(b64, Base64.DEFAULT);
        String flag = new String(flagBytes, Charset.defaultCharset());

        return flag;
    }
}