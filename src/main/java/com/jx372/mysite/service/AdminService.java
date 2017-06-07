package com.jx372.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.mysite.repository.AdminDao;
import com.jx372.mysite.vo.AdminVo;

@Service
public class AdminService {
	private static final String SAVE_PATH = "/uploads";
	private static final String PREFIX_URL = "/upload/images/";
	
	@Autowired
	private AdminDao adminDao;
	
	
	public AdminVo getInfo() {
		return adminDao.getSelectAll();
	}
	
	public void updateInfo(AdminVo adminVo){
		adminDao.update(adminVo);
	}

	public String restore(MultipartFile profilepath) {
		String url = "";
		try{
			
			if(profilepath.isEmpty()==true){
				return url;
			}
			
			String originalFileName = profilepath.getOriginalFilename();
			String extName = originalFileName.substring(
					originalFileName.lastIndexOf('.'),
					originalFileName.length()
					);
			String convertedName = createFileName(extName);
			
			writeFile(profilepath, convertedName);
			
			url = PREFIX_URL+convertedName;
		//System.out.println(url);	
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		return url;
	}
	
	private String createFileName(String extName){
		String fileName = "";
		//일단 확장자가 무엇인지 받은다음에 파일이 업로드된 시간으로 파일이름을 설정한다.
		//파일이름이 중복되는 현상을 방지하기 위함으로 꼭 시간으로 하지 않아도 되고 여러가지 generate기술을 사용하면 된다
		Calendar calendar = Calendar.getInstance();
		fileName+=calendar.get(Calendar.YEAR);
		fileName+=calendar.get(Calendar.MONTH);
		fileName+=calendar.get(Calendar.DATE);
		fileName+=calendar.get(Calendar.HOUR);
		fileName+=calendar.get(Calendar.MINUTE);
		fileName+=calendar.get(Calendar.SECOND);
		fileName+=calendar.get(Calendar.MILLISECOND);
		fileName+=extName; //마지막엔 확장자를 붙여준다.
		
		return fileName;
	}
	
	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException{
		byte[] fileData = multipartFile.getBytes();
		//데이터를 진짜 저장하는 로직으로 바이트 단위로 데이터를 저장한다.
		//서버쪽에 저장하고 싶은 path에 파일을 저장시키는 설정을 하고 저장시킨다.
		FileOutputStream fos = new FileOutputStream(SAVE_PATH+"/"+saveFileName);
		fos.write(fileData);
		fos.close();
	}
}
