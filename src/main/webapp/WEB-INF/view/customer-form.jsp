<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>

<html>
    <head>
        <title>Save customer</title>

        <link type="text/css" rel="stylesheet"
        href="${pageContext.request.contextPath}/resources/css/style.css" />
        <link type="text/css" rel="stylesheet"
                href="${pageContext.request.contextPath}/resources/css/add-customer-style.css" />
    </head>
    <body>
        <div id ="wrapper">
            <div id = "header"></div>
            <h2>CRM - Customer Relationship Manager</h2>
        </div>

        <div id = "container">
            <h3>Save customer</h3>

            <form:form action="saveCustomer" modelAttribute="customer" method="POST">

                <!-- need to associate this data with customer id -->
                <form:hidden path="id"/>
                <table>
                    <tbody>
                        <tr>
                            <td><label>First name</label></td>
                            <td><form:input path="firstName"/><td>
                        </tr>
                        <tr>
                            <td><label>Last name</label></td>
                            <td><form:input path="lastName"/><td>
                        </tr>
                        <tr>
                            <td><label>email</label></td>
                            <td><form:input path="Email"/><td>
                        </tr>
                        <tr>
                            <td><label></label></td>
                            <td><input type="submit" value="Save" class="save"/></td>
                        </tr>
                    </tbody>
                </table>
            </form:form>

            <div style="clear; both;"></div>
            <p>
                <a href="${pageContext.request.contextPath}/customer/list">Back to List</a>
            </p>

        </div>

    </body>
</html>