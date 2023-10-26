package linketinder.service

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import linketinder.controller.CandidateController
import linketinder.dao.impl.CandidateDAOImpl
import linketinder.model.entities.Candidate

public class CandidateHTTPService extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) {
    String name = request.getParameter("name")
    String age = request.getParameter("age")
    String description = request.getParameter("description")
    String state = request.getParameter("state")
    String password = request.getParameter("password")
    String email = request.getParameter("email")
    String cpf = request.getParameter("cpf")
    String cep = request.getParameter("cep")
    String skills = request.getParameter("skills")

    Candidate candidate = new Candidate(name, email, skills as ArrayList, age as
      int,
      state, description, cpf, cep as int, password);

    try {
      CandidateController.addCandidate(candidate, CandidateDAOImpl.getInstance());
      response.setStatus(HttpServletResponse.SC_OK)
    }
    catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
      e.printStackTrace()
    }

  }
}
