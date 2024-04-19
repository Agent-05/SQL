import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class enrollmentManager {
    ArrayList<Enrollment> enrollment = new ArrayList<Enrollment>();


    public void getNames(){
        enrollment.clear();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_manager","root","password");
            Statement s =  con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM enrollment;");
            while(rs!=null&&rs.next())
            {
                int student_id = rs.getInt("student_id");
                int id = rs.getInt("id");
                Enrollment existingEnrollment = new Enrollment(id, student_id);//pass in an id to not create a new teacher but just get one instead
                enrollment.add(existingEnrollment);
            }
            con.close();
        }catch(Exception e)
        {

        }
    }
}
