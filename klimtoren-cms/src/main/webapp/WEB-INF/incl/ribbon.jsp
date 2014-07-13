<%-- 
    Document   : ribbon
    Created on : 21-jun-2014, 18:30:37
    Author     : karl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- RIBBON -->
<div id="ribbon">

    <span class="ribbon-button-alignment"> 
        <span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh" 
              rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> <fmt:message key="resetwidgetsettings" />" data-html="true" data-reset-msg="<fmt:message key="resetwidgetsettings_msg" />"><i class="fa fa-refresh"></i></span> 
    </span>

    <!-- breadcrumb -->
    <ol class="breadcrumb">
        <!-- This is auto generated -->
    </ol>
    <!-- end breadcrumb -->

    <!-- You can also add more buttons to the
    ribbon for further usability

    Example below:

    <span class="ribbon-button-alignment pull-right">
    <span id="search" class="btn btn-ribbon hidden-xs" data-title="search"><i class="fa-grid"></i> Change Grid</span>
    <span id="add" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa-plus"></i> Add</span>
    <span id="search" class="btn btn-ribbon" data-title="search"><i class="fa-search"></i> <span class="hidden-mobile">Search</span></span>
    </span> -->

</div>
<!-- END RIBBON -->