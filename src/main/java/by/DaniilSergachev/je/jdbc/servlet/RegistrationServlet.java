package by.DaniilSergachev.je.jdbc.servlet;

import by.DaniilSergachev.je.jdbc.dao.UserDao;
import by.DaniilSergachev.je.jdbc.dto.CreateUserDto;
import by.DaniilSergachev.je.jdbc.entity.Gender;
import by.DaniilSergachev.je.jdbc.entity.Role;
import by.DaniilSergachev.je.jdbc.exception.ValidationException;
import by.DaniilSergachev.je.jdbc.service.UserService;
import by.DaniilSergachev.je.jdbc.utils.UrlPath;
import by.DaniilSergachev.je.jdbc.utils.jspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.DaniilSergachev.je.jdbc.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(jspHelper.getPath("registration")).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("pwd"))
                    .role(req.getParameter("role"))
                .gender(req.getParameter("gender")).build();


        try {
            userService.createUser(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req,resp);
        }
    }
}
