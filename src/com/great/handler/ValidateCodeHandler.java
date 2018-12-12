package com.great.handler;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.great.util.ValidateCodeUtil;
/*
 * ��֤����
 * */
@Controller // ��ע�͵ĺ����ǽ��������ó�Ϊ������ύ����������
@RequestMapping("/validateCode")
public class ValidateCodeHandler {
	
	@RequestMapping(value = "/validateCode.action", method = RequestMethod.GET)
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		ValidateCodeUtil vc=new ValidateCodeUtil();
		
		String keyCode=vc.getCode();
		BufferedImage image=vc.getImage();
		session.setAttribute("keyCode", keyCode);
		
		// ��ֹͼ�񻺴档
        response.setHeader("Pragma", "no-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
        OutputStream os = response.getOutputStream();  
        ImageIO.write(image, "jpeg", os); 
        os.flush();
	}
}
