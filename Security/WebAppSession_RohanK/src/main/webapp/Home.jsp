<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="Error.jsp"%>
<!DOCTYPE html>
 <html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
   <title>Home page</title>
    </head>
     <body style > <br/><br/><br/><br/><br/>
      <center> <h2> 
      <% String a=session.getAttribute("username").toString(); 
      out.println("Welcome"+a); %> </h2> <br/> <br/> <br/><br/><br/>
      <form action="demo_form.asp">
  <input type="button" value="VIEW ADMIN SETTINGS" onclick="msg()"><br>
</form> 
<script>
function msg(){
alert("You are not the admin");
}
</script>
<br/> <br/>
      <a href="Logout.jsp"><input type="button" value="Logout"><br></a> </center>
      
       </body>
        </html>

