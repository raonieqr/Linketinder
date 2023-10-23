package linketinder.service

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface CandidateService {
  void createCandidate(HttpServletRequest request, HttpServletResponse response);

}