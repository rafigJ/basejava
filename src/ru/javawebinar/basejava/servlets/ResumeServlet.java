package ru.javawebinar.basejava.servlets;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DataUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "add":
                r = new Resume();
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            default:
                throw new IllegalArgumentException("Action: " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final Resume resume;
        if (paramsIsEmpty(uuid)) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType ct : ContactType.values()) {
            String value = request.getParameter(ct.name());
            if (!paramsIsEmpty(value)) {
                resume.addContactInfo(ct, value);
            } else {
                resume.getContactMap().remove(ct);
            }
        }
        for (SectionType st : SectionType.values()) {
            Map<SectionType, Section> sectionMap = resume.getSectionMap();
            String value = request.getParameter(st.name());
            if (paramsIsEmpty(value)) {
                sectionMap.remove(st);
            } else {
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        sectionMap.put(st, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final String regex = "\\n{2,}";
                        String s = value.trim().replaceAll("\\r", "").replaceAll(regex, "\n");
                        sectionMap.put(st, new ListSection(s.split("\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] values = request.getParameterValues(st.name());
                        String[] webSites = request.getParameterValues(st.name() + "_webSite");
                        List<Company> companies = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            if (!paramsIsEmpty(values[i])) {
                                Company company = new Company(values[i], webSites[i]);
                                List<Company.Period> periods = company.getPeriods();
                                String[] titles = request.getParameterValues(st.name() + "_periodTitle" + i);
                                String[] starts = request.getParameterValues(st.name() + "_startDate" + i);
                                String[] ends = request.getParameterValues(st.name() + "_endDate" + i);
                                String[] descriptions = request.getParameterValues(st.name() + "_periodDescription" + i);
                                for (int j = 0; j < titles.length; j++) {
                                    if (!paramsIsEmpty(titles[j], starts[j], ends[j])) {
                                        periods.add(DataUtil.periodOf(starts[j], ends[j], titles[j], descriptions[j]));
                                    }
                                }
                                companies.add(company);
                            }
                        }
                        if (!companies.isEmpty()) {
                            CompanySection section = new CompanySection();
                            section.getCompanies().addAll(companies);
                            resume.getSectionMap().put(st, section);
                        }
                        break;
                }
            }
        }

        if (paramsIsEmpty(uuid)) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private static boolean paramsIsEmpty(String... params) {
        for (String param : params) {
            if (param != null && !param.isEmpty() && !param.equals("\r")) {
                return false;
            }
        }
        return true;
    }
}
