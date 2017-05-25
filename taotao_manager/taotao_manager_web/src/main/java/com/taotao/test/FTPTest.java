package com.taotao.test;

import com.taotao.utils.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class FTPTest {

	@Test
	public void testFtpClient() throws Exception {
		// 创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
		// 创建ftp链接
		/* IP地址和端口号,端口号默认21,可不写 */
		ftpClient.connect("192.168.53.60", 21);
		// 登录ftp服务器,使用用户名和密码
		ftpClient.login("root", "123456");
		// 上传文件

		// 读取本地文件
		File file = new File("F:\\图片\\波波.gif");
		FileInputStream inputStream = new FileInputStream(file);

		// 设置上传的路径
		String pathname = "/usr/local/nginx/html/images";
		ftpClient.changeWorkingDirectory(pathname);

		// 默认使用2进制格式传输,也就是文本格式,所以图片会打不开
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		/*
		* 1.服务器端文档名
		* 2.上传文档的inputStrem
		* */
		ftpClient.storeFile("bobo2.gif", inputStream);
		// 关闭连接
		ftpClient.logout();
	}

	@Test
	public void TestFtpUtil() throws Exception {
		File file = new File("F:\\图片\\女优.png");
		FileInputStream inputStream = new FileInputStream(file);
		FtpUtil.uploadFile("192.168.53.60", 21, "root", "123456", "/usr/local/nginx/html/images", "/2015/09/04", "女优.png", inputStream);
	}

}
