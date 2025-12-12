<%-- 
    Document   : Menu
    Created on : 05-dic-2024, 22:46:11
    Author     : ckan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String usuario = (String) sesion.getAttribute("usuario");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menú de Opciones</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            .menu-container {
                width: 400px;
                margin: 100px auto;
                text-align: center;
                padding: 20px;
                border: 1px solid #ccc;
                box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            }
            .menu-header {
                margin-bottom: 20px;
            }
            .menu-buttons {
                background-image: url('resources/download.jpg');
                background-repeat: no-repeat;
                background-size: cover;
                background-position: center;
                padding: 20px;
                border-radius: 10px;
            }
            .menu-buttons button {
                width: 80%;
                padding: 15px;
                margin: 10px 0;
                border: none;
                background-color: #3364ff;
                color: white;
                font-size: 16px;
                cursor: pointer;
                border-radius: 4px;
            }
            .menu-buttons button:hover {
                background-color: #555;
            }
        </style>
    </head>
    <body>
        <div class="menu-container">
            <div class="menu-header">
                <h2>Menú de Opciones</h2>
            </div>
            <div class="menu-buttons">
                <form action="realizarDeposito.jsp" method="get">
                    <button type="submit">Realizar Operación</button>
                </form>
                <form action="realizarTransferencia.jsp" method="get">
                    <button type="submit">Realizar Transferencia</button>
                </form>
                <form action="consultarMovimientos.jsp" method="get">
                    <button type="submit">Consultar Movimientos</button>
                </form>
                <form action="login.jsp" method="post">
                    <button type="submit">Salir</button>
                </form>
            </div>
        </div>
    </body>
</html>

