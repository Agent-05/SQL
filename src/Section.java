//id, course id, teacher id that teaches it
import java.sql.*;

public class Section {
    int id = -1;
    int course_id = -1;
    int teacher_id = -1;

    public Section(int id, int course_id, int teacher_id)
    {
        this.id = id;
        this.course_id = course_id;
        this.teacher_id = teacher_id;
    }
    public Section(int course_id, int teacher_id)
    {
        this.course_id = course_id;
        this.teacher_id = teacher_id;
        addSection();
    }

    public void addSection(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists section(id INTEGER NOT NULL AUTO_INCREMENT, course_id INTEGER NOT NULL, teacher_id INTEGER NOT NULL, PRIMARY KEY(id));");

            try{
                s.execute("INSERT INTO section (course_id, teacher_id) VALUES ("+course_id+", "+teacher_id+")");
                ResultSet rs = s.executeQuery("SELECT course_id, teacher_id FROM section;");
                id = rs.getInt("id");

                System.out.println(id);
            }
            catch(Exception e)
            {
                System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //updates or deletes student
    public void updateSection(int id, int course_id, int teacher_id){//must refer to it by id, pass -1 in as course_id to delete
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM section;");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("id") == id)
                {
                    if(course_id == -1)
                    {
                        s.execute("DELETE FROM section WHERE id="+id+";");
                    }else{
                        this.id = id;
                        this.course_id = course_id;
                        this.teacher_id = teacher_id;
                        s.execute("UPDATE section SET course_id="+course_id+" WHERE id="+id+";");
                        s.execute("UPDATE section SET teacher_id="+teacher_id+" WHERE id="+id+";");
                    }
                }
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


    public int getId() {
        return id;
    }

    public int getCourseId() {
        return course_id;
    }
    public int getTeacherId() {
        return teacher_id;
    }


    @Override
    public String toString()
    {
        return "" + getId();
    }



}
