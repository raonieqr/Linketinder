package linketinder.microservices

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import linketinder.controller.CandidateController
import linketinder.dao.impl.CandidateDAOImpl
import linketinder.model.entities.Candidate
import org.json.JSONException

public class CandidateHTTPService extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) {

    StringBuffer jb = new StringBuffer()
    String line = null
    try {
      BufferedReader reader = request.getReader()
      while ((line = reader.readLine()) != null) {
        jb.append(line)
      }
    } catch (Exception e) {
      e.printStackTrace()
    }

    try {
      ObjectMapper objectMapper = new ObjectMapper()
      Map<String, Object> map = objectMapper.readValue(jb.toString(), Map.class)

      String name = (String) map.get("name")
      String age = (String) map.get("age")
      String description = (String) map.get("description")
      String state = (String) map.get("state")
      String password = (String) map.get("password")
      String email = (String) map.get("email")
      String cpf = (String) map.get("cpf")
      String cep = (String) map.get("cep")
      String skills = (String) map.get("skills")


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

          response.setStatus(HttpServletResponse.SC_CREATED)
        } catch (Exception e) {
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST)

          e.printStackTrace()
        }
      }
    } catch (JSONException e) {
      throw new IOException("Error parsing JSON request string")
    }

  }
}
