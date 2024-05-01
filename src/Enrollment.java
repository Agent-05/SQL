//section id and student id
//then the program can go through and look for all sections ids that match and put them in one group
import java.sql.*;
public class Enrollment {
    int id = -1;
    int student_id = -1;

    public Enrollment(int id, int student_id)
    {
        this.id = id;
        this.student_id = student_id;
        addEnrollment();
    }
    public Enrollment(int student_id)
    {
        this.student_id = student_id;
        addEnrollment();
    }

    public void addEnrollment(){
        boolean can = true;
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists enrollment(id INTEGER NOT NULL, student_id INTEGER NOT NULL, FOREIGN KEY (id) REFERENCES section(id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE ON UPDATE CASCADE);");
            ResultSet rs = s.executeQuery("select * from enrollment");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("student_id") == student_id && rs.getInt("id") == id)
                {
                    can = false;
                }
            }
            try{
                if(can)
                    s.execute("INSERT INTO enrollment (id, student_id) VALUES ("+id+", "+student_id+")");
                //System.out.println(id);
                //System.out.println("If you see this, you need to fix sections, the id isnt updating correctly. Dont delete the select from max value line because it currently makes the jlist work");
                //System.out.println("Workpl2s");

            }
            catch(Exception e)
            {
                System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //updates or deletes student
    public void updateEnrollment(int id, int student_id){//must refer to it by id, pass -1 in as student_id to delete
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM enrollment;");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("id") == id)
                {
                    if(student_id == -1)
                    {
                        s.execute("DELETE FROM enrollment WHERE id="+id+";");
                    }else{
                        this.id = id;
                        this.student_id = student_id;
                        s.execute("UPDATE enrollment SET student_id="+student_id+" WHERE id="+id+";");
                    }
                }
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


    public int getId() {
        return id;
    }

    public int getStudentId() {
        return student_id;
    }

    @Override
    public String toString()
    {
        return Integer.toString(getStudentId());
    }

}
