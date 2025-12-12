package io.brunoborges.showmyjvm.tomcat;

import java.io.IOException;

import io.brunoborges.showmyjvm.core.ShowJVM;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet endpoint for JVM inspection. Handles HTTP routing and delegates to
 * service layer for business logic.
 */
@WebServlet(name = "ShowMyJVMServlet", urlPatterns = { "/jvm/*" })
public class ShowMyJVMServlet extends HttpServlet {

    private final ShowJVM showJVM = new ShowJVM();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var pathInfo = req.getPathInfo();

        try {
            switch (pathInfo) {
            case "/inspect":
                handlePlainTextInspect(resp);
                break;
            case "/inspect.json":
                handleJsonInspect(resp);
                break;
            default:
                handleError(resp, HttpServletResponse.SC_NOT_FOUND, "Endpoint not found");
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void handleError(HttpServletResponse response, int statusCode, String message) {
        response.setStatus(statusCode);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        try (var writer = response.getWriter()) {
            writer.print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePlainTextInspect(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            writer.print(showJVM.dumpJVMDetails());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleJsonInspect(HttpServletResponse resp) throws IOException {
        var jsonb = JsonbBuilder.create();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            jsonb.toJson(showJVM.extractJVMDetails(), writer);
        }
    }
}
