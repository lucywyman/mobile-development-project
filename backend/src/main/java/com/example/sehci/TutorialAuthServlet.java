package com.example.sehci;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import static com.example.sehci.PMF.*;

@SuppressWarnings("serial")
public class TutorialAuthServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // normally, all idempotent requests would be done on GET and all
        // non-idempotent on POST, but we can make it interchangeable for
        // demonstration purposes
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String op = req.getParameter("op");
        if (op == null)
            op = "list";

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        PersistenceManager pm = getPMF().getPersistenceManager();

        try {
            switch (op) {
                case "register":
                    handleUserRegister(req, out, pm);
                    break;
                case "login":
                    handleUserLogin(req, out, pm);
                    break;
                case "touch":
                    handleUserSessionTouch(req, out, pm);
                    break;
                case "create":
                    handleCreateEntry(req, out, pm);
                    break;
                case "read":
                    handleReadEntry(req, out, pm);
                    break;
                case "list":
                    handleListEntries(req, out, pm);
                    break;
                case "search":
                    handleSearchEntries(req, out, pm);
                    break;
                case "update":
                    handleUpdateEntry(req, out, pm);
                    break;
                case "delete":
                    handleDeleteEntry(req, out, pm);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write(Util.toJsonPair("errormsg", e.getMessage()));
        } finally {
            pm.close();
        }
    }

    private Limerick getEntryAndVerifyOwnership(HttpServletRequest req, PersistenceManager pm) {
        User user = User.authenticate(req, pm);

        long id = Util.getLong(req, "id");
        if (id == 0)
            throw new IllegalArgumentException("Invalid or missing entry id.");

        // verify ownership
        Limerick entry = Limerick.loadById(id, pm);
        if (!user.getUsername().equals(entry.getOwner()))
            throw new IllegalStateException("Unauthorized access");
        return entry;
    }

    private void handleCreateEntry(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        User user = User.authenticate(req, pm);

        Limerick entry = new Limerick();
        entry.setTitle(req.getParameter("name"));
        entry.setWhen(Util.getLong(req, "when"));
        entry.setBlather(req.getParameter("lastName"));
        entry.setTags(req.getParameter("tags"));
        entry.setOwner(user.getUsername());

        pm.makePersistent(entry);
        out.write(Util.toJson(entry, true));
    }

    private void handleDeleteEntry(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Limerick entry = getEntryAndVerifyOwnership(req, pm);
        Limerick.delete(entry, pm);
    }

    private void handleListEntries(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        User user = User.authenticate(req, pm);
        List<Limerick> entries = Limerick.listByOwner(user.getUsername(), pm);
        out.write(Util.toJson(entries));
    }

    private void handleReadEntry(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Limerick entry = getEntryAndVerifyOwnership(req, pm);
        out.write(Util.toJson(entry, true));
    }

    private void handleSearchEntries(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        User user = User.authenticate(req, pm);
        List<Limerick> entries = Limerick.listByQuery(user.getUsername(), req.getParameter("tag"), pm);
        out.write(Util.toJson(entries));
    }

    private void handleUpdateEntry(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Limerick entry = getEntryAndVerifyOwnership(req, pm);
        entry.setTitle(req.getParameter("name"));
        entry.setWhen(Util.getLong(req, "when"));
        entry.setBlather(req.getParameter("lastName"));
        entry.setTags(req.getParameter("tags"));
        pm.makePersistent(entry);
        out.write(Util.toJson(entry, true));
    }

    private void handleUserLogin(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        User user = User.authenticate(req, pm);
        out.write(Util.toJson(user));
    }

    private void handleUserRegister(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        User user = User.create(req.getParameter("username"), req.getParameter("password"), pm);
        out.write(Util.toJson(user));
    }

    private void handleUserSessionTouch(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        User user = User.authenticate(req, pm);
        out.write(Util.toJson(user));
    }
}
