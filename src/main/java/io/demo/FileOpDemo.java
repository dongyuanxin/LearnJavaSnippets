package io.demo;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 1、老的IO接口：File
 * 2、新的IO接口：走nio的Paths
 */
public class FileOpDemo {
    public static void main(String[] args) {
        /**
         * 1、File
         */
        File f = new File("/Users/dongyuanxin/IdeaProjects/LearnJavaSnippets");
        File[] fs1 = f.listFiles();
        printFiles(fs1);
        /**
         * 2、Path.of/Paths -> File
         */
        Path path = Path.of("/Users/dongyuanxin/IdeaProjects/LearnJavaSnippets");
        System.out.println(path.toAbsolutePath()); // 转换为绝对路径
        System.out.println(path.normalize()); // 转换为规范路径
        File f2 = path.toFile(); // 转换为 File
        File[] fs2 = f2.listFiles();
        printFiles(fs2);

        for (Path p : Paths.get("..").toAbsolutePath()) { // 可以直接遍历Path
            System.out.println("  " + p);
        }
    }

    static void printFiles(File[] files) {
        System.out.println("===== call printFiles() =====");
        if (files != null) {
            for (File f: files) {
                System.out.println(f);
            }
        }
        System.out.println("===== finish printFiles() =====");
    }
}
