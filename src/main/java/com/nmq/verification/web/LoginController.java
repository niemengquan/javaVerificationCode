package com.nmq.verification.web;

import com.nmq.verification.generateimage.Cache;
import com.nmq.verification.generateimage.Image;
import com.nmq.verification.generateimage.ImageResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niemengquan on 2017/2/4.
 */
@Controller
@RequestMapping(value = "/loginController")
public class LoginController {
    @RequestMapping(value = "/identify.do")
    public String indentify(Model model, ServletResponse response){
        try{
            ImageResult ir= Image.generateImage();
            model.addAttribute("file",ir.getName());
            model.addAttribute("tip",ir.getTip());
            Cache.put(ir.getUniqueKey(),ir);
            Cookie cookie=new Cookie("note",ir.getUniqueKey());
            ((HttpServletResponse)response).addCookie(cookie );
        }catch (Exception err){
            err.printStackTrace();
        }
        return "login";
    }

    @RequestMapping(value = "/login.do")
    @ResponseBody
    public String login(String location, HttpServletRequest request,String userName,String password){
        Cookie[] cookies = request.getCookies();
        Cookie note=null;
        for(Cookie cookie:cookies){
            if("note".equals(cookie.getName())){
                note=cookie;
                break;
            }
        }
        if(null==note){
            return "ERROR";
        }
        ImageResult imageResult = Cache.get(note.getValue());
        Cache.remove(note.getValue());
        if(null==location||"".equals(location)){
            return "ERROR";
        }
        if(validate(location,imageResult)){
            return "OK";
        }
        return "ERROR";
    }

    private boolean validate(String locationString, ImageResult imageResult) {
        String[] resultArray=locationString.split(";");
        int[][] array=new int[resultArray.length][2];
        for(int i=0;i<resultArray.length;i++){
            String[] temp = resultArray[i].split(",");
            array[i][0]=Integer.parseInt(temp[0])+150-10;
            array[i][1]=Integer.parseInt(temp[1])+300;
        }
        //所选答案的数量与目标答案数量不相等，返回错误
        if(array.length!=imageResult.getKeySet().size()){
            return false;
        }
        for(int i=0;i<array.length;i++){
            int location=location(array[i][1],array[i][0]);
            if(!imageResult.getKeySet().contains(location)){
                return false;
            }
        }
        return true;
    }

    private int location(int x, int y) {
        if(y>=0&&y<75){
            return xLocation(x);
        }else if(y>=75&&y<=150){
           return xLocation(x)+4;
        }else{
            //脏数据
            return -1;
        }
    }

    private int xLocation(int x) {
        if(x>=0&&x<75){
            return 0;
        }else if(x>=75&&x<150){
            return 1;
        }else if(x>=150&&x<225){
            return 2;
        }else if(x>=225&&x<=300){
            return 3;
        }else{
            //脏数据
            return -1;
        }
    }
}
