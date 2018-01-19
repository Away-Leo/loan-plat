package com.pingxun.biz.ibeesaas.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class Imagebase64 {

	/**
	 * 将图片变成 Base64编码字符串 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param imgFilePath
	 * @return
	 */
	public static String getImage64Str(String imgFilePath) {
		byte[] data = null;
		// 读取图片字节数组
		InputStream in = null;
		try {
			in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 返回Base64编码过的字节数组字符串
		return Base64.encodeBase64String(data);
	}

	/**
	 * 将 Base64编码字符串解码为字节数组，并以字节流方式输出图片
	 * 
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath, String imgFileName) {
		// 图像数据为空
		if (imgStr == null) {
			return false;
		}
		OutputStream out = null;
		try {
			// Base64 解码
			byte[] bytes = Base64.decodeBase64(imgStr);
			// 调整异常数据
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			File file = new File(imgFilePath);
			if(!file.exists()){
				file.mkdirs();
			}
			out = new FileOutputStream(new File(imgFilePath, imgFileName));
			out.write(bytes);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
