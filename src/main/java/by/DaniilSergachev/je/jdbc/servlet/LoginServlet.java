package by.DaniilSergachev.je.jdbc.servlet;

import by.DaniilSergachev.je.jdbc.dto.UserDto;
import by.DaniilSergachev.je.jdbc.service.UserService;
import by.DaniilSergachev.je.jdbc.utils.jspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.DaniilSergachev.je.jdbc.utils.UrlPath.LOGIN;


@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(jspHelper.getPath("login"))
                .forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("email"),req.getParameter("password"))
                .ifPresentOrElse(userDto -> onLoginSuccess(userDto,req,resp ),
                        () -> onLoginFail(req,resp));

    }
    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp)  {
        resp.sendRedirect("/login?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp)  {
        req.getSession().setAttribute("user",userDto);
        resp.sendRedirect("/flights");
    }
}
