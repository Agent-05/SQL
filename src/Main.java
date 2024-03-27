import java.sql.*;
class Main{
    public static void main(String args[]){
        addStudent("john", "dutton", 5);
    }

    public static void addStudent(String firstName, String lastName, int id){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table student(id integer primary key not null, first_name text, last_name text);");
            s.execute("INSERT INTO student (first_name, last_name) VALUES (\'firstName\', \'lastName\')");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


}
