<%-- 
    Document   : header
    Created on : 21-jun-2014, 18:48:43
    Author     : karl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<header id="header">

    <div id="logo-group">
        <span id="logo"> <img src="/img/logo.png" alt="SmartAdmin"> </span>
    </div>

    <span id="extr-page-header-space"> <span class="hidden-mobile"><fmt:message key="security.needanaccount"/></span> <a href="/register" class="btn btn-danger"><fmt:message key="security.createaccount" /></a> </span>

</header>