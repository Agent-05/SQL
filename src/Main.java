import java.sql.*;
class Main{
    public static void main(String args[]){
        addStudent("john", "dutton", 1);
        addStudent("jim", "dutton", 2);
        addStudent("jane", "dutton", 3);
        addStudent("jerry", "dutton", 4);
        printNames();
    }

    public static void addStudent(String firstName, String lastName, int id){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists student(id INTEGER NOT NULL AUTO_INCREMENT" + ", first_name text, last_name text, PRIMARY KEY(id));");
            boolean a = s.execute("INSERT INTO student (id, first_name, last_name) VALUES ("+id+", \'"+firstName+"\', \'"+lastName+"\')");
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    public static void printNames(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM student;");
            while(rs!=null&&rs.next())
                System.out.println(rs.getString("last_name")+", "+rs.getString("first_name"));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


}
