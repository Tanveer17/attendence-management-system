package com.tanveer;


import com.tanveer.DBUtils;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//Servlet for user authentication and authorization
@WebServlet("/user")
public class AuthenticationServlet extends HttpServlet {
    @Resource(name = "jdbc/attendence_Managent_system")
    private DataSource dataSource;
    private DBUtils dbUtils;


    @Override
    public void init(){
        dbUtils = new DBUtils(dataSource);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){


        String authority = request.getParameter("AUTHORITY");

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

//        if(authority == null){
//            throw new ServletException(new NullPointerException("authentication required"));
//        }


        try{
        switch (authority){
            case "Admin":
                adminLogin(request,response, email, pass);
                break;
            case "User":
                System.out.println("user");
                userLogin(request,response,email,pass);
                break;

            default:
                throw new ServletException();

            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }

    //log in the admin
    private void adminLogin(HttpServletRequest request, HttpServletResponse response,String email,String pass)
            throws Exception{

        HttpSession session = request.getSession(true);

        if(dbUtils.authenticateAdmin("Admin",email,pass)) {


            // if some one accesing the page directly will be redirecting to home page using this flag if was null
            session.setAttribute("AUTHORIZED",true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/home.jsp");
            dispatcher.forward(request, response);

        }
        else{
            request.setAttribute("NOT_AUTHORIZED",true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);

        }
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response,String email,String pass)
            throws Exception{

        HttpSession session = request.getSession(true);
        int id;
        if((id = dbUtils.authenticateUser(email,pass)) != -1) {


            // if some one accesing the page directly will be redirecting to home page using this flag if was null
            session.setAttribute("AUTHORIZED",true);
            session.setAttribute("ID",id);
            session.setAttribute("NAME",dbUtils.getName(id).toUpperCase());
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/home.jsp");
            dispatcher.forward(request, response);

        }
        else{
            request.setAttribute("NOT_AUTHORIZED",true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);

        }

    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        doPost(request, response);

    }
}
