<%-- 
    Document   : login
    Created on : 05-dic-2024, 22:26:07
    Author     : ckan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="ec.edu.monster.controlador.EurekaController" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%
    // Check if login form is submitted
    String usuario = request.getParameter("usuario");
    String contrasena = request.getParameter("contrasena");
    if (usuario != null && contrasena != null) {
        EurekaController controlador = new EurekaController();
        boolean autenticado = controlador.autenticarUsuario(usuario, contrasena);
        
        if (autenticado) {
            // Iniciar sesión y redirigir al dashboard o página principal
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            response.sendRedirect("Menu.jsp");
            return;
        } else {
            // Almacenar el error como un atributo de solicitud
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de Sesión</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            .login-container {
                width: 600px;
                margin: 100px auto;
                padding: 20px;
                border: 1px solid #ccc;
                box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .form-container {
                width: 60%;
            }
            .form-group {
                margin-bottom: 15px;
                text-align: left;
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }
            input[type="text"], input[type="password"] {
                width: calc(90% - 20px);
                padding: 10px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            button[type="submit"] {
                width: calc(90% - 20px);
                background-color: #3364ff;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button[type="submit"]:hover {
                background-color: #3364ff;
            }
            .error-message {
                color: red;
                font-weight: bold;
            }
            .logo-container {
                width: 35%;
                text-align: center;
            }
            .logo-container img {
                width: 150px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <div class="form-container">
                <h2>Inicio de Sesión</h2>
                <form action="login.jsp" method="post">
                    <div class="form-group">
                        <label for="usuario">Usuario:</label>
                        <input type="text" name="usuario" id="usuario" required>
                    </div>
                    <div class="form-group">
                        <label for="contrasena">Contraseña:</label>
                        <input type="password" name="contrasena" id="contrasena" required>
                    </div>
                    <button type="submit">Ingresar</button>
                    <c:if test="${not empty requestScope.error}">
                        <p class="error-message">${requestScope.error}</p>
                    </c:if>
                </form>
            </div>
            <div class="logo-container">
                <img src="resources/download.jpg" alt="ESPE Logo">
            </div>
        </div>
    </body>
</html>

