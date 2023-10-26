package linketinder.service

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import linketinder.controller.VacancyController
import linketinder.dao.impl.VacancyDAOImpl
import linketinder.model.entities.Vacancy

class VacancyHTTPService extends HttpServlet{
  @Override
  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) {
    String name = request.getParameter("name")
    String description = request.getParameter("description")
    String idCompany = request.getParameter("idCompany")
    String skills = request.getParameter("skills")

    if (name == null || description == null || skills == null ||
      idCompany == null)
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
    else {
      try {
        ArrayList<String> skillSplited = skills.split("[;,]+")
        skillSplited = skillSplited.collect { it.toLowerCase() }
        skillSplited = skillSplited.collect { it.capitalize() }

        Vacancy vacancy = new Vacancy(name, description, idCompany as int, skillSplited)

        VacancyController.addVacancy(vacancy, VacancyDAOImpl.getInstance())

        response.setStatus(HttpServletResponse.SC_OK)
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST)

        e.printStackTrace()
      }
    }
  }
}
