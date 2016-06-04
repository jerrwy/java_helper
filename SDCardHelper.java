package com.example.utils;

/**
 * Created by Administrator on 2016/6/4 0004.
 */

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * sdcard的存在于上下文无关
 *
 * @author waymon
 *
 */
public class SDCardHelper {

    /*
     * 存放在sdcard的根目录
     */
    public boolean saveFileToSdcardRoot(String fileName, byte[] data) {
        boolean flag = false;
        /*
         * 先判断sdcard的状态，是否存在
         */
        String state = Environment.getExternalStorageState();
        FileOutputStream outputStream = null;
        File rootFile = Environment.getExternalStorageDirectory(); // 获得sdcard的根路径
        /*
         * 表示sdcard挂载在手机上，并且可以读写
         */
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(rootFile, fileName);
            try {
                outputStream = new FileOutputStream(file);
                try {
                    outputStream.write(data, 0, data.length);
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }

    /*
     * 存放在sdcard下自定义的目录
     */
    public boolean saveFileToSdcardDir(String fileName, byte[] data) {
        boolean flag = false;
        /*
         * 先判断sdcard的状态，是否存在
         */
        String state = Environment.getExternalStorageState();
        FileOutputStream outputStream = null;
        File rootFile = Environment.getExternalStorageDirectory(); // 获得sdcard的根路径
        /*
         * 表示sdcard挂载在手机上，并且可以读写
         */
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(rootFile.getAbsoluteFile() + "/txt");
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                outputStream = new FileOutputStream(new File(file, fileName));
                try {
                    outputStream.write(data, 0, data.length);
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }

    /*
     * 用于读取sdcard的数据
     */
    public String readContextFromSdcard(String fileName) {

        String state = Environment.getExternalStorageState();
        File rooFile = Environment.getExternalStorageDirectory(); // 获得sdcard的目录

        FileInputStream inputStream = null;// 用于度取数据的流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // 用于存放独处的数据

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(rooFile.getAbsoluteFile() + "/txt/");// 在sdcard目录下创建一个txt目录
            File file2 = new File(file, fileName);
            int len = 0;
            byte[] data = new byte[1024];
            if (file2.exists()) {
                try {
                    inputStream = new FileInputStream(file2);
                    try {
                        while ((len = inputStream.read(data)) != -1) {
                            outputStream.write(data, 0, data.length);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new String(outputStream.toByteArray());
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 对文件进行分类的保存到固定的文件中去
     *
     * @param fileName
     * @param data
     */
    public void saveFileToSdcardBySuff(String fileName, byte[] data) {
        // File file = Environment.getExternalStoragePublicDirectory();
        // 保存文件的目录
        File file = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            /*
             * 将不同的文件放入到不同的类别中
             */
            if (fileName.endsWith(".mp3")) {
                file = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".png")
            || fileName.endsWith(".gif")) {
                file = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            } else if (fileName.endsWith(".mp4") || fileName.endsWith(".avi")
            || fileName.endsWith(".3gp")) {
                file = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            } else {
                file = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(new File(file, fileName));
                try {
                    outputStream.write(data, 0, data.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /*
     * 删除一个文件
     */
    public boolean deleteFileFromSdcard(String folder, String fileName) {
        boolean flag = false;
        File file = Environment.getExternalStorageDirectory();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File exitFile = new File(file.getAbsoluteFile() + "/" + folder);
            if (exitFile.exists()) {
                exitFile.delete();
            }
        }
        return flag;
    }
}