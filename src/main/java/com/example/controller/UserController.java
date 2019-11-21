package com.example.controller;

import com.example.Exception.UserNotFoundException;
import com.example.demo.Emp;
import com.example.demo.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/json")
    @ResponseBody
    public Model json(@RequestBody String json,Model model){
        System.out.println(json);
        ArrayList list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        String name = "zhangsan";
        return model;
    }

    @RequestMapping("{username:[a-z0-9_]+}/{id}")
    public String userProfile(@PathVariable String username,@PathVariable int id){
        return username+id;
    }

    @RequestMapping("{id}")
    public String getUser(@PathVariable int id, Model model){
        User user = userService.findUserById(id);
        if(user == null){
            throw new UserNotFoundException("User not Found");
        }
        model.addAttribute("user", user);
        return "zhangsan1";
    }

    @RequestMapping("update")
    @ResponseBody
    public String saveUser(@RequestParam User user){
        userService.saveUser(user);
        return "修改成功";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public String handlerUserNotFound(HttpServletRequest request,UserNotFoundException unfe){
        System.err.println(request.getRequestURI()+" = "+unfe);
        if(AnnotationUtils.findAnnotation(unfe.getClass(), ResponseStatus.class) != null){
            throw unfe;
        }
        return "error";
    }


    @RequestMapping("/lisi")
    public String getEmp(Emp emp){
        emp.setName("张三");
        emp.setTitle("瑰异");
//        model.addAttribute("emp", emp);
        return "lisi";
    }



    @RequestMapping("/create")
    public String createPage(User user){
        return "create";
    }

    @PostMapping("/createuser")
    public String createUser(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            return "/create";
        }
        if(userService.saveUser(user) != null){
            redirectAttributes.addFlashAttribute("message", "insert success");
            return "redirect:/demo/hello";
        }

        return "redirect:/demo/hello";
    }

    @GetMapping("/login")
    public String login(@RequestParam("next") Optional<String> next,Model model){
        model.addAttribute("next", next.orElse("/demo/hello"));
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("next") Optional<String> next, HttpSession session, Emp emp, RedirectAttributes redirectAttributes){
        session.setAttribute("CURRENT_EMP", emp);
        redirectAttributes.addFlashAttribute(emp);
//        System.out.println("登陆成功");
//        System.out.println(next.orElse("/demo/hello"));
        return "redirect:".concat(next.orElse("/demo/hello"));
    }

    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }

    @PostMapping("/upload1")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file){
        long startTime = System.currentTimeMillis();

        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream("D:/upload/"+new Date().getTime()+file.getOriginalFilename()));
            bis = new BufferedInputStream(file.getInputStream());
            int tmp = 0;
            while((tmp = bis.read()) != -1){
                bos.write(bis.read());
            }
            bos.flush();
            bos.close();
            bis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)+"ms");
        return "success";
    }

    @PostMapping("/upload2")
    @ResponseBody
    public String handleFileUpload2(@RequestParam("file") MultipartFile file){
        long startTime = System.currentTimeMillis();
        String path = "D:/upload/"+new Date().getTime()+file.getOriginalFilename();

        File newFile = new File(path);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)+"ms");
        return "success";
    }

    @PostMapping("/upload3")
    @ResponseBody
    public String handleFileUpload3(HttpServletRequest request){

        long startTime = System.currentTimeMillis();

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if(file != null){
                    String path = "D:/upload/"+new Date().getTime()+file.getOriginalFilename();
                    try {
                        file.transferTo(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime)+"ms");
        return "success";
    }

    @RequestMapping("/something")
    public ResponseEntity<String> handle(HttpEntity<String[]> requestEntity){
//        Collection<List<String>> requestHeader = requestEntity.getHeaders().values();
//        Iterator iter = requestHeader.iterator();
//        while(iter.hasNext()){
//            System.out.println(iter.next());
//        }
//        System.out.println(requestHeader);


//        for(Map.Entry e : requestEntity.getHeaders().entrySet()){
//            System.out.println(e.getKey()+":" + e.getValue());
//        }


//        byte[] requestBody = requestEntity.getBody();
//        if(requestBody != null){
//            for (byte s:requestBody
//            ) {
//                System.out.println(s);
//            }
//        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<String>("Hello World",responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping("/download")
    @ResponseBody
    public String download(HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName = new String("D:\\upload\\1544523167972spring-boot-reference.pdf".getBytes("utf-8"), "iso-8859-1");
        File file = new File(fileName);
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());
        if(file.exists()){
            OutputStream ous = null;
            BufferedInputStream bis = null;
            try {
                ous = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[1024];
                int i = 0;
                while((i = bis.read(buffer)) != -1){
                    ous.write(buffer,0,i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    ous.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }


}
