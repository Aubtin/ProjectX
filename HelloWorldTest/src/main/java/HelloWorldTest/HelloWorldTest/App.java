package HelloWorldTest.HelloWorldTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;






import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.servlet.SparkApplication;
import static spark.Spark.*;
import freemarker.*;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.template.freemarker.FreeMarkerEngine; 
import static spark.Spark.*;
//import spark.servlet.SparkApplication;
/**
 * Hello world!
 *
 */
public class App implements SparkApplication
{
//    public static void main( String[] args )
//    {
// 
//    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        try {
            Template temp = freeMarkerConfiguration.getTemplate("test.ftl");
            freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(App.class, (String)freeMarkerConfiguration.getTemplate("test.ftl")));
            freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        // get all post (using HTTP get method)
        get("/posts", (request, response) -> {
                response.status(200);
                response.type("text/html");
                Map<String, Object> attributes = new HashMap<>();
  //              attributes.put("posts", model.getAllPosts());
                return freeMarkerEngine.render(new ModelAndView(attributes, "posts.ftl"));
        });
        
    } 

}
