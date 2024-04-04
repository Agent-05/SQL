import java.sql.*;
import java.util.ArrayList;

//we pull all these things and store them in an array list when we start to have a reference to them
//and their id's

class Main{

    //you can call update student directly if you have a reference to it, otherwise
    //it returns an error if you try to create a new student with the same id
    public static void main(String[] args) {
        Student a = new Student(5, "lp", "Dutton");
        Teacher b = new Teacher(5, "Jim", "Dutton");
        Course c = new Course(5, "Jim", 1);
        Enrollment d = new Enrollment(5, 5);
        Section e = new Section(5, 5, 5);
    }




}
