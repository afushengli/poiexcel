package com.fsl.poiexcel.interceptor;

import com.alibaba.fastjson.JSON;
import com.fsl.poiexcel.common.ResponseCode;
import com.fsl.poiexcel.common.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登陆检查，
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Object user = request.getSession().getAttribute("loginUser");
        if(user == null){



            String  errMsg = JSON.toJSONString(new ServerResponse(ResponseCode.NO_LOGIN_ERROR.getCode(), ResponseCode.NO_LOGIN_ERROR.getMsg(), null));

            returnJson(response,new ServerResponse(ResponseCode.NO_LOGIN_ERROR.getCode(), ResponseCode.NO_LOGIN_ERROR.getMsg(), null));

            //未登陆，返回登陆页面
            //request.setAttribute("msg","没有权限请先登陆");
            //request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else{
            //已登陆，放行请求
            return true;
        }

    }


    private void returnJson(HttpServletResponse response, ServerResponse rs) throws Exception{

   /*     response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());//在文件输出流上套一个对象输出流

        try {
            oos.writeObject(json);

        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (oos != null)
                oos.close();
        }*/

        response.setContentType("text/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String json = JSON.toJSONString(rs);
        out.println(json);
        out.flush();
        out.close();

        try {
            out.println(json);
            out.flush();
        } catch (Exception e) {
            logger.error("response error",e);
        } finally {
            if (out != null)
                out.close();
        }



    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
