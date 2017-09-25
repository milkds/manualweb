<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<html>
<head>
    <title>Manuals</title>
    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>
    <a href="../../index.jsp">Back to main menu</a>

<br/>
<br/>
    <h1>Manual List</h1>

<c:if test="${!empty listManuals.manuals}">
    <c:url var="filterAction" value="filter"/>
    <table class="tg">
        <tr>
            <td width="80"></td>
            <td width="120">
                <form:form action="${filterAction}" commandName="filter">
                    <form:select path="manualBrand">
                    <form:option value="NONE" label="--- Select ---"/>
                    <form:options items="${listManuals.brands}" />
                    </form:select>
                    <input type="submit" value="<spring:message text="Filter"/>"/>
                </form:form>

            </td>
            <td width="120">
                <form:form action="${filterAction}" commandName="filter">
                    <form:select path="manualPart">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${listManuals.parts}" />
                    </form:select>
                    <input type="submit" value="<spring:message text="Filter"/>"/>
                </form:form>
            </td>
            <td width="120">
                <form:form action="${filterAction}" commandName="filter">
                    <form:select path="manualDoctype">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${listManuals.docTypes}" />
                    </form:select>
                    <input type="submit" value="<spring:message text="Filter"/>"/>
                </form:form>
            </td>
            <td width="120">
                <form:form action="${filterAction}" commandName="filter">
                    <form:select path="manualCategory">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${listManuals.categories}" />
                    </form:select>
                    <input type="submit" value="<spring:message text="Filter"/>"/>
                </form:form>
            </td>
            <td width="240"></td>
            <td width="60"></td>
            <td width="60"></td>
        </tr>
       <tr>
            <td width="80">ID</td>
            <td width="120">Brand</td>
            <td width="120">Part Number</td>
            <td width="120">Doc Type</td>
            <td width="120">Category</td>
            <td width="240">Description</td>
            <td width="60"></td>
            <td width="60"></td>
       </tr>
        <c:forEach items="${listManuals.manuals}" var ="manual">
            <tr>
                <td><a href="/manualdata/${manual.id}" target="_blank">${manual.id}</a></td>
                <td>${manual.brand}</td>
                <td>${manual.partNo}</td>
                <td>${manual.docType}</td>
                <td>${manual.category}</td>
                <td>${manual.description}</td>
                <td><a href="<c:url value="/edit/${manual.id}"/>">Edit</a></td>
                <td><a href="<c:url value="/remove/${manual.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
    <br><br/>
<hl>Add Manual</hl>
    <br><br/>
<c:url var="addAction" value="/manuals/add"/>
    <form:form action="${addAction}" commandName="manual">
    <table>
        <c:if test="${!empty manual.brand}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="brand">
                    <spring:message text="Brand"/>
                </form:label>
            </td>
            <td>
                <form:input path="brand"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="partNo">
                    <spring:message text="Part Number"/>
                </form:label>
            </td>
            <td>
                <form:input path="partNo"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="docType">
                    <spring:message text="Document Type"/>
                </form:label>
            </td>
            <td>
                <form:input path="docType"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="category">
                    <spring:message text="Category"/>
                </form:label>
            </td>
            <td>
                <form:input path="category"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="description">
                    <spring:message text="Description"/>
                </form:label>
            </td>
            <td>
                <form:input path="description"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty manual.brand}">
                    <input type="submit" value="<spring:message text="Edit Manual"/>"/>
                </c:if>
                <c:if test="${empty manual.brand}">
                    <input type="submit" value="<spring:message text="Add Manual"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
    </form:form>
</body>
</html>
