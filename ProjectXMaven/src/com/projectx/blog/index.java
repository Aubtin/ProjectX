package com.projectx.blog;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;






 

import org.watertemplate.Template;

import static spark.template.water.WaterTemplateEngine.render;
import static spark.template.water.WaterTemplateEngine.waterEngine;


import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.servlet.SparkApplication;
import static spark.Spark.*;
//import freemarker.*;
//import freemarker.cache.ClassTemplateLoader;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import spark.template.freemarker.FreeMarkerEngine; 

public class index implements SparkApplication
{

    
    @Override
    public void init() 
    {
        
//        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
//        Configuration freeMarkerConfiguration = new Configuration();
//        try {
//            freeMarkerConfiguration.setDirectoryForTemplateLoading(new File("C:/Users/Aubtin/Documents/workspaceFahrzin/ProjectX/ProjectXMaven/WebContent/Web-Page"));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(index.class, "index.ftl"));
//        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        
        get("/home", (req, res) -> {
            return render(new HelloPage(), req);
        }, waterEngine());

        
        get("/index.jsp", (request, response) -> {
            response.status(401);
            return "Go Away!!!";
        });
        
//        post("/index", (request, response) -> {
//            public ModelAndView handle(request, response) {
//                String username = request.queryParams("username");
//                Map<String, Object> attributes = new HashMap<>();
//                attributes.put("message", "Welcome " + username + ", Time now is: " + new Date());
//                return modelAndView(attributes, "home.ftl.html");
//            }
//        });
        
//        get("/index", (request, response) -> {
//                    Map<String, Object> attributes = new HashMap<>();
//                    return new ModelAndView(attributes, "bulk-upload.ftl");
//                }, new FreeMarkerEngine()); 

       }
    
    public class HelloPage extends Template {
        public HelloPage() { add("water", "Water"); }

        @Override
        protected String getFilePath() { return "/Users/Aubtin/Documents/workspaceFahrzin/ProjectX/ProjectXMaven/WebContent/Web-Page/index.ftl"; }
    }
}
