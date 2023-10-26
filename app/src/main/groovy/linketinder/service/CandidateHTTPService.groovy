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

    if (name == null || age == null || description == null ||
      state == null || password == null || email == null ||
      cpf == null || cep == null || skills == null)
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
    else {
      try {
        ArrayList<String> skillSplited = skills.split("[;,]+")
        skillSplited = skillSplited.collect { it.toLowerCase() }
        skillSplited = skillSplited.collect { it.capitalize() }

        Candidate candidate = new Candidate(name, email, skillSplited, Integer.parseInt(age),
          state, description, cpf, Integer.parseInt(cep), password)

        CandidateController.addCandidate(candidate, CandidateDAOImpl.getInstance())

        response.setStatus(HttpServletResponse.SC_OK)
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        e.printStackTrace()
      }
    }
  }
}
