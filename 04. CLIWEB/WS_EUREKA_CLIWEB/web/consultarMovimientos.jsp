<%-- 
    Document   : consultarMovimientos
    Created on : 05-dic-2024, 22:57:30
    Author     : ckan1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="ec.edu.monster.controlador.EurekaController" %>
<%@page import="java.util.List" %>
<%@page import="ec.edu.monster.ws.Movimiento" %>
<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String usuario = (String) sesion.getAttribute("usuario");

    String cuenta = request.getParameter("cuenta");
    List<Movimiento> movimientos = null;
    if (cuenta != null) {
        EurekaController controlador = new EurekaController();
        movimientos = controlador.traerMovimientos(cuenta);
        request.setAttribute("movimientos", movimientos);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consulta de Movimientos</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            body {
                background-image: url('resources/fondo.jpg');
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
                height: 100vh;
                margin: 0;
                font-family: Arial, sans-serif;
            }
            .movimientos-container {
                width: 800px;
                margin: 100px auto;
                padding: 20px;
                background-color: rgba(255, 255, 255, 0.9);
                box-shadow: 0px 0px 15px 0px rgba(0,0,0,0.2);
            }
            .form-group {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
                text-align: left;
            }
            label {
                margin-right: 10px;
                font-weight: bold;
            }
            input[type="text"] {
                flex: 1;
                padding: 10px;
                margin-right: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            button[type="submit"] {
                background-color: #3364ff;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button[type="submit"]:hover {
                background-color: #555;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            table, th, td {
                border: 1px solid #333;
                text-align: center;
                padding: 10px;
            }
            th {
                background-color: #333;
                color: white;
            }
            .back-button {
                position: absolute;
                top: 20px;
                right: 20px;
                background-color: #3364ff;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .back-button:hover {
                background-color: #555;
            }
        </style>
    </head>
    <body>
        <button class="back-button" onclick="window.location.href='Menu.jsp'">Volver</button>
        <div class="movimientos-container">
            <h2>Consulta de Movimientos</h2>
            <form action="consultarMovimientos.jsp" method="get">
                <div class="form-group">
                    <label for="cuenta">Cuenta:</label>
                    <input type="text" name="cuenta" id="cuenta" required>
                    <button type="submit">Consultar</button>
                </div>
            </form>
            <c:if test="${not empty requestScope.movimientos}">
                <table>
                    <thead>
                        <tr>
                            <th>CUENTA</th>
                            <th>NRO. MOV</th>
                            <th>FECHA</th>
                            <th>TIPO</th>
                            <th>ACCIÓN</th>
                            <th>IMPORTE</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mov" items="${requestScope.movimientos}">
                            <tr>
                                <td><c:out value="${mov.cuenta}"/></td>
                                <td><c:out value="${mov.nromov}"/></td>
                                <td><c:out value="${mov.fecha}"/></td>
                                <td><c:out value="${mov.tipo}"/></td>
                                <td><c:out value="${mov.accion}"/></td>
                                <td><c:out value="${mov.importe}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
