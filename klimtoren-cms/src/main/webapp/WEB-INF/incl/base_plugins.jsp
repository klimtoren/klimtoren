<%-- 
    Document   : base_scripts
    Created on : 21-jun-2014, 11:52:27
    Author     : karl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--================================================== -->	

<!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
<!--<script src="/js/plugin/pace/pace.min.js"></script>-->

<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script> if (!window.jQuery) {
        document.write('<script src="js/libs/jquery-2.0.2.min.js"><\/script>');
    }</script>

<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script> if (!window.jQuery.ui) {
        document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');
    }</script>

<!-- JS TOUCH : include this plugin for mobile drag / drop touch events 		
<script src="js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> -->

<!-- i18n support -->
<script src="/js/plugin/jquery-i18n/jquery-i18n.js"></script>

<!-- BOOTSTRAP JS -->		
<script src="/js/bootstrap/bootstrap.min.js"></script>

<!-- JQUERY VALIDATE -->
<script src="/js/plugin/jquery-validate/jquery.validate.min.js"></script>

<!-- JQUERY MASKED INPUT -->
<script src="/js/plugin/masked-input/jquery.maskedinput.min.js"></script>

<!--[if IE 8]>
    <h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>      
<![endif]-->

<!-- MAIN APP JS FILE -->
<script src="/js/app.js"></script>