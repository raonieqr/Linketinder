package linketinder.service

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


//@WebServlet("/registerCandidate")
public class CandidateHTTPService extends HttpServlet {
  @Override
  protected void service(HttpServletRequest request,
                         HttpServletResponse response) {
//    String name = request.getParameter("name")
//    String age = request.getParameter("age")
//    String description = request.getParameter("description")
//    String state = request.getParameter("uf")
//    String password = request.getParameter("password")
//    String email = request.getParameter("email")
//    String cpf = request.getParameter("cpf")
//    String cep = request.getParameter("cep")
//    String skills = request.getParameter("skills")
//
//    Candidate candidate = new Candidate(20,name, email, skills.split("") as
//      ArrayList<String>, age as int, state, description, cpf, cep as int,
//      password);
//
//    try {
//      CandidateController.addCandidate(candidate, CandidateDAOImpl.getInstance());
//      response.setStatus(HttpServletResponse.SC_OK)
//    }
//    catch (Exception e) {
//      response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
//      e.printStackTrace()
//    }
    PrintWriter printWriter = response.getWriter()
    printWriter.println("<html>")
    printWriter.println("<body>")
    printWriter.println("<h1>Finalmente!</h1>")
    printWriter.println("</body>")
    printWriter.println("</html>")

  }
}
