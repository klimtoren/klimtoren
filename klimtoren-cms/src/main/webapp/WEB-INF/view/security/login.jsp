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
                            <form action="" id="login-form" method="post" class="smart-form client-form">
                                <header>
                                    <fmt:message key="security.signin" />
                                </header>

                                <fieldset>

                                    <section>
                                        <label class="label"><fmt:message key="security.username" /></label>
                                        <label class="input"> <i class="icon-append fa fa-user"></i>
                                            <input type="email" name="username">
                                            <b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i> <fmt:message key="security.tooltip.enterusername" /></b></label>
                                    </section>

                                    <section>
                                        <label class="label"><fmt:message key="security.password" /></label>
                                        <label class="input"> <i class="icon-append fa fa-lock"></i>
                                            <input type="password" name="password">
                                            <b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> <fmt:message key="security.tooltip.password" /></b> </label>
                                        <div class="note">
                                            <a href="/forgotpassword"><fmt:message key="security.forgotpassword" /></a>
                                        </div>
                                    </section>

                                    <section>
                                        <label class="checkbox">
                                            <input type="checkbox" name="remember" checked="">
                                            <i></i><fmt:message key="security.rememberme" /></label>
                                    </section>
                                </fieldset>
                                <footer>
                                    <button type="submit" class="btn btn-primary">
                                        <fmt:message key="security.signin" />
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

        <jsp:include page="../../incl/base_plugins.jsp" />

        <script type="text/javascript">
            $(document).ready(function() { runAllForms() });

            $(function() {
                // Validation
                $("#login-form").validate({
                    // Rules for form validation
                    rules: {
                        email: {
                            required: true,
                            email: true
                        },
                        password: {
                            required: true,
                            minlength: 3,
                            maxlength: 20
                        }
                    },
                    // Messages for form validation
                    messages: {
                        email: {
                            required: '<fmt:message key="security.enteremail" />',
                            email: '<fmt:message key="security.entervalidemail" />'
                        },
                        password: {
                            required: '<fmt:message key="security.tooltip.password" />'
                        }
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });
            });
        </script>

    </body>
</html>