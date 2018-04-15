package com.itmuch.cloud.ueditor.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.itmuch.cloud.ueditor.define.AppInfo;
import com.itmuch.cloud.ueditor.define.BaseState;
import com.itmuch.cloud.ueditor.define.State;
import com.itmuch.cloud.utils.FTPUtil;

@Component
@ConfigurationProperties(prefix = "nginx")
public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	private static String fileurl;

	public static String getFileurl() {
		return fileurl;
	}

	public static void setFileurl(String fileurl) {
		StorageManager.fileurl = fileurl;
	}

	public static int getBufferSize() {
		return BUFFER_SIZE;
	}

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo("size", data.length);
		state.putInfo("title", file.getName());
		return state;
	}

	public static State saveFileByInputStream(HttpServletRequest request, InputStream is, String path, String picName,
			long maxSize) {

		State state = null;
		byte[] dataBuf = new byte[2048];

		try {
			// 转成字节流
			File file = new File("C:\\Users\\tongjiarong\\test\\" );
			if(!file.exists()) {
				file.mkdirs();
			}
			file = new File("C:\\Users\\tongjiarong\\test\\"+picName );
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outSTr = new FileOutputStream(file);
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			int rc = 0;
			while ((rc = is.read(dataBuf, 0, 100)) > 0) {
				outSTr.write(dataBuf, 0, rc);
			}

			// dataBuf = swapStream.toByteArray();
			swapStream.flush();
			swapStream.close();
			outSTr.close();
			
			state = upFTP(path, picName, file);
			return state;

		} catch (Exception e) {
			new BaseState(false, AppInfo.IO_ERROR);
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static State upFTP(String path, String picName, File file) throws FileNotFoundException, Exception {
		State state;
		FileInputStream uploadInputStream=new FileInputStream(file);
		
		
		FTPUtil ftpUtil = null;
		try {
			ftpUtil = new FTPUtil("127.0.0.1", 21, "ftp", "151705");
			ftpUtil.createDir(path);
			 ftpUtil.uploadFile(uploadInputStream,path,picName );
		} catch (Exception e) {
			ftpUtil.close();
			e.printStackTrace();
		}
		ftpUtil.close();

	
		
		state = new BaseState(true);
		state.putInfo("size",file.length());
		state.putInfo("title", file.getName());// 文件名填入此处
		state.putInfo("group", "");// 所属group填入此处
		state.putInfo("url", path+"/"+picName);// 文件访问的url填入此处

		file.deleteOnExit();
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path, String picName) {
		State state = null;
		byte[] dataBuf = new byte[2048];

		try {
			// 转成字节流
			File file = new File("C:\\Users\\tongjiarong\\test\\" );
			if(!file.exists()) {
				file.mkdirs();
			}
			file = new File("C:\\Users\\tongjiarong\\test\\"+picName );
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outSTr = new FileOutputStream(file);
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			int rc = 0;
			while ((rc = is.read(dataBuf, 0, 100)) > 0) {
				outSTr.write(dataBuf, 0, rc);
			}

			// dataBuf = swapStream.toByteArray();
			swapStream.flush();
			swapStream.close();
			outSTr.close();
			
			state = upFTP(path, picName, file);
			return state;

		} catch (Exception e) {
			new BaseState(false, AppInfo.IO_ERROR);
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo("size", targetFile.length());
		state.putInfo("title", targetFile.getName());

		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
}
