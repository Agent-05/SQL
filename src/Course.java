import java.sql.*;
import java.util.ArrayList;

//id, title, type (integer) academic - 0, KAP-1, AP-2
public class Course {
    //teacher teaches sections but the program handles that, the teacher doesnt need to know what 
    //or who they teach, the enrollment and section will have the ids of who is teaching what and who
    //section is basically periods
    int id = -1;
    public String title = "default";
    int diff = -1;
    public Course(int id, String title, int type)
    {
        this.id = id;
        this.title = title;
        diff = type;
    }
    public Course(String title, int type)
    {
        this.title = title;
        diff = type;
        addCourse();
    }

    public void addCourse(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            s.execute("CREATE table if not exists course(id INTEGER NOT NULL AUTO_INCREMENT, title text, diff INTEGER NOT NULL, PRIMARY KEY(id));");

            try{
                s.execute("INSERT INTO course (title, diff) VALUES (\'"+title+"\', "+diff+")");
                ResultSet rs = s.executeQuery("SELECT * from course;");
                while(rs!=null&&rs.next())
                {
                    if(rs.getString("title").equals(title))
                    {
                        id = rs.getInt("id");
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("id already exists");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

    //updates or deletes student
    public void updateCourse(int id, String title, int type){//must refer to it by id, pass - in as title to delete
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM course;");
            while(rs!=null&&rs.next())
            {
                if(rs.getInt("id") == id)
                {
                    System.out.println("Work1");
                    if(title.equals("-"))
                    {
                        s.execute("DELETE FROM course WHERE id="+id+";");
                    }else{
                        this.title = title;
                        this.diff = type;
                        s.execute("UPDATE course SET title=\'"+title+"\' WHERE id="+id+";");
                        s.execute("UPDATE course SET diff="+diff+" WHERE id="+id+";");

                    }
                }
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDiff() {
        return diff;
    }


    @Override
    public String toString()
    {
        int dif = getDiff();
        String academicLevel = "";
        switch (dif){
            case 0 -> {
                academicLevel = "Aca";
            }
            case 1 -> {
                academicLevel = "AP";
            }
            case 2 -> {
                academicLevel = "KAP";
            }

    }
        return getTitle() + ", " + academicLevel;
    }

}