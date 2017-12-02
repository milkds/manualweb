<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<%--This page shows single manual. For now only fields, in future here will be also shown manual body in pdf/doc.--%>
<html>
<head>
    <title>Manual Data</title>
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
    <h1>Manual Details</h1>

    <table class="tg">
        <tr>
            <td width="80">ID</td>
            <td width="120">Brand</td>
            <td width="120">Part Number</td>
            <td width="120">Doc Type</td>
            <td width="120">Category</td>
            <td width="240">Description</td>
        </tr>
        <tr>
            <td>${manual.id}</td>
            <td>${manual.brand}</td>
            <td>${manual.partNo}</td>
            <td>${manual.docType}</td>
            <td>${manual.category}</td>
            <td>${manual.description}</td>
        </tr>
    </table>

</body>
</html>
