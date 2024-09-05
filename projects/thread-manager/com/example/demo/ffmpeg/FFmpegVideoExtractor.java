package com.example.demo.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FFmpegVideoExtractor {

    public static void extractVideo(String inputVideoPath, String outputVideoPath, String startTime, String duration) throws IOException, InterruptedException {
        // FFmpeg命令格式: ffmpeg -ss START -t DURATION -i INPUT -vcodec copy -acodec copy OUTPUT
        String command = "ffmpeg -ss " + startTime + " -t " + duration + " -i " + inputVideoPath + " -vcodec copy -acodec copy " + outputVideoPath;

        // 执行命令
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);

        // 读取错误流
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = errorReader.readLine()) != null) {
            System.out.println("FFmpeg Error: " + line);
        }

        // 等待命令执行完成
        process.waitFor();

        System.out.println("Video extracted successfully.");
    }

    public static void main(String[] args) {
        try {
            // 输入输出视频路径
            String inputVideoPath = "E://video1.mp4";
            String outputVideoPath = "E://output.mp4";

            // 开始时间和持续时间，格式为HH:mm:ss
            String startTime = "00:00:3"; // 从10秒开始
            String duration = "00:00:4"; // 截取30秒长的视频

            extractVideo(inputVideoPath, outputVideoPath, startTime, duration);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
