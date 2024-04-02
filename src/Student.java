import java.sql.*;

public class Student {

    int id = -1;
    public String firstName = "default";
    String lastName = "name";
    Schedule s = null;

    public Student(int id, String fn, String ln, Schedule s)
    {
        this.id = id;
        firstName = fn;
        lastName = ln;
        this.s = s;
    }

    public void addStudent(){//schedule
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists student(id INTEGER NOT NULL AUTO_INCREMENT" + ", first_name text, last_name text, PRIMARY KEY(id));");

            try{
                s.execute("INSERT INTO student (id, first_name, last_name) VALUES ("+id+", \'"+firstName+"\', \'"+lastName+"\')");
            }
            catch(Exception e)
            {
                updateStudent(id, firstName, lastName);
                System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //updates or deletes student
    public static void updateStudent(int id, String firstName, String lastName){//must refer to it by id, pass - in as first name to delete
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM student;");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("id") == id)
                {
                    if(rs.getString("first_name").equals("-"))
                    {
                        s.execute("DELETE FROM student WHERE student_id="+id+" OR last_name=’"+lastName+"’;");
                    }else{
                        //updating students' name
                        s.execute("UPDATE student SET first_name=’"+firstName+"’ WHERE student_id="+id+";");
                        s.execute("UPDATE student SET last_name=’"+lastName+"’ WHERE student_id="+id+";");
                    }
                }
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


    public int getId() {
        return id;
    }

    public String getFn() {
        return firstName;
    }

    public String getLn() {
        return lastName;
    }

    public Schedule getS() {
        return s;
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
