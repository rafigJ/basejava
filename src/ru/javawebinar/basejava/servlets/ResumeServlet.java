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
import java.time.LocalDate;
import java.util.*;

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
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
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
                case EXPERIENCE:
                case EDUCATION:
                    if (sectionMap.containsKey(st)) {
                        updateCompanySection(request, response, (CompanySection) sectionMap.get(st), st);
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
                        String startDate = request.getParameter(st + "_startDate00").trim();
                        String endDate = request.getParameter(st + "_endDate00").trim();
                        String periodTitle = request.getParameter(st + "_periodTitle00").trim();
                        String periodDescription = request.getParameter(st + "_periodDescription00").trim();
                        if (paramsIsEmpty(startDate, endDate, periodTitle, periodDescription)) {
                            response.sendError(400, "One period should always be present");
                            return;
                        }
                        company.getPeriods().add(new Company.Period(
                                LocalDate.parse(startDate),
                                LocalDate.parse(endDate),
                                periodTitle,
                                periodDescription)
                        );
                        companies.add(company);
                        sectionMap.put(st, cs);
                    }
                    continue;

                case ACHIEVEMENT:
                case QUALIFICATIONS:
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(st.name()).trim();
                    if (sectionMap.containsKey(st)) {
                        updateTextListSection(value, resume, st);
                    } else if (!paramsIsEmpty(value) && (st == SectionType.ACHIEVEMENT || st == SectionType.QUALIFICATIONS)) {
                        ListSection listSection = new ListSection();
                        List<String> list = listSection.getList();
                        list.addAll(Arrays.asList(value.split("\n")));
                        list.removeIf(e -> e.equals("\r") || e.isEmpty());
                        sectionMap.put(st, listSection);
                    } else if (!paramsIsEmpty(value)) {
                        TextSection textSection = new TextSection(value);
                        sectionMap.put(st, textSection);
                    }
            }
        }
        if (storage.getAllSorted().stream().anyMatch(r -> Objects.equals(r.getUuid(), resume.getUuid()))) {
            storage.update(resume);
        } else {
            storage.save(resume);
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

    private static void updateTextListSection(String value, Resume resume, SectionType st) {
        if (paramsIsEmpty(value)) {
            resume.getSectionMap().remove(st);
        } else if (st == SectionType.ACHIEVEMENT || st == SectionType.QUALIFICATIONS) {
            List<String> list = ((ListSection) resume.getSection(st)).getList();
            list.clear();
            list.addAll(Arrays.asList(value.split("\n")));
            list.removeIf(e -> e.equals("\r") || e.isEmpty());
        } else {
            resume.addInfoAtSection(st, value);
        }
    }

    private static void updateCompanySection(HttpServletRequest request, HttpServletResponse response,
                                             CompanySection cs, SectionType st) throws IOException {
        List<Company> companies = cs.getCompanies();
        int listSize = companies.size();
        for (int i = 0; i < listSize; i++) {
            String companyName = request.getParameter(st + "_companyName" + i).trim();
            String webSite = request.getParameter(st + "_webSite" + i).trim();
            if (paramsIsEmpty(companyName)) {
                response.sendError(400, "Сompany should always be");
                return;
            }
            Company company = companies.get(i);
            company.setCompanyName(companyName);
            company.setWebsite(webSite);
            List<Company.Period> periods = company.getPeriods();
            List<Integer> toDelete = new ArrayList<>();
            for (int k = 0; k < periods.size(); k++) {
                String startDate = request.getParameter(st + "_startDate" + i + k).trim();
                String endDate = request.getParameter(st + "_endDate" + i + k).trim();
                String periodTitle = request.getParameter(st + "_periodTitle" + i + k).trim();
                String periodDescription = request.getParameter(st + "_periodDescription" + i + k).trim();
                if (paramsIsEmpty(startDate, endDate, periodTitle)) {
                    if (periods.size() == 1) {
                        response.sendError(400, "One period should always be present");
                        return;
                    }
                    toDelete.add(k);
                    continue;
                }
                periods.set(k, new Company.Period(
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate),
                        periodTitle,
                        periodDescription)
                );
            }
            for (int d : toDelete) {
                periods.remove(d);
            }
        }
    }
}
