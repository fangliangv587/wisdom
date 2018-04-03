package com.xz.cenco.temp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Temp {
    public static void main(String[] args) {
        System.out.println("running");
        //这里是包名+辅助功能类名
        String cmd1 = "settings put secure enabled_accessibility_services com.xz.cenco.wisdom/com.xz.cenco.assits.DetectionService";
        
        String cmd2 = "settings put secure accessibility_enabled 1";
        execShell(cmd1);
        execShell(cmd2);
    }
    
    //运行命令行的方法
    private static void execShell(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String readLine = br.readLine();
            while (readLine != null) {
                System.out.println(readLine);
                readLine = br.readLine();
            }
            if (br != null) {
                br.close();
            }
            p.destroy();
            p = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}