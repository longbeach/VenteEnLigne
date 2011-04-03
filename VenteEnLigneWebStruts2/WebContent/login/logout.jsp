<%

session.invalidate();
response.sendRedirect(request.getContextPath() + "/index.jsp");

%> 