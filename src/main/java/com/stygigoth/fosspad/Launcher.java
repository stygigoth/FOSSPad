package com.stygigoth.fosspad;

import java.io.File;

public class Launcher {
    public static void main(String[] args) {
        FOSSPad.main(args);
        File file = new File("Untitled.txt");
        file.delete();
    }
}
