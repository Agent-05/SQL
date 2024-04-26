import java.sql.*;

public class Teacher {
    //teacher teaches sections but the program handles that, the teacher doesnt need to know what
    //or who they teach, the enrollment and section will have the ids of who is teaching what and who
    //section is basically periods
    int id = -1;
    public String firstName = "default";
    String lastName = "name";

    public Teacher(String fn, String ln)
    {
        firstName = fn;
        lastName = ln;
        addTeacher();
    }
    public Teacher(int id, String fn, String ln)
    {
        this.id = id;
        firstName = fn;
        lastName = ln;
    }

    public void addTeacher(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists teacher(id INTEGER NOT NULL AUTO_INCREMENT, first_name text, last_name text, PRIMARY KEY(id));");

            try{
                s.execute("INSERT INTO teacher (first_name, last_name) VALUES (\'"+firstName+"\', \'"+lastName+"\')");
                ResultSet rs = s.executeQuery("SELECT first_name, last_name FROM teacher;");
                id = rs.getInt("id");
            }
            catch(Exception e)
            {
                //System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //updates or deletes student
    public void updateTeacher(int id, String firstName, String lastName){//must refer to it by id, pass - in as first name to delete
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM teacher;");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("id") == id)
                {
                    if(firstName.equals("-"))
                    {
                        System.out.println("deleted");
                        s.execute("DELETE FROM teacher WHERE id="+id+";");
                    }else{
                        this.firstName = firstName;
                        this.lastName = lastName;
                        //updating teachers' names
                        s.execute("UPDATE teacher SET first_name=\'"+firstName+"\' WHERE id="+id+";");
                        s.execute("UPDATE teacher SET last_name=\'"+lastName+"\' WHERE id="+id+";");
                    }
                }
            }

            rs = s.executeQuery("select * from section");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("teacher_id") == id)
                {
                        s.execute("DELETE FROM section WHERE teacher_id="+id+";");
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

    @Override
    public String toString()
    {
        return getFn() + ", " + getLn();
    }
}