package com.ymw.love.system.service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月22日  类说明：阿里 Oss 文件存储
 */
@Service
public class OssFileService {

	@Value("${aliyun.oss.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.keyId}")
	private String keyId;

	@Value("${aliyun.oss.keySecret}")
	private String keySecret;

	@Value("${aliyun.oss.bucketName.lfshz}")
	private String lfshzBucketName;

	@Value("${aliyun.oss.path.lfshz-images}")
	private String lfshzPath;

	private String url = "https://lfshz.oss-cn-shenzhen.aliyuncs.com/";
 
	private static List<String> listImg=null;
	
	private static List<String> listVideo=null;
	
	
	static {
		listImg = new ArrayList<String>();
		listImg.add("bmp");
		listImg.add("jpg");
		listImg.add("jpeg");
		listImg.add("png");
		listImg.add("tiff");
		listImg.add("gif");
		listImg.add("pcx");
		listImg.add("tga");
		listImg.add("exif");
		listImg.add("fpx");
		listImg.add("svg");
		listImg.add("psd");
		listImg.add("cdr");
		listImg.add("pcd");
		listImg.add("dxf");
		listImg.add("ufo");
		listImg.add("eps");
		listImg.add("ai");
		listImg.add("raw");
		listImg.add("wmf");
		
		listVideo =new ArrayList<String>();
		listVideo.add("rm");
		listVideo.add("rmvb");
		listVideo.add("avi");
		listVideo.add("mp4");
		listVideo.add("3gp");
		listVideo.add("mkv");
		
	}

	
	
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param type 1：图片   2：视频
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public List<String> fileUpload(MultipartFile[] file,Integer type,String fileName) throws InterruptedException, ExecutionException {

		// 验证图片格式
		verifyFormat(file,type);
		
		List<String> list = new ArrayList<String>();

		if (file.length == 1) {
			list.add(single(file[0], lfshzPath,fileName));
		} else {
			list.addAll(multi(file, lfshzPath,fileName));
		}

		return list;
	}

	//验证图片格式   
	private void verifyFormat(MultipartFile[] file,Integer type) {
		
		if(file.length>10) {
			throw new MissRequiredParamException(HintTitle.System.file_scope_error);
		}
		
		
		for (int i = 0; i < file.length; i++) {
			String name=  file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf(".")+1, file[i].getOriginalFilename().length());
			if(type==1 && !listImg.contains(name)) {//图片格式
				throw new MissRequiredParamException(HintTitle.System.img_format_error);
			}else  if(type==2 && !listVideo.contains(name)) {//视频格式
				throw new MissRequiredParamException(HintTitle.System.video_format_error);
			}
		}
    
	}
	public static void delay() {
		try {
			Thread.sleep(3);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


		// 单个文件上传
	public String single(MultipartFile file, String path,String fileName) {
		delay();
		if (path == null) {
			path = lfshzPath;
		}

		OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
		
		StringBuffer name =new StringBuffer(path);//文件夹
		if(StringUtils.isNotEmpty(fileName)) {//使用自定义名字
			name.append(fileName);
		}else {//随机取
		name.append(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDDHHmm))
		.append(SnowFlakeUtil.getStochastic(6));
		}
		try {
			name.append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length()));
			ossClient.putObject(lfshzBucketName,  name.toString(), file.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}

		return url + name.toString();
	}

	// 多个文件上传
	private List<String> multi(MultipartFile[] file, String path,String fileName) throws InterruptedException, ExecutionException {
//		List<String> list = new ArrayList<String>();
//		List<Future> listFuture = new ArrayList<Future>();
		Executor executor = Executors.newCachedThreadPool();
		List<CompletableFuture<String>> urlFuture = Arrays.asList(file).stream().map(f -> CompletableFuture
				.supplyAsync(() -> single(f, path, fileName), executor))
				.collect(Collectors.toList());
		return urlFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());


//		ExecutorService executorService = Executors.newFixedThreadPool(file.length);
//
//
//
//		for (int i = 0; i < file.length; i++) {
//			Future fu = executorService.submit(new SelectTask<>(this, "single", new Object[] { file[i], path ,fileName}));
//			listFuture.add(fu);
//		}
//
//		for (Future future : listFuture) {
//			while (true) {
//				if (future.isDone() && !future.isCancelled()) {
//					list.add((String) future.get());
//					break;
//				} else {
//					Thread.sleep(2);
//				}
//
//			}
//		}
//		executorService.shutdown();
//		return list;
	}

	public class SelectTask<T> implements Callable<T> {

		private Object object;
		private Object[] args;
		private String methodName;

		public SelectTask(Object object, String methodName, Object[] args) {
			this.object = object;
			this.args = args;
			this.methodName = methodName;
		}

		@Override
		public T call() throws Exception {
			Method method = object.getClass().getMethod(methodName,MultipartFile.class,String.class,String.class); // 此处应用反射机制，String.class是根据实际方法参数设置的
			return (T) method.invoke(object, args);
		}
	}
	
}
