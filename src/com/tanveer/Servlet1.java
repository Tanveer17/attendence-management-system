package com.tanveer;

import com.tanveer.data.Attendence;
import com.tanveer.data.LeaveRequest;
import com.tanveer.data.User;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@WebServlet("/servlet1")
public class Servlet1 extends HttpServlet {

    @Resource(name = "jdbc/attendence_Managent_system")
    private DataSource dataSource;
    private DBUtils dbUtils;


    @Override
    public void init(){
        dbUtils = new DBUtils(dataSource);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String command = request.getParameter("command");

        try {
            switch (command) {
                case "adminLogin":
                    setUser(request, response);
                    break;
                case "userLogin":
                    setUser(request, response);
                    break;
                case "Register User":
                    registerUser(request,response);
                    break;
                case "MARK ATTENDENCE":
                    markAttendence(request,response);
                    break;

                case "Edit Profile":
                    setEditProfilePage(request,response);
                    break;

                case "VIEW ATTENDENCE":
                    viewAttendence(request,response);
                    break;
                case "Update User":
                    updateUser(request,response);
                    break;
                case "Process Leave Request":
                    leaveRequest(request,response);
                    break;
                case "Show Students":
                    showStudents(request,response);
                    break;
                case "Students Attendence":
                    studentsAttendence(request,response);
                    break;
                case "Mark Present":
                      markPresent(request,response);
                      break;
                case "Mark Absent":
                    markAbsent(request,response);
                    break;
                case "Mark Leave":
                    markLeave(request,response);
                    break;
                case "Generate Report":
                    generateReportForUser(request,response);
                    break;


            }

        } catch (Exception e) {
            throw new ServletException(e);

        }
    }


    private void setUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession(true);
        String authority = request.getParameter("authority");
        session.setAttribute("AUTHORITY",authority);
        System.out.println(authority);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request,response);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("in register");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String gender = request.getParameter("gender");
        String email  = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(firstName,lastName,gender,email,password);

        if(dbUtils.createUser(user)){
            int id = dbUtils.getId(user.getEmail());
            HttpSession session = request.getSession(true);
            session.setAttribute("AUTHORIZED",true);
            session.setAttribute("ID",id);
            session.setAttribute("NAME",user.getFirstName().toUpperCase());
            response.sendRedirect("home.jsp");
        }

    }

    private void markAttendence(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("mark attendenec");
        boolean isAttendenceMarked = dbUtils.isAttendencePresent((int)request.getSession().getAttribute("ID"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/home.jsp");

        if(isAttendenceMarked){
            dispatcher.forward(request,response);
        }
        else if(dbUtils.markPresent((int)request.getSession().getAttribute("ID"))){
            dispatcher.forward(request,response);
        }


    }

    private void viewAttendence(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = (int)request.getSession(false).getAttribute("ID");
        List<Attendence> attendence = dbUtils.getAttendence(id);

        request.setAttribute("ATTENDENCE",attendence);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user/attendence.jsp");
        dispatcher.forward(request,response);
    }

    private void setEditProfilePage(HttpServletRequest request, HttpServletResponse response)throws Exception{
        User user = dbUtils.getUser((int)request.getSession(false).getAttribute("ID"));

        request.setAttribute("USER",user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/userPage.jsp");
        dispatcher.forward(request,response);

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        int id = (int)request.getSession(false).getAttribute("ID");

        User user = new User(id,firstName,lastName,null,email,null);

        dbUtils.updateUser(user);
        setEditProfilePage(request,response);

    }

    private void leaveRequest(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String message = request.getParameter("message");
        System.out.println(request.getParameter("from"));
        LocalDate from = LocalDate.parse(request.getParameter("from"));
        LocalDate to = LocalDate.parse(request.getParameter("to"));
        int id = (int) request.getSession(false).getAttribute("ID");

        LeaveRequest leaveRequest = new LeaveRequest(message,from,to,id);

        dbUtils.postLeaveRequest(leaveRequest);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user/sendLeaveRequest.jsp");
        dispatcher.forward(request,response);
    }

    public void showStudents(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List<User> students = dbUtils.getStudents(request);

        request.setAttribute("STUDENTS",students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/students.jsp");
        dispatcher.forward(request,response);
    }

    private void studentsAttendence(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<Attendence> attendences = dbUtils.getAttendences();
        request.setAttribute("ATTENDENCE",attendences);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/students_attendence.jsp");
        dispatcher.forward(request,response);


    }

    private void markPresent(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int userId = Integer.valueOf(request.getParameter("userId"));
        dbUtils.updatePresent(userId);
        studentsAttendence(request,response);
    }

    private void markAbsent(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int userId = Integer.valueOf(request.getParameter("userId"));
        dbUtils.markAbsent(userId);
        studentsAttendence(request,response);
    }

    private void markLeave(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int userId = Integer.valueOf(request.getParameter("userId"));
        dbUtils.markLeave(userId);
        studentsAttendence(request,response);

    }

    private void generateReportForUser(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String email = request.getParameter("email");
        Date from = Date.valueOf(request.getParameter("from"));
        Date to = Date.valueOf(request.getParameter("to"));
        RequestDispatcher dispatcher;
        if(!email.equals("")) {
            List<Attendence> attendences = dbUtils.generateReportForUser(email, from, to);
            String name = dbUtils.getName(email);
            request.setAttribute("ATTENDENCES", attendences);
            request.setAttribute("NAME", name);
            dispatcher = request.getRequestDispatcher("admin/generated_report.jsp");
        }
        else{
            List<Attendence> attendences = dbUtils.generateReportForDuration(from,to);
            request.setAttribute("ATTENDENCES", attendences);
            dispatcher = request.getRequestDispatcher("admin/generated_report2.jsp");
        }

        dispatcher.forward(request,response);


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException{
        doGet(request,response);
    }



}
