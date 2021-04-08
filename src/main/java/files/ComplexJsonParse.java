package files;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main (String [] args){
    JsonPath js  =new JsonPath(Payload.CoursePrice());

   int count =  js.getInt("courses.size()");//count of array
   System.out.println(count);

   int amount = js.getInt("dashboard.purchaseAmount");

   System.out.println(amount);

  String titleFirstCourse =  js.get("courses[0].title");

  System.out.println(titleFirstCourse);

  //print all course titles
System.out.println("############# All course titles #############");
        for (int i=0; i < count; i++ ){
        String title=   js.get("courses["+i+"].title");
        System.out.println(title);

        int price= js.get("courses["+i+"].price");
        System.out.println(price);
        }
        System.out.println("Print no of the copies sold by RPA Courses");

        for (int i=0; i < count; i++ ){
            String courseTitle= js.get("courses["+i+"].title");
            if (courseTitle.equalsIgnoreCase("RPA")){
                int copies= js.get("courses["+i+"].copies");
               System.out.println(copies);
            }
        }
    }
}
