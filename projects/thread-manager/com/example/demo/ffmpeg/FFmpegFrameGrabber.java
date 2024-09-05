package com.example.demo.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FFmpegFrameGrabber {

    public static void captureImageFromVideo(String videoPath, String imagePath, String timeStamp) {
        try {
            // FFmpeg命令格式：ffmpeg -i [输入视频文件] -ss [时间戳] -frames:v 1 [输出图片文件]
            String command = "ffmpeg -i " + videoPath + " -ss " + timeStamp + " -frames:v 1 " + imagePath;

            // 执行FFmpeg命令
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // 读取错误流，如果有错误信息，打印输出
            String line;
            while ((line = errorReader.readLine()) != null) {
                System.out.println("FFmpeg Error: " + line);
            }

            // 等待FFmpeg进程结束
            process.waitFor();
            System.out.println("Image captured successfully.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error capturing image.");
        }
    }

    public static void main(String[] args) {
        // 视频文件路径
        String videoPath = "E://video1.mp4";
        // 输出图片路径
        String imagePath = "E://image.jpg";
        // 需要截取的时间戳，例如："00:00:10"
        String timeStamp = "00:00:05";

        captureImageFromVideo(videoPath, imagePath, timeStamp);
    }
}
