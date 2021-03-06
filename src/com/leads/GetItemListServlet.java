package com.leads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetItemListServlet extends HttpServlet{

  private static final long serialVersionUID = 874899128358365710L;

  @SuppressWarnings("unchecked")
  public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
    String sortParam = req.getParameter("sort");
    if(sortParam == null || sortParam.equals("")) {
      sortParam = "title";
    }
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Query gqlQuery = pm.newQuery(Lead.class);
    gqlQuery.setOrdering(sortParam);

    List<Lead> leads = (List<Lead>) gqlQuery.execute();
    JSONArray arr = new JSONArray();
    for(Lead lead : leads) {
      arr.put(lead.asAbridgedJson(sortParam));
    }
    JSONObject obj = new JSONObject();
    try {
      obj.put("items", arr);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    resp.getWriter().println(obj.toString());
  }
}
