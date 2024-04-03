import java.sql.*;
import java.util.ArrayList;

//we pull all these things and store them in an array list when we start to have a reference to them
//and their id's

class Main{

    //you can call update student directly if you have a reference to it, otherwise
    //it returns an error if you try to create a new student with the same id
    public static void main(String[] args) {
        Student a = new Student(5, "John", "Dutton");
    }


    public static void addCourse(String courseName, String type, int id){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists student(id INTEGER NOT NULL AUTO_INCREMENT" + ", first_name text, last_name text, PRIMARY KEY(id));");

            try{
                //s.execute("INSERT INTO student (id, first_name, last_name) VALUES ("+id+", \'"+firstName+"\', \'"+lastName+"\')");
            }
            catch(Exception e)
            {
                System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //sections show the teacher that teachers a course, the course, and the students taking the course
    public static void addSections(ArrayList<String> courses, ArrayList<String> teachers, ArrayList<String> students, int id){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists student(id INTEGER NOT NULL AUTO_INCREMENT" + ", first_name text, last_name text, PRIMARY KEY(id));");

            try{
                //s.execute("INSERT INTO student (id, first_name, last_name) VALUES ("+id+", \'"+firstName+"\', \'"+lastName+"\')");
            }
            catch(Exception e)
            {
                System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


}
