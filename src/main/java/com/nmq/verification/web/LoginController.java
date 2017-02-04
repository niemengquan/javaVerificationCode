package com.nmq.verification.web;

import com.nmq.verification.generateimage.Image;
import com.nmq.verification.generateimage.ImageResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niemengquan on 2017/2/4.
 */
@Controller
@RequestMapping(value = "/loginController")
public class LoginController {
    @RequestMapping(value = "identify")
    public String indentify(Model model, ServletResponse response){
        try{
            ImageResult ir= Image.generateImage();
            model.addAttribute("file",ir.getName());
            model.addAttribute("tip",ir.getTip());
            //Cache.put(ir.getUniqueKey(),ir);
            Cookie cookie=new Cookie("note",ir.getUniqueKey());
            ((HttpServletResponse)response).addCookie(cookie );
        }catch (Exception err){
            err.printStackTrace();
        }
        return "login";
    }
}
