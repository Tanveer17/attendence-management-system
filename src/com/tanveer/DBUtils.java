package com.tanveer;




import com.sun.corba.se.pept.transport.ConnectionCache;
import com.tanveer.data.Attendence;
import com.tanveer.data.LeaveRequest;
import com.tanveer.data.User;

import javax.servlet.GenericFilter;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private DataSource dataSource;

    public DBUtils(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    public boolean authenticateAdmin(String user, String email, String password ) throws Exception {
        String sql = "SELECT id FROM admins WHERE email = ? and password = ?";
        try (Connection connection = dataSource.getConnection()) {
            //getting the user id from users table if the password and email is valid//
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

            ////////////////////////////////////////////////////////////////////////////
        }

    }

    public int authenticateUser(String email, String password ) throws Exception {
        boolean authenticated = false;
        int userId = -1;
        String userAuthority;
        String sql = "SELECT id FROM users WHERE email = ? and password = ?";


        try (Connection connection = dataSource.getConnection()) {
            //getting the user id from users table if the password and email is valid//
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");

            }

            ////////////////////////////////////////////////////////////////////////////

            //// if id exists than see if the user is an is @user//////////////
        }
        return userId;

    }

    public boolean createUser(User user) throws Exception{
        System.out.println("in create user");
        String sql = "INSERT INTO users(first_name,last_name,gender,email,password) VALUES(?,?,?,?,?)";
        int rowsUpdated = 0;

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setString(3,user.getGender());
            statement.setString(4,user.getEmail());
            statement.setString(5,user.getPassword());

            rowsUpdated = statement.executeUpdate();
        }

        if(rowsUpdated > 0){
            return true;
        }
        return false;
    }

    public int getId(String email)throws Exception{
        String sql = "SELECT id FROM users WHERE email = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            else {
                return -1;
            }
        }
    }

    public String getName(int id) throws Exception{
        String sql = "SELECT first_name FROM users WHERE id = ?";
        String firstName = null;

        try(Connection con = dataSource.getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                firstName = resultSet.getString(1);
            }

        }

        return firstName;
    }

    public boolean isAttendencePresent(int id)throws Exception{
        Date date = Date.valueOf(LocalDate.now());

        String sql = "SELECT id FROM attendence WHERE user_id = ? AND attendence_date = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setDate(2,date);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }
            else
                return false;
        }

    }

    public boolean markPresent(int userId)throws Exception{
        Date date = Date.valueOf(LocalDate.now());
        String sql = "INSERT INTO attendence (attendence_date,user_id,attendence) VALUES(?,?,?)";

        try(Connection connection  = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1,date);
            preparedStatement.setInt(2,userId);
            preparedStatement.setString(3,"present");

            int rows =  preparedStatement.executeUpdate();

            if(rows > 0){
                return true;
            }
            else
                return false;
        }
    }

    public List<Attendence> getAttendence(int id) throws Exception{
        List<Attendence> attendences = new ArrayList<>();
        String sql = "SELECT attendence_date, attendence FROM attendence WHERE user_id = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                LocalDate date = resultSet.getDate(1).toLocalDate();
                String attendence = resultSet.getString(2);

                attendences.add(new Attendence(date,attendence));
            }
        }
        return attendences;

    }

    public User getUser(int id)throws Exception{
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet rs  =statement.executeQuery();

            if(rs.next()){
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String imageUrl = rs.getString("image_url");
                user = new User(firstName,lastName,email,imageUrl);

            }
        }
        return user;
    }

    public void updateUser(User user)throws Exception{
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ? WHERE id = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setString(3,user.getEmail());
            statement.setInt(4,user.getId());

            statement.execute();
        }
    }

    public void postLeaveRequest(LeaveRequest request) throws Exception{
        String sql = "INSERT INTO leave_requests(message,from_date,to_date,user_id)" +
                    " VALUES(?,?,?,?)";

        try(Connection connection  = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,request.getMessage());
            statement.setDate(2,Date.valueOf(request.getFrom()));
            statement.setDate(3,Date.valueOf(request.getTo()));
            statement.setInt(4,request.getUserId());

            statement.execute();
        }
    }

    public List<User> getStudents(HttpServletRequest request)throws Exception{
        List<User> students = new ArrayList<>();
        String sql = "SELECT first_name,last_name,gender,email FROM users";

        try(Connection con = dataSource.getConnection();
        Statement statement  = con.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String gender = resultSet.getString(3);
                String email  =resultSet.getString(4);
                String grade = calculateGrade(email);
                students.add(new User(firstName,lastName,gender,email,grade,null));
            }
        }
        return students;
    }

    public List<Attendence> getAttendences()throws Exception{
        List<Attendence> attendences =  new ArrayList<>();
        Date today = Date.valueOf(LocalDate.now());
        String sql = "SELECT users.first_name,attendence_date,attendence ,user_id FROM attendence" +
                     " INNER JOIN users ON user_id = users.id WHERE attendence_date =?";

        try(Connection con = dataSource.getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setDate(1,today);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String firstName = resultSet.getString("first_name");
                LocalDate attendenceDate = resultSet.getDate("attendence_date").toLocalDate();
                String attendence = resultSet.getString("attendence");
                int userId = resultSet.getInt("user_id");

                attendences.add(new Attendence(firstName,attendenceDate,attendence,userId));


            }

        }
        return attendences;


    }

    public void updatePresent(int userId)throws Exception{
        String sql = "Update attendence SET attendence = 'present' WHERE user_id = ?";
        try(Connection connection  = dataSource.getConnection()){
            PreparedStatement statement  = connection.prepareStatement(sql);
            statement.setInt(1,userId);

            statement.execute();
        }
    }

    public void markAbsent(int userId)throws Exception{
        String sql = "Update attendence SET attendence = 'absent' WHERE user_id = ?";
        try(Connection connection  = dataSource.getConnection()){
            PreparedStatement statement  = connection.prepareStatement(sql);
            statement.setInt(1,userId);

            statement.execute();
        }

    }
    public void markLeave(int userId)throws Exception{
        String sql = "Update attendence SET attendence = 'leave' WHERE user_id = ?";
        try(Connection connection  = dataSource.getConnection()){
            PreparedStatement statement  = connection.prepareStatement(sql);
            statement.setInt(1,userId);

            statement.execute();
        }
    }

    public List<Attendence> generateReportForUser(String email, Date from , Date to )throws Exception{
        List<Attendence> attendences = new ArrayList<>();
        String sql = "SELECT attendence_date, attendence FROM attendence INNER JOIN  users ON user_id = users.id" +
                     " WHERE users.email = ? AND attendence_Date >= ? AND attendence_Date <= ? ";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement  = connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setDate(2,from);
            statement.setDate(3,to);


            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                LocalDate attendenceDate = resultSet.getDate(1).toLocalDate();
                String attendence = resultSet.getString(2);

                attendences.add(new Attendence(attendenceDate,attendence));
            }
        }
        return attendences;
    }

    public List<Attendence> generateReportForDuration(Date from , Date to )throws Exception{
        List<Attendence> attendences = new ArrayList<>();
        String sql = "SELECT first_name,attendence_date, attendence FROM attendence INNER JOIN  users ON user_id = users.id" +
                " WHERE  attendence_Date >= ? AND attendence_Date <= ? ";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement  = connection.prepareStatement(sql);
            statement.setDate(1,from);
            statement.setDate(2,to);


            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String firstName = resultSet.getString(1);
                LocalDate attendenceDate = resultSet.getDate(2).toLocalDate();
                String attendence = resultSet.getString(3);

                attendences.add(new Attendence(firstName,attendenceDate,attendence));
            }
        }
        return attendences;
    }

    public String getName(String email)throws Exception{
        String firstName = null;
        String sql = "SELECT first_name FROM users WHERE email = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection. prepareStatement(sql);
            statement.setString(1,email);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                firstName = resultSet.getString(1);
            }
        }
        return firstName;
    }

    private String calculateGrade(String email)throws Exception{
        String grade;
        int numOfpresents = 0;
        int id = getId(email);
        String sql = "SELECT count(*) as 'presents' FROM attendence WHERE user_id = ? AND attendence= 'present'";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
               numOfpresents = Integer.valueOf(resultSet.getInt(1));
            }
        }
         if(numOfpresents >= 20)
             grade = "A";
         else if(numOfpresents >=16)
             grade = "B";
         else if(numOfpresents >= 13)
             grade = "C";
         else if(numOfpresents >= 10)
             grade = "D";
         else
             grade = "E";

         return grade;
    }
}


