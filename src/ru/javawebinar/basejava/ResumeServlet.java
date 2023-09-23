package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        SqlStorage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes",
                "postgres", "8postProger3");

        List<Resume> allSorted = storage.getAllSorted();
        StringBuilder table = new StringBuilder();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>All Resumes</title>");
        out.println("</head>");

        out.println("<body>");


        table.append(
                "<table border=\"2\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                        "            <tr>\n" +
                        "                <td>uuid</td>\n" +
                        "                <td>full_name</td>\n" +
                        "            </tr>\n");
        for (Resume r : allSorted) {
            table.append(String.format("" +
                            "            <tr>\n" +
                            "                <td>%s</td>\n" +
                            "                <td>%s</td>\n" +
                            "            </tr>\n",
                    r.getUuid(), r.getFullName()));
        }
        table.append("        </table>\n");

        out.write(table.toString());
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
