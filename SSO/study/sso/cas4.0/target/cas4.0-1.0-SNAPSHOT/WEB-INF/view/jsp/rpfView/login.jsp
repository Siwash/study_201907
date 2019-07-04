<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
.form-bg{
    background: #00b4ef;
}
.form-horizontal{
    background: #fff;
    padding-bottom: 40px;
    border-radius: 15px;
    text-align: center;
}
.form-horizontal .heading{
    display: block;
    font-size: 35px;
    font-weight: 700;
    padding: 35px 0;
    border-bottom: 1px solid #f0f0f0;
    margin-bottom: 30px;
}
.form-horizontal .form-group{
    padding: 0 40px;
    margin: 0 0 25px 0;
    position: relative;
}
.form-horizontal .form-control{
    background: #f0f0f0;
    border: none;
    border-radius: 20px;
    box-shadow: none;
    padding: 0 20px 0 45px;
    height: 40px;
    transition: all 0.3s ease 0s;
}
.form-horizontal .form-control:focus{
    background: #e0e0e0;
    box-shadow: none;
    outline: 0 none;
}
.form-horizontal .form-group i{
    position: absolute;
    top: 12px;
    left: 60px;
    font-size: 17px;
    color: #c8c8c8;
    transition : all 0.5s ease 0s;
}
.form-horizontal .form-control:focus + i{
    color: #00b4ef;
}
.form-horizontal .fa-question-circle{
    display: inline-block;
    position: absolute;
    top: 12px;
    right: 60px;
    font-size: 20px;
    color: #808080;
    transition: all 0.5s ease 0s;
}
.form-horizontal .fa-question-circle:hover{
    color: #000;
}
.form-horizontal .main-checkbox{
    float: left;
    width: 20px;
    height: 20px;
    background: #11a3fc;
    border-radius: 50%;
    position: relative;
    margin: 5px 0 0 5px;
    border: 1px solid #11a3fc;
}
.form-horizontal .main-checkbox label{
    width: 20px;
    height: 20px;
    position: absolute;
    top: 0;
    left: 0;
    cursor: pointer;
}
.form-horizontal .main-checkbox label:after{
    content: "";
    width: 10px;
    height: 5px;
    position: absolute;
    top: 5px;
    left: 4px;
    border: 3px solid #fff;
    border-top: none;
    border-right: none;
    background: transparent;
    opacity: 0;
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
}
.form-horizontal .main-checkbox input[type=checkbox]{
    visibility: hidden;
}
.form-horizontal .main-checkbox input[type=checkbox]:checked + label:after{
    opacity: 1;
}
.form-horizontal .text{
    float: left;
    margin-left: 7px;
    line-height: 20px;
    padding-top: 5px;
    text-transform: capitalize;
}
.form-horizontal .btn{
    float: right;
    font-size: 14px;
    color: #fff;
    background: #00b4ef;
    border-radius: 30px;
    padding: 10px 25px;
    border: none;
    text-transform: capitalize;
    transition: all 0.5s ease 0s;
}
@media only screen and (max-width: 479px){
    .form-horizontal .form-group{
        padding: 0 25px;
    }
    .form-horizontal .form-group i{
        left: 45px;
    }
    .form-horizontal .btn{
        padding: 10px 20px;
    }
}
</style>
<body>
<%--<form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">--%>

	<%--<form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false" />--%>

	<%--<h2><spring:message code="screen.welcome.instructions" /></h2>--%>

	<%--<section class="row">--%>
		<%--<label for="username"><spring:message code="screen.welcome.label.netid" /></label>--%>
		<%--<c:choose>--%>
			<%--<c:when test="${not empty sessionScope.openIdLocalId}">--%>
				<%--<strong><c:out value="${sessionScope.openIdLocalId}" /></strong>--%>
				<%--<input type="hidden" id="username" name="username" value="<c:out value="${sessionScope.openIdLocalId}" />" />--%>
			<%--</c:when>--%>
			<%--<c:otherwise>--%>
				<%--<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />--%>
				<%--<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="off" htmlEscape="true" />--%>
			<%--</c:otherwise>--%>
		<%--</c:choose>--%>
	<%--</section>--%>

	<%--<section class="row">--%>
		<%--<label for="password"><spring:message code="screen.welcome.label.password" /></label>--%>
			<%--&lt;%&ndash;--%>
            <%--NOTE: Certain browsers will offer the option of caching passwords for a user.  There is a non-standard attribute,--%>
            <%--"autocomplete" that when set to "off" will tell certain browsers not to prompt to cache credentials.  For more--%>
            <%--information, see the following web page:--%>
            <%--http://www.technofundo.com/tech/web/ie_autocomplete.html--%>
            <%--&ndash;%&gt;--%>
		<%--<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />--%>
		<%--<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />--%>
	<%--</section>--%>

	<%--<section class="row check">--%>
		<%--<input id="warn" name="warn" value="true" tabindex="3" accesskey="<spring:message code="screen.welcome.label.warn.accesskey" />" type="checkbox" />--%>
		<%--<label for="warn"><spring:message code="screen.welcome.label.warn" /></label>--%>
	<%--</section>--%>

	<%--<section class="row btn-row">--%>
		<%--<input type="hidden" name="lt" value="${loginTicket}" />--%>
		<%--<input type="hidden" name="execution" value="${flowExecutionKey}" />--%>
		<%--<input type="hidden" name="_eventId" value="submit" />--%>

		<%--<input class="btn-submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />--%>
		<%--<input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset" />--%>
	<%--</section>--%>
<%--</form:form>--%>
	<div class="container">
		<div class="row">
			<div class="col-md-offset-3 col-md-6">
				<form:form method="post"  cssClass="form-horizontal" id="fm1" commandName="${commandName}" htmlEscape="true">
				<%--<form class="form-horizontal" action="/login" method="post">--%>
					<span class="heading">用户登录</span>
					<div class="form-group">
						<input type="text" name="username" class="form-control" id="inputEmail3"
							   placeholder="用户名或电子邮件"> <i class="fa fa-user"></i>
					</div>
					<div class="form-group help">
						<input type="password" name="password" class="form-control" id="inputPassword3"
							   placeholder="密　码"> <i class="fa fa-lock"></i> <a href="#"
																				class="fa fa-question-circle"></a>
					</div>
					<div class="form-group">
						<div class="main-checkbox">
							<input type="checkbox" value="None" id="checkbox1" name="check" />
							<label for="checkbox1"></label>
						</div>
						<span class="text">Remember me</span>
						<input type="hidden" name="lt" value="${loginTicket}" />
						<input type="hidden" name="execution" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
						<button type="submit" class="btn btn-default">登录</button>
					</div>
				<%--</form>--%>
				</form:form>
			</div>
		</div>
	</div>
<%--<form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">--%>
	<%--<div class="row">--%>
		<%--<div class="col-lg-12">--%>
			<%--<div class="form-group row margin-bot5 margin-top5" style="height:30px">--%>
				<%--<div class="col-lg-12 margin-bot5 margin-top5">--%>
					<%--<font color="red"></i><form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false" /></font>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group margin-bot20 has-feedback">--%>
				<%--<span class="fa fa-user form-control-feedback"></span>--%>
				<%--<form:input id="username" name="username" class="form-control login-user" placeholder="请输入用户名" value="" tabindex="1" path="username" autocomplete="off" htmlEscape="true"/>--%>
			<%--</div>--%>
			<%--<div class="form-group margin-bot20 has-feedback">--%>
				<%--<span class="fa fa-key form-control-feedback"></span>--%>
				<%--<form:password id="password" name="password" class="form-control login-pwd" placeholder="请输入密码" value="" tabindex="2" path="password" autocomplete="off" htmlEscape="true" />--%>
			<%--</div>--%>
			<%--<div class="form-group ">--%>
				<%--<input type="hidden" name="lt" value="${loginTicket}" />--%>
				<%--<input type="hidden" name="execution" value="${flowExecutionKey}" />--%>
				<%--<input type="hidden" name="_eventId" value="submit" />--%>
				<%--<input class="btn btn-info w100 btn-lg" id="login" value="<spring:message code="screen.welcome.button.login" />" type="submit" value="登录"></input>--%>
			<%--</div>--%>
		<%--</div>--%>
	<%--</div>--%>
<%--</form:form>--%>
</body>
</html>