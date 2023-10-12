package ru.javawebinar.basejava.servlets;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.basejava.util.DataUtil.periodOf;

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

        final Resume resume = paramsIsEmpty(uuid) ? new Resume(fullName) : storage.get(uuid);

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
            switch (st) {
                case PERSONAL:
                case OBJECTIVE:
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String value = request.getParameter(st.name()).trim();
                    if (paramsIsEmpty(value)) {
                        sectionMap.remove(st);
                    } else if (st == SectionType.PERSONAL || st == SectionType.OBJECTIVE) {
                        sectionMap.put(st, new TextSection(value));
                    } else {
                        ListSection listSection = new ListSection(value.split("\n"));
                        List<String> list = listSection.getList();
                        list.removeIf(e -> e.equals("\r") || e.isEmpty());
                        sectionMap.put(st, listSection);
                    }
                    continue;
                case EXPERIENCE:
                case EDUCATION:
                    if (sectionMap.containsKey(st)) {
                        updateCompanySection(request, (CompanySection) sectionMap.get(st), st);
                    } else {
                        CompanySection cs = new CompanySection();
                        List<Company> companies = cs.getCompanies();
                        Company company = new Company();
                        String companyName = request.getParameter(st + "_companyName0").trim();
                        if (paramsIsEmpty(companyName)) {
                            continue;
                        }
                        String webSite = request.getParameter(st + "_webSite0").trim();
                        company.setCompanyName(companyName);
                        company.setWebsite(webSite);

                        String start = request.getParameter(st + "_startDate00").trim();
                        String end = request.getParameter(st + "_endDate00").trim();
                        String periodTitle = request.getParameter(st + "_periodTitle00").trim();
                        String periodDescription = request.getParameter(st + "_periodDescription00").trim();
                        if (paramsIsEmpty(start, end, periodTitle, periodDescription)) {
                            throw new IllegalArgumentException("one period should always be present");
                        }

                        company.getPeriods().add(
                                periodOf(start, end, periodTitle, periodDescription)
                        );
                        companies.add(company);
                        sectionMap.put(st, cs);
                    }
                    continue;
                default:
                    throw new IllegalArgumentException("SectionType " + st + " is illegal");
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

    private static void updateCompanySection(HttpServletRequest request, CompanySection cs, SectionType st) throws IOException {
        List<Company> companies = cs.getCompanies();
        int listSize = companies.size();
        List<Integer> removeCompany = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            String companyName = request.getParameter(st + "_companyName" + i).trim();
            String webSite = request.getParameter(st + "_webSite" + i).trim();

            if (paramsIsEmpty(companyName)) {
                removeCompany.add(i);
            }

            Company company = companies.get(i);
            company.setCompanyName(companyName);
            company.setWebsite(webSite);
            List<Company.Period> periods = company.getPeriods();

            int size = periods.size();
            List<Integer> removePeriod = new ArrayList<>(size);

            for (int k = 0; k < size + 1; k++) {
                String start = request.getParameter(st + "_startDate" + i + k);
                String end = request.getParameter(st + "_endDate" + i + k);
                String title = request.getParameter(st + "_periodTitle" + i + k).trim();
                String description = request.getParameter(st + "_periodDescription" + i + k).trim();

                if (k < size) {
                    if (paramsIsEmpty(start, end, title)) {
                        if (size == 1) {
                            throw new IllegalArgumentException("one period should always be present in " + st.getTitle());
                        }
                        removePeriod.add(k);
                        continue;
                    }
                    periods.set(k, periodOf(start, end, title, description));
                } else if (!paramsIsEmpty(start, end, title)) {
                    periods.add(periodOf(start, end, title, description));
                }
            }
            for (int d : removePeriod) {
                periods.remove(d);
            }
        }

        String companyName = request.getParameter(st + "_companyName" + listSize).trim();
        String webSite = request.getParameter(st + "_webSite" + listSize).trim();
        String start = request.getParameter(st + "_startDate" + listSize + 0);
        String end = request.getParameter(st + "_endDate" + listSize + 0);
        String title = request.getParameter(st + "_periodTitle" + listSize + 0).trim();
        String description = request.getParameter(st + "_periodDescription" + listSize + 0).trim();
        if (!paramsIsEmpty(companyName, start, end, title)) {
            companies.add(createNewCompany(companyName, webSite, periodOf(start, end, title, description)));
        }
        for (int r : removeCompany) {
            companies.remove(r);
        }
    }

    private static Company createNewCompany(String companyName, String webSite, Company.Period period) {
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setWebsite(webSite);
        company.getPeriods().add(period);
        return company;
    }
}
