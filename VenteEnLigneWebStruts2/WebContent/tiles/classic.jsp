<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
  </head>
  <body>
    <table width="100%" height="100%" border="1">
      <tr height="5%">
        <td colspan="2">
          <center><tiles:insertAttribute name="header" /></center>
        </td>
      </tr>
      <tr height="90%">
        <td width="80">
          <tiles:insertAttribute name="menu" />
        </td>
        <td>
          <tiles:insertAttribute name="body" />
        </td>
      </tr>
      <tr height="5%" > 
        <td colspan="2" >
          <center><tiles:insertAttribute name="footer" /></center>
        </td>
      </tr>
    </table>
  </body>
</html>