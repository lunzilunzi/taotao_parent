package com.taotao.test;

import com.taotao.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFS_Test {
	String absPath = "D:\\IDEA_Project\\framework\\base\\taotao_parent\\taotao_manager\\taotao_manager_web\\src\\main\\resources\\properties\\client.conf";
	String path = "classpath:properties/client.conf";

	@Test
	public void testUpload() throws Exception {
//		1、把FastDFS提供的jar包添加到工程中
//		2、初始化全局配置。加载一个配置文件。
		ClientGlobal.init(absPath);
//		3、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
//		4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
//		5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
//		6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		7、直接调用StorageClient对象方法上传文件即可。

		String filePath = "D:\\file\\psb.jpg";
		String expan = "jpg";
		String[] strings = storageClient.upload_file(filePath, expan, null);
		for (String s : strings) {
			System.out.println(s);
		}
	}

	@Test
	public void testFastDFSClient() throws Exception {
		FastDFSClient client = new FastDFSClient(path);
		String jpg = client.uploadFile("D:\\file\\abc.jpg", "jpg");
		System.out.println(jpg);
	}
}















