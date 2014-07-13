<%-- 
    Document   : forgotpassword
    Created on : 21-jun-2014, 18:42:24
    Author     : karl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en-us" id="extr-page">
    <head>
        <jsp:include page="../../incl/meta.jsp" />
        <jsp:include page="../../incl/stylesheets.jsp" />
    </head>

    <body class="animated fadeInDown">
        <jsp:include page="header.jsp" />

        <div id="main" role="main">

            <!-- MAIN CONTENT -->
            <div id="content" class="container">

                <div class="row">
                    <jsp:include page="static_side.jsp" />

                    <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
                        <div class="well no-padding">
                            <form action="/login" id="login-form" class="smart-form client-form">
                                <header>
                                    <fmt:message key="security.forgotpassword" />
                                </header>

                                <fieldset>

                                    <section>
                                        <label class="label"><fmt:message key="security.enteremail" /></label>
                                        <label class="input"> <i class="icon-append fa fa-envelope"></i>
                                            <input type="email" name="email">
                                            <b class="tooltip tooltip-top-right"><i class="fa fa-envelope txt-color-teal"></i> <fmt:message key="security.tooltip.enteremail" /></b></label>
                                    </section>
                                    <section>
                                        <span class="timeline-seperator text-center text-primary"> <span class="font-sm"><fmt:message key="or" /></span> 
                                    </section>
                                    <section>
                                        <label class="label"><fmt:message key="security.enterusername" /></label>
                                        <label class="input"> <i class="icon-append fa fa-user"></i>
                                            <input type="text" name="username">
                                            <b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i> <fmt:message key="security.tooltip.enterusername" /></b> </label>
                                        <div class="note">
                                            <a href="/login"><fmt:message key="security.iremember" /></a>
                                        </div>
                                    </section>

                                </fieldset>
                                <footer>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-refresh"></i> <fmt:message key="security.resetpassword" />
                                    </button>
                                </footer>
                            </form>

                        </div>

                                        <h5 class="text-center"> - <fmt:message key="security.orsignin" /> -</h5>

                        <ul class="list-inline text-center">
                            <li>
                                <a href="javascript:void(0);" class="btn btn-primary btn-circle"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li>
                                <a href="javascript:void(0);" class="btn btn-info btn-circle"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li>
                                <a href="javascript:void(0);" class="btn btn-warning btn-circle"><i class="fa fa-linkedin"></i></a>
                            </li>
                        </ul>

                    </div>
                </div>
            </div>

        </div>

        <!--================================================== -->	

        <jsp:include page="../../incl/base_plugins.jsp" />

        <!-- MAIN APP JS FILE -->
        <script src="js/app.min.js"></script>

        <script type="text/javascript">
        </script>

    </body>
</html>