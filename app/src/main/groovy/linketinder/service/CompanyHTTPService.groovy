package linketinder.service

import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import linketinder.controller.CompanyController
import linketinder.dao.impl.CompanyDAOImpl
import linketinder.model.entities.Company

public class CompanyHTTPService extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) {
    String name = request.getParameter("name")
    String description = request.getParameter("description")
    String country = request.getParameter("country")
    String state = request.getParameter("state")
    String password = request.getParameter("password")
    String email = request.getParameter("email")
    String cnpj = request.getParameter("cnpj")
    String cep = request.getParameter("cep")

    if (name == null || description == null ||
      country == null || password == null || email == null ||
      cnpj == null || cep == null || state == null)
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
    else {
      try {

        Company company = new Company(name, email, cnpj,
          country, description, state, Integer.parseInt(cep), password)

        CompanyController.addCompany(company, CompanyDAOImpl.getInstance())

        response.setStatus(HttpServletResponse.SC_OK)
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST)

        e.printStackTrace()
      }
    }
  }
}
