import java.sql.*;

public class Student {

    int id = -1;
    public String firstName = "default";
    String lastName = "name";

    public Student(String fn, String ln)
    {
        firstName = fn;
        lastName = ln;
        addStudent();
    }

    public Student(int id, String fn, String ln)
    {
        this.id = id;
        firstName = fn;
        lastName = ln;
    }
    public void addStudent(){//schedule
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists student(id INTEGER NOT NULL AUTO_INCREMENT, first_name text, last_name text, PRIMARY KEY(id));");

            try{
                s.execute("INSERT INTO student (first_name, last_name) VALUES (\'"+firstName+"\', \'"+lastName+"\')");
                ResultSet rs = s.executeQuery("SELECT first_name, last_name FROM student;");
                id = rs.getInt("id");
            }
            catch(Exception e)
            {
                System.out.println("id already exists");
                //you can call a function to tell user that it doesnt work
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //updates or deletes student
    public void updateStudent(int id, String firstName, String lastName){//must refer to it by id, pass - in as first name to delete
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM student;");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("id") == id)
                {
                    if(firstName.equals("-"))
                    {
                        s.execute("DELETE FROM student WHERE id="+id+";");
                    }else{
                        this.firstName = firstName;
                        this.lastName = lastName;
                        //updating students' name
                        s.execute("UPDATE student SET first_name=\'"+firstName+"\' WHERE id="+id+";");
                        s.execute("UPDATE student SET last_name=\'"+lastName+"\' WHERE id="+id+";");
                    }
                }
            }
            con.close();
        }catch(Exception e){ }
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

    @Override
    public String toString()
    {
        return getFn() + ", " + getLn();
    }

}
