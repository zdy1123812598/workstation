package com.example.demo.ffmpeg;

import java.io.IOException;

public class FFmpegRTSPCapture {


    public static void RTSPCapture(String inputRTSPUrl, String startTime, String duration, String outputFile) {
        try {

            String command = String.format("ffmpeg -i %s -ss %s -t %s %s", inputRTSPUrl, startTime, duration, outputFile);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error capturing image.");
        }
    }


    public static void main(String[] args) {
        // 设置FFmpeg命令
        String inputRTSPUrl = "rtsp://your_rtsp_url"; // 替换为你的RTSP流地址
        String outputFile = "output.mp4"; // 输出文件名
        String startTime = "00:00:10"; // 开始时间（例如："00:00:10" 表示10秒）
        String duration = "00:00:30"; // 持续时间（例如："00:00:30" 表示30秒）
        RTSPCapture(inputRTSPUrl, startTime, duration, outputFile);
    }
}
