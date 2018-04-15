package com.itmuch.cloud.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

/**
 * User: hk Date: 2017/8/10 下午4:31 version: 1.0
 */
public class FTPUtil implements AutoCloseable {

	private FTPClient ftpClient;

	public FTPUtil(String serverIP, int port, String userName, String password) throws IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(serverIP, port);
		ftpClient.login(userName, password);
		ftpClient.setBufferSize(1024);// 设置上传缓存大小
		ftpClient.setControlEncoding("UTF-8");// 设置编码
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置文件类型
	}

	/** 
     * 下载文件 
     * @param ftpDirName ftp目录名 
     * @param ftpFileName ftp文件名 
     * @param localFileFullName 本地文件名 
     * @return 
     *  @author xxj 
     */  
	public boolean downloadFile(String ftpDirName, String ftpFileName, String localFileFullName) {
		try {
			if ("".equals(ftpDirName))
				ftpDirName = "/";
			String dir = new String(ftpDirName.getBytes("GBK"), "iso-8859-1");
			if (!ftpClient.changeWorkingDirectory(dir)) {
				System.out.println("切换目录失败：" + ftpDirName);
				return false;
			}
			FTPFile[] fs = ftpClient.listFiles();
			String fileName = new String(ftpFileName.getBytes("GBK"), "iso-8859-1");
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					FileOutputStream is = new FileOutputStream(new File(localFileFullName));
					ftpClient.retrieveFile(ff.getName(), is);
					is.close();
					System.out.println("下载ftp文件已下载：" + localFileFullName);
					return true;
				}
			}
			System.out.println("下载ftp文件失败：" + ftpFileName + ";目录：" + ftpDirName);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 上 /** 
     *  
     * ftp上传文件 (使用inputstream) 
     * @param localFileName 待上传文件 
     * @param ftpDirName ftp 目录名 
     * @param ftpFileName ftp目标文件 
     * @return true||false 
     */  
	public boolean uploadFile(FileInputStream uploadInputStream, String ftpDirName, String ftpFileName)
			throws IOException {
		Debug.printFormat("准备上传 [流] 到 ftp://{0}/{1}", ftpDirName, ftpFileName);
		// if(StringExtend.isNullOrEmpty(ftpDirName))
		// ftpDirName="/";
		if (StringExtend.isNullOrEmpty(ftpFileName))
			throw new RuntimeException("上传文件必须填写文件名！");

		try {
			// 设置上传目录(没有则创建)
			if (!createDir(ftpDirName)) {
				throw new RuntimeException("切入FTP目录失败：" + ftpDirName);
			}
			ftpClient.setBufferSize(1024);
			// 解决上传中文 txt 文件乱码
			ftpClient.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");

			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 上传
			String fileName = new String(ftpFileName.getBytes("GBK"), "iso-8859-1");
			if (ftpClient.storeFile(fileName, uploadInputStream)) {
				uploadInputStream.close();
				Debug.printFormat("文件上传成功：{0}/{1}", ftpDirName, ftpFileName);
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
		}
	}

	/**
	 * 切换目录
	 *
	 * @param path
	 *            创建目录
	 * @return 创建标志
	 * @throws IOException
	 *             异常
	 */
	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	/**
	 * 创建目录(有则切换目录，没有则创建目录)
	 * 
	 * @param dir
	 * @return
	 */
	public boolean createDir(String dir) {
		if (StringExtend.isNullOrEmpty(dir))
			return true;
		String d;
		try {
			// 目录编码，解决中文路径问题
			d = new String(dir.toString().getBytes("GBK"), "iso-8859-1");
			// 尝试切入目录
			if (ftpClient.changeWorkingDirectory(d))
				return true;
			dir = StringExtend.trimStart(dir, "/");
			dir = StringExtend.trimEnd(dir, "/");
			String[] arr = dir.split("/");
			StringBuffer sbfDir = new StringBuffer();
			// 循环生成子目录
			for (String s : arr) {
				sbfDir.append("/");
				sbfDir.append(s);
				// 目录编码，解决中文路径问题
				d = new String(sbfDir.toString().getBytes("GBK"), "iso-8859-1");
				// 尝试切入目录
				if (ftpClient.changeWorkingDirectory(d))
					continue;
				if (!ftpClient.makeDirectory(d)) {
					System.out.println("[失败]ftp创建目录：" + sbfDir.toString());
					return false;
				}
				System.out.println("[成功]创建ftp目录：" + sbfDir.toString());
			}
			// 将目录切换至指定路径
			return ftpClient.changeWorkingDirectory(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 自动关闭资源
	 */
	@Override
	public void close() throws Exception {
		if (ftpClient != null && ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}
}